package com.ap.infinitypos_v1;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

import static com.ap.infinitypos_v1.HelloApplication.conn;
import static com.ap.infinitypos_v1.HelloApplication.user;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField User;
    @FXML
    private PasswordField Password;
    @FXML
    protected void OnClickLogin() throws IOException {
        boolean Logged = user.Login(User.getText(), Password.getText());
        if (Logged) {
            welcomeText.setText("¡Bienvenido!, " + user.Nombre);
            // to home
            HelloApplication.Home();
            //close login
            Scene scene = welcomeText.getScene();
            scene.getWindow().hide();
        } else {
            welcomeText.setText("Usuario o contraseña incorrectos");
        }
    }
    @FXML
    public void OnOlvido() throws IOException {
        HelloApplication.Olvido();
    }
}