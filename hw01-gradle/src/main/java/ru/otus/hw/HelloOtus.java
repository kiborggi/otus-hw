package ru.otus.hw;

import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

public class HelloOtus {

    public static void main(String[] args) {
        Random rand = new Random();
        Set<Integer> set1 = Sets.newHashSet();
        Set<Integer> set2 = Sets.newHashSet();

        while (set1.size() < 5) set1.add(rand.nextInt(10) + 1);
        while (set2.size() < 5) set2.add(rand.nextInt(10) + 1);

        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);

        Set<Integer> intersection = Sets.intersection(set1, set2);

        System.out.println("Intersection: " + intersection);
    }
}