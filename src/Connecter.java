import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connecter {

    public static final String url ="jdbc:mysql://localhost:3306/Meteo";
    public static final String username="root";
    public static final String password="";
    public static Connection getconnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
    }
}
