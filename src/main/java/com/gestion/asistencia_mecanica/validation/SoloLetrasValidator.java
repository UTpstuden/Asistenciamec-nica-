package com.gestion.asistencia_mecanica.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SoloLetrasValidator implements ConstraintValidator<SoloLetras, String> {

    private static final String REGEX = "^[A-Za-zÁÉÍÓÚáéíóúÑñ '\\-]+$";
    // Explicación del regex: Permite letras mayúsculas y minúsculas, acentos,
    // espacios, guiones y apóstrofes.

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true; // Si quieres que @NotBlank lo maneje
        return value.matches(REGEX);
    }
}
