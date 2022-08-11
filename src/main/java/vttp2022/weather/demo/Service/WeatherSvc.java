package vttp2022.weather.demo.Service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.weather.demo.models.Weather;

@Service 
public class WeatherSvc {

    private static final Logger logger = LoggerFactory.getLogger(WeatherSvc.class);

    private static String URL = "https://api.openweathermap.org/data/2.5/weather";
/* 
    @Value("${open.weather.map}")
    private String apiKey;

    private boolean hasKey;

    @PostConstruct
    private void init(){
        hasKey =null !=apiKey;
        logger.info(">>> API KEY set :" + hasKey);
    }
*/

    //To get the values from the websites
    public Optional<Weather> getWeather (String city){
        
        // the apikey will be inserted into the heroku app under Config Vars
        String apiKey = System.getenv("OPEN_WEATHER_MAP_API_KEY");

        //Using the in build formula to build the get request
        String weatherUrl = UriComponentsBuilder.fromUriString(URL)
            .queryParam("q",city.replaceAll(" ", "+"))
            .queryParam("units", "metric")
            .queryParam("appid", apiKey)
            .toUriString();
            logger.info(">>>> Complete Weather URI API address : " + weatherUrl);

            //Initialise the restTemplate
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try{
                resp = template.getForEntity(weatherUrl, String.class);
                Weather w = Weather.create(resp.getBody());
                return Optional.of(w);
                
            }catch(Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            return Optional.empty();
    }
    
}
