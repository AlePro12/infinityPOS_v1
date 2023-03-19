package com.ap.infinitypos_v1;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class conexion {
    //mondoDB
    //construir la conexion
    public MongoClient mongoClient;
    public  MongoDatabase DB = null;
    public conexion() {
        //mongodb+srv://admin:<password>@cluster0.4zaig6r.mongodb.net/?retryWrites=true&w=majority

        String uri = "mongodb+srv://admin:LuRRzJzkBFksIezA@cluster0.4zaig6r.mongodb.net/?retryWrites=true&w=majority";


        // Construct a ServerApi instance using the ServerApi.builder() method
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        this.mongoClient  = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Infinity");
            try {
                // Send a ping to confirm a successful connection
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException me) {
                System.err.println(me);
            }
        DB = mongoClient.getDatabase("Infinity");
    }
    public void GetLogin() {
        if (DB == null) {
            System.out.println("No exist DB");
            return;
        }
        try {
            MongoCollection<Document> collection = DB.getCollection("Login");
            //if no exist collection
            if (collection == null) {
                System.out.println("No exist collection");
                return;
            } else {
                Document myDoc = collection.find().first();
                System.out.println(myDoc.toJson());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //sleep 5 seg
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    }
