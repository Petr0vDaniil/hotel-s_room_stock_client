package com.example.applic_client.client_controllers;

import com.example.applic_client.Main;
import com.example.applic_client.models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;

public class InteractController {
    private Stage stage;
    String type;
    String name;
    @FXML
    DatePicker check_in = new DatePicker();
    @FXML
    DatePicker check_out = new DatePicker();
    @FXML
    Label error_label = new Label();
    @FXML
    TextField number_of_beds = new TextField();
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    private static Client client;
    public void prepare_inter(String new_type, String inp_name, Socket new_socket, ObjectInputStream new_ois,
                              ObjectOutputStream new_oos) throws IOException {
        socket = new_socket;
        ois = new_ois;
        oos = new_oos;
        type = new_type;
        name = inp_name;
        System.out.println(name + "Interact");
    }

    public void sendForm(ActionEvent event) throws IOException, ClassNotFoundException {
        if (!check_out.getValue().isAfter(check_in.getValue())) {
            error_label.setText("Check-out must be after Check-in");
        }
        else{
            try {
                int number = Integer.parseInt(number_of_beds.getText());
                sendFormOnServer(event, type, number, check_in.getValue(), check_out.getValue());
            } catch (NumberFormatException e) {
                error_label.setText("Number of beds must be integer number");
            }
        }
    }

    public void sendFormOnServer(ActionEvent event, String type, Integer nums_of_beds, LocalDate check_in,
                             LocalDate check_out) throws IOException, ClassNotFoundException {
        connect(socket, ois, oos);
        client.sendForm(name, type, nums_of_beds, check_in, check_out);
        String answer = client.getForm();
        switch (answer){
            case "Yes":
                load_final(event, "The room has been successfully booked. Come to our hotel at the specified time " +
                        check_in + " and tell us your name " + name);
                break;
            case "No":
                load_final(event, "The room was not booked. There are no numbers that meet your requirements," +
                        " try changing your input or call support");
                break;
        }
    }


    public void load_final(ActionEvent event, String text) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/applic_client/client_views/final.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        FinalController finalController = fxmlLoader.getController();
        finalController.prepare_text(text);
        stage.setScene(scene);
        stage.show();
    }

    public void connect(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.reset();
            client = new Client(socket, ois, oos);
            Thread clientThread = new Thread(client);
            clientThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
