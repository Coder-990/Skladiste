package main.java.db;

import main.java.model.Izdatnica;
import main.java.model.Poduzece;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IzdatnicaCRUD {
    DatabaseConnections dbConnections = new DatabaseConnections();
    PoduzeceCRUD poduzeceCRUD = new PoduzeceCRUD();
    List<Izdatnica> listaIzdatnica = new ArrayList<>();

    public void create(Izdatnica izdatnica) throws IOException, SQLException {
        PreparedStatement ps = dbConnections.preparedStatementExecuteUpdate("INSERT INTO Izdatnica(IDFirme, Datum) VALUES (?, ?)");
        ps.setLong(1, izdatnica.getPoduzeceID().getId());
        ps.setString(2, izdatnica.getDatumIzdatnice().toString());
        ps.executeUpdate();
    }

    public List<Izdatnica> get() throws SQLException, IOException {
        ResultSet rs = dbConnections.resultSetQueryExecuter("SELECT * FROM Izdatnica WHERE 1 = 1");
        while (rs.next()) {
            Long id = rs.getLong("IDIzdatnice");
            Long idPoduzeca = rs.getLong("IDFirme");
            Poduzece poduzeceId = poduzeceCRUD.getPoduzeceById(idPoduzeca);
            LocalDate datum = rs.getDate("Datum").toLocalDate();
            Izdatnica novaIzdatnica = new Izdatnica(id, poduzeceId, datum);
            listaIzdatnica.add(novaIzdatnica);
        }
        return listaIzdatnica;
    }
}
