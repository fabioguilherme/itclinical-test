package org.itclinicaltest;

import org.itclinicaltest.services.*;
import org.itclinicaltest.validators.ValidatorFactory;
import org.itclinicaltest.validators.ValidatorFactoryImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        ValidatorFactory validatorFactory = new ValidatorFactoryImpl();
        TextProcessorService textProcessorService = new TextProcessorServiceImpl(validatorFactory);

        InputStreamReader r = new InputStreamReader(System.in);
        ReaderConsoleService readerConsoleService = new ReadConsoleServiceImpl(new BufferedReader(r));

        Orchestrator orchestrator = new OrchestratorImpl(textProcessorService, readerConsoleService);
        
        orchestrator.start();
    }
}