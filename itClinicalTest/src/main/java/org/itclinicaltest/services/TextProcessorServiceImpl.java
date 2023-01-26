package org.itclinicaltest.services;


import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.itclinicaltest.validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextProcessorServiceImpl implements TextProcessorService {

    private final Validator uppercaseValidator;

    public TextProcessorServiceImpl(Validator uppercaseValidator) {
        this.uppercaseValidator = uppercaseValidator;
    }

    @Override
    public TextProcessorResponse processText(String text, int inputN, ProcessType type) {

        if (isInputInvalid(text, inputN))
                return TextProcessorResponse.Builder.withEmptyResponse().build();

        String processedString = processString(text, inputN);
        Map<Character, Integer> letterGroups = groupLetters(processedString);

        return TextProcessorResponse.Builder.with().outputString(processedString).lettersGroup(letterGroups).build();
    }

    private Map<Character, Integer> groupLetters(String processedString) {

        Map<Character, Integer> result = new HashMap<>();

        String countingString = processedString;

        int position = 0;

        while(countingString.length() != 0) {

            position = 0;
            char target = countingString.charAt(position);

            int numberOfLetters = countingString.replaceAll("[^" + target + "]","").length();

            result.put(Character.valueOf(target), numberOfLetters);

            countingString = countingString.replaceAll("[" + target + "]","");
        }

        return result;
    }

    private String processString(String text, int inputN) {
        return IntStream.range(0, text.length()).filter(position -> uppercaseValidator.isValid(text.charAt(position), position, inputN)).mapToObj(position -> Character.valueOf(text.charAt(position)).toString()).collect(Collectors.joining());
    }


    private boolean isInputInvalid(String text, int inputN) {
        return text.length() < inputN || inputN == -1;
    }
}
