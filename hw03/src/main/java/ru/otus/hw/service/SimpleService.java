package ru.otus.hw.service;

import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class SimpleService {

    /*public SimpleService(String s){

    }*/

    @Test
    public void performAction1(){
        int i = 1;
        i++;
    }

    @Test
    public void performAction2(){
        int i = 1;
        i++;
    }
    @Before
    public void performAction3(){
        int i = 1;
        i++;
    }

    @Before
    public void performAction4(){
        int i = 1;
        i++;
    }

}
