package com.gestion.asistencia_mecanica.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.asistencia_mecanica.models.User;
import com.gestion.asistencia_mecanica.service.interfaces.CustomUserDetails;
import com.gestion.asistencia_mecanica.service.interfaces.ConductorService;
import com.gestion.asistencia_mecanica.service.interfaces.MecanicoService;
import com.gestion.asistencia_mecanica.models.Vehiculo;
import com.gestion.asistencia_mecanica.models.SolicitudServicio;
import com.gestion.asistencia_mecanica.dto.response.ConductorResponseDTO;
import com.gestion.asistencia_mecanica.dto.response.MecanicoResponseDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping
public class DashboardController {

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private MecanicoService mecanicoService;

    @GetMapping("/dashboardconductor")
    public String showConductorDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            model.addAttribute("usuario", user);

            // Fetch and add service history
            List<SolicitudServicio> serviceHistory = conductorService.getServiceHistory(user.getUserId().toString());

            // Enhance serviceHistory with mechanic names
            List<Object> enhancedServiceHistory = new java.util.ArrayList<>();
            for (SolicitudServicio solicitud : serviceHistory) {
                String mecanicoId = solicitud.getIdMecanico();
                String mecanicoName = "Desconocido";
                if (mecanicoId != null && !mecanicoId.isEmpty()) {
                    var mecanicoProfile = mecanicoService.getProfile(mecanicoId);
                    if (mecanicoProfile != null) {
                        mecanicoName = mecanicoProfile.getNombres() + " " + mecanicoProfile.getApellidos();
                    }
                }
                java.util.Map<String, Object> map = new java.util.HashMap<>();
                map.put("solicitud", solicitud);
                map.put("mecanicoName", mecanicoName);
                enhancedServiceHistory.add(map);
            }
            model.addAttribute("serviceHistory", enhancedServiceHistory);

            // Fetch and add vehicle data
            Vehiculo vehiculo = conductorService.getVehicleData(user.getUserId().toString());
            model.addAttribute("vehiculo", vehiculo);

            // Fetch and add statistics
            ConductorResponseDTO estadisticas = conductorService.getStatistics(user.getUserId().toString());
            model.addAttribute("estadisticas", estadisticas);

            // Fetch and add profile data
            ConductorResponseDTO perfil = conductorService.getProfile(user.getUserId().toString());
            model.addAttribute("perfil", perfil);

            return "dashboardconductor";
        } else {
            return "redirect:/login?error=notAuthenticated";
        }
    }

    @PostMapping("/dashboardconductor/solicitar-asistencia")
    @ResponseBody
    public String solicitarAsistencia(@RequestParam String descripcion, @RequestParam String lugar,
            @RequestParam(required = false) Double latitud, @RequestParam(required = false) Double longitud) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            conductorService.requestAssistance(user.getUserId().toString(), descripcion, lugar, latitud, longitud);
            return "OK";
        }
        return "ERROR";
    }

    @PostMapping("/dashboardconductor/solicitar-calificacion")
    @ResponseBody
    public String solicitarCalificacion(@RequestParam Integer idSolicitud, @RequestParam Integer calificacion) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            boolean updated = conductorService.updateCalificacion(idSolicitud, calificacion);
            if (updated) {
                return "OK";
            } else {
                return "ERROR";
            }
        }
        return "ERROR";
    }

    @GetMapping("/dashboardmecanico")
    public String showMecanicoDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("usuario", user);

            // Fetch and add assigned requests
            List<SolicitudServicio> assignedRequests = mecanicoService.getAssignedRequests(user.getUserId().toString());

            // Enhance assignedRequests with conductor profile and price
            List<Object> enhancedRequests = new java.util.ArrayList<>();
            for (SolicitudServicio solicitud : assignedRequests) {
                ConductorResponseDTO conductorProfile = conductorService.getProfile(solicitud.getIdCliente());
                String conductorName = (conductorProfile != null) ? conductorProfile.getNombres() + " " + conductorProfile.getApellidos() : "Desconocido";

                // Extract price from descripcion string, e.g. "Cambio de llantas (S/50)"
                String descripcion = solicitud.getDescripcion();
                String priceStr = "";
                if (descripcion != null && descripcion.contains("S/")) {
                    int start = descripcion.indexOf("S/") + 2;
                    int end = descripcion.indexOf(")", start);
                    if (end > start) {
                        priceStr = descripcion.substring(start, end).trim();
                    }
                }

                java.util.Map<String, Object> map = new java.util.HashMap<>();
                map.put("solicitud", solicitud);
                map.put("conductorName", conductorName);
                map.put("conductorProfile", conductorProfile);
                map.put("price", priceStr);
                enhancedRequests.add(map);
            }
            model.addAttribute("assignedRequests", enhancedRequests);

            // Fetch and add service history
            List<SolicitudServicio> serviceHistory = mecanicoService.getServiceHistory(user.getUserId().toString());
            model.addAttribute("serviceHistory", serviceHistory);

            // Fetch and add statistics
            MecanicoResponseDTO estadisticas = mecanicoService.getStatistics(user.getUserId().toString());
            model.addAttribute("estadisticas", estadisticas);

            // Fetch and add profile data
            MecanicoResponseDTO perfil = mecanicoService.getProfile(user.getUserId().toString());
            model.addAttribute("perfil", perfil);

            // Add mecanico object for template usage
            if (perfil == null) {
                perfil = MecanicoResponseDTO.builder().build();
            }
            model.addAttribute("mecanico", perfil);

            // Add taller object for template usage
            if (perfil != null && perfil.getTaller() != null) {
                model.addAttribute("taller", perfil.getTaller());
            } else {
                model.addAttribute("taller", new com.gestion.asistencia_mecanica.dto.response.TallerResponseDTO());
            }

            // Fetch and add availability
            boolean disponibilidad = mecanicoService.getDisponibilidad(user.getUserId().toString());
            model.addAttribute("disponibilidad", disponibilidad);

            return "dashboardmecanico";
        } else {
            return "redirect:/login?error=notAuthenticated";
        }
    }

    @PostMapping("/dashboardmecanico/disponibilidad")
    @ResponseBody
    public String updateDisponibilidad(@RequestParam boolean disponibilidad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            mecanicoService.setDisponibilidad(user.getUserId().toString(), disponibilidad);
            return "OK";
        }
        return "ERROR";
    }

    @PostMapping("/dashboardmecanico/solicitud/{id}/aceptar")
    @ResponseBody
    public String aceptarSolicitud(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            // Update the status of the request to "En Proceso"
            boolean updated = mecanicoService.updateSolicitudEstado(id, "En Proceso");
            if (updated) {
                return "OK";
            } else {
                return "ERROR";
            }
        }
        return "ERROR";
    }

    @PostMapping("/dashboardmecanico/solicitud/{id}/finalizar")
    @ResponseBody
    public String finalizarSolicitud(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            // Update the status of the request to "Finalizado"
            boolean updated = mecanicoService.updateSolicitudEstado(id, "Finalizado");
            if (updated) {
                return "OK";
            } else {
                return "ERROR";
            }
        }
        return "ERROR";
    }

    @PostMapping("/dashboardmecanico/solicitud/{id}/rechazar")
    @ResponseBody
    public String rechazarSolicitud(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            // Update the status of the request to "Rechazado"
            boolean updated = mecanicoService.updateSolicitudEstado(id, "Rechazado");
            if (updated) {
                return "OK";
            } else {
                return "ERROR";
            }
        }
        return "ERROR";
    }
}
