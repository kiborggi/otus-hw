package ru.otus.hw.executor;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TestContext {
   private final String className;
   private final List<Method> beforeMethods = new ArrayList<>();
   private final List<Method> testMethods = new ArrayList<>();
   private final List<Method> afterMethods = new ArrayList<>();
   private int passed = 0;
   private int failed = 0;

   public void incrementPassed(){
       passed++;
   }
    public void incrementFailed(){
        failed++;
    }

    public TestContext(String className) {
        this.className = className;
    }
}
