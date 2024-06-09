package it.unicam.cs.idsflsm.municipalplatform.application.validators;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidLatitude;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidLongitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
/**
 * Represents a JSON validator for latitude field
 */
public class LatitudeValidator implements ConstraintValidator<ValidLatitude, String> {
    @Override
    public void initialize(ValidLatitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            double latitude = Double.parseDouble(s);
            return latitude >= -90 && latitude <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
