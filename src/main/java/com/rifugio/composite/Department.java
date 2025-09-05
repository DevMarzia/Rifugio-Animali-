package com.rifugio.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Department implements ShelterComponent, Iterable<ShelterComponent> {
    private String name;
    private List<ShelterComponent> components = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void addComponent(ShelterComponent component) {
        components.add(component);
    }

    @Override
    public void displayDetails() {
        System.out.println("Reaprto: " + name);
        for (ShelterComponent component : this) {
            component.displayDetails();
        }
    }

    @Override
    public Iterator<ShelterComponent> iterator() {
        return components.iterator();
    }
}