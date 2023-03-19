package com.ap.infinitypos_v1;


import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Usuario {
    public String Nombre;
    public String User;
    private String Password;
    public String role;
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
            this.role = LoginDoc.getString("role");
            this.isLogin = true;
            return true;
        }





    }

    }