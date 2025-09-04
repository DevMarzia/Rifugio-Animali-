package com.rifugio;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {

    @Test
    void testAnimalFactoryCreatesCorrectType() {
        AnimalFactory factory = new ConcreteAnimalFactory();
        Animal dog = factory.createAnimal("dog", "Fido", 5);
        Animal cat = factory.createAnimal("cat", "Milo", 4);

        // Verifica che la factory crei istanze del tipo corretto
        assertTrue(dog instanceof Dog);
        assertTrue(cat instanceof Cat);

        // Verifica che i dati siano stati impostati correttamente
        assertEquals("Fido", dog.getName());
        assertEquals(5, dog.getAge());
    }

    @Test
    void testAnimalFactoryThrowsExceptionForInvalidType() {
        AnimalFactory factory = new ConcreteAnimalFactory();
        // Verifica che venga lanciata un'eccezione quando il tipo non è valido
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createAnimal("bird", "Tweetie", 1);
        });
    }
}