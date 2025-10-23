package ru.otus.hw.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores).thenComparingLong(Customer::getId));

    public Map.Entry<Customer, String> getSmallest() {
        if (map.isEmpty()) return null;
        Map.Entry<Customer, String> entry = map.firstEntry();
        return Map.entry(new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()), entry.getValue());

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        if (Objects.isNull(entry)) return null;
        return Map.entry(new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()), entry.getValue());

    }

    public void add(Customer customer, String data) {
        if (Objects.isNull(customer)) map.put(null, data);
        map.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
