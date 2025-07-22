package com.gestion.asistencia_mecanica.service.interfaces;

import java.util.List;

import com.gestion.asistencia_mecanica.models.SolicitudServicio;
import com.gestion.asistencia_mecanica.dto.registration.MecanicoRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.MecanicoResponseDTO;

public interface MecanicoService {

    boolean updateSolicitudEstado(Integer idSolicitud, String nuevoEstado);


    MecanicoResponseDTO registerMecanico(MecanicoRegistrationDTO dto);

    List<SolicitudServicio> getAssignedRequests(String mecanicoId);

    List<SolicitudServicio> getServiceHistory(String mecanicoId);

    MecanicoResponseDTO getStatistics(String mecanicoId);

    MecanicoResponseDTO getProfile(String mecanicoId);

    void updateProfile(String mecanicoId, MecanicoResponseDTO profileData);

    boolean getDisponibilidad(String mecanicoId);

    void setDisponibilidad(String mecanicoId, boolean disponibilidad);
}
