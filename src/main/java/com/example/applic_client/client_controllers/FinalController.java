package com.example.applic_client.client_controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class FinalController {
    @FXML
    Label text = new Label();
    public void exit(ActionEvent event) throws IOException {
        Platform.exit();
    }

    public void prepare_text(String new_text) {
        text.setText(new_text);
    }
}
