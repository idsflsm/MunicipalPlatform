package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.CoordinateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = CoordinateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoordinate {
    String message() default "Coordinate field is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
