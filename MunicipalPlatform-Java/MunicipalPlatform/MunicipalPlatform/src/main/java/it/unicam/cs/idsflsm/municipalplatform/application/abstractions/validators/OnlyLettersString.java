package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.DateFormatValidator;
import it.unicam.cs.idsflsm.municipalplatform.application.validators.OnlyLettersStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = OnlyLettersStringValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLettersString {
    String message() default "String field does not contain only letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
