module com.ap.infinitypos_v1 {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.ap.infinitypos_v1 to javafx.fxml;
    exports com.ap.infinitypos_v1;
}