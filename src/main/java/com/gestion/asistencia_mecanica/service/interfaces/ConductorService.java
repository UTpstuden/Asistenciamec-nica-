package com.gestion.asistencia_mecanica.service.interfaces;

import java.util.List;

import com.gestion.asistencia_mecanica.models.SolicitudServicio;
import com.gestion.asistencia_mecanica.models.Vehiculo;
import com.gestion.asistencia_mecanica.dto.registration.ConductorRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.ConductorResponseDTO;

public interface ConductorService {

    ConductorResponseDTO registerConductor(ConductorRegistrationDTO dto);

    List<SolicitudServicio> getServiceHistory(String conductorId);

    Vehiculo getVehicleData(String conductorId);

    void requestAssistance(String conductorId, String descripcion, String lugar, Double latitud, Double longitud);

    ConductorResponseDTO getStatistics(String conductorId);

    ConductorResponseDTO getProfile(String conductorId);

    void updateProfile(String conductorId, ConductorResponseDTO profileData);

    boolean updateCalificacion(Integer idSolicitud, Integer calificacion);
}
