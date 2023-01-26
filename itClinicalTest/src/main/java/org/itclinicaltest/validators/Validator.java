package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;

public interface Validator {

    public boolean isValid(char letter, int position, int nInput);

    public ProcessType getProcessType();
}
