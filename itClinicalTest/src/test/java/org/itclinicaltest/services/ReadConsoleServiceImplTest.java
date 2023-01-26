package org.itclinicaltest.services;


import org.itclinicaltest.error.NoProcessTypeFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadConsoleServiceImplTest {

    @Mock
    private BufferedReader reader;

    @InjectMocks
    private ReadConsoleServiceImpl readerService;

    @Test
    public void canReadAStringFromTheConsole() throws IOException {
        // Given
        String inputString = "testString";

        when(reader.readLine()).thenReturn(inputString);

        // When
        String result = readerService.readStringFromConsole();

        // Then
        assertThat(result).isEqualTo(inputString);
    }

    @Test
    public void canReadANumberFromTheConsole() throws IOException {
        // Given
        String inputString = "1";

        when(reader.readLine()).thenReturn(inputString);

        // When
        int result = readerService.readNumberFromConsole();

        // Then
        int expectedValue = 1;
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void canReadAProcessTypeFromTheConsole() throws IOException {
        // Given
        String inputString = "upper";

        when(reader.readLine()).thenReturn(inputString);

        // When
        ProcessType result = readerService.readProcessTypeFromConsole();

        // Then
        ProcessType expectedValue = ProcessType.ONLY_UPPERCASE;

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldThrowAValidatorNotFoundExceptionIfThereIsNoValidator() throws IOException {
        // Given
        String inputString = "upper2";

        when(reader.readLine()).thenReturn(inputString);

        // When
        assertThatExceptionOfType(NoProcessTypeFoundException.class).isThrownBy(() -> readerService.readProcessTypeFromConsole()).withMessage("No process type found for the input: " + inputString);
    }
}