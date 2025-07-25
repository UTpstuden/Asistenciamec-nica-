package com.gestion.asistencia_mecanica.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = SoloLetrasValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SoloLetras {

    String message() default "El campo solo debe contener letras, espacios y/o guiones";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
