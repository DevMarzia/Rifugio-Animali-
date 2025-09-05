package com.rifugio.model;

public class Dog extends Animal {
    public Dog(String name, int age, String sex) {
        super(name, age, sex, "cane");
    }

    @Override
    public void displayDetails() {
        System.out.println("  Cane - Nome: " + this.name + ", Età: " + this.age + ", Sesso: " + this.sex);
    }
}