package org.itclinicaltest.services;

import org.itclinicaltest.error.ValidatorNotFoundException;
import org.itclinicaltest.models.textprocessor.ProcessType;

import java.io.IOException;

public interface ReaderConsoleService {

    String readStringFromConsole() throws IOException;

    int readNumberFromConsole() throws IOException, NumberFormatException;

    ProcessType readProcessTypeFromConsole() throws IOException, ValidatorNotFoundException;
}
