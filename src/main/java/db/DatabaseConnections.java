package main.java.db;

import java.io.IOException;
import java.sql.*;

public class DatabaseConnections {

    private Connection connectToDatabase() throws SQLException, IOException {
        return DriverManager
                .getConnection("jdbc:ucanaccess://D://JAVA//Projekti//JAVA2021//Seminarski i diplomski//Skladiste//accessdb//DBSKLADISTE10.mdb");
    }

    ResultSet resultSetQueryExecuter(String upit) throws IOException, SQLException {
        Connection con = connectToDatabase();
        Statement st = con.createStatement();
        return st.executeQuery(upit);
    }

    PreparedStatement preparedStatementExecuteUpdate(String upit) throws IOException, SQLException {
        Connection con = connectToDatabase();
        return con.prepareStatement(upit);
    }

//    public List<Izdatnica> getIzdatnicu() {
//
//        List<Izdatnica> listaIzdatnica = new ArrayList<>();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
//        try (Connection con = connectToDatabase()) {
//
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM Izdatnica WHERE 1 = 1");
//
//            while (rs.next()) {
//                Long id = rs.getLong("IDIzdatnice");
//                Long idFirme = rs.getLong("IDFirme");
//                String poduzecee = getPoduzceByName();
//                LocalDate datumIzdatnice = rs.getString("Datum").of.atZone(ZoneId.systemDefault())
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
