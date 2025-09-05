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
        logger.info("Avvio dell'applicazione: Gestione Rifugio Aniamli");
        animals = shelterManager.loadAnimals();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

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
                        logger.info("Dati salvati. Chiusura dell'applicazione.");
                        System.out.println("Dati salvati. Arrivederci!");
                        break;
                    default:
                        System.err.println("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                logger.warn("Ricevuto input non valido nella selezione del menu.");
                System.err.println("Errore: Inserire un numero valido per la scelta.");
                scanner.nextLine();
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
        // CORRETTO: Chiede "cane" o "gatto"
        System.out.print("Che tipo di animale? (cane/gatto): ");
        String type = scanner.nextLine().toLowerCase().trim();
        // CORRETTO: Valida l'input in italiano
        if (!type.equals("cane") && !type.equals("gatto")) {
            logger.warn("Inserito tipo di animale non valido: {}", type);
            System.err.println("Tipo non valido. Inserire 'cane' o 'gatto'.");
            return;
        }

        System.out.print("Nome: ");
        String name = scanner.nextLine();

        System.out.print("Sesso? (m/f): ");
        String sex = scanner.nextLine().toLowerCase().trim();
        if (!sex.equals("m") && !sex.equals("f")) {
            logger.warn("Inserito sesso non valido: {}", sex);
            System.err.println("Sesso non valido. Inserire 'm' o 'f'.");
            return;
        }
        
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
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Animal newAnimal = factory.createAnimal(type, name, age, sex);
        animals.add(newAnimal);
        logger.info("Nuovo animale aggiunto: {} - {} - {}", type, name, sex);
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
// mvn clean package

// java -jar target/RifugioAnimali-1.0-SNAPSHOT-jar-with-dependencies.jar