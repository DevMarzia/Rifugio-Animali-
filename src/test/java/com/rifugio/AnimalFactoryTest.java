package com.rifugio;

import com.rifugio.factory.AnimalFactory;
import com.rifugio.factory.ConcreteAnimalFactory;
import com.rifugio.model.Animal;
import com.rifugio.model.Cat;
import com.rifugio.model.Dog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalFactoryTest {

    private AnimalFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ConcreteAnimalFactory();
    }

    @Test
    void testAnimalFactoryCreatesCorrectType() {
        Animal dog = factory.createAnimal("cane", "Fido", 5, "m");
        Animal cat = factory.createAnimal("gatto", "Milo", 4, "f");

        assertInstanceOf(Dog.class, dog);
        assertInstanceOf(Cat.class, cat);
    }

    @Test
    void testAnimalFactorySetsDataCorrectly() {
        Animal dog = factory.createAnimal("cane", "Fido", 5, "m");
        assertEquals("Fido", dog.getName());
        assertEquals(5, dog.getAge());
        assertEquals("m", dog.getSex());
    }

    @Test
    void testAnimalFactoryThrowsExceptionForInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createAnimal("coniglio", "Tweetie", 1, "m");
        });
    }
}