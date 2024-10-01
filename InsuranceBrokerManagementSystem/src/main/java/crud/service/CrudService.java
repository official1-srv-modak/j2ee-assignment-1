package crud.service;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrudService<T> {

    private String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Class<T> type; // Class type for deserialization

    public CrudService(Class<T> type) {
        this.type = type; // Store the type for later use
    }

    public String getFilePath() {
        this.filePath = type.getSimpleName() + ".json"; // Use the class name as the filename
        return this.filePath;
    }

    // Get all objects from the file
    public List<T> getAll() throws IOException {
        filePath = getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        // Read the file
        try (FileReader reader = new FileReader(file)) {
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            List<T> objectList = new ArrayList<>();

            // Deserialize each JsonElement into the generic type
            for (JsonElement jsonElement : jsonArray) {
                objectList.add(gson.fromJson(jsonElement, type));
            }

            return objectList;
        }
    }

    // Get a single object by its ID
    public T getById(String id) throws IOException {
        return getAll().stream()
                .filter(item -> {
                    if (item instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) item;
                        return map.containsKey("id") && id.equals(map.get("id").toString());
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    // Save all objects to the file
    public void saveAll(List<T> objectList) throws IOException {
        // Convert the list of objects to a JsonArray
        JsonArray jsonArray = new JsonArray();
        objectList.forEach(item -> jsonArray.add(gson.toJsonTree(item)));

        // Get the file path
        this.filePath = getFilePath();

        // Write the JsonArray to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(jsonArray, writer);
        }
    }

    // Add a new object
    public void add(T obj) throws IOException {
        List<T> objectList = getAll();
        objectList.add(obj);
        saveAll(objectList);
    }

    // Update an existing object by its ID
    public void update(String id, T updatedObject) throws IOException {
        List<T> objectList = getAll();

        // Update the object with the matching ID
        objectList = objectList.stream().map(item -> {
            if (item instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) item;
                if (map.containsKey("id") && map.get("id").toString().equals(id)) {
                    return updatedObject; // Replace the object
                }
            }
            return item; // Keep the original object if no match
        }).collect(Collectors.toList());

        saveAll(objectList);
    }

    // Delete an object by its ID
    public void delete(String id) throws IOException {
        List<T> objectList = getAll();

        // Remove objects with the matching ID
        objectList.removeIf(item -> {
            if (item instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) item;
                return map.containsKey("id") && map.get("id").toString().equals(id);
            }
            return false;
        });

        saveAll(objectList);
    }
}
