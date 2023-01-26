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

    @Test
    public void shouldReturnAProcessedStringUsingN1OnlyUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl";
        int nInput = 1;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "ITCLNA";
        MapEntry<Character,Integer> entryExceptedI = MapEntry.entry('I',1);
        MapEntry<Character,Integer> entryExceptedT = MapEntry.entry('T',1);
        MapEntry<Character,Integer> entryExceptedC = MapEntry.entry('C',1);
        MapEntry<Character,Integer> entryExceptedL = MapEntry.entry('L',1);
        MapEntry<Character,Integer> entryExceptedN = MapEntry.entry('N',1);
        MapEntry<Character,Integer> entryExceptedA = MapEntry.entry('A',1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(6);
        assertThat(response.getLettersGroup()).contains(entryExceptedI, entryExceptedT, entryExceptedC, entryExceptedL, entryExceptedN,entryExceptedA);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN2OnlyUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl";
        int nInput = 2;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "TLN";

        MapEntry<Character,Integer> entryExceptedT = MapEntry.entry('T',1);
        MapEntry<Character,Integer> entryExceptedL = MapEntry.entry('L',1);
        MapEntry<Character,Integer> entryExceptedN = MapEntry.entry('N',1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(3);
        assertThat(response.getLettersGroup()).contains(entryExceptedT, entryExceptedL, entryExceptedN);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN3OnlyUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl";
        int nInput = 3;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "CNA";
        MapEntry<Character,Integer> entryExceptedC = MapEntry.entry('C',1);
        MapEntry<Character,Integer> entryExceptedN = MapEntry.entry('N',1);
        MapEntry<Character,Integer> entryExceptedA = MapEntry.entry('A',1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(3);
        assertThat(response.getLettersGroup()).contains(entryExceptedC, entryExceptedN,entryExceptedA);
    }
}