package com.example.applic_client.admin_controllers;

import com.example.applic_client.models.Admin;
import com.example.applic_client.models.HotelRoom;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdmNewRoomController {
    @FXML
    public TextField number_of_beds;
    @FXML
    public Label error_label;
    @FXML
    public ChoiceBox<String> type_of_room;
    @FXML
    public ChoiceBox<String> cleaner_of_room;
    @FXML
    public TextField floor_of_room;
    @FXML
    public TextField cost_of_room;
    ObservableList<String> user_types = FXCollections.observableArrayList();
    ObservableList<String> user_cleaners = FXCollections.observableArrayList();
    Admin admin;
    List<String> cleaners = new ArrayList<>();

    public void sendForm(ActionEvent event) throws IOException {
        int number = 0, floor = 0;
        double cost = 0.0;
        try {
            number = Integer.parseInt(number_of_beds.getText());
        } catch (NumberFormatException e) {
            error_label.setText("Number of beds must be integer number");
        }
        try {
            floor = Integer.parseInt(floor_of_room.getText());
        } catch (NumberFormatException e) {
            error_label.setText("Number of beds must be integer number");
        }
        try {
            cost = Double.parseDouble(cost_of_room.getText());
        } catch (NumberFormatException e) {
            error_label.setText("Number of beds must be integer number");
        }
        if (number < 0) {
            error_label.setText("Number of beds must be more than 0");
        } else if (floor < 0) {
            error_label.setText("Floor must be more than 0");
        } else if (cost < 0) {
            error_label.setText("Cost must be more than 0");
        } else {
            admin.sendForm("New", type_of_room.getValue(), cleaner_of_room.getValue(), number, floor, cost);
            Platform.exit();
        }
    }

    public void prepare(Admin admin_inp, List<String> cleaners_inp){
        admin = admin_inp;
        cleaners = cleaners_inp;
        prepare_type_choice();
    }
    public void prepare_type_choice(){
        user_types.addAll("Standard", "Standard Business", "Standard Premium", "Deluxe Comfort", "Lux Studio",
                "Lux Premium", "Lux Romantic", "Lux Marine", "Business Lux", "Apartments");
        type_of_room.setItems(user_types);
        type_of_room.setValue("Standard");
        user_cleaners.addAll(cleaners);
        System.out.println(cleaners);
        cleaner_of_room.setItems(user_cleaners);
    }
}
