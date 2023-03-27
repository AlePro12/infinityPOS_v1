package com.ap.infinitypos_v1;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.bson.Document;
import javax.print.PrintService;


import java.util.function.UnaryOperator;

import static com.ap.infinitypos_v1.HelloApplication.alertInfo;
import static com.ap.infinitypos_v1.HelloApplication.conn;

public class Config {
    // Checkbox PrintFac
    @FXML
    private CheckBox PrintFac;
    // ChoiceBox Printer
    @FXML
    private ChoiceBox Printer;

    // TextField User
    @FXML
    private TextField User;
    // TextField Nombre
    @FXML
    private TextField Nombre;
    //TextField Telefono
    @FXML
    private TextField Telefono;
    // CheckBox AccessInv
    @FXML
    private CheckBox AccessInv;
    // CheckBox AccessFac
    @FXML
    private CheckBox AccessFac;
    // CheckBox AccessConfig
    @FXML
    private CheckBox AccessConfig;
    // PasswordField Password
    @FXML
    private PasswordField Password;
    public conexion condb;
    public boolean exist = false;


    //onSearchButton function
    @FXML
    private void onSearchButton() {
        BuscarController Buscar = new BuscarController(null, null,this);
        Buscar.showStage();
        System.out.println("Search");
    }
    public void setUsrFromSearch(String User){
        this.User.setText(User);
        this.loadForm();
    }
    @FXML
    public void onUserKey(KeyEvent event) {
        //if User Enter
        if (event.getCode().toString().equals("ENTER")) {
            System.out.println("Enter");
            this.loadForm();
        }
    }
    public void OpenForm() {
        System.out.println("OpenForm");
        User.setDisable(true);
        Nombre.setDisable(false);
        Telefono.setDisable(false);
        AccessInv.setDisable(false);
        AccessFac.setDisable(false);
        AccessConfig.setDisable(false);
        Password.setDisable(false);
    }
    public void ReleaseForm() {
        ClearForm();
        System.out.println("ReleaseForm");
        User.setDisable(false);
        Nombre.setDisable(true);
        Telefono.setDisable(true);
        AccessInv.setDisable(true);
        AccessFac.setDisable(true);
        AccessConfig.setDisable(true);
        Password.setDisable(true);
    }
    public void ClearForm() {
        System.out.println("ClearForm");
        User.setText("");
        Nombre.setText("");
        Telefono.setText("");
        AccessInv.setSelected(false);
        AccessFac.setSelected(false);
        AccessConfig.setSelected(false);
        Password.setText("");
    }
    public void loadForm(){
        //si codigo esta vacio
        if (User.getText().isEmpty()) {
            System.out.println("User vacio");
            alertInfo("INFO", "User vacio", "Usuario vacio");
            return;
        }
        //search in db mongo and fill form
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Login");
        Document InvDoc = collection.find( new Document("User", User.getText())).first();
        if (InvDoc == null) {
            System.out.println("No exist User");
            //Open Form
            OpenForm();
            return;
        } 
            exist = true;
        Usuario usr = new Usuario(conn);
        usr.setUsr(InvDoc.getString("User"), InvDoc.getString("Password"), InvDoc.getString("Nombre"), InvDoc.getString("Telefono"), InvDoc.getBoolean("AccessInv"), InvDoc.getBoolean("AccessPos"), InvDoc.getBoolean("AccessConfig"));
        //set form
        User.setText(usr.getUser());
        Nombre.setText(usr.getNombre());
        Telefono.setText(usr.getTelefono());
        AccessInv.setSelected(usr.getAccessInv());
        AccessFac.setSelected(usr.getAccessFac());
        AccessConfig.setSelected(usr.getAccessConfig());
        Password.setText(usr.getPassword());
        //Open Form
        OpenForm();
    }

    @FXML
    public void onSave() {
        System.out.println("Save");
        //save in db mongo
        this.condb = conn;
        MongoCollection<Document> collection = conn.DB.getCollection("Login"); //if no exist create
        Usuario usr = new Usuario(conn);
        usr.setUsr(User.getText(), Password.getText(), Nombre.getText(), Telefono.getText(), AccessInv.isSelected(), AccessFac.isSelected(), AccessConfig.isSelected());
        //set form
        User.setText(usr.getUser());
        Nombre.setText(usr.getNombre());
        Telefono.setText(usr.getTelefono());
        AccessInv.setSelected(usr.getAccessInv());
        AccessFac.setSelected(usr.getAccessFac());
        AccessConfig.setSelected(usr.getAccessConfig());
        Password.setText(usr.getPassword());
        if (exist) {
            System.out.println("Update");
            //update
            Document doc = new Document("User", usr.getUser())
                    .append("Password", usr.getPassword())
                    .append("Nombre", usr.getNombre())
                    .append("Telefono", usr.getTelefono())
                    .append("AccessInv", usr.getAccessInv())
                    .append("AccessPos", usr.getAccessFac())
                    .append("AccessConfig", usr.getAccessConfig());
            collection.updateOne(new Document("User", usr.getUser()), new Document("$set", doc));
        } else {
            System.out.println("Insert");
            //insert
            Document doc = new Document("User", User.getText())
                    .append("Password", usr.getPassword())
                    .append("Nombre", usr.getNombre())
                    .append("Telefono", usr.getTelefono())
                    .append("AccessInv", usr.getAccessInv())
                    .append("AccessPos", usr.getAccessFac())
                    .append("AccessConfig", usr.getAccessConfig());
            collection.insertOne(doc);
        }
        ReleaseForm();
    }
    @FXML
    public void onCancel() {
        System.out.println("Cancel");
        ReleaseForm();
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

        MongoCollection<Document> ConfigColl = conn.DB.getCollection("Config");
        Document ConfigDoc = ConfigColl.find().first();
        if (ConfigDoc == null) {
            System.out.println("No exist Config");
        }else{
            PrintFac.setSelected(ConfigDoc.getBoolean("PrintFac"));
            Printer.setValue(ConfigDoc.getString("Printer"));
        }
        System.out.println("Config");
        ReleaseForm();
        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
        for (String printServicesName : printServicesNames) {
            Printer.getItems().add(printServicesName);
        }
    }
    @FXML
    public void TestPrint() {
        System.out.println("TestPrint");
        EscPos escpos;
        try {
            PrintService printService = PrinterOutputStream.getPrintServiceByName(Printer.getValue().toString());
            escpos = new EscPos(new PrinterOutputStream(printService));
            escpos.writeLF("Test Print InfinityPOS :)");
            escpos.feed(3);
            escpos.cut(EscPos.CutMode.FULL);
            escpos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void SaveConfig(){
        // Only printFac and Printer
        System.out.println("SaveConfig");

        MongoCollection<Document> ConfigColl = conn.DB.getCollection("Config");
        Document ConfigDoc = ConfigColl.find().first();
        if (ConfigDoc == null) {
            System.out.println("No exist Config");
            ConfigDoc = new Document(
                "PrintFac", PrintFac.isSelected()
            ).append(
                "Printer", Printer.getValue().toString()
            );
            ConfigColl.insertOne(ConfigDoc);
        }else{
            ConfigDoc.replace("PrintFac", PrintFac.isSelected());
            ConfigDoc.replace("Printer", Printer.getValue().toString());
            ConfigColl.replaceOne(new Document("_id", ConfigDoc.get("_id")), ConfigDoc);
        }
    }
}
