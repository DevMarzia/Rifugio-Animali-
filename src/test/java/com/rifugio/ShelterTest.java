package com.rifugio;

import com.rifugio.exception.ShelterException;
import com.rifugio.model.Animal;
import com.rifugio.model.Cat;
import com.rifugio.model.Dog;
import com.rifugio.service.ShelterManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterTest {

    private ShelterManager shelterManager;

    @TempDir
    File tempDir;

    private String tempFilePath;

    @BeforeEach
    void setUp() {
        tempFilePath = new File(tempDir, "test_animals.json").getAbsolutePath();
        shelterManager = new ShelterManager(tempFilePath);
    }

    @Test
    void testSaveAndLoadAnimals() {
        List<Animal> originalAnimals = new ArrayList<>();
        // Questo è il punto corretto, con i costruttori a 3 parametri
        originalAnimals.add(new Dog("Buddy", 3, "m"));
        originalAnimals.add(new Cat("Luna", 2, "f"));

        shelterManager.saveAnimals(originalAnimals);

        List<Animal> loadedAnimals = shelterManager.loadAnimals();

        assertNotNull(loadedAnimals);
        assertEquals(2, loadedAnimals.size());

        assertEquals("Buddy", loadedAnimals.get(0).getName());
        assertEquals(3, loadedAnimals.get(0).getAge());
        assertEquals("m", loadedAnimals.get(0).getSex());

        assertEquals("Luna", loadedAnimals.get(1).getName());
        assertEquals(2, loadedAnimals.get(1).getAge());
        assertEquals("f", loadedAnimals.get(1).getSex());
    }

    @Test
    void testLoadAnimalsFromEmptyFile() throws IOException {
        new File(tempFilePath).createNewFile();
        List<Animal> loadedAnimals = shelterManager.loadAnimals();
        assertNotNull(loadedAnimals);
        assertTrue(loadedAnimals.isEmpty());
    }

    @Test
    void testLoadAnimalsWithCorruptedFile() {
        File corruptedFile = new File(tempFilePath);
        try {
            Files.write(corruptedFile.toPath(), "dati non validi".getBytes());
        } catch (IOException e) {
            fail("Salvataggio dati corrotti per il test fallito.");
        }

        assertThrows(ShelterException.class, () -> {
            shelterManager.loadAnimals();
        });
    }
}