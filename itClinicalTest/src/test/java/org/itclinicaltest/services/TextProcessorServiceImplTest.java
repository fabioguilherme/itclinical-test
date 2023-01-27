package org.itclinicaltest.services;

import org.assertj.core.data.MapEntry;
import org.itclinicaltest.error.ValidatorNotFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.itclinicaltest.validators.ValidatorFactory;
import org.itclinicaltest.validators.ValidatorFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextProcessorServiceImplTest {

    private TextProcessorService service;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
        this.service = new TextProcessorServiceImpl(validatorFactory);
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
        assertThat(response.getLettersGroup()).isEmpty();
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
        assertThat(response.getLettersGroup()).isEmpty();
    }


    @Test
    public void shouldThrowExceptionWhenThereIsNoValidator() {
        // Given
        String inputString = "textString";
        int nInput = 2;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        ValidatorFactory mockValidatorFactory = mock(ValidatorFactory.class);
        TextProcessorServiceImpl serviceWithMockFactory = new TextProcessorServiceImpl(mockValidatorFactory);

        when(mockValidatorFactory.getValidator(typeInput)).thenReturn(Optional.empty());

        // When
        assertThatExceptionOfType(ValidatorNotFoundException.class).isThrownBy(() -> serviceWithMockFactory.processText(inputString, nInput, typeInput)).withMessage("Validator not found for type: ONLY_UPPERCASE");

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
        MapEntry<Character, Integer> entryExcepted = MapEntry.entry('B', 3);

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
        MapEntry<Character, Integer> entryExceptedI = MapEntry.entry('I', 1);
        MapEntry<Character, Integer> entryExceptedT = MapEntry.entry('T', 1);
        MapEntry<Character, Integer> entryExceptedC = MapEntry.entry('C', 1);
        MapEntry<Character, Integer> entryExceptedL = MapEntry.entry('L', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedA = MapEntry.entry('A', 1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(6);
        assertThat(response.getLettersGroup()).contains(entryExceptedI, entryExceptedT, entryExceptedC, entryExceptedL, entryExceptedN, entryExceptedA);
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

        MapEntry<Character, Integer> entryExceptedT = MapEntry.entry('T', 1);
        MapEntry<Character, Integer> entryExceptedL = MapEntry.entry('L', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);

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
        MapEntry<Character, Integer> entryExceptedC = MapEntry.entry('C', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedA = MapEntry.entry('A', 1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(3);
        assertThat(response.getLettersGroup()).contains(entryExceptedC, entryExceptedN, entryExceptedA);
    }


    //special chars, number and uppercase letter////

    @Test
    public void shouldReturnAProcessedStringUsingN2AndSpecialCharsNumbersUpperCaseTypeAndAStringAsInputs() {
        // Given
        String inputString = "AB!BnBn1nnBBB!Bn11nn*";
        int nInput = 2;
        ProcessType typeInput = ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "BBB1B!1";
        MapEntry<Character, Integer> entryExceptedB = MapEntry.entry('B', 4);
        MapEntry<Character, Integer> entryExceptedStar = MapEntry.entry('!', 1);
        MapEntry<Character, Integer> entryExceptedOne = MapEntry.entry('1', 2);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(3);
        assertThat(response.getLettersGroup()).contains(entryExceptedB, entryExceptedStar, entryExceptedOne);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN1SpecialCharsNumbersUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl";
        int nInput = 1;
        ProcessType typeInput = ProcessType.ONLY_UPPERCASE;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "ITCLNA";
        MapEntry<Character, Integer> entryExceptedI = MapEntry.entry('I', 1);
        MapEntry<Character, Integer> entryExceptedT = MapEntry.entry('T', 1);
        MapEntry<Character, Integer> entryExceptedC = MapEntry.entry('C', 1);
        MapEntry<Character, Integer> entryExceptedL = MapEntry.entry('L', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedA = MapEntry.entry('A', 1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(6);
        assertThat(response.getLettersGroup()).contains(entryExceptedI, entryExceptedT, entryExceptedC, entryExceptedL, entryExceptedN, entryExceptedA);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN2SpecialCharsNumbersUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl!n!n!!n///";
        int nInput = 2;
        ProcessType typeInput = ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "TLN!//";

        MapEntry<Character, Integer> entryExceptedT = MapEntry.entry('T', 1);
        MapEntry<Character, Integer> entryExceptedL = MapEntry.entry('L', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedExclamationPoint = MapEntry.entry('!', 1);
        MapEntry<Character, Integer> entryExceptedBar = MapEntry.entry('/', 2);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(5);
        assertThat(response.getLettersGroup()).contains(entryExceptedT, entryExceptedL, entryExceptedN, entryExceptedExclamationPoint, entryExceptedBar);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN3SpecialCharsNumbersUpperCaseTypeAndITCLiNicAlStringAsInputs() {
        // Given
        String inputString = "ITCLiNicAl!!!nnnn*";
        int nInput = 3;
        ProcessType typeInput = ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "CNA!*";
        MapEntry<Character, Integer> entryExceptedC = MapEntry.entry('C', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedA = MapEntry.entry('A', 1);
        MapEntry<Character, Integer> entryExceptedExclamationPoint = MapEntry.entry('!', 1);
        MapEntry<Character, Integer> entryExceptedStar = MapEntry.entry('*', 1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(5);
        assertThat(response.getLettersGroup()).contains(entryExceptedC, entryExceptedN, entryExceptedA, entryExceptedExclamationPoint, entryExceptedStar);
    }

    @Test
    public void shouldReturnAProcessedStringUsingN1SpecialCharsNumbersUpperCaseTypeAndExampleStringAsInputs() {
        // Given
        String inputString = "!tCL1Nical";
        int nInput = 1;
        ProcessType typeInput = ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;

        // When
        TextProcessorResponse response = this.service.processText(inputString, nInput, typeInput);

        // Then
        String expectedString = "!CL1N";
        MapEntry<Character, Integer> entryExceptedC = MapEntry.entry('C', 1);
        MapEntry<Character, Integer> entryExceptedN = MapEntry.entry('N', 1);
        MapEntry<Character, Integer> entryExceptedL = MapEntry.entry('L', 1);
        MapEntry<Character, Integer> entryExceptedOne = MapEntry.entry('1', 1);
        MapEntry<Character, Integer> entryExceptedExclamationPoint = MapEntry.entry('!', 1);

        assertThat(response).isNotNull();
        assertThat(response.getOutputString()).isNotEmpty();
        assertThat(response.getOutputString()).isEqualTo(expectedString);

        assertThat(response.getLettersGroup()).isNotNull();

        assertThat(response.getLettersGroup()).hasSize(5);
        assertThat(response.getLettersGroup()).contains(entryExceptedC, entryExceptedN, entryExceptedL, entryExceptedExclamationPoint, entryExceptedOne);
    }
}