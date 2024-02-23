import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User {

    int id;
    String name;
    int temperature;
    int humidity;
    int windSpeed;
    String dateHistory;

    public void showAllCity(Connection connection) throws SQLException {
        AtomicInteger index = new AtomicInteger(1);
        ArrayList<City> cityList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from City order by cityId asc");
        while(resultSet.next())
        {
            id = resultSet.getInt("cityId");
            name = resultSet.getString("cityName");
            temperature = resultSet.getInt("currentTemperature");
            humidity = resultSet.getInt("currentHumidity");
            windSpeed = resultSet.getInt("currentWindSpeed");

            City city = new City(id, name, temperature, humidity, windSpeed);
            cityList.add(city);
        }
        cityList.stream()
                .forEach(city -> {
                    int i = index.getAndIncrement();
                    System.out.println("--------------- Ville " + i+" ---------------");
                    System.out.println("\tID : " + city.cityId);
                    System.out.println("\tNom ville : " + city.getCityName());
                    System.out.println("\tTempérature : " + city.getCurrentTemperature());
                    System.out.println("\tHumidité : " + city.getCurrentHumidity());
                    System.out.println("\tVitesse de vent : " + city.getCurrentWindSpeed());
                });
        statement.close();
        connection.close();
    }
    public boolean searchCity(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        System.out.println("Quelle ville Chercher-Vous ?");
        System.out.print("Nom ville :");name=Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * from City where cityName='"+name+"' order by cityId asc");
        if(!resultSet.next())
        {
            System.out.println("Ville introuvable !!");
            return false;
        }
        else {
            System.out.println("------------------------------------------------");
            System.out.println("\tID : "+resultSet.getInt("cityId"));
            System.out.println("\tNom ville : "+resultSet.getString("cityName"));
            System.out.println("\tTempérature : "+resultSet.getInt("currentTemperature"));
            System.out.println("\tHumidité : "+resultSet.getInt("currentHumidity"));
            System.out.println("\tVitesse de vent : "+resultSet.getInt("currentWindSpeed"));
        }
        statement.close();
        connection.close();
        return true;
    }

    public boolean temperatureNowOfCity(Connection connection) throws SQLException {
        ArrayList<HistoryOfCity> temprature = new ArrayList<>();
        Statement statement = connection.createStatement();
        System.out.println("QUELLE VILLE VOULLEZ-VOUS AFFICHER SES PREVISION METEOROLOGIQUES ACTUELLES ?");
        System.out.print("Nom ville :");name=Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * FROM city where cityName='"+name+"'");
        if (!resultSet.next())
        {
            System.out.println("Aucune ville trouvé !!");
            return false;
        }
        resultSet=statement.executeQuery("SELECT distinct cityName , currentTemperature,currentHumidity,currentWindSpeed FROM city where cityName='"+name+"'");
        if(resultSet.next())
        {
            System.out.println("----------------------- les prévisions météorologiques actuelles pour la ville "+resultSet.getString("cityName")+" -------------------------");
            System.out.println("\tTempérature : "+resultSet.getInt("currentTemperature"));
            System.out.println("\tHumidité : "+resultSet.getInt("currentHumidity"));
            System.out.println("\tVitesse de vent : "+resultSet.getInt("currentWindSpeed"));
        }
        statement.close();
        connection.close();
        return true;
    }
    public boolean historyOfCity(Connection connection) throws SQLException {
        ArrayList<HistoryOfCity> HistoryList = new ArrayList<>();
        Statement statement = connection.createStatement();
        System.out.println("Quelle ville Voulez-vous afficher son historique ?");
        System.out.print("Nom ville :");name=Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * FROM cityhistory inner join city on cityhistory.cityId = city.cityId where cityName='"+name+"'");
        while(!resultSet.next())
        {
            System.out.println("Aucun historique trouvé !! ");
            return false;
        }
        resultSet=statement.executeQuery("SELECT city.cityName,cityhistory.eventDate,cityhistory.temperature FROM cityhistory inner join city on cityhistory.cityId = city.cityId where cityName='"+name+"'");
        if(resultSet.next())
        {
            name = resultSet.getString("cityName");
            dateHistory = resultSet.getString("eventDate");
            temperature = resultSet.getInt("temperature");

            HistoryOfCity historyOfCity = new HistoryOfCity(name, dateHistory, temperature);
            HistoryList.add(historyOfCity);
        }
        HistoryList.stream().forEach(history -> {history.toString();});
        statement.close();
        connection.close();
        return true;
    }
}
