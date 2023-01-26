package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;

public class UpperCaseNumbersAndSpecialCharsValidator implements Validator{

    @Override
    public boolean isValid(char letter, int position, int nInput) {
        return Character.valueOf(letter).toString().matches("[A-Z0-9!\"#$%&'()*+,-.:;<=>?@^_{|}/\\[\\]`~]") && ((position + 1) % nInput == 0);
    }

    @Override
    public ProcessType getProcessType() {
        return ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;
    }
}
