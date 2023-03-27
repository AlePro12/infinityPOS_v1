package com.ap.infinitypos_v1;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
//date
import javafx.application.Platform;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.ap.infinitypos_v1.HelloApplication.Inventario;
import static com.ap.infinitypos_v1.HelloApplication.alertInfo;
import static java.util.concurrent.CompletableFuture.runAsync;
//joptionpane



public class HomeController  implements Initializable {
    @FXML
    private Label MainText;
    @FXML
    private Label TimeTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertInfo("Bienvenido", "Bienvenido a InfinityPOS", "Bienvenido " + HelloApplication.user.Nombre);
        MainText.setText("Bienvenido " + HelloApplication.user.Nombre);


    }
    //run reloj in background
    public void runReloj() {
        runAsync(this::reloj);
    }
    @FXML
    public void onClickInv() throws IOException {
        Inventario();
    }
    @FXML
    public void onClickPOS() throws IOException {
        HelloApplication.POS();
    }
    @FXML
    public void onClickConfig() throws IOException {
        HelloApplication.Config();
    }

    //alert

    public void reloj() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //hora y fecha hh:mm dd/MM/yyyy
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                Date date = new Date();
                //alert joptionpane
                TimeTextField.setText(formatter.format(date));
            }
    }
}
