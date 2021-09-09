package main.resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.java.application.Main;
import main.java.db.IzdatnicaCRUD;
import main.java.db.PoduzeceCRUD;
import main.java.model.Entitet;
import main.java.model.Izdatnica;
import main.java.model.Poduzece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IzdatnicaController {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String IZDATNICA_EXCEPTION_MESSAGE = "Ups, something went wrong with: ";
    public static final String FORMAT_DATUMA = "dd.MM.yyyy.";

    private final IzdatnicaCRUD izdatnicaCRUD = new IzdatnicaCRUD();
    private final PoduzeceCRUD poduzeceCRUD = new PoduzeceCRUD();

    @FXML
    private TextField textFieldNazivPoduzeca;
    @FXML
    private ComboBox<Poduzece> comboBoxNazivPoduzeca;
    @FXML
    private DatePicker datePickerDatum;
    @FXML
    private TableView<Izdatnica> tableView;
    @FXML
    private TableColumn<Entitet, Long> tableColumnId;
    @FXML
    private TableColumn<Entitet, String> tableColumnNazivPoduzeca;
    @FXML
    private TableColumn<LocalDate, String> tableColumnDatumIzdatnice;
    @FXML
    private Button buttonPretrazi;
    @FXML
    private Button buttonUnesi;
    @FXML
    private Button buttonPonistiPolja;
    @FXML
    private Button buttonObrisi;

    private ObservableList<Izdatnica> izdatnicaObservableList;

    public void initialize() throws IOException, SQLException {

        try {
            izdatnicaObservableList = FXCollections.observableList(izdatnicaCRUD.get());
        } catch (Exception ex) {
            logger.error(IZDATNICA_EXCEPTION_MESSAGE + " Artikl controller!", ex);
            ex.printStackTrace();
        }

        System.out.println("$%$%$% Poduzece records initializing! $%$%$%");

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnId.setStyle("-fx-alignment: CENTER");

        tableColumnNazivPoduzeca.setCellValueFactory(new PropertyValueFactory<>("poduzeceID"));
        tableColumnNazivPoduzeca.setStyle("-fx-alignment: CENTER");

        tableColumnDatumIzdatnice.setCellValueFactory(new PropertyValueFactory<>("datumIzdatnice"));
        tableColumnDatumIzdatnice.setStyle("-fx-alignment: CENTER");

        ObservableList<Poduzece> observableListNazivPoduzeca = FXCollections.observableList(poduzeceCRUD.get());
        comboBoxNazivPoduzeca.setItems(observableListNazivPoduzeca);
        comboBoxNazivPoduzeca.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Poduzece> call(ListView<Poduzece> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Poduzece poduzece, boolean empty) {
                        super.updateItem(poduzece, empty);
                        if (Optional.ofNullable(poduzece).isPresent()) {
                            setText(poduzece.getNaziv());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        tableView.setItems(izdatnicaObservableList);
        System.out.println("$%$%$% Izdatnica records initialized successfully with size of: + " + izdatnicaObservableList.size() + "!$%$%$%");
        logger.info("$%$%$% Izdatnica records initialized successfully with size of: " + izdatnicaObservableList.size() + "!$%$%$%");
    }


    public void pretraga() {

        String nazivPoduzeca = textFieldNazivPoduzeca.getText();
        String datum = datePickerDatum.getValue().format(DateTimeFormatter.ofPattern(FORMAT_DATUMA));

        List<Izdatnica> filtriranaListaIzdatnica = new ArrayList<>(izdatnicaObservableList
                .filtered(izdatnica -> izdatnica.getPoduzeceID().getNaziv().contains(nazivPoduzeca)));
        filtriranaListaIzdatnica = filtriranaListaIzdatnica.stream()
                .filter(izdatnica -> izdatnica.getDatumIzdatnice()
                        .format(DateTimeFormatter.ofPattern(FORMAT_DATUMA))
                        .contains(datum)).collect(Collectors.toList());

        tableView.setItems(FXCollections.observableList(filtriranaListaIzdatnica));

        System.out.println("Search result of: " + filtriranaListaIzdatnica.size() + " records.");
        logger.info("Izdatnica record searched successfully!");
    }

    public void unos() {

//        String naziv = textFieldNaziv.getText();
//        String oib = textFieldOIB.getText();
//
//        String alert = unosProvjera(naziv, oib);
//        if (!alert.isEmpty()) {
//            Alert alertWindow = new Alert(Alert.AlertType.WARNING);
//            alertWindow.setTitle("Pogreska");
//            alertWindow.setHeaderText("Molim popunite sljedeca polja: ");
//            alertWindow.setContentText(alert);
//            alertWindow.showAndWait();
//        } else {
//            Poduzece novoPoduzece = new Poduzece(nextId(), naziv, oib);
//            try {
//                izdatnicaRepo.createPoduzece(novoPoduzece);
//                izdatnicaObservableList.add(novoPoduzece);
//                textFieldNaziv.clear();
//                textFieldOIB.clear();
//            } catch (Exception ex) {
//                System.err.println("Error in method 'unesi poduzece'" + ex);
//                ex.printStackTrace();
//            }
//            tableView.setItems(izdatnicaObservableList);
//            System.out.println("Poduzece records saved successfully!");
//            logger.info("Poduzece records saved successfully!");
//        }
    }

    private void nextId() {
//        OptionalLong nextId = izdatnicaObservableList.stream().mapToLong(Entitet::getId).max();
//        return nextId.getAsLong() + 1;
    }

    private String unosProvjera(String naziv, String oib) {

        System.out.println("Checking data...");

        List<String> listaProvjere = new ArrayList<>();
        if (naziv.trim().isEmpty()) listaProvjere.add("naziv");
        if (oib.trim().isEmpty()) listaProvjere.add("oib");

        return String.join("\n", listaProvjere);
    }

    public void ponistavanje() {
//        textFieldNaziv.clear();
//        textFieldOIB.clear();
    }
}
