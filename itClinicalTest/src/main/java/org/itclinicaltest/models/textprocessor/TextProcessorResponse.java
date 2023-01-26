package org.itclinicaltest.models.textprocessor;

import java.util.Map;
import java.util.stream.Collectors;

public class TextProcessorResponse {

    private final String outputString;
    private final Map<Character, Integer> lettersGroup;

    private TextProcessorResponse(Builder builder) {
        this.outputString = builder.outputString;
        this.lettersGroup = builder.lettersGroup;
    }

    public String getOutputString() {
        return outputString;
    }

    public Map<Character, Integer> getLettersGroup() {
        return lettersGroup;
    }

    @Override
    public String toString() {
        return "outputString = '" + outputString + "\n" +
                lettersGroup.entrySet().stream().map(entry -> entry.getKey() + " = " + entry.getValue() + "\n").collect(Collectors.joining());
    }

    public static final class Builder {
        private String outputString;
        private Map<Character, Integer> lettersGroup;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public static Builder withEmptyResponse() {

            Builder builder = new Builder();
            builder.outputString = "";

            return builder;
        }

        public Builder outputString(String outputString) {
            this.outputString = outputString;
            return this;
        }

        public Builder lettersGroup(Map<Character, Integer> lettersGroup) {
            this.lettersGroup = lettersGroup;
            return this;
        }

        public TextProcessorResponse build() {
            return new TextProcessorResponse(this);
        }
    }
}
