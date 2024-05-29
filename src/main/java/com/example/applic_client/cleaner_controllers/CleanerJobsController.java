package com.example.applic_client.cleaner_controllers;

import com.example.applic_client.models.Cleaner;
import com.example.applic_client.models.HotelRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CleanerJobsController {
    List<HotelRoom> dirty_rooms;
    @FXML
    AnchorPane applics_anchor;
    private String name;
    private Cleaner cleaner;


    public void switchToMainMenu(ActionEvent go_back_clicked) throws IOException {
        Stage stage = (Stage)((Node)go_back_clicked.getSource()).getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/applic_client/cleaner_views/work-main.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);
        CleanerMainController menuController = menuLoader.getController();
//        menuController.connect();
        menuController.prepare_main_menu(cleaner, dirty_rooms);

        stage.setScene(menuScene);
        stage.show();

    }
    public void prepare_jobs(List<HotelRoom> dirty_hotel_rooms, String name, Cleaner cleaner){
        this.dirty_rooms = dirty_hotel_rooms;
//        System.out.println("Progress applics inflitrated: " + this.jobs_applics);
        this.name = name;
        this.cleaner = cleaner;
        render_applications();
    }


        public void update_applications(ActionEvent event) throws IOException {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/cleaner_views/clean-jobs.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            CleanerJobsController applicController = loader.getController();
            applicController.prepare_jobs(dirty_rooms, name, cleaner);

            stage.setScene(scene);
            stage.show();
        }
        public void render_applications(){
        render_buttons();
        render_applic_text();
    }

    public void render_applic_text(){
        double fp_top_anchor = 10;
        for (HotelRoom curr : dirty_rooms) {
            FlowPane fp = new FlowPane(0, 10);
            fp.setPrefWidth(600);
            List<Label> lbl_list = new ArrayList<>();
            lbl_list.add(new Label(" Id: " + curr.getId()));
            lbl_list.add(new Label(" Floor: " + curr.getFloor()));
            lbl_list.add(new Label(" Type: " + curr.getType()));
            if (curr.isBusy()) {
                lbl_list.add(new Label(" " + "Occupied"));
            }
            else {
                lbl_list.add(new Label(" " + "Free"));
            }
            if (curr.isDirty()) {
                lbl_list.add(new Label(" " + "Needs cleaning"));
            } else {
                lbl_list.add(new Label(" " + "Clear"));
            }
            if (curr.isBusy()) {
                lbl_list.add(new Label(" Guest: " + curr.getGuest_name()));
            }
            lbl_list.forEach(lbl -> {
                lbl.setPrefWidth(70);
                lbl.setStyle("-fx-border-color: grey;");
                lbl.setPrefHeight(30);
            });
            fp.getChildren().addAll(lbl_list);
            AnchorPane.setTopAnchor(fp, fp_top_anchor);
            fp_top_anchor += 80;
            AnchorPane.setLeftAnchor(fp, 10.0);
            applics_anchor.getChildren().addAll(fp);
        }
    }

    public void render_buttons(){
        List<Button> finish_btn_list = new ArrayList<>();
        for (int i = 0; i< dirty_rooms.size(); i++){
            Button fin_btn = new Button();
            fin_btn.setPrefWidth(150);
            fin_btn.setId("finish_button_" + i);

            fin_btn.setOnAction(event -> {
                try {
                    finish_application(event, fin_btn.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fin_btn.setText("Clean the room");

            finish_btn_list.add(fin_btn);
        }

        double top_padding = 43;
        double left_acc_padding = 230;
        for(int i = 0; i< dirty_rooms.size(); i++){
            AnchorPane.setTopAnchor(finish_btn_list.get(i), top_padding);
            AnchorPane.setLeftAnchor(finish_btn_list.get(i), left_acc_padding);

            top_padding += 80;
        }

        applics_anchor.setCenterShape(true);
        applics_anchor.getChildren().addAll(finish_btn_list);
    }
    public void finish_application(ActionEvent accept_clicked, String acc_id) throws IOException {
        int acc_app_num = Integer.parseInt(acc_id.substring(acc_id.length() - 1));
        dirty_rooms.get(acc_app_num).setDirty(false);
//        System.out.println("Job about to finish: " + jobs_applics.get(acc_app_num).get_status());
        cleaner.sendForm(dirty_rooms.get(acc_app_num));
        dirty_rooms.remove(acc_app_num);
//        CleanerMainController.update_all_applications(jobs_applics);
//        CleanerMainController.delete_application(acc_app_num);
        update_applications(accept_clicked);
    }

}


