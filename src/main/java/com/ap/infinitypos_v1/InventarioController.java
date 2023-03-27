package com.ap.infinitypos_v1;

import com.mongodb.client.MongoCollection;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.bson.Document;

import javax.swing.text.NumberFormatter;
import java.io.IOException;
import java.util.function.UnaryOperator;

import static com.ap.infinitypos_v1.HelloApplication.*;

public class InventarioController {
    @FXML
    private TextField Codigo;
    @FXML
    private TextField Descrip;
    // Costo, Precio, Util, Stock
    @FXML
    private TextField Costo;

    public conexion condb;

    @FXML
    private TextField Precio;
    @FXML TextField Util;
    @FXML TextField Stock;
    public boolean exist = false;
    @FXML
    public void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            // only allow digits and  dots
            System.out.println(newText);
            if (newText.matches("[0-9]*")) {
                return change;
            }
            if (newText.matches("[0-9]*[.][0-9]*")) {
                return change;
            }

            System.out.println("no match" + newText);
            return null;
        };

        UnaryOperator<TextFormatter.Change> UtilUn = change -> {
            String newText = change.getControlNewText();
            // Calc Precio total from Util
            if (newText.matches("[0-9]*")) {
                if (newText.length() > 0) {
                    double util = Double.parseDouble(newText);
                    double costo = Double.parseDouble(Costo.getText());
                    double precio = costo + (costo * util / 100);
                    Precio.setText(String.valueOf(precio));
                }
                return change;
            }
            if (newText.matches("[0-9]*[.][0-9]*")) {
                if (newText.length() > 0) {
                    double util = Double.parseDouble(newText);
                    double costo = Double.parseDouble(Costo.getText());
                    double precio = costo + (costo * util / 100);
                    Precio.setText(String.valueOf(precio));
                }
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        //TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter5 = new TextFormatter<>(UtilUn);

        Costo.setTextFormatter(textFormatter);
        Precio.setTextFormatter(textFormatter2);
        Util.setTextFormatter(textFormatter5);
        Stock.setTextFormatter(textFormatter4);
        ReleaseForm();
    }
    @FXML
    public void onCodigoKey(KeyEvent event) {
        //if Codigo Enter
        if (event.getCode().toString().equals("ENTER")) {
            System.out.println("Enter");
            this.loadForm();
        }
    }
    public void setCodigoFromSearch(String Codigo) {
        System.out.println(Codigo);
        this.Codigo.setText(Codigo);
        this.loadForm();
    }
    public void loadForm() {
        //si codigo esta vacio
        if (Codigo.getText().isEmpty()) {
            System.out.println("Codigo vacio");
            alertInfo("INFO", "Codigo vacio","Ingrese un codigo");
            return;
        }
        //search in db mongo and fill form
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Inventario");
        Document InvDoc = collection.find( new Document("Codigo", Codigo.getText())).first();
        if (InvDoc == null) {
            System.out.println("No exist Codigo");
            //Open Form
            OpenForm();
        } else {
            exist = true;
            ItemInv Item = new ItemInv(InvDoc.getString("Codigo"), InvDoc.getString("Descrip"), InvDoc.getDouble("Costo"), InvDoc.getDouble("Precio"), InvDoc.getDouble("Util"), InvDoc.getDouble("Stock"));
            //System.out.println(LoginDoc.toJson());
            Descrip.setText(Item.getDescrip());
            Costo.setText(Item.getCostoStr());
            System.out.println(Item.getCostoStr());
            Precio.setText(Item.getPrecioStr());
            Util.setText(Item.getUtilStr());
            Stock.setText(Item.getStockStr());
            //Disable Form
            OpenForm();

        }
    }
    @FXML
    public void onUtilChange(){
        //if Util change
        System.out.println("Util");
    }
    //on key typed handler

    @FXML
    public void onSave() {
        System.out.println("Save");
        //save in db mongo
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Inventario"); //if no exist create
        ItemInv Item = new ItemInv(Codigo.getText(), Descrip.getText(), 0.0, 0.0, 0.0, 0.0);
        Item.setCostoStr(Costo.getText());
        Item.setPrecioStr(Precio.getText());
        Item.setUtilStr(Util.getText());
        Item.setStockStr(Stock.getText());
        if (exist) {
            System.out.println("Update");
            //update
            Document doc = new Document("Codigo", Item.Codigo)
                    .append("Descrip", Item.Descrip)
                    .append("Costo", Item.Costo)
                    .append("Precio", Item.Precio)
                    .append("Util", Item.Util)
                    .append("Stock", Item.Stock);
            collection.updateOne(new Document("Codigo", Item.Codigo), new Document("$set", doc));
        } else {
            System.out.println("Insert");
            //insert
            Document doc = new Document("Codigo", Item.Codigo)
                    .append("Descrip", Item.Descrip)
                    .append("Costo", Item.Costo)
                    .append("Precio", Item.Precio)
                    .append("Util", Item.Util)
                    .append("Stock", Item.Stock);
            collection.insertOne(doc);
        }
        ReleaseForm();
    }
    public void onCancel() {
        System.out.println("Cancel");
        ReleaseForm();
    }
    @FXML
    public void onDelete() {
        Alert dec = decisionDialog("Borrar", "Borrar", "Esta seguro que desea borrar el producto?", "Borrar", "Cancelar");
        dec.showAndWait();
        if (dec.getResult() == ButtonType.CANCEL) {
            return;
        }
        System.out.println("Delete");
        //delete in db mongo
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Inventario");
        collection.deleteOne(new Document("Codigo", Codigo.getText()));
        ReleaseForm();
    }
    public void ReleaseForm() {
        ClearForm();
        exist = false;
        Codigo.setDisable(false);
        Descrip.setDisable(true);
        Costo.setDisable(true);
        Precio.setDisable(true);
        Util.setDisable(true);
        Stock.setDisable(true);
    }
    public void ClearForm() {
        Codigo.setText("");
        Descrip.setText("");
        Costo.setText("0");
        Precio.setText("0");
        Util.setText("0");
        Stock.setText("0");

    }
    public void OpenForm() {

        Codigo.setDisable(true);
        Descrip.setDisable(false);
        Costo.setDisable(false);
        Precio.setDisable(false);
        Util.setDisable(false);
        Stock.setDisable(false);
    }
    @FXML
    public void onSearchButton() throws IOException {
        BuscarController Buscar = new BuscarController(this,null,null);
        Buscar.showStage();
    }
}
