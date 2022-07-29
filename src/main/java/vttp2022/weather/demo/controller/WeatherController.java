package vttp2022.weather.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.weather.demo.Service.WeatherSvc;
import vttp2022.weather.demo.models.Weather;

@Controller
@RequestMapping(path= "/weather")
public class WeatherController {
    
    @Autowired
    private WeatherSvc weatherSvc;

    @GetMapping
    public String getWeather(@RequestParam(required = true) String city, Model model){

        //Optional -> the data that is retrieved from the API, this can take value or no value 
        Optional<Weather> opt = weatherSvc.getWeather(city);
        if(opt.isEmpty()){
            return "weather"; 
        }

        model.addAttribute("weather", opt.get());
        return "weather";
    }
}
