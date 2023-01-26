package org.itclinicaltest.models.textprocessor;

import java.util.Map;

public class TextProcessorResponse {

    private final String outputString;
    private final Map<String, Integer> lettersGroup;

    private TextProcessorResponse(Builder builder) {
        this.outputString = builder.outputString;
        this.lettersGroup = builder.lettersGroup;
    }

    public String getOutputString() {
        return outputString;
    }

    public Map<String, Integer> getLettersGroup() {
        return lettersGroup;
    }

    @Override
    public String toString() {
        return "TextProcessorResponse{" +
                "outputString='" + outputString + '\'' +
                ", lettersGroup=" + lettersGroup +
                '}';
    }

    public static final class Builder {
        private String outputString;
        private Map<String, Integer> lettersGroup;

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

        public Builder lettersGroup(Map<String, Integer> lettersGroup) {
            this.lettersGroup = lettersGroup;
            return this;
        }

        public TextProcessorResponse build() {
            return new TextProcessorResponse(this);
        }
    }
}
