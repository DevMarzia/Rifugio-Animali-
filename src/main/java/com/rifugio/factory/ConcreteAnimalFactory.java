package com.rifugio.factory;

import com.rifugio.model.Animal;
import com.rifugio.model.Cat;
import com.rifugio.model.Dog;

public class ConcreteAnimalFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(String type, String name, int age, String sex) {
        switch (type.toLowerCase()) {
            case "cane":
                return new Dog(name, age, sex);
            case "gatto":
                return new Cat(name, age, sex);
            default:
                throw new IllegalArgumentException("Animale non presente nel rifugio: " + type);
        }
    }
}