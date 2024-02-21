import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

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

    int id;
    String name;
    int temperature;
    int humidity;
    int windSpeed;


    public void addCity(Connection connection) throws SQLException {
        System.out.println("Remplir les informations de la ville :");
        System.out.print("\tID : ");id=Menu.scanner.nextInt();
        System.out.print("\tNom ville : ");name=Menu.scanner.next();
        System.out.print("\tTempérature : ");temperature=Menu.scanner.nextInt();
        System.out.print("\tHumidité : ");humidity=Menu.scanner.nextInt();
        System.out.print("\tVitesse de vent : ");windSpeed=Menu.scanner.nextInt();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into City (cityId,cityName,currentTemperature,currentHumidity,currentWindSpeed) values ('"+cityId+"','"+cityName+"','"+currentTemperature+"','"+currentHumidity+"','"+currentWindSpeed+"')");
        System.out.println("Ville ajoutée avec succées");
        statement.close();
        connection.close();
    }
    public void showAllCity(Connection connection) throws SQLException{
        int i=1;
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
        for(City city:cityList)
        {
            System.out.println("Ville "+i);
            System.out.println("\tID : "+city.cityId);
            System.out.println("\tNom ville : "+city.getCityName());
            System.out.println("\tTempérature : "+city.getCurrentTemperature());
            System.out.println("\tHumidité : "+city.getCurrentHumidity());
            System.out.println("\tVitesse de vent : "+city.getCurrentWindSpeed());
            i++;
        }
        statement.close();
        connection.close();
    }
    public void searchCity(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        System.out.println("Quelle ville Chercher-Vous ?");
        System.out.print("Nom ville :");name=Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * from City where cityName='"+name+"' order by cityId asc");
        while(resultSet.next())
        {
            System.out.println("\tID : "+resultSet.getInt("cityId"));
            System.out.println("\tNom ville : "+resultSet.getString("cityName"));
            System.out.println("\tTempérature : "+resultSet.getInt("currentTemperature"));
            System.out.println("\tHumidité : "+resultSet.getInt("currentHumidity"));
            System.out.println("\tVitesse de vent : "+resultSet.getInt("currentWindSpeed"));
        }
        statement.close();
        connection.close();
    }
    public void updateCity(Connection connection) throws SQLException, ClassNotFoundException {
        Statement statement = connection.createStatement();
        System.out.println("Quelle ville Voullez-vous Modifier ?");
        System.out.print("Nom ville :");
        name = Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * from City where cityName='" + name + "' order by cityId asc");
        if (resultSet.next()) {
            int choix;
            System.out.println("-------------Quelle information Voullez-vous Modifier ?----------");
            System.out.println("\t1-NOM");
            System.out.println("\t2-Température");
            System.out.println("\t3-Humidité");
            System.out.println("\t4-Vitesse vent");
            System.out.println("-----------------------------------------------------------------");
            System.out.print("CHOISISSEZ : ");
            choix = Menu.scanner.nextInt();
            String query;
            PreparedStatement preparedStatement;
            switch (choix) {
                case 1:
                    String nameupdate;
                    System.out.print("Nom ville :");nameupdate = Menu.scanner.next();
                    query = "UPDATE City SET cityName = ? WHERE cityName = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,nameupdate);
                    preparedStatement.setString(2,name );
                    preparedStatement.executeUpdate();
                    break;
                case 2:
                    System.out.print("Température :");temperature = Menu.scanner.nextInt();
                    query = "UPDATE City SET currentTemperature = ? WHERE  cityName = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, temperature);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                    break;
                case 3:
                    System.out.print("Humidité :");humidity = Menu.scanner.nextInt();
                    query = "UPDATE City SET currentHumidity = ? WHERE cityName = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, humidity);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                    break;
                case 4:
                    System.out.print("Vitesse vent :");windSpeed = Menu.scanner.nextInt();
                    query = " UPDATE City SET currentWindSpeed = ? WHERE cityName = ? ";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, windSpeed);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                    break;
                default:
                    System.out.println("CHOIX INVALID !! Ressayer..");
                    updateCity(connection);
                    break;
            }

            System.out.println("Modification effectuer avec succées");
            statement.close();
            showAllCity(connection);
        }
    }
        public void deleteCity (Connection connection) throws SQLException{
            Statement statement = connection.createStatement();
            System.out.println("Quelle ville Voulez-vous supprimer ?");
            System.out.print("Nom ville :");name=Menu.scanner.next();
            String query = "DELETE FROM City WHERE cityName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            System.out.println("Suppression effectué avec succées");
            statement.close();
            showAllCity(connection);
        }
}
