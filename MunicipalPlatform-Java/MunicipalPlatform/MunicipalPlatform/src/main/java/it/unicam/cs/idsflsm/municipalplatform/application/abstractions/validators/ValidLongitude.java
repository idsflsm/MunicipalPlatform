package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.LongitudeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLongitude {
    String message() default "Longitude field is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
