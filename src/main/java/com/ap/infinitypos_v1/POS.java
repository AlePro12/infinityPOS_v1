package com.ap.infinitypos_v1;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.bson.Document;

import java.io.IOException;

import static com.ap.infinitypos_v1.HelloApplication.*;
import static com.ap.infinitypos_v1.HelloApplication.conn;

public class POS {

    //array of products Positem
    public PosItem[] posItems;
    @FXML
    private TableView<PosItem> PosTable;
    @FXML
    private TextField Codigo;
    @FXML
    private Label visor;
    private conexion condb;
@FXML
public void initialize() {
    PosTable.getItems().clear();
    //clear columns
    PosTable.getColumns().clear();
    TableColumn<PosItem, String> colCodp = new TableColumn<PosItem, String>("codigo");
    colCodp.setCellValueFactory(new PropertyValueFactory<PosItem, String>("codigo")); //width
    colCodp.setPrefWidth(103);
    //colCodp.getStyleClass().add("tablecolumn");
    TableColumn<PosItem, String> colDescrip = new TableColumn<PosItem, String>("descrip");
    colDescrip.setCellValueFactory(new PropertyValueFactory<PosItem, String>("descrip"));
    colDescrip.setPrefWidth(227);
    //colDescrip.getStyleClass().add("tablecolumn");
    TableColumn<PosItem, String> colCantidad = new TableColumn<PosItem, String>("cantidad");
    colCantidad.setCellValueFactory(new PropertyValueFactory<PosItem, String>("cantidad"));
    colCantidad.setPrefWidth(85);
    //colCantidad.getStyleClass().add("tablecolumn");
    TableColumn<PosItem, String> colPrecio = new TableColumn<PosItem, String>("precio");
    colPrecio.setCellValueFactory(new PropertyValueFactory<PosItem, String>("precio"));
    colPrecio.setPrefWidth(122);
    //colPrecio.getStyleClass().add("tablecolumn");
    TableColumn<PosItem, String> colTotal = new TableColumn<PosItem, String>("total");
    colTotal.setCellValueFactory(new PropertyValueFactory<PosItem, String>("total"));
    colTotal.setPrefWidth(140);
    //colTotal.getStyleClass().add("tablecolumn");
    PosTable.getColumns().addAll(colCodp, colDescrip, colCantidad, colPrecio, colTotal);

    //supr key event or delete in mac
    PosTable.setOnKeyPressed(event -> {
        if (event.getCode().toString().equals("DELETE") || event.getCode().toString().equals("SUPR")) {
            System.out.println("Delete");
            EraseByCodigo(PosTable.getSelectionModel().getSelectedItem().getCodigo());
        }
    });
    //on double click on table
    PosTable.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) {
            //confirmar que se quiere borrar
            System.out.println("Double click");
            Alert dec = decisionDialog("Borrar", "Borrar", "Esta seguro que desea borrar el producto?", "Borrar", "Cancelar");
            dec.showAndWait();
            if (dec.getResult().getText().equals("Borrar")) {
                EraseByCodigo(PosTable.getSelectionModel().getSelectedItem().getCodigo());
            }
        }
    });
}
    @FXML
    public void onSearchButton() throws IOException {
        BuscarController Buscar = new BuscarController(null, this);
        Buscar.showStage();
    }
    public void setCodigoFromSearch(String codigo) {
        Codigo.setText(codigo);
        this.AddCodigo();
    }
    @FXML
    public void onCodigoKey(KeyEvent event) {
        //if Codigo Enter
        if (event.getCode().toString().equals("ENTER")) {
            System.out.println("Enter");
            this.AddCodigo();
        }
    }

    public void AddCodigo() {
        if (Codigo.getText().isEmpty()) {
            System.out.println("Codigo vacio");
            alertInfo("INFO", "Codigo vacio","Ingrese un codigo");
            Codigo.setText("");
            return;
        }
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Inventario");
        Document InvDoc = collection.find( new Document("Codigo", Codigo.getText())).first();
        if (InvDoc == null) {
            alertError("ERROR", "Codigo no encontrado", "El codigo no existe en el inventario");
            Codigo.setText("");
            return;
        }
        TextInputDialog TextCantidad = HelloApplication.inputDialog("Agregar", "Cantidad", "Cantidad","1");
        TextCantidad.showAndWait();
        //si no es un numero
        if (!TextCantidad.getResult().matches("[0-9]+")) {
            alertError("ERROR", "Cantidad no valida", "La cantidad debe ser un numero");
            return;
        }
        String Cantidad = TextCantidad.getResult();
        Integer cantidadInt = Integer.parseInt(Cantidad);
        System.out.println(Cantidad);
        ItemInv Item = new ItemInv(InvDoc.getString("Codigo"), InvDoc.getString("Descrip"), InvDoc.getDouble("Costo"), InvDoc.getDouble("Precio"), InvDoc.getDouble("Util"), InvDoc.getDouble("Stock"));
        PosItem posItem = new PosItem(
                Item.Codigo,
                Item.Descrip,
                Item.Precio,
                cantidadInt
        );
        Codigo.setText("");
        Add(posItem);
    }
    //Refresh pos table
    public void Add(PosItem posItem) {

        PosTable.getItems().add(posItem);
        //add to array
        if (posItems == null) {
            posItems = new PosItem[1];
        } else {
            PosItem[] temp = new PosItem[posItems.length + 1];
            System.arraycopy(posItems, 0, temp, 0, posItems.length);
            posItems = temp;
        }
        posItems[posItems.length - 1] = posItem;
        //print array
        for (int i = 0; i < posItems.length; i++) {
            System.out.print(posItems[i].codigo + " ");
            System.out.print(posItems[i].descrip + " ");
            System.out.print(posItems[i].cantidad);
            System.out.print("\n");
        }
        RecalVisor();
    }
    public void RecalVisor() {
        Double total = 0.0;
        if (posItems == null) {
            visor.setText(total.toString()+"$");
            return;
        }
        for (int i = 0; i < posItems.length; i++) {
            total += posItems[i].total;
        }
        visor.setText(total.toString()+"$");
    }

    public void BorrarTodo() {
        PosTable.getItems().clear();
        posItems = null;
        RecalVisor();
    }
    public void EraseByCodigo(String codigo) {
        for (int i = 0; i < posItems.length; i++) {
            if (posItems[i].codigo.equals(codigo)) {
                PosTable.getItems().remove(i);
                PosItem[] temp = new PosItem[posItems.length - 1];
                System.arraycopy(posItems, 0, temp, 0, i);
                System.arraycopy(posItems, i + 1, temp, i, posItems.length - i - 1);
                posItems = temp;
                RecalVisor();
                return;
            }
        }
    }

}
