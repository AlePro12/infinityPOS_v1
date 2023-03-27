package com.ap.infinitypos_v1;


import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Usuario {
    public String Nombre;
    public String User;
    private String Password;
    public String Telefono;
    public Boolean AccessInv;
    public Boolean AccessPos;
    public Boolean AccessConfig;
    public conexion conn;
    private boolean isLogin = false;
    public Usuario(conexion conn) {

        this.conn = conn;
    }
    public boolean Login(String User, String Password) {
        //Login DB mongodb
        this.User = User;
        this.Password = Password;
        MongoCollection<Document> collection = conn.DB.getCollection("Login");

        Document LoginDoc = collection.find( new Document("User", User).append("Password", Password)).first();

        if (LoginDoc == null) {
            System.out.println("No exist User");
            return false;
        } else {
            //System.out.println(LoginDoc.toJson());
            this.Nombre = LoginDoc.getString("Nombre");
            this.Telefono = LoginDoc.getString("Telefono");
            this.AccessInv = LoginDoc.getBoolean("AccessInv");
            this.AccessPos = LoginDoc.getBoolean("AccessPos");
            this.AccessConfig = LoginDoc.getBoolean("AccessConfig");
            this.isLogin = true;
            return true;
        }
    }
    //gets
    public String getNombre() {
        return this.Nombre;
    }
    public String getUser() {
        return this.User;
    }
    //set user
    public void setUsr(String User, String Password, String Nombre, String Telefono, Boolean AccessInv, Boolean AccessPos, Boolean AccessConfig) {
        this.User = User;
        this.Password = Password;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.AccessInv = AccessInv;
        this.AccessPos = AccessPos;
        this.AccessConfig = AccessConfig;
    }

    public String getPassword() {
        return this.Password;
    }
public String getTelefono() {
        return this.Telefono;
    }
    public Boolean getAccessInv() {
        return this.AccessInv;
    }
    public Boolean getAccessFac() {
        return this.AccessPos;
    }
    public Boolean getAccessConfig() {
        return this.AccessConfig;
    }
    public boolean isLogin() {
        return this.isLogin;
    }
}