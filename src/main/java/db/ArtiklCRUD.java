package main.java.db;

import main.java.application.Main;
import main.java.model.Artikl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtiklCRUD {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String DATABASE_EXCEPTION_MESSAGE = "Ups, something went wrong with database table: ";

    DatabaseConnections dbConnections = new DatabaseConnections();
    List<Artikl> listaArtikla = new ArrayList<>();

    public void create(Artikl artikl) throws SQLException, IOException {
        PreparedStatement ps = dbConnections.preparedStatementExecuteUpdate("INSERT INTO Roba(NazivArtikla, Kolicina, Cijena, Opis, Jmj) VALUES (?,?,?,?,?)");
        ps.setString(1, artikl.getNaziv());
        ps.setString(2, artikl.getKolicina().toString());
        ps.setString(3, artikl.getCijena().toString());
        ps.setString(4, "default describe");
        ps.setString(5, artikl.getJedinicnaMjera());
        ps.executeUpdate();
    }

    public List<Artikl> get() throws IOException, SQLException {
        ResultSet rs = dbConnections.resultSetQueryExecuter("SELECT * FROM Roba WHERE 1 = 1");
        while (rs.next()) {
            Long id = rs.getLong("IDRobe");
            String nazivArtikla = rs.getString("NazivArtikla");
            Integer kolicina = rs.getInt("Kolicina");
            BigDecimal cijena = rs.getBigDecimal("Cijena");
            // String opis = rs.getString("Opis");
            String jedinicnaMjera = rs.getString("Jmj");
            listaArtikla.add(new Artikl(id, nazivArtikla, kolicina, cijena, jedinicnaMjera));
        }
        return listaArtikla;
    }

    public void update(Long id){

    }

    public void delete(Long id){

    }
}
