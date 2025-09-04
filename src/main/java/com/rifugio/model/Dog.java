package com.rifugio.model;

public class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age, "dog");
    }

    @Override
    public void displayDetails() {
        System.out.println("  Dog - Name: " + this.name + ", Age: " + this.age);
    }
}