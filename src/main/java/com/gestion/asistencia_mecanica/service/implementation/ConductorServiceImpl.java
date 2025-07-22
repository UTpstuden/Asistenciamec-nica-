package com.gestion.asistencia_mecanica.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.asistencia_mecanica.models.SolicitudServicio;
import com.gestion.asistencia_mecanica.models.Vehiculo;
import com.gestion.asistencia_mecanica.models.Conductor;
import com.gestion.asistencia_mecanica.models.Rol;
import com.gestion.asistencia_mecanica.models.Mecanico;
import com.gestion.asistencia_mecanica.dto.mapper.ConductorMapper;
import com.gestion.asistencia_mecanica.dto.registration.ConductorRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.ConductorResponseDTO;
import com.gestion.asistencia_mecanica.repository.ConductorRepository;
import com.gestion.asistencia_mecanica.repository.MecanicoRepository;
import com.gestion.asistencia_mecanica.repository.RolRepository;
import com.gestion.asistencia_mecanica.repository.SolicitudServicioRepository;
import com.gestion.asistencia_mecanica.repository.VehiculoRepository;
import com.gestion.asistencia_mecanica.service.interfaces.ConductorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ConductorServiceImpl implements ConductorService {

    private final ConductorRepository conductorRepo;
    private final ConductorMapper conductorMapper;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Override
    public ConductorResponseDTO registerConductor(ConductorRegistrationDTO dto) {

        // 1. Mapear el DTO a entidad Conductor
        Conductor conductor = conductorMapper.toEntity(dto);

        // 2. Asignar el rol CONDUCTOR
        conductor.setRol(
                rolRepository.findByNombre(Rol.RolNombre.ROLE_CONDUCTOR)
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        // Verificar si ya existe un mecánico con el mismo email o DNI
        if (conductorRepo.existsByEmail(dto.getEmail()) || conductorRepo.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico o DNI");
        }
        conductor.setPassword(passwordEncoder.encode(dto.getPassword()));
        // 3. Guardar el conductor
        Conductor saved = conductorRepo.save(conductor);

        // 4. Devolver DTO de respuesta
        return conductorMapper.toResponseDTO(saved);
    }


    @Override
    public List<SolicitudServicio> getServiceHistory(String conductorId) {
        log.info("Obteniendo historial de servicios para conductor {}", conductorId);
        return solicitudServicioRepository.findByIdCliente(conductorId);
    }

    @Override
    public Vehiculo getVehicleData(String conductorId) {
        log.info("Obteniendo datos del vehículo para conductor {}", conductorId);
        Optional<Conductor> conductorOpt = conductorRepository.findById(Integer.valueOf(conductorId));
        if (conductorOpt.isPresent()) {
            Conductor conductor = conductorOpt.get();
            if (conductor.getVehiculo() != null) {
                return conductor.getVehiculo();
            }
        }
        // Return a default Vehiculo object to avoid null in template
        return new Vehiculo();
    }

    @Override
    public void requestAssistance(String conductorId, String descripcion, String lugar, Double latitud,
            Double longitud) {
        log.info("Solicitando asistencia para conductor {}, descripción: {}, lugar: {}", conductorId, descripcion,
                lugar);
        SolicitudServicio solicitud = new SolicitudServicio();
        solicitud.setIdCliente(conductorId);

        // Find available mechanics by distrito (location)
        List<Mecanico> availableMechanics = mecanicoRepository.findAvailableByDistrito(lugar);
        if (availableMechanics != null && !availableMechanics.isEmpty()) {
            // Assign the first available mechanic (could be improved with better selection
            // logic)
            solicitud.setIdMecanico(availableMechanics.get(0).getUserId().toString());
        } else {
            // Throw exception if no mechanic found
            throw new com.gestion.asistencia_mecanica.exception.ResourceNotFoundException(
                    "No se encontró ningún mecánico disponible en la zona");
        }

        solicitud.setDescripcion(descripcion);
        solicitud.setEstado("Pendiente");
        solicitud.setFechaCreacion(System.currentTimeMillis());
        solicitud.setLugar(lugar);
        solicitud.setLatitud(latitud);
        solicitud.setLongitud(longitud);
        solicitudServicioRepository.save(solicitud);
        log.info("Solicitud de asistencia guardada para conductor {}", conductorId);
    }

    @Override
    public ConductorResponseDTO getStatistics(String conductorId) {
        // Implement statistics calculation for the conductor
        List<SolicitudServicio> services = solicitudServicioRepository.findByIdCliente(conductorId);
        int totalServices = services.size();
        int completedServices = (int) services.stream()
                .filter(s -> "Finalizado".equalsIgnoreCase(s.getEstado()))
                .count();

        // Calculate average rating of completed services with rating
        double averageRating = services.stream()
                .filter(s -> "Finalizado".equalsIgnoreCase(s.getEstado()) && s.getCalificacion() != null)
                .mapToInt(SolicitudServicio::getCalificacion)
                .average()
                .orElse(0.0);

        return ConductorResponseDTO.builder()
                .serviciosSolicitados(totalServices)
                .serviciosCompletados(completedServices)
                .calificacionPromedio(averageRating)
                .build();
    }

    @Override
    public boolean updateCalificacion(Integer idSolicitud, Integer calificacion) {
        Optional<SolicitudServicio> solicitudOpt = solicitudServicioRepository.findById(idSolicitud);
        if (solicitudOpt.isPresent()) {
            SolicitudServicio solicitud = solicitudOpt.get();
            solicitud.setCalificacion(calificacion);
            solicitudServicioRepository.save(solicitud);
            return true;
        }
        return false;
    }

    @Override
    public ConductorResponseDTO getProfile(String conductorId) {
        Optional<Conductor> conductorOpt = conductorRepository.findById(Integer.valueOf(conductorId));
        if (conductorOpt.isPresent()) {
            Conductor conductor = conductorOpt.get();
            Vehiculo vehiculo = getVehicleData(conductorId);
            return ConductorResponseDTO.builder()
                    .nombres(conductor.getNombres())
                    .apellidos(conductor.getApellidos())
                    .telefono(conductor.getTelefono())
                    .vehiculo(conductorMapper.toVehiculoResponseDTO(vehiculo))
                    .build();
        }
        return null;
    }

    @Override
    public void updateProfile(String conductorId, ConductorResponseDTO profileData) {
        Optional<Conductor> conductorOpt = conductorRepository.findById(Integer.valueOf(conductorId));
        if (conductorOpt.isPresent()) {
            Conductor conductor = conductorOpt.get();
            conductor.setNombres(profileData.getNombres());
            conductor.setApellidos(profileData.getApellidos());
            // Email field update skipped as UserResponseDTO does not have email
            conductor.setTelefono(profileData.getTelefono());
            // Update other fields as needed
            conductorRepository.save(conductor);
        }
    }

}
