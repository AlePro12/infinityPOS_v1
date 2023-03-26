package com.ap.infinitypos_v1;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.util.Optional;

///
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 350, 270);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        conn = new conexion();
        user = new Usuario(conn);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    //home
    public static void Home() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 599, 400);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    public static void Inventario() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inventario.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 689, 496);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setTitle("Inventario");
        stage.setScene(scene);
        stage.show();
    }

    public static void POS() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("POS.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setTitle("POS");
        stage.setScene(scene);
        stage.show();
    }

    //https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html
    public static void main(String[] args) {
        launch();

    }

    //Conexion a la base de datos
    public static conexion conn = null;
    // Contexto de usuario
    public static Usuario user = null;

    public static void alertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void alertInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //TextInputDialog
    public static TextInputDialog inputDialog(String title, String header, String content, String defaultValue) {
        TextInputDialog inputdialog = new TextInputDialog(defaultValue);
        inputdialog.setContentText(content);
        inputdialog.setHeaderText(header);
        inputdialog.setTitle(title);
        return inputdialog;
    }
    //decision dialog
    public static Alert decisionDialog(String title, String header, String content, String ButtonType1, String ButtonType2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType buttonTypeOne = new ButtonType(ButtonType1);
        ButtonType buttonTypeTwo = new ButtonType(ButtonType2);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        return alert;
    }
}