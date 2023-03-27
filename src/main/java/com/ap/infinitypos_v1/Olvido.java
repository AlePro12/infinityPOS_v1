package com.ap.infinitypos_v1;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.bson.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.UnaryOperator;

import static com.ap.infinitypos_v1.HelloApplication.*;

public class Olvido {
    @FXML
    private TextField User;
    @FXML
    private TextField Telefono;
    @FXML
    private TextField Verify;
    @FXML
    private TextField NewPass;
    @FXML
    private Button Recuperar;
    @FXML
    private Button VerificarButton;
    @FXML
    private Button CambiarClave;
    @FXML
    private TextField Clave;
    @FXML
    private Label IngrVerif;
    @FXML
    private Label IngrNvPass;
    @FXML
    private Label IngrUsr;
    public conexion condb;
    public String Codigo ;

    @FXML
    public void OnClickEnviar() throws IOException {
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Login");
        Document doc = collection.find(new Document("User", User.getText())).first();
        if (doc != null) {
            if (doc.get("Telefono").equals(Telefono.getText())) {
                System.out.println("El telefono coincide");
                User.setDisable(true);
                Telefono.setDisable(true);
                //http request
                //generar codigo de 4 digitos
                Codigo = String.valueOf((int) (Math.random() * 9999));
                String msg = "Su codigo de verificacion InfinityPOS es " + Codigo;
                //msg urlencode
                msg = msg.replace(" ", "%20");
                String urlStr ="http://192.168.32.110:2987/api/SendFile?token=AP6673&tel="+Telefono.getText()+"&msg="+msg;
                System.out.println(urlStr);
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.connect();
                int status = con.getResponseCode();
                System.out.println(status);
                IngrUsr.setVisible(true);
                Verify.setVisible(true);
                Recuperar.setVisible(true);
                VerificarButton.setVisible(false);
            }else{
                alertError("El telefono no coincide", "El telefono no coincide con el usuario", "El telefono no coincide con el usuario");
            }
        } else {
            alertError("El usuario no existe", "El usuario no existe", "El usuario no existe");
        }
    }
    @FXML
    public void OnClickVerificar() throws IOException {
        System.out.println("Verificar" + Verify.getText() + " " + Codigo);

        if (Verify.getText().equals(Codigo)){
            //cambiar contraseña
            IngrUsr.setVisible(false);
            VerificarButton.setVisible(false);
            Verify.setVisible(false);
            Recuperar.setVisible(false);
            IngrNvPass.setVisible(true);
            CambiarClave.setVisible(true);
            Clave.setVisible(true);
        }else{
            alertError("El codigo no coincide", "El codigo no coincide", "El codigo no coincide");
        }
    }
    @FXML
    public void onChangepass() {
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Login");
        Document doc = collection.find(new Document("User", User.getText())).first();
        if (doc != null) {
            doc.put("Password", Clave.getText());
            collection.replaceOne(new Document("User", User.getText()), doc);
            alertInfo("Contraseña cambiada", "Contraseña cambiada", "Contraseña cambiada");
            //close this window
            Scene scene = User.getScene();
            scene.getWindow().hide();

        } else {
            alertError("El usuario no existe", "El usuario no existe", "El usuario no existe");
        }
    }
    @FXML
    public void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            String newText = change.getControlNewText();
            // parse phone number 584126587502
            //debe menos o 12 digitos
            //debe empezar con 58
            System.out.println(newText);
            if (text.matches("[0-9]*") ) {
                if (newText.length() > 12) {
                    alertInfo("INFO", "Telefono invalido", "Telefono invalido debe tener menos de 12 digitos");
                    return null;
                }
                if (newText.length() == 2) {
                    if (newText.equals("58")) {
                        return change;
                    } else {
                        alertInfo("INFO", "Telefono invalido", "Telefono invalido debe empezar con 58");
                        return null;
                    }
                }
                if (newText.length() == 1) {
                    if (newText.equals("5")) {
                        return change;
                    } else {
                        alertInfo("INFO", "Telefono invalido", "Telefono invalido debe empezar con 58");
                        return null;
                    }
                }
                // despues de 2 digitos 58 viene 424, 412, 414, 416, 426, 416
                if (newText.length() == 5) {
                    if (newText.equals("58412") || newText.equals("58414") || newText.equals("58416") || newText.equals("58426") || newText.equals("58416")) {
                        return change;
                    } else {
                        alertInfo("INFO", "Telefono invalido", "Telefono invalido debe ser 58 + 412, 414, 416, 426, 416 + 7 digitos");
                        return null;
                    }
                }
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        Telefono.setTextFormatter(textFormatter);
    }

}
