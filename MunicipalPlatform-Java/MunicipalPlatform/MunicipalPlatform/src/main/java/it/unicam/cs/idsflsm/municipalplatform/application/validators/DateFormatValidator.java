package it.unicam.cs.idsflsm.municipalplatform.application.validators;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidDateFormat;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
/**
 * Represents a JSON validator for date field
 */
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
            Date d = Date.fromString(s);
            return !d.beforeThan(Date.toDate(LocalDate.now()));
        } catch (InvalidDateFormatException e1) {
            return false;
        }
    }
}
