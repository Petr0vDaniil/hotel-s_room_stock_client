module com.example.applic_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.applic_client to javafx.fxml;
    opens com.example.applic_client.controllers to javafx.fxml;
    exports com.example.applic_client;
    opens com.example.applic_client.client_controllers to javafx.fxml;
    opens com.example.applic_client.admin_controllers to javafx.fxml;
    opens com.example.applic_client.cleaner_controllers to javafx.fxml;
}