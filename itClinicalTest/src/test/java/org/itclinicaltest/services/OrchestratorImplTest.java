package org.itclinicaltest.services;

import org.itclinicaltest.error.NoProcessTypeFoundException;
import org.itclinicaltest.error.ValidatorNotFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;
import org.itclinicaltest.models.textprocessor.TextProcessorResponseFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrchestratorImplTest {

    public static final String QUESTION_STRING = "Insert string to be process please:";
    public static final String PROGRAM_ENDED = "Program ended";
    private static final String ERROR_MESSAGE_QUESTION_STRING = "There was a problem reading the string, please, try again:";

    public static final String QUESTION_NUMBER = "Insert N to be used to process the string please:";
    private static final String ERROR_MESSAGE_QUESTION_NUMBER = "There was a problem reading the N, please, try again:";

    public static final String QUESTION_PROCESS_TYPE = "Please insert the type of process to used please:(INPUS:'\n\"upper\": to read only upper case letters \n\"special\": to read upper case letters, numbers and special chars):";
    private static final String ERROR_MESSAGE_PROCESS_TYPE = "There was a problem reading the N, please, try again:";
    private static final String ERROR_MESSAGE_PROCESS_TYPE_NOT_FOUND = "There is no process type for that string, please, try again:";

    private static final String ERROR_MESSAGE_VALIDATOR_NOT_FOUND = "There is no validator for that process type, we will restart.";
    public static final String RESPONSE_MESSAGE = "your number is:\n %s";
    public static final String QUESTION_CONTINUE = "Want to continue: \"yes\" or \"no\"";
    public static final String ERROR_MESSAGE_QUESTION_CONTINUE = "Please type: \"yes\" or \"no\"";
    public static final String STARTING_AGAIN = "Starting again...";

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private ReaderConsoleService readerConsoleService;
    @Mock
    private TextProcessorService textProcessorService;
    private Orchestrator orchestrator;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outputStreamCaptor));
        this.orchestrator = new OrchestratorImpl(textProcessorService, readerConsoleService);
    }

    @Test
    public void shouldShowMessageToUserIfTheresIsAnErrorOnTheFirstQuestion() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenThrow(IOException.class).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_STRING);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_QUESTION_STRING);

        ArgumentCaptor<String> inputStringCapture = ArgumentCaptor.forClass(String.class);

        verify(textProcessorService).processText(inputStringCapture.capture(), anyInt(), any());

        assertThat(inputStringCapture.getValue()).isEqualTo(inputString);
    }

    @Test
    public void shouldShowMessageToUserIfTheUserDoNotWriteANumberInTheSecondQuestion() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenThrow(NumberFormatException.class).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_NUMBER);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_QUESTION_NUMBER);

        ArgumentCaptor<Integer> inputIntCapture = ArgumentCaptor.forClass(Integer.class);

        verify(textProcessorService).processText(anyString(), inputIntCapture.capture(), any());

        assertThat(inputIntCapture.getValue()).isEqualTo(inputN);
    }

    @Test
    public void shouldShowMessageToUserIfThereWasAProblemInTheSecondQuestion() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenThrow(IOException.class).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_NUMBER);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_QUESTION_NUMBER);

        ArgumentCaptor<Integer> inputIntCapture = ArgumentCaptor.forClass(Integer.class);

        verify(textProcessorService).processText(anyString(), inputIntCapture.capture(), any());

        assertThat(inputIntCapture.getValue()).isEqualTo(inputN);
    }

    @Test
    public void shouldShowMessageToUserIfTheresIfTheUserDoNotWriteAValidProcessType() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenThrow(NoProcessTypeFoundException.class).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_PROCESS_TYPE);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_PROCESS_TYPE_NOT_FOUND);

        ArgumentCaptor<ProcessType> inputProcessCapture = ArgumentCaptor.forClass(ProcessType.class);

        verify(textProcessorService).processText(anyString(), anyInt(), inputProcessCapture.capture());

        assertThat(inputProcessCapture.getValue()).isEqualTo(type);
    }

    @Test
    public void shouldShowMessageToUserIfTheresIfThereWasAProblemReadingProcessType() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenThrow(IOException.class).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_PROCESS_TYPE);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_PROCESS_TYPE);

        ArgumentCaptor<ProcessType> inputProcessCapture = ArgumentCaptor.forClass(ProcessType.class);

        verify(textProcessorService).processText(anyString(), anyInt(), inputProcessCapture.capture());

        assertThat(inputProcessCapture.getValue()).isEqualTo(type);
    }

    @Test
    public void shouldShowMessageIfThereIsNoValidatorForAProcessType() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenThrow(ValidatorNotFoundException.class).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_PROCESS_TYPE);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_VALIDATOR_NOT_FOUND);

        ArgumentCaptor<ProcessType> inputProcessCapture = ArgumentCaptor.forClass(ProcessType.class);

        verify(textProcessorService, times(2)).processText(anyString(), anyInt(), inputProcessCapture.capture());

        assertThat(inputProcessCapture.getValue()).isEqualTo(type);
    }

    @Test
    public void canDoFullFlow() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(String.format(RESPONSE_MESSAGE, response));
    }

    @Test
    public void canContinueAfterPresentingTheResults() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("yes").thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_CONTINUE);
        assertThat(outputStreamCaptor.toString()).contains(STARTING_AGAIN);

    }

    @Test
    public void canExit() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(PROGRAM_ENDED);
    }

    @Test
    public void whenAskedToContinueIfTheUserEnteredAWrongStringIShouldAskAgain() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenReturn("blah").thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_CONTINUE);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_QUESTION_CONTINUE);
        assertThat(outputStreamCaptor.toString()).contains(PROGRAM_ENDED);
    }

    @Test
    public void whenAskedToContinueIfThereWasAProblemIShouldAskTheUserToTypeAgain() throws IOException {
        // Given
        String inputString = "inputString";
        int inputN = 1;
        ProcessType type = ProcessType.ONLY_UPPERCASE;
        TextProcessorResponse response = TextProcessorResponseFixture.getResponse().build();

        when(readerConsoleService.readStringFromConsole()).thenReturn(inputString).thenThrow(IOException.class).thenReturn("no");
        when(readerConsoleService.readNumberFromConsole()).thenReturn(inputN);
        when(readerConsoleService.readProcessTypeFromConsole()).thenReturn(type);
        when(textProcessorService.processText(anyString(), anyInt(), any())).thenReturn(response);

        // When
        orchestrator.start();

        // Then
        assertThat(outputStreamCaptor.toString()).contains(QUESTION_CONTINUE);
        assertThat(outputStreamCaptor.toString()).contains(ERROR_MESSAGE_QUESTION_CONTINUE);
        assertThat(outputStreamCaptor.toString()).contains(PROGRAM_ENDED);
    }

}
