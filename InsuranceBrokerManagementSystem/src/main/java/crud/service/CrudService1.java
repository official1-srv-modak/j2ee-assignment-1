package crud.service;

import com.google.gson.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrudService1 {

    private String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public CrudService1() {
    }
    
    
    

	public String getFilePath(Object obj) {
        this.filePath = obj.getClass().getSimpleName()+".json";
        return this.filePath;
	}




	// Get all products (as Objects) from the file
	public List<Object> getAll(Object obj) throws IOException {
	    filePath = getFilePath(obj);
	    File file = new File(filePath);
	    if (!file.exists()) {
	        return new ArrayList<>();
	    }

	    // Read the file
	    try (FileReader reader = new FileReader(file)) {
	        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
	        List<Object> objectList = new ArrayList<>();
	        
	        // Add each JsonElement as Object to the list
	        jsonArray.forEach(jsonElement -> objectList.add(gson.fromJson(jsonElement, Object.class)));
	        
	        return objectList;
	    }
	}

    	// Get a single product by its ID
	public Object getById(String id, Object obj) throws IOException {
	    return getAll(obj).stream()
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


    // Save all products to the file
	public void saveAll(List<Object> objectList, Object obj) throws IOException {
	    // Convert the list of objects to a JsonArray
	    JsonArray jsonArray = new JsonArray();
	    objectList.forEach(item -> jsonArray.add(gson.toJsonTree(item)));

	    // Get the file path
	    this.filePath = getFilePath(obj);

	    // Write the JsonArray to the file
	    try (FileWriter writer = new FileWriter(filePath)) {
	        gson.toJson(jsonArray, writer);
	    }
	}


    // Add a new product
    public void add(Object obj) throws IOException { // again should accept object
        List<Object> jsonObjectList = (List<Object>) getAll(obj);
        JsonObject newJsonObject = convertToJson(obj);
        jsonObjectList.add(newJsonObject);
        saveAll(jsonObjectList, obj);
    }

 // Update an existing product by its ID
    public void update(String id, Object updatedObject, Object obj) throws IOException {
        // Get the list of existing objects
        List<Object> objectList = getAll(obj);
        
        // Convert the updated object to JSON
        JsonElement updatedJsonElement = gson.toJsonTree(updatedObject);
        
        // Map the object list and update the object with the matching ID
        objectList = objectList.stream().map(item -> {
            JsonElement jsonElement = gson.toJsonTree(item);
            if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("id") 
                    && jsonElement.getAsJsonObject().get("id").getAsString().equals(id)) {
                return updatedObject; // Replace the object
            }
            return item; // Keep the original object if no match
        }).collect(Collectors.toList());
        
        // Save the updated list
        saveAll(objectList, obj);
    }


    // Delete a product by its ID
    public void delete(String id, Object obj) throws IOException {
        // Get the list of existing objects
        List<Object> objectList = getAll(obj);
        
        // Remove objects with the matching ID
        objectList.removeIf(item -> {
            JsonElement jsonElement = gson.toJsonTree(item);
            return jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("id") 
                    && jsonElement.getAsJsonObject().get("id").getAsString().equals(id);
        });
        
        // Save the updated list
        saveAll(objectList, obj);
    }

    
    public JsonObject convertToJson(Object object) {
        JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
        return jsonObject;
    }
}
