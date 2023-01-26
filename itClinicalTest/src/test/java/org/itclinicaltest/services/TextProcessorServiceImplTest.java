package org.itclinicaltest.services;

import org.assertj.core.data.MapEntry;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.itclinicaltest.validators.AllUpperCaseValidator;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class TextProcessorServiceImplTest {

    private TextProcessorService service;

    @Before
    public void setUp() throws Exception {
        this.service = new TextProcessorServiceImpl(new AllUpperCaseValidator());
    }

    @Test
    public void shouldReturnEmptyStringIfNisBiggerThanInputString() {
        // Given
        String inputString = "textString";
        int nInput = 100;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isEmpty();
        assertThat(response.getLettersGroup()).isNull();
    }

    @Test
    public void shouldReturnEmptyStringIfNIsEqualToMinusOne() {
        // Given
        String inputString = "textString";
        int nInput = -1;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isEmpty();
        assertThat(response.getLettersGroup()).isNull();
    }

    @Test
    public void shouldReturnAProcessedStringUsingN2AndOnlyUpperCaseTypeAndAStringAsInputs() {
        // Given
        String inputString = "ABBnBnnnBBBB";
        int nInput = 2;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "BBB";
        MapEntry<Character,Integer> entryExcepted = MapEntry.entry('B',3);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(1);
        assertThat(response.getLettersGroup()).contains(entryExcepted);
    }
}