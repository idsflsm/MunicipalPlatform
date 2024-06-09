package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidLongitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Represents a JSON validator for longitude field
 */
public class LongitudeValidator implements ConstraintValidator<ValidLongitude, String> {
    @Override
    public void initialize(ValidLongitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            double longitude = Double.parseDouble(s);
            return longitude >= -180 && longitude <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
