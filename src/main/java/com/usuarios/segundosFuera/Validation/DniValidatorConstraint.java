package com.usuarios.segundosFuera.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DniValidatorConstraint implements ConstraintValidator<ValidDni, String> {

    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private static final Pattern PATTERN = Pattern.compile("^\\d{8}[A-Z]$");

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || dni.isBlank()) {
            return false;
        }
        String normalized = dni.trim().toUpperCase();
        if (!PATTERN.matcher(normalized).matches()) {
            return false;
        }
        int number = Integer.parseInt(normalized.substring(0, 8));
        char expected = LETTERS.charAt(number % 23);
        return normalized.charAt(8) == expected;
    }
}