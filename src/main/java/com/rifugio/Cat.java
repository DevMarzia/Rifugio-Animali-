package com.rifugio;

public class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }
    @Override
    public void displayDetails() {
        System.out.println("Cat - Name: " + this.name + ", Age: " + this.age);
    }
}