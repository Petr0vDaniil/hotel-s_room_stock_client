package com.example.applic_client.cleaner_controllers;

import com.example.applic_client.models.Cleaner;
import com.example.applic_client.models.HotelRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CleanerMainController {
    @FXML
    Label cleanerNameHeader;
    private Stage stage;
    private static String name;
    private Cleaner cleaner;
    private static List<HotelRoom> progress_rooms;

    public void switchToJobs(ActionEvent applications_clicked) throws IOException {
        stage = (Stage)((Node)applications_clicked.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/cleaner_views/clean-jobs.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        CleanerJobsController controller = loader.getController();
        controller.prepare_jobs(progress_rooms, name, cleaner);


        stage.setScene(scene);
        stage.show();


    }
    public void set_header_name(String name){
        CleanerMainController.name = name;
        cleanerNameHeader.setText("Cleaner " + name + ".");
    }
    public void prepare_main_menu(Cleaner cleaner_inp, List<HotelRoom> rooms){
        set_header_name(name);
        cleaner = cleaner_inp;
        progress_rooms = rooms;
    }
    public void prepare_main_menu(String inp_name, Cleaner cleaner_inp){
        set_header_name(inp_name);
        cleaner = cleaner_inp;

    }
    public void connect(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            cleaner = new Cleaner(socket, ois, oos);
            cleaner.sendNameToServer(name);
            Thread cleanerThread = new Thread(cleaner);
            cleanerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void quit(ActionEvent quit_clicked) throws IOException{
        Platform.exit();
        System.exit(0);
    }

    public static void update_all_applications(List<HotelRoom> rooms){
        progress_rooms = rooms;
    }
    public static void update_all_rooms(List<HotelRoom> rooms){
        progress_rooms = rooms;
    }
    public static void delete_application(int applic){
        progress_rooms.remove(applic);
    }


}
