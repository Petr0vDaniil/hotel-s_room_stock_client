package com.example.applic_client.models;
//package com.example.applic_server.models;

import com.example.applic_client.admin_controllers.AdmMainController;
import com.example.applic_client.client_controllers.InteractController;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends User{
    public Client(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        super(socket, "Client", ois, oos);
    }

    @Override
    public void run() {}


    public void sendForm(String name, String type, Integer nums_of_beds, LocalDate check_in, LocalDate check_out) {
        try {
            oos.writeObject(name);
            oos.writeObject(type);
            oos.writeObject(nums_of_beds);
            oos.writeObject(check_in);
            oos.writeObject(check_out);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("Error sending application to client");
            close_everything(socket, oos, ois);
        }
    }

    public String getForm() throws IOException, ClassNotFoundException {
        return (String) ois.readObject();
    }
}
