package com.example.applic_client.controllers;

import com.example.applic_client.Main;
import com.example.applic_client.admin_controllers.AdmMainController;
import com.example.applic_client.client_controllers.ChooseForInteractController;
import com.example.applic_client.models.Client;
import com.example.applic_client.models.Admin;
import com.example.applic_client.models.Cleaner;
import com.example.applic_client.cleaner_controllers.CleanerMainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

public class NameEnterController {
    @FXML
    ChoiceBox<String> user_type_choice;
    @FXML
    TextField nameTextField;
    @FXML
    Label enterNameLabel;
    @FXML
    AnchorPane nameEnterAnchor;
    ObservableList<String> user_types = FXCollections.observableArrayList();
    private Stage stage;
    private Client client;
    private Admin admin;
    private Cleaner cleaner;
    Label pass_label = new Label();
    Label pass_label_auth = new Label();
    Label pass_label_phone = new Label();
    Label pass_label_email = new Label();
    PasswordField user_password = new PasswordField();
    PasswordField user_password_auth = new PasswordField();
    TextField user_phone = new TextField();
    TextField user_email = new TextField();
    Button login_button = new Button();
    Button signup_button = new Button();
    Button back_button = new Button();
    Boolean signing_up = false;
    Socket createSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;




    public void prepare_enter_name() throws IOException {
        createSocket = new Socket("localhost",
                1234);
        oos = new ObjectOutputStream(createSocket.getOutputStream());
        ois = new ObjectInputStream(createSocket.getInputStream());
        nameTextField.requestFocus();
        prepare_type_choice();
        prepare_form_elements();
        loadUserLoginForm();

//        user_type_choice.AddListener

    }

    public void signup_enter(ActionEvent signUpClicked) throws IOException, ClassNotFoundException {
        String inp_name = nameTextField.getText();
        if (inp_name.length() > 9){
            enterNameLabel.setText("Enter shorter name, please");
        }
        else if(!validatePhoneNumber(user_phone.getText())){
            enterNameLabel.setText("You should enter correct phone number");
        }
        else if(!validateEmail(user_email.getText())){
            enterNameLabel.setText("You should enter correct email");
        }
        else if(inp_name.isEmpty()){
            enterNameLabel.setText("You should enter your name");
        }
        else if(user_type_choice.getValue() == null){
            enterNameLabel.setText("Choose who you are.");
        }
        else if(!user_password.getText().equals(user_password_auth.getText())){
           enterNameLabel.setText("Passwords are not equal!");
        }
        else if(user_password.getText().isEmpty()){
            enterNameLabel.setText("Enter password.");
        }
        else {
            register(signUpClicked, inp_name, user_password.getText(), user_phone.getText(), user_email.getText());
        }
    }
    
    public void register(ActionEvent event, String inp_name, String password, String phone, String email) throws IOException, ClassNotFoundException {
        check_with_server("Signup", inp_name, password, phone, email, event);
    }


    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\+\\d{11}|\\d{11})");
        return pattern.matcher(phoneNumber).matches();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        return pattern.matcher(email).matches();
    }


    public void login_enter(ActionEvent enterNameClicked) throws IOException, ClassNotFoundException {

        String inp_name = nameTextField.getText();
        if (inp_name.length() > 9){
            enterNameLabel.setText("Enter shorter name, please");
        }
        else if(inp_name.isEmpty()){
            enterNameLabel.setText("You should enter your name");
        }
        else if(user_type_choice.getValue() == null){
            enterNameLabel.setText("Choose who you are.");
        }
        else if(user_type_choice.getValue().equals("Admin")){
            check_with_server("Login", "Admin", user_password.getText(), user_phone.getText(),
                    user_email.getText(), enterNameClicked);
        }
        else {
          check_with_server("Login", inp_name, user_password.getText(), user_phone.getText(),
                  user_email.getText(), enterNameClicked);
        }
    }

    public void load_user(ActionEvent event, String name, String password, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        switch(user_type_choice.getValue()){
            case "Client":
                load_client(event, name, socket, ois, oos);
                break;
            case "Admin":
                load_admin(event, name, socket, ois, oos);
                break;
            case "Cleaner":
                load_cleaner(event, name, socket, ois, oos);
                break;
        }
    }
    public void load_client(ActionEvent enterNameClicked, String inp_name, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        stage = (Stage)((Node)enterNameClicked.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/applic_client/client_views/choose_for_interact.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        ChooseForInteractController chooseForInteractController = fxmlLoader.getController();
        chooseForInteractController.render_view_buttons();
        chooseForInteractController.prepare_name(inp_name);
        System.out.println(inp_name);
        ChooseForInteractController.connect(socket, ois, oos);
//        menuController.prepare_main_menu(inp_name,client);
//        menuController.connect(socket, ois, oos);
        //TODO Разобраться с серваком
        stage.setScene(scene);
        stage.show();
    }

    public void load_admin(ActionEvent enterNameClicked, String inp_name, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        stage = (Stage)((Node)enterNameClicked.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/applic_client/adm_views/adm-main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        AdmMainController controller = fxmlLoader.getController();
        controller.prepare_main_menu(inp_name, admin);
        controller.connect(socket, ois, oos);
        stage.setScene(scene);
        stage.show();
    }


    public void load_cleaner(ActionEvent enterNameClicked, String inp_name, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        stage = (Stage)((Node)enterNameClicked.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/applic_client/cleaner_views/work-main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        CleanerMainController controller = fxmlLoader.getController();
        controller.prepare_main_menu(inp_name, cleaner);
        controller.connect(socket, ois, oos);
        stage.setScene(scene);
        stage.show();
    }

    public void prepare_form_elements(){
        pass_label.setText("Enter password:");
        pass_label_auth.setText("Confirm password:");
        pass_label_phone.setText("Enter phone:");
        pass_label_email.setText("Enter email:");

        user_password.setPrefWidth(258);
        user_password.setPrefHeight(34);
        user_password.setFont(Font.font(18));

        user_password_auth.setPrefWidth(258);
        user_password_auth.setPrefHeight(34);
        user_password_auth.setFont(Font.font(18));

        user_phone.setPrefWidth(258);
        user_phone.setPrefHeight(34);
        user_phone.setFont(Font.font(18));

        user_email.setPrefWidth(258);
        user_email.setPrefHeight(34);
        user_email.setFont(Font.font(18));

        login_button.setPrefWidth(80);
        login_button.setText("Login");
        login_button.setId("login_button");
        login_button.setOnAction(event -> {
            try {
                login_enter(event);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        signup_button.setPrefWidth(80);
        signup_button.setText("Sign up");
        signup_button.setId("signup_button");
        signup_button.setOnAction(event -> {
            try {
                signupButtonClicked(event);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        back_button.setPrefWidth(80);
        back_button.setText("Go back");
        back_button.setOnAction(event -> loadUserLoginForm());

        AnchorPane.setTopAnchor(pass_label, 180.0);
        AnchorPane.setTopAnchor(user_password, 200.0);

        AnchorPane.setLeftAnchor(login_button, 260.0);
        AnchorPane.setLeftAnchor(user_password, 170.0);
        AnchorPane.setLeftAnchor(user_password_auth, 170.0);
        AnchorPane.setLeftAnchor(user_phone, 170.0);
        AnchorPane.setLeftAnchor(user_email, 170.0);
        AnchorPane.setLeftAnchor(signup_button, 260.0);
        AnchorPane.setLeftAnchor(pass_label, 255.0);
        AnchorPane.setLeftAnchor(pass_label_auth, 255.0);
        AnchorPane.setLeftAnchor(pass_label_phone, 265.0);
        AnchorPane.setLeftAnchor(pass_label_email, 265.0);
    }


    public void clearForm(){
        nameEnterAnchor.getChildren().removeAll(user_password, user_password_auth, login_button,
                signup_button, pass_label, pass_label_auth, pass_label_phone, pass_label_email, user_phone, user_email);
    }

    public void prepare_type_choice(){
        user_types.addAll("Client", "Admin", "Cleaner");
        user_type_choice.setItems(user_types);
        user_type_choice.setValue("Client");
        user_type_choice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Action to be taken when choice box value changes
                switch(newValue){
                    case "Client", "Cleaner":
                        loadUserLoginForm();
                        break;
                    case "Admin":
                        loadAdminLoginForm();
                        break;
                }
            }
        });
    }
    public void loadUserLoginForm(){
        signing_up = false;
        clearForm();
//        AnchorPane.setTopAnchor(pass_label, 200.0);
//        AnchorPane.setTopAnchor(user_password, 260.0);
        AnchorPane.setTopAnchor(login_button, 250.0);
        AnchorPane.setTopAnchor(signup_button, 280.0);

        nameEnterAnchor.setCenterShape(true);
        nameEnterAnchor.getChildren().addAll(user_password, login_button, signup_button, pass_label);
    }

    public void signupButtonClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        if (signing_up){
           signup_enter(event);
        }
        else {
//          name_enter(event);
           loadSignupForm();

        }
    }

    public void loadSignupForm(){
        clearForm();
        signing_up = true;
        AnchorPane.setTopAnchor(pass_label_auth, 240.0);
        AnchorPane.setTopAnchor(user_password_auth, 260.0);
        AnchorPane.setTopAnchor(pass_label_phone, 300.0);
        AnchorPane.setTopAnchor(user_phone, 320.0);
        AnchorPane.setTopAnchor(pass_label_email, 360.0);
        AnchorPane.setTopAnchor(user_email, 380.0);
        AnchorPane.setTopAnchor(signup_button, 430.0);

        nameEnterAnchor.setCenterShape(true);
        nameEnterAnchor.getChildren().addAll(pass_label, user_password, pass_label_auth, pass_label_phone,
                pass_label_email, user_password_auth, user_phone, user_email, signup_button);

    }

    public void loadAdminLoginForm(){
        signing_up = false;
        clearForm();
        AnchorPane.setTopAnchor(signup_button, 280.0);
        nameEnterAnchor.getChildren().addAll(pass_label, user_password, login_button);


    }

    public void check_with_server(String desire, String name, String password, String phone, String email, ActionEvent event) throws IOException, ClassNotFoundException {
        oos.writeObject(desire);
        oos.writeObject(name);
        System.out.println(name + "1");
        oos.writeObject(user_type_choice.getValue());
        oos.writeObject(password);
        if (phone.equals("")) {phone="None";}
        oos.writeObject(phone);
        if (email.equals("")) {email="None";}
        oos.writeObject(email);
        oos.flush();
        switch((String) ois.readObject()){
            case "Correct":
                load_user(event, name, password, createSocket, ois, oos);
                break;
            case "Incorrect":
                enterNameLabel.setText("Incorrect password or name or desire!");
                break;
            case "Created":
                loadUserLoginForm();
                break;
            case "Exist":
                enterNameLabel.setText("This account already exists!");
                break;
        }
//        close_everything(createSocket, oos, ois);
    }

//    public void close_everything(Socket socket, ObjectOutputStream oos, ObjectInputStream ois){
//        try {
//            socket.close();
//            oos.close();
//            ois.close();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
};
