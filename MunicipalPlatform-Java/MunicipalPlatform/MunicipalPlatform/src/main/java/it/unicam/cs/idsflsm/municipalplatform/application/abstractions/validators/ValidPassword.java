package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Annotation for JSON validation, used to validate password strings
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Contains at least one uppercase letter" +
            ", contains at least one lowercase letter" +
            ", contains at least one digit" +
            ", contains at least one non alphanumeric character" +
            ", has a length of 8 or more characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
