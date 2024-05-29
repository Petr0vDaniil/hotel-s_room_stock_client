package com.example.applic_client.admin_controllers;

import com.example.applic_client.models.Admin;
import com.example.applic_client.models.HotelRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AdmMainController {
    private Stage stage;
    private static String name;
    private static List<HotelRoom> all_rooms;
    private static Admin admin;
    private static List<String> cleaners = new ArrayList<>();


    public void connect(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            admin = new Admin(socket, ois, oos);
            admin.sendNameToServer(name);
            Thread adminThread = new Thread(admin);
            adminThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void update_all_rooms(List<HotelRoom> rooms){
        all_rooms = rooms;
    }

    public static void update_cleaners(List<String> new_cleaners){
        cleaners = new_cleaners;
    }

    public void prepare_main_menu(String name, Admin admin_inp){
        admin = admin_inp;
        AdmMainController.name = name;
    }
    public void prepare_main_menu2(String name, List<HotelRoom> rooms, Admin admin_inp, List<String> cleaners_inp){
        AdmMainController.name = name;
        admin = admin_inp;
        all_rooms = rooms;
        cleaners = cleaners_inp;
    }
    public void quit(ActionEvent quit_clicked) throws IOException{
        Platform.exit();
        System.exit(0);
    }

    public void switchToJobs(ActionEvent applications_clicked) throws IOException {
        stage = (Stage)((Node)applications_clicked.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/all-rooms.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AdmRoomsController controller = loader.getController();
        controller.prepare_jobs(all_rooms, name, admin, cleaners);


        stage.setScene(scene);
        stage.show();


    }

    public void switchToNewRoom(ActionEvent applications_clicked) throws IOException {
        stage = (Stage)((Node)applications_clicked.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/new-room.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AdmNewRoomController controller = loader.getController();
        controller.prepare(admin, cleaners);


        stage.setScene(scene);
        stage.show();


    }
}

