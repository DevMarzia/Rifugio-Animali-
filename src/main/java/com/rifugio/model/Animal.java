package com.rifugio.model;

import com.rifugio.composite.ShelterComponent;

public abstract class Animal implements ShelterComponent {
    protected String name;
    protected int age;
    protected String type;

    public Animal(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getType() { return type; }

    @Override
    public abstract void displayDetails();
}