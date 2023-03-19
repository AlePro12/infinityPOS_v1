package com.ap.infinitypos_v1;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    //home
    public static void  Home() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }
    //Conexion a la base de datos
    public static conexion conn = new conexion();
    // Contexto de usuario
    public static Usuario user = new Usuario(conn);
//https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html
    public static void main(String[] args) {
        launch();
    }
}