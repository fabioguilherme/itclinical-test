package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;

public class AllUpperCaseValidator implements Validator{
    @Override
    public boolean isValid(char letter, int position, int nInput) {
        return Character.valueOf(letter).toString().matches("[A-Z]") && ((position + 1) % nInput == 0);
    }

    @Override
    public ProcessType getProcessType() {
        return ProcessType.ONLY_UPPERCASE;
    }
}
