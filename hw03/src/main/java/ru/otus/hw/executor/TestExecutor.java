package ru.otus.hw.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.List;

@Slf4j
public class TestExecutor {

    public void executeTest(Class<?> clazz) {
        TestContext context = new TestContext(clazz.getCanonicalName());
        prepareMethods(clazz, context);

        log.info("Starting test for {}", clazz.getCanonicalName());
        try {
            executeTestSuite(context, clazz);
        } catch (Exception e) {
            log.error("Unexpected exception during test run {} {}", clazz.getCanonicalName(), e.getMessage(), e);
        }
        printResults(context);
    }




    private void executeTestSuite(TestContext context, Class<?> clazz) {
        for (Method testMethod : context.getTestMethods()) {
            executeSingleTestWithBeforeAfter(testMethod, clazz, context);
        }
    }

    private void executeSingleTestWithBeforeAfter(Method testMethod, Class<?> clazz, TestContext context) {
        try {
            Object testInstance = createInstance(clazz);

            executeMethods(context.getBeforeMethods(), testInstance, context);

            executeSingleTest(testMethod, testInstance, context);

            executeMethods(context.getAfterMethods(), testInstance, context);

        }
        catch (Exception e) {
            log.error("Test FAILED: {} {}", testMethod.getName(), e.getMessage());
            context.incrementFailed();
        }
    }

    private void executeMethods(List<Method> methods, Object instance, TestContext context) {
        for (Method method : methods) {
            try {
                method.setAccessible(true);
                method.invoke(instance);
            }
            catch (Exception e) {
                log.error("Method FAILED: {}", method.getName());
                throw new RuntimeException("Method execution failed: " + method.getName(), e);
            }
        }
    }

    private void executeSingleTest(Method testMethod, Object testInstance, TestContext context) {
        try {
            testMethod.setAccessible(true);
            testMethod.invoke(testInstance);
            context.incrementPassed();
            log.info("Test PASSED: {}", testMethod.getName());
        } catch (Exception e) {
            log.error("Test FAILED: {}", testMethod.getName());
            throw new RuntimeException("Test failed: " + testMethod.getName(), e);
        }
    }

    @SneakyThrows
    private Object createInstance(Class<?> clazz) {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void prepareMethods(Class<?> clazz, TestContext context) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                context.getBeforeMethods().add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                context.getTestMethods().add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                context.getAfterMethods().add(method);
            }
        }
    }

    private void printResults(TestContext context) {
        log.info("""
                        
                        === TEST RESULTS ===
                        Passed: {}
                        Failed: {}
                        Total:  {}
                        ==================
                        """,
                context.getPassed(),
                context.getFailed(),
                context.getPassed() + context.getFailed()
        );
    }
}