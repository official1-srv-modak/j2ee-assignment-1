package crud.values;

import java.util.ArrayList;
import java.util.List;

// Add the file name in this if required

public enum JSONFilePath {
	SAMPLE("Sample.json");
	
    private final String stringValue;


	JSONFilePath(String stringValue) {
		this.stringValue = stringValue;
	}
	
    public String getStringValue() {
        return stringValue;
    }
    
    public static List<String> getAllStringValues() {
        List<String> stringValues = new ArrayList<>();
        for (JSONFilePath val : JSONFilePath.values()) {
            stringValues.add(val.getStringValue());
        }
        return stringValues;
    }

}
