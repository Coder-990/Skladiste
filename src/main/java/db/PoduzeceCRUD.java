package main.java.db;

import main.java.application.Main;
import main.java.dto.PoduzeceToDto;
import main.java.iznimke.IznimkaSlanjePodatakaPremaBazi;
import main.java.iznimke.IznimkuDohvacanjaPodatakaIzBaze;
import main.java.model.Poduzece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoduzeceCRUD {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String IZNIMKA_BAZE_PODATAKA = "Nešto je pošlo po zlu, objašnjenje slijedi u iznimci:\n";
    private final List<Poduzece> listaPoduzeca = new ArrayList<>();

    Database bazaRepozitorij = new Database();

    HibernatePoduzece hb = new HibernatePoduzece();
    public void create(PoduzeceToDto poduzece) throws IOException, SQLException {
//        PreparedStatement ps = bazaRepozitorij.izvrsiAzuriranjePoQueryu(
//                "INSERT INTO Firme(OIBFirme, NazivFirme) VALUES (?, ?)");
//        try (ps) {
//            slanjaPodatakaPremaBazi(poduzece, ps);
            hb.create(poduzece);
//        } catch (IznimkaSlanjePodatakaPremaBazi iznimka) {
//            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
//            System.err.println(Arrays.toString(iznimka.getStackTrace()));
//        }
    }

    public List<PoduzeceToDto> get() throws SQLException, IOException {
//        ResultSet rs = bazaRepozitorij.izvrsiUpitPoQueryu("SELECT * FROM Firme WHERE 1 = 1");
//        try (rs) {
//            dohvacanjePodatakaIzBaze(rs);
//        } catch (IznimkuDohvacanjaPodatakaIzBaze iznimka) {
//            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
//            System.err.println(Arrays.toString(iznimka.getStackTrace()));
//        }
        return hb.get();
//                listaPoduzeca;
    }

    public void update(Long id) {

    }

    public void delete(Long id) {

    }

    Poduzece getPoduzeceById(Long id) {
        return new Poduzece(id, "", "");
    }

    private void slanjaPodatakaPremaBazi(Poduzece poduzece, PreparedStatement ps) throws SQLException {
        ps.setString(1, poduzece.getOib());
        ps.setString(2, poduzece.getNaziv());
        ps.executeUpdate();
    }

    private void dohvacanjePodatakaIzBaze(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Long id = rs.getLong("IDFirme");
            String naziv = rs.getString("NazivFirme");
            String oib = rs.getString("OIBFirme");
            Poduzece novoPoduzece = new Poduzece(id, naziv, oib);
            listaPoduzeca.add(novoPoduzece);
        }
    }
}
