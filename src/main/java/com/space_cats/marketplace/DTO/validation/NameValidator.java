package com.space_cats.marketplace.DTO.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidNames, String> {

    private static final List<String> KEYWORDS = List.of("nebula", "celestial", "supernova", "interstellar",
            "gravitational", "comet", "quasar", "astrological", "dark matter", "planetary", "luminous", "stellar",
            "exoplanet", "photon", "galaxy", "ionized", "elliptical", "asteroid", "cosmic ray", "star", "event horizon");

    private static final Pattern TITLE_KEYWORD_PATTERN = Pattern.compile(
            String.format("(?i)\\b(%s)\\b", String.join("|", KEYWORDS))
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return TITLE_KEYWORD_PATTERN.matcher(value).find();
    }
}
