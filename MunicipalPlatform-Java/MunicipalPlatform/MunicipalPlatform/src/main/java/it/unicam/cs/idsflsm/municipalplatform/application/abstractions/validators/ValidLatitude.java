package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;

import it.unicam.cs.idsflsm.municipalplatform.application.validators.LatitudeValidator;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.LongitudeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLatitude {
    String message() default "Latitude field is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
