package com.gestion.asistencia_mecanica.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class HtmlExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        log.warn("Error: {}", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "error/404"; // o podrías redirigir a una vista específica
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        log.error("Error inesperado: ", ex);
        model.addAttribute("error", "Ha ocurrido un error inesperado.");
        return "error/general"; // crea esta vista en templates/error
    }
}
