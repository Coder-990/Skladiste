package main.java.db;

import main.java.model.Entitet;
import main.java.model.Poduzece;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoduzeceCRUD {

    DatabaseConnections dbConnections = new DatabaseConnections();
    List<Poduzece> listaPoduzeca = new ArrayList<>();

    public void create(Poduzece poduzece) throws IOException, SQLException {
        PreparedStatement ps = dbConnections.preparedStatementExecuteUpdate("INSERT INTO Firme(OIBFirme, NazivFirme) VALUES (?, ?)");
        ps.setString(1, poduzece.getOib());
        ps.setString(2, poduzece.getNaziv());
        ps.executeUpdate();
    }

    public List<Poduzece> get() throws SQLException, IOException {
        ResultSet rs = dbConnections.resultSetQueryExecuter("SELECT * FROM Firme WHERE 1 = 1");
        while (rs.next()) {
            Long id = rs.getLong("IDFirme");
            String naziv = rs.getString("NazivFirme");
            String oib = rs.getString("OIBFirme");
            Poduzece novoPoduzece = new Poduzece(id, naziv, oib);
            listaPoduzeca.add(novoPoduzece);
        }
        return listaPoduzeca;
    }

    public Poduzece getPoduzceByName(String nazivPoduzeca) throws SQLException, IOException {
        return new Poduzece(null, nazivPoduzeca, null);
    }

    public Poduzece getPoduzeceById(Long id){
        return new Poduzece(id, null,null);
    }
}
