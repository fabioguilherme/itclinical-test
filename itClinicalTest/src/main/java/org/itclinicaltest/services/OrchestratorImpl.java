package org.itclinicaltest.services;

import org.itclinicaltest.error.NoProcessTypeFoundException;
import org.itclinicaltest.error.ValidatorNotFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;
import org.itclinicaltest.models.textprocessor.TextProcessorResponse;

import java.io.IOException;

public class OrchestratorImpl implements Orchestrator {

    public static final String QUESTION_STRING = "Insert string to be process please:";
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
    public static final String PROGRAM_ENDED = "Program ended";

    private final TextProcessorService textProcessorService;
    private final ReaderConsoleService readerConsoleService;

    public OrchestratorImpl(TextProcessorService textProcessorService, ReaderConsoleService readerConsoleService) {
        this.textProcessorService = textProcessorService;
        this.readerConsoleService = readerConsoleService;
    }

    @Override
    public void start() {

        int inputNumber;
        String textToProcess;
        ProcessType processType;
        boolean continueProcessing = true;
        String answerContinue = "yes";

        while (continueProcessing) {
            try {
                //reset variables
                inputNumber = 0;
                textToProcess = null;
                processType = null;

                textToProcess = readStringToProcess();

                inputNumber = readN();

                processType = readProcessType();

                TextProcessorResponse response = textProcessorService.processText(textToProcess, inputNumber, processType);

                System.out.printf(RESPONSE_MESSAGE, response);

                System.out.println(QUESTION_CONTINUE);

                continueProcessing = "yes".equals(readContinueString());

                if (continueProcessing) {
                    System.out.println(STARTING_AGAIN);
                } else {
                    System.out.println(PROGRAM_ENDED);
                }

            } catch (ValidatorNotFoundException e) {
                System.out.println(ERROR_MESSAGE_VALIDATOR_NOT_FOUND);
            }
        }
    }

    private String readStringToProcess() {
        String textToProcess = null;

        System.out.println(QUESTION_STRING);

        while (textToProcess == null || textToProcess.isEmpty()) {

            try {
                textToProcess = readerConsoleService.readStringFromConsole();

            } catch (IOException e) {
                System.out.println(ERROR_MESSAGE_QUESTION_STRING);
            }

        }

        return textToProcess;
    }

    private int readN() {
        Integer n = null;

        System.out.println(QUESTION_NUMBER);

        while (n == null) {

            try {
                n = readerConsoleService.readNumberFromConsole();

            } catch (IOException | NumberFormatException e) {
                System.out.println(ERROR_MESSAGE_QUESTION_NUMBER);
            }

        }

        return n;
    }

    private ProcessType readProcessType() {
        ProcessType processType = null;

        System.out.println(QUESTION_PROCESS_TYPE);

        while (processType == null) {

            try {
                processType = readerConsoleService.readProcessTypeFromConsole();

            } catch (IOException e) {
                System.out.println(ERROR_MESSAGE_PROCESS_TYPE);
            } catch (NoProcessTypeFoundException e) {
                System.out.println(ERROR_MESSAGE_PROCESS_TYPE_NOT_FOUND);
            }

        }

        return processType;
    }

    private String readContinueString() {
        String answer = null;

        System.out.println(QUESTION_CONTINUE);

        while (answer == null) {

            try {
                answer = readerConsoleService.readStringFromConsole();

                if (!"yes".equals(answer) && !"no".equals(answer)) {
                    answer = null;
                    System.out.println(ERROR_MESSAGE_QUESTION_CONTINUE);
                }
            } catch (IOException e) {
                System.out.println(ERROR_MESSAGE_QUESTION_CONTINUE);
            }
        }

        return answer;
    }
}
