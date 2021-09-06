package main.java.db;

import main.java.application.Main;
import main.java.model.Artikl;
import main.java.model.Poduzece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Msdb {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String DATABASE_EXCEPTION_MESSAGE = "Ups, something went wrong with database table: ";

    private Connection connectToDatabase() throws SQLException, IOException {
        return DriverManager
                .getConnection("jdbc:ucanaccess://D://JAVA//Projekti//JAVA2021//Seminarski i diplomski//Skladiste//accessdb//DBSKLADISTE10.mdb");
    }

    public void izvrsiUpit(StringBuilder upit) throws IOException, SQLException {
        Connection con = connectToDatabase();
        Statement st;
        try {
            st = con.createStatement();
            st.executeQuery(upit.toString());
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

    }

    public List<Artikl> getArtikl() {

        List<Artikl> listaArtikla = new ArrayList<>();

        try (Connection con = connectToDatabase()) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Roba WHERE 1 = 1");

            while (rs.next()) {
                Long id = rs.getLong("IDRobe");
                String nazivArtikla = rs.getString("NazivArtikla");
                Integer kolicina = rs.getInt("Kolicina");
                BigDecimal cijena = rs.getBigDecimal("Cijena");
                // String opis = rs.getString("Opis");
                String jedinicnaMjera = rs.getString("Jmj");

                Artikl noviArtikl = new Artikl(id, nazivArtikla, kolicina, cijena, jedinicnaMjera);
                listaArtikla.add(noviArtikl);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return listaArtikla;
    }

    public void createArtikl(Artikl artikl) {

        try (Connection con = connectToDatabase()) {
            String str = "default describe";
            PreparedStatement ps = con.prepareStatement("INSERT INTO Roba(NazivArtikla, Kolicina, Cijena, Opis, Jmj) VALUES (?,?,?,?,?)");
            ps.setString(1, artikl.getNaziv());
            ps.setString(2, artikl.getKolicina().toString());
            ps.setString(3, artikl.getCijena().toString());
            ps.setString(4, str);
            ps.setString(5, artikl.getJedinicnaMjera());
            ps.executeUpdate();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Poduzece> getPoduzece() throws SQLException, IOException {

        List<Poduzece> listaPoduzeca = new ArrayList<>();

        try (Connection con = connectToDatabase()) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Firme WHERE 1 = 1");

            while (rs.next()) {
                Long id = rs.getLong("IDFirme");
                String naziv = rs.getString("NazivFirme");
                String oib = rs.getString("OIBFirme");

                Poduzece novoPoduzece = new Poduzece(id, naziv, oib);
                listaPoduzeca.add(novoPoduzece);
            }

        } catch (SQLException | IOException ex) {
            System.err.println(DATABASE_EXCEPTION_MESSAGE + "'Poduzece'!\n " + ex);
            logger.error(DATABASE_EXCEPTION_MESSAGE + "'Poduzece'!", ex);
        }
        return listaPoduzeca;
    }

    public void createPoduzece(Poduzece poduzece) throws IOException, SQLException {

        try (Connection con = connectToDatabase()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Firme(OIBFirme, NazivFirme) VALUES (?, ?)");
            ps.setString(1, poduzece.getOib());
            ps.setString(2, poduzece.getNaziv());
            ps.executeUpdate();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }

    }

//    public static List<Izdatnica> dohvatiIzdatnicu(Izdatnica p_izdatnice) {
//
//        List<Izdatnica> m_listaIzdatnica = new ArrayList<>();
//
//        try (Connection con = ConnectToDatabase()) {
//            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Izdatnica WHERE 1 = 1");
//
//            if (Optional.ofNullable(p_izdatnice).isEmpty() == false) {
//
//                if (Optional.ofNullable(p_izdatnice).map(Izdatnica::getId).isPresent()) {
//                    sqlQuery.append("AND ID = " + p_izdatnice.getId());
//                }
//
//                if (Optional.ofNullable(p_izdatnice.getIdFirme().getNazivFirme()).isPresent()) {
//                    sqlQuery.append("AND NAZIV FIRME LIKE '%" + p_izdatnice.getIdFirme().getNazivFirme() + "%'");
//                }
//
//                if (Optional.ofNullable(p_izdatnice.getDatumIzdatnice()).isPresent()) {
//                    sqlQuery.append("AND DATUM LIKE '%"
//                            + p_izdatnice.getDatumIzdatnice().format(DateTimeFormatter.ISO_DATE) + "%'");
//                }
//            }
//
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sqlQuery.toString());
//
//            while (rs.next()) {
//                Long id = rs.getLong("IDIzdatnice");
//                String nazivFirme = rs.getString("IDFirme");
//                Company company = getCompanyByName(nazivFirme);
//                LocalDate datumIzdatnice = rs.getTimestamp("Datum").toInstant().atZone(ZoneId.systemDefault())
//                        .toLocalDate();
//
//                Izdatnica novaIzdatnica = new Izdatnica(id, company, datumIzdatnice);
//
//                m_listaIzdatnica.add(novaIzdatnica);
//
//            }
//
//        } catch (Exception ex) {
//            log.error(DATABASE_EXCEPTION_MESSAGE + "'Izdatnica'!", ex);
//            throw new DatabaseException();
//        } finally {
//            ConnectToDatabase().close();
//        }
//
//        return m_listaIzdatnica;
//    }
}
