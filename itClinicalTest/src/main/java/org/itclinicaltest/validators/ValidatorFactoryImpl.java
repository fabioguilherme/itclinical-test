package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidatorFactoryImpl implements ValidatorFactory {

    private final Map<ProcessType, Validator> registeredValidators;

    public ValidatorFactoryImpl() {
         Map<ProcessType, Validator> map = new HashMap<>();

        Validator upperCaseValidator = new AllUpperCaseValidator();
        map.put(upperCaseValidator.getProcessType(), upperCaseValidator);

        this.registeredValidators = Collections.unmodifiableMap(map);
    }

    @Override
    public Optional<Validator> getValidator(ProcessType type) {

        if (!this.registeredValidators.containsKey(type))
            return Optional.empty();

        return Optional.of(this.registeredValidators.get(type));
    }
}
