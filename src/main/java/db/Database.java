package main.java.db;

import java.io.IOException;
import java.sql.*;

public class Database implements DatabaseConnection {

    @Override
    public ResultSet izvrsiUpitPoQueryu(String upit) throws IOException, SQLException {
        Connection con = connectToDatabase();
        Statement st = con.createStatement();
        return st.executeQuery(upit);
    }

    @Override
    public PreparedStatement izvrsiAzuriranjePoQueryu(String upit) throws IOException, SQLException {
        Connection con = connectToDatabase();
        return con.prepareStatement(upit);
    }
}
