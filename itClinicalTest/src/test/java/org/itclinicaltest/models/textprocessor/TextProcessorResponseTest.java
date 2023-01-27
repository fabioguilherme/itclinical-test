package org.itclinicaltest.models.textprocessor;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TextProcessorResponseTest {

    @Test
    public void canBuildAResponse() {
        // Given
        String processedString = "BBBAA";
        Map<Character, Integer> groups = new HashMap<Character, Integer>();
        groups.put('B', 3);
        groups.put('A', 2);

        // When
        TextProcessorResponse response = TextProcessorResponse.Builder.with().outputString(processedString).lettersGroup(groups).build();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isEqualTo(processedString);
        assertThat(response.getLettersGroup()).isEqualTo(groups);

        assertThat(response.toString()).isEqualTo("outputString = 'BBBAA'\n" +
                "A = 2\n" +
                "B = 3\n");

    }

    @Test
    public void canBuildAEmptyResponse() {
        // Given

        // When
        TextProcessorResponse response = TextProcessorResponse.Builder.withEmptyResponse().build();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isEqualTo("");
        assertThat(response.getLettersGroup()).isNotNull();
        assertThat(response.getLettersGroup()).hasSize(0);

        assertThat(response.toString()).isEqualTo("outputString = ''\n");
    }
}