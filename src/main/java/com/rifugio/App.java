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

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final ShelterManager shelterManager = new ShelterManager("animals.json");
    private static final AnimalFactory factory = new ConcreteAnimalFactory();
    private static List<Animal> animals;

    public static void main(String[] args) {
        logger.info("Starting the animal shelter management application.");
        animals = shelterManager.loadAnimals(); // Carica i dati all'avvio

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consuma il carattere newline dopo il numero

                switch (choice) {
                    case 1:
                        addNewAnimal(scanner);
                        break;
                    case 2:
                        showAllAnimals();
                        break;
                    case 3:
                        showCompositeStructure();
                        break;
                    case 4:
                        shelterManager.saveAnimals(animals);
                        running = false;
                        logger.info("Data saved. Shutting down.");
                        System.out.println("Dati salvati. Arrivederci!");
                        break;
                    default:
                        System.err.println("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                // Gestisce l'input non numerico per prevenire il crash
                logger.warn("Invalid input received in menu selection.");
                System.err.println("Errore: Inserire un numero valido per la scelta.");
                scanner.nextLine(); // Pulisce lo scanner dall'input errato
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Menu Rifugio Animali ---");
        System.out.println("1. Aggiungi un nuovo animale");
        System.out.println("2. Mostra la lista di tutti gli animali");
        System.out.println("3. Mostra la struttura a reparti del rifugio");
        System.out.println("4. Salva ed esci");
        System.out.print("Scegli un'opzione: ");
    }

    private static void addNewAnimal(Scanner scanner) {
        System.out.print("Che tipo di animale? (dog/cat): ");
        String type = scanner.nextLine().toLowerCase().trim();

        // --- REQUISITO: INPUT SANITIZATION ---
        // Controlliamo che il tipo sia uno di quelli permessi (whitelisting)
        if (!type.equals("dog") && !type.equals("cat")) {
            logger.warn("Invalid animal type entered: {}", type);
            System.err.println("Tipo non valido. Inserire 'dog' o 'cat'.");
            return; // Torna al menu
        }

        System.out.print("Nome: ");
        String name = scanner.nextLine();
        
        int age = -1;
        while (age < 0) {
            System.out.print("Età: ");
            try {
                age = scanner.nextInt();
                if (age < 0) {
                    System.err.println("L'età non può essere negativa.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Errore: Inserire un'età numerica valida.");
                scanner.nextLine(); // Pulisce lo scanner
            }
        }
        scanner.nextLine(); // Consuma il newline

        Animal newAnimal = factory.createAnimal(type, name, age);
        animals.add(newAnimal);
        logger.info("New animal added: {} - {}", type, name);
        System.out.println(name + " è stato aggiunto al rifugio!");
    }

    private static void showAllAnimals() {
        System.out.println("\n--- Lista Animali nel Rifugio ---");
        if (animals.isEmpty()) {
            System.out.println("Il rifugio è attualmente vuoto.");
        } else {
            for (Animal animal : animals) {
                animal.displayDetails();
            }
        }
    }

    private static void showCompositeStructure() {
        System.out.println("\n--- Struttura a Reparti del Rifugio ---");
        Department mainShelter = new Department("Rifugio Principale");
        Department dogDepartment = new Department("Reparto Cani");
        Department catDepartment = new Department("Reparto Gatti");

        mainShelter.addComponent(dogDepartment);
        mainShelter.addComponent(catDepartment);

        for (Animal animal : animals) {
            if (animal instanceof Dog) {
                dogDepartment.addComponent(animal);
            } else if (animal instanceof Cat) {
                catDepartment.addComponent(animal);
            }
        }
        mainShelter.displayDetails();
    }
}