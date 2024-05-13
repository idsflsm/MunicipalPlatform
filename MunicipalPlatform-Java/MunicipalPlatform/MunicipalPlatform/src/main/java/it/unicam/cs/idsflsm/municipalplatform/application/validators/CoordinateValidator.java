package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidCoordinate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class CoordinateValidator implements ConstraintValidator<ValidCoordinate, String> {
    @Override
    public void initialize(ValidCoordinate constraintAnnotation) {
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
