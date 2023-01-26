package org.itclinicaltest.services;

import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextProcessorServiceImplTest {

    private TextProcessorService service;

    @Before
    public void setUp() throws Exception {
        this.service = new TextProcessorServiceImpl();
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
}