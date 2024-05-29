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

public class AdmBookingsController {
    List<HotelRoom> all_rooms;
    List<Booking> all_bookings;
    @FXML
    AnchorPane applics_anchor;
    private Admin admin;
    private String name;
    private List<String> cleaners = new ArrayList<>();
    public void switchToMainMenu(ActionEvent go_back_clicked) throws IOException {
        Stage stage = (Stage)((Node)go_back_clicked.getSource()).getScene().getWindow();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/adm-main.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);
        AdmMainController menuController = menuLoader.getController();
        menuController.prepare_main_menu2(name, all_rooms, admin, cleaners);

        stage.setScene(menuScene);
        stage.show();
    }

    public void prepare_jobs(List<HotelRoom> all_rooms_inp, String name, Admin admin_inp, List<String> cleaners_inp,
                             List<Booking> bookings_inp) {
        this.all_rooms = all_rooms_inp;
        admin = admin_inp;
//        System.out.println("Progress applics inflitrated: " + this.jobs_applics);
        this.name = name;
        this.cleaners = cleaners_inp;
        all_bookings = bookings_inp;
        render_applications();
    }
    public void update_bookings(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applic_client/adm_views/bookings.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AdmBookingsController controller = loader.getController();
        controller.prepare_jobs(all_rooms, name, admin, cleaners, all_bookings);

        stage.setScene(scene);
        stage.show();
    }
    public void render_applications(){
        render_buttons();
        render_applic_text();
    }

    public void render_applic_text(){
        double fp_top_anchor = 10;
        for (Booking curr : all_bookings) {
            FlowPane fp = new FlowPane(0, 10);
            fp.setPrefWidth(600);
            List<Label> lbl_list = new ArrayList<>();
            lbl_list.add(new Label(" Id: " + curr.getId()));
            lbl_list.add(new Label(" RoomId: " + curr.getRoom()));
            lbl_list.add(new Label(" Start: " + curr.getStartDate()));
            lbl_list.add(new Label(" End: " + curr.getEndDate()));
            lbl_list.add(new Label(" GuestName: " + curr.getGuestName()));
            lbl_list.forEach(lbl -> {
                lbl.setPrefWidth(95);
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
        for (int i = 0; i < all_bookings.size(); i++){
            // Создание кнопки для удаления комнаты
            Button fin_btn = new Button("delete the booking");
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
            finish_btn_list.add(fin_btn);
        }

        double top_padding = 43;
        double left_acc_padding = 230;
        double button_spacing = 80; // Расстояние между кнопками
        for(int i = 0; i < all_bookings.size(); i++){
            AnchorPane.setTopAnchor(finish_btn_list.get(i), top_padding);
            AnchorPane.setLeftAnchor(finish_btn_list.get(i), left_acc_padding);
            top_padding += button_spacing;
        }
        applics_anchor.getChildren().addAll(finish_btn_list);
    }
    public void finish_application(ActionEvent accept_clicked, String acc_id) throws IOException {
        int acc_app_num = Integer.parseInt(acc_id.substring(acc_id.length() - 1));
//        System.out.println("Job about to finish: " + jobs_applics.get(acc_app_num).get_status());
        admin.sendForm("Booking",  all_bookings.get(acc_app_num).getId());
        all_bookings.remove(acc_app_num);
//        CleanerMainController.update_all_applications(jobs_applics);
//        CleanerMainController.delete_application(acc_app_num);
        update_bookings(accept_clicked);
    }
}
