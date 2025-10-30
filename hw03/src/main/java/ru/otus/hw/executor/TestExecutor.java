package ru.otus.hw.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        }
        catch (Exception e){
            log.error("Unexpected exception during test run {} {}",clazz.getCanonicalName(),e.getMessage(),e);
        }
        printResults(context);

    }

    private void prepareMethods(Class<?> clazz, TestContext context) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                context.getBeforeMethods().add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                context.getTestMethods().add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                context.getBeforeMethods().add(method);
            }
        }
    }

    @SneakyThrows
    private Object createInstance(Class<?> clazz) {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    private void executeTestSuite(TestContext context, Class<?> clazz) {
        invokeAllMethods(context.getBeforeMethods(), clazz, context);
        invokeAllMethods(context.getTestMethods(), clazz, context);
        invokeAllMethods(context.getAfterMethods(), clazz, context);
    }

    private void invokeAllMethods(List<? extends Method> methods, Class<?> clazz, TestContext context) {
        Object instance = createInstance(clazz);
        for (Method method : methods) {
            executeSingleTest(method, instance, context);
        }
    }

    private void executeSingleTest(Method testMethod, Object testInstance, TestContext context) {
        try {

            testMethod.setAccessible(true);
            testMethod.invoke(testInstance);

            context.incrementPassed();
            log.info("Test PASSED: {}", testMethod.getName());

        } catch (InvocationTargetException e) {
            context.incrementFailed();
            log.error("Test FAILED: {} - {}", testMethod.getName(), e.getCause().getMessage());
        } catch (Exception e) {
            context.incrementFailed();
            log.error("Test FAILED: {}", testMethod.getName());
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