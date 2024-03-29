import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class City {


    public int cityId;
    private String cityName;
    private int currentTemperature;
    private int currentHumidity;
    private int currentWindSpeed;

    public City(){}

    public City (int cityId,String cityName,int currentTemperature,int currentHumidity,int currentWindSpeed){
        this.cityId=cityId;
        this.cityName = cityName;
        this.currentTemperature = currentTemperature;
        this.currentHumidity = currentHumidity;
        this.currentWindSpeed = currentWindSpeed;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(int currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public int getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public void setCurrentWindSpeed(int currentWindSpeed) {
        this.currentWindSpeed = currentWindSpeed;
    }

}
