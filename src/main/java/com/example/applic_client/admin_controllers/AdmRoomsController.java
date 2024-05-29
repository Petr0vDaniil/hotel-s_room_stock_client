package com.example.applic_client.admin_controllers;

import com.example.applic_client.models.Admin;
import com.example.applic_client.models.Booking;
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

public class AdmRoomsController {
    List<HotelRoom> all_rooms;
    List<Booking> bookings;
    @FXML
    AnchorPane applics_anchor;
    private String name;
    private Admin admin;
    private List<String> cleaners = new ArrayList<>();


    public void switchToMainMenu(ActionEvent go_back_clicked) throws IOException {
        Stage stage = (Stage)((Node)go_back_clicked.getSource()).getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/adm-main.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);
        AdmMainController menuController = menuLoader.getController();
//        menuController.connect();
        menuController.prepare_main_menu2(name, all_rooms, admin, cleaners);

        stage.setScene(menuScene);
        stage.show();

    }
    public void prepare_jobs(List<HotelRoom> dirty_hotel_rooms, String name, Admin admin_inp, List<String> cleaners_inp){
        this.all_rooms = dirty_hotel_rooms;
        admin = admin_inp;
//        System.out.println("Progress applics inflitrated: " + this.jobs_applics);
        this.name = name;
        this.cleaners = cleaners_inp;
        render_applications();
    }


    public void update_rooms(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/all-rooms.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AdmRoomsController applicController = loader.getController();
        applicController.prepare_jobs(all_rooms, name, admin, cleaners);

        stage.setScene(scene);
        stage.show();
    }
    public void render_applications(){
        render_buttons();
        render_applic_text();
    }

    public void render_applic_text(){
        double fp_top_anchor = 10;
        for (HotelRoom curr : all_rooms) {
            FlowPane fp = new FlowPane(0, 10);
            fp.setPrefWidth(600);
            List<Label> lbl_list = new ArrayList<>();
            lbl_list.add(new Label(" Id: " + curr.getId()));
            lbl_list.add(new Label(" Floor: " + curr.getFloor()));
            lbl_list.add(new Label(" Type: " + curr.getType()));
            lbl_list.add(new Label(" Cost: " + curr.getCost()));
            lbl_list.add(new Label(" Number_of_beds: " + curr.getNumber_of_beds()));
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
                lbl.setPrefWidth(80);
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
        List<Button> edit_btn_list = new ArrayList<>();
        for (int i = 0; i < all_rooms.size(); i++){
            // Создание кнопки для удаления комнаты
            Button fin_btn = new Button("delete the room");
            fin_btn.setPrefWidth(150);
            fin_btn.setId("finish_button_" + i);

            // Обработчик события для кнопки удаления
            fin_btn.setOnAction(event -> {
                try {
                    finish_application(event, fin_btn.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Создание кнопки для редактирования бронирования
            Button edit_btn = new Button("edit bookings");
            edit_btn.setPrefWidth(150);
            edit_btn.setId("edit_button_" + i);

            // Обработчик события для кнопки редактирования
            edit_btn.setOnAction(event -> {
                try {
                    open_list_bookings(event, fin_btn.getId());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            // Добавление кнопок в соответствующие списки
            finish_btn_list.add(fin_btn);
            edit_btn_list.add(edit_btn);
        }

        double top_padding = 43;
        double left_acc_padding = 150;
        double button_spacing = 80; // Расстояние между кнопками
        for(int i = 0; i < all_rooms.size(); i++){
            // Позиционирование кнопки удаления
            AnchorPane.setTopAnchor(finish_btn_list.get(i), top_padding);
            AnchorPane.setLeftAnchor(finish_btn_list.get(i), left_acc_padding);

            // Позиционирование кнопки редактирования
            AnchorPane.setTopAnchor(edit_btn_list.get(i), top_padding);
            AnchorPane.setLeftAnchor(edit_btn_list.get(i), left_acc_padding + 160); // Смещение относительно кнопки удаления

            top_padding += button_spacing;
        }

        // Добавление всех кнопок в AnchorPane
        applics_anchor.getChildren().addAll(finish_btn_list);
        applics_anchor.getChildren().addAll(edit_btn_list);
    }

    private void open_list_bookings(ActionEvent event, String id) throws IOException, ClassNotFoundException {
        int acc_app_num = Integer.parseInt(id.substring(id.length() - 1));
        admin.sendForm("List",  all_rooms.get(acc_app_num).getId());
        bookings = admin.getForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/bookings.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AdmBookingsController controller = loader.getController();
        controller.prepare_jobs(all_rooms, name, admin, cleaners, bookings);

        stage.setScene(scene);
        stage.show();
    }

    public void finish_application(ActionEvent accept_clicked, String acc_id) throws IOException {
        int acc_app_num = Integer.parseInt(acc_id.substring(acc_id.length() - 1));
//        System.out.println("Job about to finish: " + jobs_applics.get(acc_app_num).get_status());
        admin.sendForm("Room",  all_rooms.get(acc_app_num).getId());
        all_rooms.remove(acc_app_num);
//        CleanerMainController.update_all_applications(jobs_applics);
//        CleanerMainController.delete_application(acc_app_num);
        update_rooms(accept_clicked);
    }

}
