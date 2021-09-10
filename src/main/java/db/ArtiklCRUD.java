package main.java.db;

import main.java.application.Main;
import main.java.iznimke.IznimkaSlanjePodatakaPremaBazi;
import main.java.iznimke.IznimkuDohvacanjaPodatakaIzBaze;
import main.java.model.Artikl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtiklCRUD {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String IZNIMKA_BAZE_PODATAKA = "Nešto je pošlo po zlu, objašnjenje slijedi u iznimci:\n";
    private final List<Artikl> listaArtikla = new ArrayList<>();

    Database bazaRepozitorij = new Database();

    public void create(Artikl artikl) throws SQLException, IOException {
        PreparedStatement ps = bazaRepozitorij.izvrsiAzuriranjePoQueryu(
                "INSERT INTO Roba(NazivArtikla, Kolicina, Cijena, Opis, Jmj) VALUES (?,?,?,?,?)");
        try (ps) {
            slanjePodatakaPremaBazi(artikl, ps);
        } catch (IznimkaSlanjePodatakaPremaBazi iznimka) {
            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
            System.err.println(Arrays.toString(iznimka.getStackTrace()));
        }
    }

    public List<Artikl> get() throws IOException, SQLException {
        ResultSet rs = bazaRepozitorij.izvrsiUpitPoQueryu("SELECT * FROM Roba WHERE 1 = 1");
        try (rs) {
            dohvacanjePodatkaIzBaze(rs);
        } catch (IznimkuDohvacanjaPodatakaIzBaze iznimka) {
            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
            System.err.println(Arrays.toString(iznimka.getStackTrace()));
        }
        return listaArtikla;
    }

    public void update(Long id) {

    }

    public void delete(Long id) {

    }

    private void slanjePodatakaPremaBazi(Artikl artikl, PreparedStatement ps) throws SQLException {
        ps.setString(1, artikl.getNaziv());
        ps.setString(2, artikl.getKolicina().toString());
        ps.setString(3, artikl.getCijena().toString());
        ps.setString(4, "default describe");
        ps.setString(5, artikl.getJedinicnaMjera());
        ps.executeUpdate();
    }

    private void dohvacanjePodatkaIzBaze(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Long id = rs.getLong("IDRobe");
            String nazivArtikla = rs.getString("NazivArtikla");
            Integer kolicina = rs.getInt("Kolicina");
            BigDecimal cijena = rs.getBigDecimal("Cijena");
            String jedinicnaMjera = rs.getString("Jmj");
            listaArtikla.add(new Artikl(id, nazivArtikla, kolicina, cijena, jedinicnaMjera));
        }
    }
}
