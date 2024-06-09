package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;
/**
 * Represents a JSON validator for strings, such as name, description, author etc.
 */
public class OnlyLettersStringValidator implements ConstraintValidator<OnlyLettersString, String> {
    @Override
    public void initialize(OnlyLettersString constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return Pattern.compile("[a-zA-Z\\s]+")
                .matcher(s)
                .matches();
    }
}
