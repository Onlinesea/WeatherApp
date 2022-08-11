package vttp2022.weather.demo.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.JsonObject;

public class Conditions {

    private static final Logger logger = LoggerFactory.getLogger(Conditions.class); 

    private String icon;
    private String description;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    //Setting the description of the conditions to main-description from the json file 
    public static Conditions createJson(JsonObject o){
        Conditions c = new Conditions();
        c.description ="%S-%s".formatted(o.getString("main"),o.getString("description"));
        c.icon="%s".formatted(o.getString("icon"));
        
        return c;
    }

}
