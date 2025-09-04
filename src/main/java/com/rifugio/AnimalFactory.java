package com.rifugio;

public interface AnimalFactory {
    Animal createAnimal(String type, String name, int age);
}