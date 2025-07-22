package com.gestion.asistencia_mecanica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número o carácter especial";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}