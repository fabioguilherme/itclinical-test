package org.itclinicaltest.services;


import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;

public class TextProcessorServiceImpl implements TextProcessorService {


    @Override
    public TextProcessorResponse processText(String text, int inputN, ProcessType type) {

        if (isInputInvalid(text, inputN))
                return TextProcessorResponse.Builder.withEmptyResponse().build();

        return null;
    }


    private boolean isInputInvalid(String text, int inputN) {
        return text.length() < inputN || inputN == -1;
    }
}
