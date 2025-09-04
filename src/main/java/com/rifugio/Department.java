package com.rifugio;

// composite pattern
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// ✅ Aggiunta l'interfaccia Iterable
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
        System.out.println("Department: " + name);
        // Utilizza il this implicito per iterare sui componenti di questo dipartimento
        for (ShelterComponent component : this) {
            component.displayDetails();
        }
    }

    // Ora l'override è corretto perché implementiamo l'interfaccia Iterable
    @Override
    public Iterator<ShelterComponent> iterator() {
        return components.iterator();
    }
}