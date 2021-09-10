package main.java.db;

import main.java.application.Main;
import main.java.iznimke.IznimkaSlanjePodatakaPremaBazi;
import main.java.iznimke.IznimkuDohvacanjaPodatakaIzBaze;
import main.java.model.Izdatnica;
import main.java.model.Poduzece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IzdatnicaCRUD {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String IZNIMKA_BAZE_PODATAKA = "Nešto je pošlo po zlu, objašnjenje slijedi u iznimci:\n";
    private final List<Izdatnica> listaIzdatnica = new ArrayList<>();

    Database bazaRepozitorij = new Database();
    PoduzeceCRUD poduzeceRepozitorij = new PoduzeceCRUD();

    public void create(Izdatnica izdatnica) throws IOException, SQLException {
        PreparedStatement ps = bazaRepozitorij
                .izvrsiAzuriranjePoQueryu("INSERT INTO Izdatnica(IDFirme, Datum) VALUES (?, ?)");
        try (ps) {
            slanjePodatakaPremaBazi(izdatnica, ps);
        } catch (IznimkaSlanjePodatakaPremaBazi iznimka) {
            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
            System.err.println(Arrays.toString(iznimka.getStackTrace()));
        }
    }

    public List<Izdatnica> get() throws SQLException, IOException {
        ResultSet rs = bazaRepozitorij.izvrsiUpitPoQueryu("SELECT * FROM Izdatnica WHERE 1 = 1");
//        ResultSet rs2 = dbConnections.resultSetQueryExecuter("SELECT * FROM IZDATNICA WHERE 1 = 1 " +
//                "AND WHERE Izdatnica.IDFirme FROM Izdatnica INNERJOIN Izdatnica ON Firme.IDFirme = Izdatnica.IdFirme");
        try (rs) {
            dohvacanjePodatakaIzBaze(rs);
        } catch (IznimkuDohvacanjaPodatakaIzBaze iznimka) {
            logger.error(IZNIMKA_BAZE_PODATAKA + " " + iznimka);
            System.err.println(Arrays.toString(iznimka.getStackTrace()));
        }
        return listaIzdatnica;
    }

    public void update(Long id) {

    }

    public void delete(Long id) {

    }

    private void slanjePodatakaPremaBazi(Izdatnica izdatnica, PreparedStatement ps) throws SQLException {
        ps.setLong(1, izdatnica.getPoduzeceID().getId());
        ps.setString(2, izdatnica.getDatumIzdatnice().toString());
        ps.executeUpdate();
    }

    private void dohvacanjePodatakaIzBaze(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Long id = rs.getLong("IDIzdatnice");
            Long idPoduzeca = rs.getLong("IDFirme");
            Poduzece poduzeceId = poduzeceRepozitorij.getPoduzeceById(idPoduzeca);
            LocalDate datum = rs.getDate("Datum").toLocalDate();
            Izdatnica novaIzdatnica = new Izdatnica(id, poduzeceId, datum);
            listaIzdatnica.add(novaIzdatnica);
        }
    }

}
