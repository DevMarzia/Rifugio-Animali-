package com.rifugio;

import com.rifugio.composite.Department;
import com.rifugio.factory.AnimalFactory;
import com.rifugio.factory.ConcreteAnimalFactory;
import com.rifugio.model.Animal;
import com.rifugio.model.Cat;
import com.rifugio.model.Dog;
import com.rifugio.service.ShelterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting the animal shelter management application.");

        ShelterManager shelterManager = new ShelterManager("animals.json");
        List<Animal> animals = new ArrayList<>();

        AnimalFactory factory = new ConcreteAnimalFactory();
        animals.add(factory.createAnimal("dog", "Buddy", 3));
        animals.add(factory.createAnimal("cat", "Luna", 2));
        animals.add(factory.createAnimal("dog", "Max", 5));

        shelterManager.saveAnimals(animals);
        List<Animal> loadedAnimals = shelterManager.loadAnimals();

        Department mainShelter = new Department("Rifugio Principale");
        Department dogDepartment = new Department("Reparto Cani");
        Department catDepartment = new Department("Reparto Gatti");

        mainShelter.addComponent(dogDepartment);
        mainShelter.addComponent(catDepartment);

        if (loadedAnimals != null) {
            for (Animal animal : loadedAnimals) {
                if (animal instanceof Dog) {
                    dogDepartment.addComponent(animal);
                } else if (animal instanceof Cat) {
                    catDepartment.addComponent(animal);
                }
            }
        }
        
        System.out.println("\nMostrando i dettagli della struttura del rifugio:");
        mainShelter.displayDetails();
    }
}