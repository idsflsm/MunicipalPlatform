package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidDateFormat;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {
    @Override
    public void initialize(ValidDateFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Date.fromString(s);
            return true;
        } catch (InvalidDateFormatException e1) {
            return false;
        }
    }
}
