package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Username field is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
