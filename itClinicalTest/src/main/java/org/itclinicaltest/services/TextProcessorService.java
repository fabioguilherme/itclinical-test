package org.itclinicaltest.services;


import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;

public interface TextProcessorService {
    public TextProcessorResponse processText(String text, int input, ProcessType type);
}
