package ru.otus.hw.homework;

import java.util.ArrayDeque;
import java.util.Deque;

@SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerReverseOrder {

    private final Deque<Customer> deque = new ArrayDeque<>();

    public void add(Customer customer) {
        deque.push(customer);
    }

    public Customer take() {
        return deque.poll();
    }
}
