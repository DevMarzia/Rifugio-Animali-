package com.rifugio;
//  factory pattern
public class ConcreteAnimalFactory implements AnimalFactory {
    @Override
    public Animal createAnimal(String type, String name, int age) {
        switch (type.toLowerCase()) {
            case "dog":
                return new Dog(name, age);
            case "cat":
                return new Cat(name, age);
            default:
                throw new IllegalArgumentException("Unknown animal type: " + type);
        }
    }
}
