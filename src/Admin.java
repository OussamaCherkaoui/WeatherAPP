import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Admin {
    int id;
    String name;
    int temperature;
    int humidity;
    int windSpeed;
    String dateHistory;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public Admin() {
    }

    public boolean addCity(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Remplir les informations de la ville :");
        System.out.print("\tID : ");id=Menu.scanner.nextInt();
        ResultSet resultSet = statement.executeQuery("select * from City where cityId='"+id+"'");
        while(resultSet.next()) {
            System.out.println("Id dèja reserver !! Ressayer avec un autre Id");
            System.out.print("\tID : ");id=Menu.scanner.nextInt();
            resultSet = statement.executeQuery("select * from City where cityId='"+id+"'");
        }
        System.out.print("\tNom ville : ");name=Menu.scanner.next();
        resultSet = statement.executeQuery("select * from City where cityName='"+name+"'");
        while(resultSet.next()) {
            System.out.println("Ville dèja ajouter !!");
            return false;
        }
        System.out.print("\tTempérature : ");temperature=Menu.scanner.nextInt();
        System.out.print("\tHumidité : ");humidity=Menu.scanner.nextInt();
        System.out.print("\tVitesse de vent : ");windSpeed=Menu.scanner.nextInt();
        statement.executeUpdate("insert into City (cityId,cityName,currentTemperature,currentHumidity,currentWindSpeed) values ('"+id+"','"+name+"','"+temperature+"','"+humidity+"','"+windSpeed+"')");
        System.out.println("Ville ajoutée avec succées");
        statement.close();
        connection.close();
        return true;
    }
    public boolean updateCity(Connection connection) throws SQLException, ClassNotFoundException {
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
            connection.close();
        }
        else {
            System.out.println("Ville introuvable !!");
            return false;
        }
        return true;
    }
    public boolean deleteCity (Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        System.out.println("Quelle ville Voulez-vous supprimer ?");
        System.out.print("Nom ville :");name=Menu.scanner.next();
        ResultSet resultSet = statement.executeQuery("select * from City where cityName='"+name+"'");
        if(!resultSet.next()) {
            System.out.println("Ville introuvable !!");
            return false;
        }
        String query = "DELETE FROM City WHERE cityName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        System.out.println("Suppression effectué avec succées");
        statement.close();
        connection.close();
        return true;
    }
    public void addHistory(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        System.out.println("Remplir les informations de la ville :");
        System.out.print("\tID Historique : ");id=Menu.scanner.nextInt();
        ResultSet resultSet = statement.executeQuery("select * from CityHistory where historicalDataId='"+id+"'");
        while(resultSet.next()) {
            System.out.println("Id dèja utilisé !! Ressayer avec un autre Id..");
            System.out.print("\tID Historique : ");id=Menu.scanner.nextInt();
            resultSet = statement.executeQuery("select * from CityHistory where historicalDataId='"+id+"'");
        }
        System.out.print("\tVille : ");name=Menu.scanner.next();
        resultSet = statement.executeQuery("select * from City where cityName='"+name+"'");
        while(!resultSet.next()) {
            System.out.println("Ville introuvable !! Ressayer..");
            System.out.print("\tVille : ");name=Menu.scanner.next();
            resultSet = statement.executeQuery("select * from City where cityName='"+name+"'");
        }
        System.out.print("\tDate de l'événement météorologique (yyyy-MM-dd) : ");dateHistory = Menu.scanner.next();
        LocalDate date = LocalDate.parse(dateHistory, dateFormatter);
        statement.executeUpdate("insert into CityHistory (historicalDataId,eventDate,temperature,cityId) values ('"+id+"','"+date+"','"+resultSet.getInt("currentTemperature")+"','"+resultSet.getInt("cityId")+"')");
        System.out.println("Historique ajoutée avec succées");
        statement.close();
        connection.close();
    }

    public boolean updateHistory(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Quelle Historique Voullez-vous Modifier (Id)?");
        System.out.print("\tID Historique : ");id=Menu.scanner.nextInt();
        ResultSet resultSet = statement.executeQuery("select * from CityHistory where historicalDataId='"+id+"'");
        if(!resultSet.next()) {
            System.out.println("Historique introuvable !!");
            return false;
        }
        int choix;
        System.out.println("-------------Quelle information Voullez-vous Modifier ?----------");
        System.out.println("\t1-Date de l'événement météorologique");
        System.out.println("\t2-Température");
        System.out.println("-----------------------------------------------------------------");
        System.out.print("CHOISISSEZ : ");
        choix = Menu.scanner.nextInt();

        String query;
        PreparedStatement preparedStatement;

        switch (choix) {
            case 1:
                System.out.print("\tDate de l'événement météorologique (yyyy-MM-dd) : ");dateHistory = Menu.scanner.next();
                LocalDate date = LocalDate.parse(dateHistory, dateFormatter);
                query = "UPDATE CityHistory SET eventDate = ? WHERE historicalDataId = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setInt(2,id );
                preparedStatement.executeUpdate();
                break;
            case 2:
                System.out.print("\tTempérature : ");temperature = Menu.scanner.nextInt();
                query = "UPDATE CityHistory SET temperature = ? WHERE historicalDataId = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, temperature);
                preparedStatement.setInt(2,id );
                preparedStatement.executeUpdate();
                break;
            default:
                System.out.println("CHOIX INVALID !! Ressayer..");
                updateHistory(connection);
                break;
        }

        System.out.println("Modification effectuer avec succées");
        statement.close();
        connection.close();
        return true;
    }

    public boolean deleteHistory(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Quelle Historique Voulez-vous supprimer (id)?");
        System.out.print("\tID Historique : ");id=Menu.scanner.nextInt();
        ResultSet resultSet = statement.executeQuery("select * from CityHistory where historicalDataId='"+id+"'");
        if(!resultSet.next()) {
            System.out.println("Historique introuvable !!");
            return false;
        }
        String query = "DELETE FROM CityHistory WHERE historicalDataId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Suppression effectué avec succées");
        statement.close();
        connection.close();
        return true;
    }
}
