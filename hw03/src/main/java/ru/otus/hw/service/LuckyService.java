package ru.otus.hw.service;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.util.Random;


public class LuckyService {
    private final Random random;

    private int i;

    public LuckyService(){
        random = new Random();
    }

    @Before
    public void beforeMethod(){
        i = random.nextInt(15);
    }

    @Test
    public void luckyMethod(){
        if (i <= 5){
            throw new RuntimeException("not lucky enough");
        }
    }

    @Test
    public void luckyMethod2(){
        int i = random.nextInt(30);

        if (i <= 10){
            throw new RuntimeException("not lucky enough 2");
        }
    }

    @After
    public void afterMethod(){

    }

}
