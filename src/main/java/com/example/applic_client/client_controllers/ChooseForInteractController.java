package com.example.applic_client.client_controllers;

import com.example.applic_client.models.Client;
import com.example.applic_client.models.HotelRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ChooseForInteractController {

    @FXML
    TilePane choose_for_interact_anchor;
    String name;
    private static Client client;
    static Socket socket;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;


    public class CustomButtonComponent extends VBox {
        public CustomButtonComponent(String imagePath, Integer i) {
            Button button = new Button("Go");
            button.setId("rem_button_" + i);
            button.setPrefWidth(270);
            String label;
            label = switch (i) {
                case 1 -> "Standard";
                case 2 -> "Standard Business";
                case 3 -> "Standard Premium";
                case 4 -> "Deluxe Comfort";
                case 5 -> "Lux Studio";
                case 6 -> "Lux Premium";
                case 7 -> "Lux Romantic";
                case 8 -> "Lux Marine";
                case 9 -> "Business Lux";
                case 10 -> "Apartments";
                default -> "";
            };
            String type;
            type = switch (i) {
                case 1 -> "Standard";
                case 2 -> "Standard Business";
                case 3 -> "Standard Premium";
                case 4 -> "Deluxe Comfort";
                case 5 -> "Lux Studio";
                case 6 -> "Lux Premium";
                case 7 -> "Lux Romantic";
                case 8 -> "Lux Marine";
                case 9 -> "Business Lux";
                case 10 -> "Apartments";
                default -> "";
            };
            Text text = new Text(label);
            text.setFont(Font.font(18));
            button.setFont(Font.font(18));
            this.setAlignment(Pos.CENTER);
            button.setOnAction(event -> {
                try {
                    switchToInteract(event, type);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            //TODO Сделать переход по кнопке
            this.getChildren().addAll(text, button);
        }
    }

    public void render_view_buttons(){
        choose_for_interact_anchor.setPadding(new Insets(5));
        choose_for_interact_anchor.setHgap(10);
        choose_for_interact_anchor.setVgap(10);
        choose_for_interact_anchor.setCenterShape(true);
        choose_for_interact_anchor.setPrefColumns(5);
        for (int i = 1; i <= 10; i++) {
            CustomButtonComponent customButton = new CustomButtonComponent("/com/example/applic_client/client_views/image" + i + ".jpg", i);
//            TilePane.setMargin(customButton, new Insets(10));
            choose_for_interact_anchor.getChildren().add(customButton);
        }
    }

    public void prepare_name(String inp_name){
        name = inp_name;
        System.out.println(name);
    }

    public void switchToInteract(ActionEvent event, String type) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader specLoader;
        specLoader = new FXMLLoader(getClass().getResource("/com/example/applic_client/client_views/interact.fxml"));
        Parent specRoot = specLoader.load();
        Scene specScene = new Scene(specRoot);
        InteractController interController = specLoader.getController();
        interController.prepare_inter(type, name, socket, ois, oos);
        stage.setScene(specScene);
        stage.show();
    }
    public static void connect(Socket new_socket, ObjectInputStream new_ois, ObjectOutputStream new_oos) {
        socket = new_socket;
        ois = new_ois;
        oos = new_oos;
    }
}
