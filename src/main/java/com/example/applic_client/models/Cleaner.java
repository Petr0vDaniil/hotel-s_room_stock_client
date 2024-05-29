package com.example.applic_client.models;

import com.example.applic_client.cleaner_controllers.CleanerMainController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Cleaner extends User{
    Integer total_id;


    public Cleaner(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        super(socket, "Cleaner", ois, oos);
    }


    @Override
    public void run(){
        while(socket.isConnected()){
            List<HotelRoom> roomsFromServer;
            try {
                List<String> roomsString = (List<String>) ois.readObject();
                roomsFromServer = converter2.list_to_rooms(roomsString);
                CleanerMainController.update_all_rooms(roomsFromServer);
            } catch (IOException e) {
                close_everything(socket,oos,ois);
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public void sendForm(HotelRoom room) {
        try {
            List<String> list = converter2.room_to_list(room);
            oos.writeObject(list);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close_everything(socket, oos, ois);
        }
    }

    public void sendNameToServer(String name) throws IOException {
        try {
            oos.writeObject(name);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
            close_everything(socket, oos, ois);
        }
    }
}
