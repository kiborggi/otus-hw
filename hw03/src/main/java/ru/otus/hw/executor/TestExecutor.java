package ru.otus.hw.executor;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestExecutor {

    public void executeTest(Class<?> clazz) {
        TestContext context = new TestContext(clazz.getCanonicalName());
        prepareMethods(clazz, context);
        Object testInstance;

        System.out.printf("\nStarting tests for %s", clazz.getCanonicalName());

        try {
            testInstance = createInstance(clazz);
        } catch (Exception exception) {
            System.out.printf("\nError during preparing test %s, canceling execution\n", clazz.getCanonicalName());
            return;
        }

        executeTestSuite(testInstance, context);
        printResults(context);



    }

    private void prepareMethods(Class<?> clazz, TestContext context) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                context.beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                context.testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                context.afterMethods.add(method);
            }
        }
    }

    private Object createInstance(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void executeTestSuite(Object testInstance, TestContext context) {
        invokeAllMethods(context.beforeMethods, testInstance, context);
        invokeAllMethods(context.testMethods, testInstance, context);
        invokeAllMethods(context.afterMethods, testInstance, context);
    }

    private void invokeAllMethods(List<Method> methods, Object instance, TestContext context) {
        for (Method method : methods) {
            executeSingleTest(method, instance, context);
        }
    }

    private void executeSingleTest(Method testMethod, Object testInstance, TestContext context) {
        try {
            //System.out.println("Running test: " + testMethod.getName());

            testMethod.setAccessible(true);
            testMethod.invoke(testInstance);

            context.passed++;
            System.out.println("Test PASSED: " + testMethod.getName());

        } catch (InvocationTargetException e) {
            context.failed++;
            System.out.println("Test FAILED: " + testMethod.getName() +
                    " - " + e.getCause().getMessage());
        } catch (Exception e) {
            context.failed++;
            System.out.println("Test FAILED: " + testMethod.getName() +
                    " - ");
        }
    }

    private void printResults(TestContext context) {
        System.out.println("\n=== TEST RESULTS ===");
        System.out.println("Passed: " + context.passed);
        System.out.println("Failed: " + context.failed);
        System.out.println("Total: " + (context.passed + context.failed));
        System.out.println("==================\n");
    }

    private static class TestContext {
        String className;
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        int passed = 0;
        int failed = 0;

        public TestContext(String className) {
            this.className = className;
        }
    }
}