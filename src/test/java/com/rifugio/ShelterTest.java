package com.rifugio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Import necessari per le classi del tuo progetto
import com.rifugio.exception.ShelterException;
import com.rifugio.model.Animal;
import com.rifugio.model.Cat;
import com.rifugio.model.Dog;
import com.rifugio.service.ShelterManager;


import static org.junit.jupiter.api.Assertions.*;

// La classe di test deve essere una singola classe pubblica
public class ShelterTest {

    private ShelterManager shelterManager;

    @TempDir
    File tempDir; // JUnit 5 crea e pulisce una directory temporanea

    private String tempFilePath;

    @BeforeEach
    void setUp() {
        tempFilePath = new File(tempDir, "test_animals.json").getAbsolutePath();
        shelterManager = new ShelterManager(tempFilePath);
    }

    @Test
    void testSaveAndLoadAnimals() {
        // Crea una lista di animali da salvare
        List<Animal> originalAnimals = new ArrayList<>();
        originalAnimals.add(new Dog("Buddy", 3));
        originalAnimals.add(new Cat("Luna", 2));

        // Salva gli animali
        shelterManager.saveAnimals(originalAnimals);

        // Carica gli animali e verifica che siano gli stessi
        List<Animal> loadedAnimals = shelterManager.loadAnimals();

        assertNotNull(loadedAnimals);
        assertEquals(2, loadedAnimals.size());
        
        // Controlla che gli oggetti caricati abbiano le proprietà corrette
        assertEquals("Buddy", loadedAnimals.get(0).getName());
        assertEquals(3, loadedAnimals.get(0).getAge());
        assertEquals("Luna", loadedAnimals.get(1).getName());
        assertEquals(2, loadedAnimals.get(1).getAge());
    }

    @Test
    void testLoadAnimalsFromEmptyFile() throws IOException {
        // Crea un file vuoto e testa che l'applicazione gestisca correttamente il caricamento
        new File(tempFilePath).createNewFile();
        List<Animal> loadedAnimals = shelterManager.loadAnimals();
        assertNotNull(loadedAnimals);
        assertTrue(loadedAnimals.isEmpty());
    }

    @Test
    void testLoadAnimalsWithCorruptedFile() {
        // Tenta di caricare un file non valido per testare l'Exception Shielding
        File corruptedFile = new File(tempFilePath);
        try {
            // Scrivi dati non JSON nel file
            java.nio.file.Files.write(corruptedFile.toPath(), "dati non validi".getBytes());
        } catch (IOException e) {
            fail("Failed to write corrupted data for test setup.");
        }

        // Verifica che venga lanciata la nostra eccezione generica e non una più specifica
        assertThrows(ShelterException.class, () -> {
            shelterManager.loadAnimals();
        });
    }
}