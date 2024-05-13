package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Starts with one or more alphanumeric characters, " +
            "dots (.), percent signs (%), plus signs (+), or hyphens (-)." +
            " Followed by the at symbol (@) and one or more alphanumeric characters, " +
            "dots (.), or hyphens (-)." +
            " Ends with a dot (.) followed by two or more alphabetic characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
