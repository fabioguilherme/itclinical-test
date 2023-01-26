package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;

import java.util.Optional;

public interface ValidatorFactory {

    Optional<Validator> getValidator(ProcessType type);
}
