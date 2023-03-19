module com.ap.infinitypos_v1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires MaterialFX;

    opens com.ap.infinitypos_v1 to javafx.fxml;
    exports com.ap.infinitypos_v1;
}