package org.itclinicaltest.services;

import org.itclinicaltest.error.NoProcessTypeFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class ReadConsoleServiceImpl implements ReaderConsoleService {

    private final BufferedReader reader;

    public ReadConsoleServiceImpl(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readStringFromConsole() throws IOException {
        return reader.readLine();
    }

    @Override
    public int readNumberFromConsole() throws IOException, NumberFormatException {
        String numbString = reader.readLine();

        return Integer.parseInt(numbString);
    }

    @Override
    public ProcessType readProcessTypeFromConsole() throws IOException, NoProcessTypeFoundException {

        String inputString = reader.readLine();
        Optional<ProcessType> optionalProcessType = ProcessType.getProcessTypeProcessName(inputString);


        return optionalProcessType.orElseThrow(() -> new NoProcessTypeFoundException(inputString));
    }
}
