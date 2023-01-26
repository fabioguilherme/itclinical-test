package org.itclinicaltest.services;


import org.itclinicaltest.error.ValidatorNotFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.itclinicaltest.validators.Validator;
import org.itclinicaltest.validators.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextProcessorServiceImpl implements TextProcessorService {

    private final ValidatorFactory validatorFactory;

    public TextProcessorServiceImpl(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Override
    public TextProcessorResponse processText(String text, int inputN, ProcessType type) throws ValidatorNotFoundException {

        if (isInputInvalid(text, inputN))
            return TextProcessorResponse.Builder.withEmptyResponse().build();

        Optional<Validator> optionalValidator = this.validatorFactory.getValidator(type);

        if (!optionalValidator.isPresent())
            throw new ValidatorNotFoundException("Validator not found for type: " + type.name());

        String processedString = processString(text, inputN, optionalValidator.get());
        Map<Character, Integer> letterGroups = groupLetters(processedString);

        return TextProcessorResponse.Builder.with().outputString(processedString).lettersGroup(letterGroups).build();
    }

    private Map<Character, Integer> groupLetters(String processedString) {

        Map<Character, Integer> result = new HashMap<>();

        String countingString = processedString;

        int position = 0;

        while (countingString.length() != 0) {

            position = 0;
            char target = countingString.charAt(position);

            int numberOfLetters = countingString.replaceAll("[^" + target + "]", "").length();

            result.put(Character.valueOf(target), numberOfLetters);

            countingString = countingString.replaceAll("[" + target + "]", "");
        }

        return result;
    }

    private String processString(String text, int inputN, Validator validator) {
        return IntStream.range(0, text.length()).filter(position -> validator.isValid(text.charAt(position), position, inputN)).mapToObj(position -> Character.valueOf(text.charAt(position)).toString()).collect(Collectors.joining());
    }


    private boolean isInputInvalid(String text, int inputN) {
        return text.length() < inputN || inputN == -1;
    }
}
