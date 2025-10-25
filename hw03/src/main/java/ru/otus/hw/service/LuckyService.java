package ru.otus.hw.service;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.util.Random;


public class LuckyService {
    private final Random random;

    public LuckyService(){
        random = new Random();
    }

    @Before
    public void luckyMethod(){
        int i = random.nextInt(10);

        if (i <= 5){
            throw new RuntimeException("not lucky enough");
        }
    }

    @Test
    public void luckyMethod2(){
        int i = random.nextInt(30);

        if (i <= 5){
            throw new RuntimeException("not lucky enough");
        }
    }

    @After
    public void luckyMethod3(){
        int i = random.nextInt(100);

        if (i <= 5){
            throw new RuntimeException("not lucky enough");
        }
    }

}
