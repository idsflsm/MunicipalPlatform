package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.DateFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Annotation for JSON validation, used to validate date values (dd/MM/yyyy)
 */
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
    String message() default "ExpiryDate field is not valid. Expected format: dd/MM/yyyy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
