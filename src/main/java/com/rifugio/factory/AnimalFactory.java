package com.rifugio.factory;

import com.rifugio.model.Animal;

public interface AnimalFactory {
    Animal createAnimal(String type, String name, int age, String sex);
}