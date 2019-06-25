

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.extflightdelays.db.Collegamento;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	int minDist;
    	try {
			minDist = Integer.parseInt(distanzaMinima.getText());
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Errore");
			alert.setHeaderText("Formato distanza non valido");
			alert.showAndWait();
			return;
		}
    	if(model.creaGrafo(minDist)) {
    		this.txtResult.setText("Grafo creato correttamente\n");
    	} else {
    		this.txtResult.setText("Errore nella creazione del grafo\n");
    		return;
    	}
    	this.cmbBoxAeroportoPartenza.getItems().setAll(model.getAllAirports());
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	Airport a = this.cmbBoxAeroportoPartenza.getSelectionModel().getSelectedItem();
    	if(a==null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Errore");
			alert.setHeaderText("Selezionare almeno un aeroporto");
			alert.showAndWait();
			return;
    	}
    	List<Collegamento> vicini = model.getAeroportiVicini(a);
    	txtResult.clear();
    	if(vicini.size()==0)
    		txtResult.appendText(String.format("L'aeroporto %s non ha vicini\n", a));
    	vicini.forEach(aa->txtResult.appendText(aa.getA2() + " - " + aa.getPeso() + "\n"));
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	Airport a = this.cmbBoxAeroportoPartenza.getSelectionModel().getSelectedItem();
    	if(a==null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Errore");
			alert.setHeaderText("Selezionare almeno un aeroporto");
			alert.showAndWait();
			return;
    	}
    	double maxMiglia;
    	try {
			maxMiglia = Double.parseDouble(this.numeroVoliTxtInput.getText());
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Errore");
			alert.setHeaderText("Formato miglia massime non valido");
			alert.showAndWait();
			return;
		}
    	Set<Airport> itinerario = model.calcolaItinerario(a, maxMiglia);
    	txtResult.setText("Itinerario migliore\n");
    	itinerario.forEach(res->txtResult.appendText(res.toString() + "\n"));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
		
	}
}

