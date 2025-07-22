package com.gestion.asistencia_mecanica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.asistencia_mecanica.dto.registration.ConductorRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.registration.MecanicoRegistrationDTO;
import com.gestion.asistencia_mecanica.service.interfaces.ConductorService;
import com.gestion.asistencia_mecanica.service.interfaces.MecanicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/registro")
@RequiredArgsConstructor
public class RegistroController {

    private final ConductorService conductorService;
    private final MecanicoService mecanicoService;

    @GetMapping("/conductor")
    public String formConductor(Model model) {
        model.addAttribute("conductor", new ConductorRegistrationDTO());
        return "registroconductor";
    }

    @PostMapping("/conductor")
    public String registrarConductor(@ModelAttribute @Valid ConductorRegistrationDTO dto,
            BindingResult br,
            RedirectAttributes ra) {
        if (br.hasErrors())
            return "registroconductor";
        conductorService.registerConductor(dto);
        ra.addFlashAttribute("success", "Conductor registrado");
        return "redirect:/login";
    }

    @GetMapping("/mecanico")
    public String formMecanico(Model model) {
        model.addAttribute("mecanico", new MecanicoRegistrationDTO());
        return "registromecanico";
    }

    @PostMapping("/mecanico")
    public String registrarMecanico(@ModelAttribute @Valid MecanicoRegistrationDTO dto,
            BindingResult br,
            RedirectAttributes ra) {
        if (br.hasErrors())
            return "registromecanico";
        mecanicoService.registerMecanico(dto);
        ra.addFlashAttribute("success", "Mecánico registrado");
        return "redirect:/login";
    }
}