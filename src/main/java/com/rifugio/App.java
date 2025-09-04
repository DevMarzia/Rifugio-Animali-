package com.rifugio;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class for the animal shelter management system.
 */
public class App {

    // 1. Logger inizializzato correttamente
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting the animal shelter management application.");

        ShelterManager shelterManager = new ShelterManager("animals.json");
        List<Animal> animals = new ArrayList<>();

        // Aggiungi un po' di dati di esempio
        AnimalFactory factory = new ConcreteAnimalFactory();
        animals.add(factory.createAnimal("dog", "Buddy", 3));
        animals.add(factory.createAnimal("cat", "Luna", 2));
        animals.add(factory.createAnimal("dog", "Max", 5));

        // Salva e ricarica gli animali per simulare la persistenza
        shelterManager.saveAnimals(animals);
        List<Animal> loadedAnimals = shelterManager.loadAnimals();

        // 2. Logica del pattern Composite corretta
        // Crea la struttura gerarchica del rifugio
        Department mainShelter = new Department("Rifugio Principale");
        Department dogDepartment = new Department("Reparto Cani");
        Department catDepartment = new Department("Reparto Gatti");

        // Aggiungi i reparti al rifugio principale
        mainShelter.addComponent(dogDepartment);
        mainShelter.addComponent(catDepartment);

        // Assegna ogni animale al reparto corretto
        if (loadedAnimals != null) {
            for (Animal animal : loadedAnimals) {
                if (animal instanceof Dog) {
                    dogDepartment.addComponent(animal);
                } else if (animal instanceof Cat) {
                    catDepartment.addComponent(animal);
                }
            }
        }
        
        // 3. Mostra la struttura gerarchica usando l'Iterator del Composite
        System.out.println("\nMostrando i dettagli della struttura del rifugio:");
        mainShelter.displayDetails();
    }
}