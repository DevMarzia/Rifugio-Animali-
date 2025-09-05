package com.rifugio.model;

public class Cat extends Animal {
    public Cat(String name, int age, String sex) {
        super(name, age, sex, "gatto");
    }

    @Override
    public void displayDetails() {
        System.out.println("  Gatto - Nome: " + this.name + ", Età: " + this.age + ", Sesso: "+ this.sex);
    }
}