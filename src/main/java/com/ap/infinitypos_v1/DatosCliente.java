package com.ap.infinitypos_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.ap.infinitypos_v1.HelloApplication.alertError;

public class DatosCliente {
    private Stage thisStage;
    private final POS pos;
    @FXML
    private TextField RIF;
    @FXML
    private TextField NOMBRE;
    @FXML
    private TextField DIRECCION;
    public DatosCliente(POS pos) {
        this.pos = pos;
        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DatosCliente.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Datos del cliente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void OnAceptar(ActionEvent actionEvent) throws IOException {
        //validar datos
        if (RIF.getText().isEmpty() || NOMBRE.getText().isEmpty() || DIRECCION.getText().isEmpty()) {
            alertError("Error", "Error", "Debe llenar todos los campos");
        }
        Cliente cliente = new Cliente(RIF.getText(), NOMBRE.getText(), DIRECCION.getText());
        pos.setCliente(cliente);
        pos.Pay();
        //thisStage.close();
    }

    public void showStage() {
        thisStage.showAndWait();
    }
}
