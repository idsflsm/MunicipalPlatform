package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
/**
 * Represents a JSON validator for username (email) field
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );
    }
}
