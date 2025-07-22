package com.gestion.asistencia_mecanica.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestion.asistencia_mecanica.models.SolicitudServicio;
import com.gestion.asistencia_mecanica.models.Mecanico;
import com.gestion.asistencia_mecanica.models.Rol;
import com.gestion.asistencia_mecanica.dto.mapper.MecanicoMapper;
import com.gestion.asistencia_mecanica.dto.registration.MecanicoRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.MecanicoResponseDTO;
import com.gestion.asistencia_mecanica.dto.response.TallerResponseDTO;
import com.gestion.asistencia_mecanica.repository.MecanicoRepository;
import com.gestion.asistencia_mecanica.repository.RolRepository;
import com.gestion.asistencia_mecanica.repository.SolicitudServicioRepository;
import com.gestion.asistencia_mecanica.repository.TallerRepository;
import com.gestion.asistencia_mecanica.service.interfaces.MecanicoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MecanicoServiceImpl implements MecanicoService {


    private final MecanicoRepository mecanicoRepo;
    private final MecanicoMapper mecanicoMapper;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @Autowired
    private TallerRepository tallerRepository;

    @Override
    public List<SolicitudServicio> getAssignedRequests(String mecanicoId) {
        log.info("Obteniendo solicitudes asignadas para mecánico {}", mecanicoId);
        return solicitudServicioRepository.findByIdMecanico(mecanicoId);
    }

    @Override
    public List<SolicitudServicio> getServiceHistory(String mecanicoId) {
        log.info("Obteniendo historial de servicios para mecánico {}", mecanicoId);
        return solicitudServicioRepository.findByIdMecanico(mecanicoId);    
    }

    @Override
    public MecanicoResponseDTO getStatistics(String mecanicoId) {
        List<SolicitudServicio> services = solicitudServicioRepository.findByIdMecanico(mecanicoId);
        long totalServices = services.size();
        long completedServices = services.stream()
            .filter(s -> "completado".equalsIgnoreCase(s.getEstado()))
            .count();

        // Calculate services completed this month
        long currentTimeMillis = System.currentTimeMillis();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(currentTimeMillis);
        int currentMonth = cal.get(java.util.Calendar.MONTH);
        int currentYear = cal.get(java.util.Calendar.YEAR);

        long servicesThisMonth = services.stream()
            .filter(s -> {
                java.util.Calendar serviceCal = java.util.Calendar.getInstance();
                serviceCal.setTimeInMillis(s.getFechaCreacion());
                int serviceMonth = serviceCal.get(java.util.Calendar.MONTH);
                int serviceYear = serviceCal.get(java.util.Calendar.YEAR);
                return "completado".equalsIgnoreCase(s.getEstado()) &&
                       serviceMonth == currentMonth &&
                       serviceYear == currentYear;
            })
            .count();

        // Calculate ingresosHoy and calificacionSum for finalized services today
        java.util.Calendar todayCal = java.util.Calendar.getInstance();
        int todayDay = todayCal.get(java.util.Calendar.DAY_OF_MONTH);
        int todayMonth = todayCal.get(java.util.Calendar.MONTH);
        int todayYear = todayCal.get(java.util.Calendar.YEAR);

        java.math.BigDecimal ingresosHoy = java.math.BigDecimal.ZERO;
        java.math.BigDecimal calificacionSum = java.math.BigDecimal.ZERO;

        for (SolicitudServicio s : services) {
            java.util.Calendar serviceCal = java.util.Calendar.getInstance();
            serviceCal.setTimeInMillis(s.getFechaCreacion());
            int serviceDay = serviceCal.get(java.util.Calendar.DAY_OF_MONTH);
            int serviceMonth = serviceCal.get(java.util.Calendar.MONTH);
            int serviceYear = serviceCal.get(java.util.Calendar.YEAR);

            if ("Finalizado".equalsIgnoreCase(s.getEstado()) &&
                serviceDay == todayDay &&
                serviceMonth == todayMonth &&
                serviceYear == todayYear) {

                // Parse price from description string e.g. "Cambio de llantas (S/50)"
                String descripcion = s.getDescripcion();
                if (descripcion != null && descripcion.contains("S/")) {
                    int start = descripcion.indexOf("S/") + 2;
                    int end = descripcion.indexOf(")", start);
                    if (end > start) {
                        String priceStr = descripcion.substring(start, end).trim();
                        try {
                            // Replace comma with dot for decimal if present
                            String normalizedPriceStr = priceStr.replace(",", ".");
                            java.math.BigDecimal price = new java.math.BigDecimal(normalizedPriceStr);
                            ingresosHoy = ingresosHoy.add(price);
                        } catch (NumberFormatException e) {
                            // Ignore parse errors
                        }
                    }
                }

                // Sum ratings
                if (s.getCalificacion() != null) {
                    calificacionSum = calificacionSum.add(new java.math.BigDecimal(s.getCalificacion()));
                }
            }
        }

        // Average service time calculation is not possible without end time data
        Double averageServiceTime = null;

        // Calculate average rating
        long ratedServicesCount = services.stream()
            .filter(s -> s.getCalificacion() != null)
            .count();

        java.math.BigDecimal calificacionPromedio = java.math.BigDecimal.ZERO;
        if (ratedServicesCount > 0) {
            calificacionPromedio = calificacionSum.divide(new java.math.BigDecimal(ratedServicesCount), 2, java.math.RoundingMode.HALF_UP);
        }

        // Build DTO with extended stats
        return MecanicoResponseDTO.builder()
            .totalServicios((int) totalServices)
            .serviciosCompletados((int) completedServices)
            .serviciosEsteMes((int) servicesThisMonth)
            .ingresosHoy(ingresosHoy)
            .calificacionSum(calificacionSum)
            .tiempoPromedioAtencion(averageServiceTime)
            .nombres(null)
            .apellidos(null)
            .telefono(null)
            .especialidad(null)
            .calificacionPromedio(calificacionPromedio)
            .taller(null)
            .build();
    }

    @Override
    public MecanicoResponseDTO getProfile(String mecanicoId) {
        Optional<Mecanico> mecanicoOpt = mecanicoRepository.findById(Integer.valueOf(mecanicoId));
        if (mecanicoOpt.isPresent()) {
            Mecanico mecanico = mecanicoOpt.get();

            TallerResponseDTO tallerDTO = null;
            if (mecanico.getTaller() != null) {
                var taller = mecanico.getTaller();
                tallerDTO = new TallerResponseDTO();
                tallerDTO.setNombre(taller.getNombre());
                tallerDTO.setDireccion(taller.getDireccion());
                tallerDTO.setDistrito(taller.getDistrito());
                tallerDTO.setRadioCobertura(taller.getRadioCobertura() != null ? taller.getRadioCobertura().toString() : null);
                tallerDTO.setTipoServicio(taller.getTipoServicio() != null ? taller.getTipoServicio().name() : null);
            }

            return MecanicoResponseDTO.builder()
                .nombres(mecanico.getNombres())
                .apellidos(mecanico.getApellidos())
                .telefono(mecanico.getTelefono())
                .especialidad(mecanico.getEspecialidad())
                .experiencia(mecanico.getExperiencia())
                .calificacionPromedio(mecanico.getCalificacionPromedio())
                .taller(tallerDTO)
                .build();
        }
        return null;
    }

    @Override
    public void updateProfile(String mecanicoId, MecanicoResponseDTO profileData) {
        Optional<Mecanico> mecanicoOpt = mecanicoRepository.findById(Integer.valueOf(mecanicoId));
        if (mecanicoOpt.isPresent()) {
            Mecanico mecanico = mecanicoOpt.get();
            mecanico.setNombres(profileData.getNombres());
            mecanico.setApellidos(profileData.getApellidos());
            mecanico.setTelefono(profileData.getTelefono());
            mecanico.setEspecialidad(profileData.getEspecialidad());
            mecanicoRepository.save(mecanico);
        }
    }

    @Override
    public boolean getDisponibilidad(String mecanicoId) {
        Optional<Mecanico> mecanicoOpt = mecanicoRepository.findById(Integer.valueOf(mecanicoId));
        return mecanicoOpt.map(Mecanico::isDisponibilidad).orElse(false);
    }

    @Override
    public void setDisponibilidad(String mecanicoId, boolean disponibilidad) {
        Optional<Mecanico> mecanicoOpt = mecanicoRepository.findById(Integer.valueOf(mecanicoId));
        if (mecanicoOpt.isPresent()) {
            Mecanico mecanico = mecanicoOpt.get();
            mecanico.setDisponibilidad(disponibilidad);
            mecanicoRepository.save(mecanico);
        }
    }

    @Override
    public MecanicoResponseDTO registerMecanico(MecanicoRegistrationDTO dto) {
        // 1. Mapear el DTO a entidad Mecanico
        Mecanico mecanico = mecanicoMapper.toEntity(dto);

        // 2. Asignar el rol Mecanico
        mecanico.setRol(
                rolRepository.findByNombre(Rol.RolNombre.ROLE_MECANICO)
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        // Verificar si ya existe un mecánico con el mismo email o DNI
        if (mecanicoRepo.existsByEmail(dto.getEmail()) || mecanicoRepo.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico o DNI");
        }
        mecanico.setPassword(passwordEncoder.encode(dto.getPassword())); // Aquí deberías encriptar la contraseña
        // 3. Guardar el Mecanico
        Mecanico saved = mecanicoRepo.save(mecanico);

        // 4. Retornar el DTO de respuesta
        return mecanicoMapper.toResponseDTO(saved);
    }

    @Override
    public boolean updateSolicitudEstado(Integer idSolicitud, String nuevoEstado) {
        Optional<SolicitudServicio> solicitudOpt = solicitudServicioRepository.findById(idSolicitud);
        if (solicitudOpt.isPresent()) {
            SolicitudServicio solicitud = solicitudOpt.get();
            solicitud.setEstado(nuevoEstado);
            solicitudServicioRepository.save(solicitud);
            return true;
        }
        return false;
    }
}
