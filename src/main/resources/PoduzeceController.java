package main.resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.application.Main;
import main.java.db.Msdb;
import main.java.model.Entitet;
import main.java.model.Poduzece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class PoduzeceController {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PODUZECE_EXCEPTION_MESSAGE = "Ups, something went wrong with: ";

    private final Msdb poduzeceRepo = new Msdb();

    @FXML
    private ComboBox<Long> comboBoxID;
    @FXML
    private TextField textFieldNaziv;
    @FXML
    private TextField textFieldOIB;
    @FXML
    private TableView<Poduzece> tableView;
    @FXML
    private TableColumn<Entitet, Long> tableColumnId;
    @FXML
    private TableColumn<Poduzece, String> tableColumnNaziv;
    @FXML
    private TableColumn<Poduzece, String> tableColumnOIB;
    @FXML
    private Button buttonPretrazi;
    @FXML
    private Button buttonUnesi;
    @FXML
    private Button buttonPonistiPolja;
    @FXML
    private Button buttonObrisi;

    private ObservableList<Poduzece> poduzeceObservableList;
    private List<Long> listaPoduzecaID;
    private ObservableList<Long> obListPoduzeceID;

    public void initialize() {

        try {
            poduzeceObservableList = FXCollections.observableList(poduzeceRepo.getPoduzece());
        } catch (Exception ex) {
            logger.error(PODUZECE_EXCEPTION_MESSAGE + " Artikl controller!", ex);
            ex.printStackTrace();
        }

        System.out.println("$%$%$% Poduzece records initializing! $%$%$%");

        tableColumnId.setCellValueFactory(new PropertyValueFactory<Entitet, Long>("id"));
        tableColumnId.setStyle("-fx-alignment: CENTER");

        tableColumnNaziv.setCellValueFactory(new PropertyValueFactory<Poduzece, String>("naziv"));
        tableColumnNaziv.setStyle("-fx-alignment: CENTER");

        tableColumnOIB.setCellValueFactory(new PropertyValueFactory<Poduzece, String>("oib"));
        tableColumnOIB.setStyle("-fx-alignment: CENTER");

        listaPoduzecaID = poduzeceObservableList.stream().map(Entitet::getId).collect(Collectors.toList());
        obListPoduzeceID = FXCollections.observableList(listaPoduzecaID);
        comboBoxID.setItems(obListPoduzeceID);
        comboBoxID.getSelectionModel().selectFirst();

        tableView.setItems(poduzeceObservableList);
        System.out.println("$%$%$% Poduzece records initialized successfully with size of: + " + poduzeceObservableList.size() + "!$%$%$%");
        logger.info("$%$%$% Poduzece records initialized successfully with size of: " + poduzeceObservableList.size() + "!$%$%$%");
    }


    public void pretraga() {

        String naziv = textFieldNaziv.getText();
        String oib = textFieldOIB.getText();

        List<Poduzece> filtriranaListaPoduzeca = new ArrayList<>(poduzeceObservableList
                .filtered(poduzece -> poduzece.getNaziv().toLowerCase().contains(naziv))
                .filtered(poduzece -> poduzece.getOib().toLowerCase().contains(oib)));

        tableView.setItems(FXCollections.observableList(filtriranaListaPoduzeca));

        System.out.println("Search result of: " + filtriranaListaPoduzeca.size() + " records.");
        logger.info("Article record searched successfully!");
    }

    public void unos() {

        String naziv = textFieldNaziv.getText();
        String oib = textFieldOIB.getText();

        String alert = unosProvjera(naziv, oib);
        if (!alert.isEmpty()) {
            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
            alertWindow.setTitle("Pogreska");
            alertWindow.setHeaderText("Molim popunite sljedeca polja: ");
            alertWindow.setContentText(alert);
            alertWindow.showAndWait();
        } else {
            Poduzece novoPoduzece = new Poduzece(nextId(), naziv, oib);
            try {
                poduzeceRepo.createPoduzece(novoPoduzece);
                poduzeceObservableList.add(novoPoduzece);
                textFieldNaziv.clear();
                textFieldOIB.clear();
            } catch (Exception ex) {
                System.err.println("Error in method 'unesi poduzece'" + ex);
                ex.printStackTrace();
            }
            tableView.setItems(poduzeceObservableList);
            System.out.println("Poduzece records saved successfully!");
            logger.info("Poduzece records saved successfully!");
        }
    }

    private Long nextId() {
        OptionalLong nextId = poduzeceObservableList.stream().mapToLong(Entitet::getId).max();
        return nextId.getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String oib) {

        System.out.println("Checking data...");

        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (oib.trim().isEmpty()) listaProvjere.add("oib");

        return String.join("\n", listaProvjere);
    }

    public void ponistavanje() {
        textFieldNaziv.clear();
        textFieldOIB.clear();
    }
}