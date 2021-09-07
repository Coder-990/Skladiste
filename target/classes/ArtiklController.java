package main.resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.application.Main;
import main.java.db.ArtiklCRUD;
import main.java.model.Artikl;
import main.java.model.Entitet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class ArtiklController {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String ARTIKL_EXCEPTION_MESSAGE = "Ups, something went wrong with: ";

    private final ArtiklCRUD artiklCRUD = new ArtiklCRUD();

    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldCijena;
    @FXML
    private TextField textFieldKolicina;
    @FXML
    private TextField textFieldJedinicaMjere;
    @FXML
    private TextArea textAreaOpis;
    @FXML
    private TableView<Artikl> tableView;
    @FXML
    private TableColumn<Entitet, Long> tableColumnId;
    @FXML
    private TableColumn<Artikl, String> tableColumnNaziv;
    @FXML
    private TableColumn<Artikl, BigDecimal> tableColumnCijena;
    @FXML
    private TableColumn<Artikl, Integer> tableColumnKolicina;
    @FXML
    private TableColumn<Artikl, String> tableColumnJedinicnaMjera;
    @FXML
    private Button buttonPretrazi;
    @FXML
    private Button buttonUnesi;
    @FXML
    private Button buttonPonistiPolja;

    private ObservableList<Artikl> artiklObservableList;

    public void initialize() {

        try {
            artiklObservableList = FXCollections.observableList(artiklCRUD.getArtikl());
        } catch (Exception ex) {
            logger.error(ARTIKL_EXCEPTION_MESSAGE + " Artikl controller!", ex);
            ex.printStackTrace();
        }

        System.out.println("$%$%$% Article records initializing! $%$%$%");

        tableColumnId.setCellValueFactory(new PropertyValueFactory<Entitet, Long>("id"));
        tableColumnId.setStyle("-fx-alignment: CENTER");

        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
        tableColumnNaziv.setStyle("-fx-alignment: CENTER");

        tableColumnCijena.setCellValueFactory(new PropertyValueFactory<Artikl, BigDecimal>("cijena"));
        tableColumnCijena.setStyle("-fx-alignment: CENTER");

        tableColumnKolicina.setCellValueFactory(new PropertyValueFactory<Artikl, Integer>("kolicina"));
        tableColumnKolicina.setStyle("-fx-alignment: CENTER");

        tableColumnJedinicnaMjera.setCellValueFactory(new PropertyValueFactory<Artikl, String>("jedinicnaMjera"));
        tableColumnJedinicnaMjera.setStyle("-fx-alignment: CENTER");

        tableView.setItems(artiklObservableList);
        System.out.println("$%$%$% Article records initialized successfully with size of: + " + artiklObservableList.size() + "!$%$%$%");
        logger.info("$%$%$% Article records initialized successfully with size of: " + artiklObservableList.size() + "!$%$%$%");
    }

    public void pretraga() {

        String naziv = textFieldNaziv.getText();
        String kolicina = textFieldKolicina.getText();
        String cijena = textFieldCijena.getText();

        List<Artikl> filtriranaListaArtikla = new ArrayList<>(artiklObservableList
                .filtered(artikl -> artikl.getNaziv().toLowerCase().contains(naziv))
                .filtered(artikl -> kolicina == null || kolicina.equals("")
                        || artikl.getKolicina() == Integer.parseInt(kolicina))
                .filtered(artikl -> cijena == null || cijena.equals("") ||
                        artikl.getCijena().equals(new BigDecimal(cijena))));

        tableView.setItems(FXCollections.observableList(filtriranaListaArtikla));

        System.out.println("Search result of: " + filtriranaListaArtikla.size() + " records.");
        logger.info("Article record searched successfully!");
    }

    public void unos() throws SQLException, IOException {

        String naziv = textFieldNaziv.getText();
        String kolicina = textFieldKolicina.getText();
        Integer kolicinaCastFromString = Integer.parseInt(kolicina);
        String cijena = textFieldCijena.getText();
        BigDecimal CijenaCastFromString = new BigDecimal(cijena);
        String jmj = textFieldJedinicaMjere.getText();

        String alert = unosProvjera(naziv, kolicina, cijena, jmj);
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Pogreska");
            alertWindow.setHeaderText("Molim popunite sljedeca polja: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {
            Artikl noviArtikl = new Artikl(nextId(), naziv, kolicinaCastFromString, CijenaCastFromString, jmj);
            artiklCRUD.createArtikl(noviArtikl);
            artiklObservableList.add(noviArtikl);
            textFieldNaziv.clear();
            textFieldCijena.clear();
            textFieldKolicina.clear();
            textFieldJedinicaMjere.clear();
            tableView.setItems(artiklObservableList);
            System.out.println("Article records saved successfully!");
        }
    }

    private Long nextId() {
        OptionalLong nextId = artiklObservableList.stream().mapToLong(Entitet::getId).max();
        return nextId.getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String kolicina, String cijena, String jmj) {

        System.out.println("Checking data...");

        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (kolicina.trim().isEmpty()) listaProvjere.add("kolicina");
        if (cijena.trim().isEmpty()) listaProvjere.add("cijena");
        if (jmj.trim().isEmpty()) listaProvjere.add("jedinicnaMjera");

        return String.join("\n", listaProvjere);
    }

    public void ponistavanje() {
        textFieldNaziv.clear();
        textFieldCijena.clear();
        textFieldKolicina.clear();

    }
}
