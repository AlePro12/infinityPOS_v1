package com.ap.infinitypos_v1;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.bson.Document;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.ap.infinitypos_v1.HelloApplication.*;

public class BuscarController {
    private Stage thisStage;
    private final InventarioController inventarioController;
    private final POS pos;

    public BuscarController(InventarioController inventarioController, POS pos) {
        if (inventarioController != null) {
            this.inventarioController = inventarioController;
        }else{
            this.inventarioController = null;
        }
        if (pos != null) {
            this.pos = pos;
        }else{
            this.pos = null;
        }

        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Buscar.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Buscar");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void showStage() {
        thisStage.showAndWait();
    }
    @FXML
    private void initialize() {

    }

        @FXML
    private TextField BuscarTerm;
    @FXML
    private TableView<ItemInv> tableSearch;

    @FXML
    public conexion condb;

    @FXML
    public void onClickSearch() {
        this.condb = conn;
        //clear table
        tableSearch.getItems().clear();
        //clear columns
        tableSearch.getColumns().clear();
        //Create Tableview  Descrip, Precio and Stock for ItemInv
        TableColumn<ItemInv, String> colDescrip = new TableColumn<ItemInv, String>("Descrip");
        colDescrip.setCellValueFactory(new PropertyValueFactory<ItemInv, String>("Descrip"));
        TableColumn<ItemInv, String> colPrecio = new TableColumn<ItemInv, String>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<ItemInv, String>("Precio"));
        TableColumn<ItemInv, String> colStock = new TableColumn<ItemInv, String>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<ItemInv, String>("Stock"));

        // Defines how to fill data for each cell.

       //ex: userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("Descrip"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        tableSearch.getColumns().addAll(colDescrip, colPrecio, colStock);


        MongoCollection<Document> collection = conn.DB.getCollection("Inventario");
        MongoCursor<Document> cursor = collection.find(
                new Document("Descrip", new Document("$regex", BuscarTerm.getText()))
        ).iterator();
        try {
            while (cursor.hasNext()) {
                Document InvDoc = cursor.next();
                //add to table
                tableSearch.getItems().add(new ItemInv(
                        InvDoc.getString("Codigo"),
                        InvDoc.getString("Descrip"),
                        InvDoc.getDouble("Costo"),
                        InvDoc.getDouble("Precio"),
                        InvDoc.getDouble("Util"),
                        InvDoc.getDouble("Stock")
                ));
                System.out.println(InvDoc.toJson());
            }
        } catch (Exception e){
            System.out.println(e);
        }
        finally {
            cursor.close();
        }
        //Show Table

        //on click table
        tableSearch.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                //Open Form
                //alertInfo("Open Form", "Open Form", "Open Form codp:" + tableSearch.getSelectionModel().getSelectedItem().getCodigo());

                if (this.inventarioController != null) {
                    this.inventarioController.setCodigoFromSearch(tableSearch.getSelectionModel().getSelectedItem().getCodigo());
                }
                if (this.pos != null) {
                    this.pos.setCodigoFromSearch(tableSearch.getSelectionModel().getSelectedItem().getCodigo());
                }
                //close this window
                thisStage.close();
            }
        });


        System.out.println("Buscar");
    }
//            alertError("No se encontro el producto", "No se encontro el producto", "No se encontro el producto segun la descripcion");
}
