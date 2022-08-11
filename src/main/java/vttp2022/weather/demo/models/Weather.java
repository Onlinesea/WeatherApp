package vttp2022.weather.demo.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather {

    private static final Logger logger = LoggerFactory.getLogger(Weather.class); 

    private String city;

    private String temperature; 

    public List<Conditions> conditions =new LinkedList<>();

    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public List<Conditions> getConditions() {
        return conditions;
    }
    public void setConditions(List<Conditions> conditions) {
        this.conditions = conditions;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public void addCondition (String description, String icon){
        Conditions c = new Conditions();
        c.setDescription(description);
        c.setIcon(icon);
        conditions.add(c);
    }

    //Receive the json file from the API and creating an object out of it 
    public static Weather create(String json) throws IOException{
        Weather w = new Weather();

        //Converting the json file to inputstream 
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            //Using a json reader to read the inputStream(bytes) by the json file
            JsonReader r = Json.createReader(is);

            //Using JsonObject to read the json file into json object
            //The object that will be build will be similiar to the gameboard
            JsonObject o = r.readObject();

            logger.info(o.toString());

            //w is the new weather object while o is the full json object
            //You can get the information based on the key that you put in
            w.city = o.getString("name");

            logger.info(" city name > " + w.city);
 
            //the key "main" contains information on key such as "temp"
            JsonObject mainObj = o.getJsonObject("main"); 
            logger.info("mainObj" + mainObj.toString());

            //.getJsonNumber will return the number value which the name is mapped
            w.temperature = mainObj.getJsonNumber("temp").toString();

            //
            w.conditions = o.getJsonArray("weather").stream()
            .map(v -> (JsonObject)v)
            .map(v -> Conditions.createJson(v))
            .toList();


        }catch(IOException e){
            e.printStackTrace();
        }
        return w; 
    }


}
