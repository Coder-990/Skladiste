package main.java.db;

import java.io.IOException;
import java.sql.*;

public interface DatabaseConnection {

    default Connection connectToDatabase() throws SQLException {
        return DriverManager
                .getConnection("jdbc:ucanaccess://D://JAVA//Projekti//JAVA2021//" +
                        "Seminarski i diplomski//Skladiste//accessdb//DBSKLADISTE10.mdb");
    }

    ResultSet izvrsiUpitPoQueryu(String upit) throws IOException, SQLException;

    PreparedStatement izvrsiAzuriranjePoQueryu(String upit) throws IOException, SQLException;
}
