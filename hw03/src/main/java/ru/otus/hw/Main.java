package ru.otus.hw;

import ru.otus.hw.executor.TestExecutor;
import ru.otus.hw.service.LuckyService;
import ru.otus.hw.service.ObjectService;
import ru.otus.hw.service.SimpleService;


public class Main {
    public static void main(String[] args) {
        TestExecutor testExecutor = new TestExecutor();

        testExecutor.executeTest(LuckyService.class);

        testExecutor.executeTest(SimpleService.class);

        testExecutor.executeTest(ObjectService.class);

    }
}