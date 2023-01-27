package org.itclinicaltest.models.textprocessor;

import java.util.HashMap;
import java.util.Map;

public class TextProcessorResponseFixture {

    public static TextProcessorResponse.Builder getResponse() {

        String processedString = "AAABB";
        Map<Character, Integer> lettersGroup = new HashMap<>();
        lettersGroup.put('A', 3);
        lettersGroup.put('B', 2);

        TextProcessorResponse.Builder builder = TextProcessorResponse.Builder.with();
        builder.outputString(processedString);
        builder.lettersGroup(lettersGroup);

        return builder;
    }
}
