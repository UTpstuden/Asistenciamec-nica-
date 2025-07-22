package com.gestion.asistencia_mecanica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gestion.asistencia_mecanica.dto.login.LoginRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "inicio";
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/contactanos")
    public String contactanos() {
        return "contactanos";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // Agregar objeto user al modelo para Thymeleaf
        model.addAttribute("user", new LoginRequest());
        return "login";
    }

}
