package ru.otus.hw.service;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.util.Objects;

public class ObjectService {

    private Object object;

    @Before
    private void initObject(){
        object = "This is the object";
    }

    @Test
    private void performAction(){
       String a = object.toString();
    }

    @After
    private void setNewObject(){
        object = 3;
    }

}
