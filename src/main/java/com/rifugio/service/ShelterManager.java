package com.rifugio.service;

import com.rifugio.exception.ShelterException;
import com.rifugio.factory.ConcreteAnimalFactory;
import com.rifugio.model.Animal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShelterManager {
    private static final Logger logger = LoggerFactory.getLogger(ShelterManager.class);
    private final String filePath;

    public ShelterManager(String filePath) {
        this.filePath = filePath;
    }

    public void saveAnimals(List<Animal> animals) {
        JSONArray jsonArray = new JSONArray();
        for (Animal animal : animals) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", animal.getType());
            jsonObject.put("name", animal.getName());
            jsonObject.put("age", animal.getAge());
            jsonArray.put(jsonObject);
        }
        try {
            Files.write(Paths.get(filePath), jsonArray.toString(4).getBytes());
            logger.info("Successfully saved {} animals to {}.", animals.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save animals to file: {}", filePath, e);
            throw new ShelterException("Error while saving animal data.", e);
        }
    }

    public List<Animal> loadAnimals() {
        List<Animal> animals = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(filePath))) {
                logger.warn("Animal file {} not found, returning empty list.", filePath);
                return animals;
            }
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            if (content.isEmpty()) {
                logger.info("Animal file {} is empty, returning empty list.", filePath);
                return animals;
            }

            JSONArray jsonArray = new JSONArray(content);
            ConcreteAnimalFactory factory = new ConcreteAnimalFactory();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String type = obj.getString("type");
                String name = obj.getString("name");
                int age = obj.getInt("age");
                String sex = obj.getString("sex");
                animals.add(factory.createAnimal(type, name, age, sex));
            }
            logger.info("Successfully loaded {} animals from {}.", animals.size(), filePath);
            return animals;
        } catch (IOException | JSONException e) {
            // EXCEPTION SHIELDING PATTERN
            logger.error("Failed to load animals from file {}: {}", filePath, e.getMessage());
            throw new ShelterException("Could not load animal data. The file might be corrupted.", e);
        }
    }
}