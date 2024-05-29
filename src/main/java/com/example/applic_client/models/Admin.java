package com.example.applic_client.models;
import com.example.applic_client.admin_controllers.AdmMainController;
import com.example.applic_client.cleaner_controllers.CleanerMainController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
    Integer total_id;


    public Admin(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        super(socket, "Admin", ois, oos);
    }


    @Override
    public void run(){
           while(socket.isConnected()){
               List<HotelRoom> roomsFromServer;
               try {
                   List<String> roomsString = (List<String>) ois.readObject();
                   roomsFromServer = converter2.list_to_rooms(roomsString);
                   AdmMainController.update_all_rooms(roomsFromServer);
                   List<String> cleaners = (List<String>) ois.readObject();
                   AdmMainController.update_cleaners(cleaners);
                   while (true) {
                       //command = (String) ois.readObject();
                   }
               } catch (IOException e) {
                   close_everything(socket,oos,ois);
                   break;
               } catch (ClassNotFoundException e) {
                   throw new RuntimeException(e);
               }

           }
       }

    public void sendForm(String message,  Integer id) throws IOException {
        sendString(message);
        deleteRoom(id);
    }
    private void deleteRoom(Integer id) {
        try {
            oos.writeObject(id);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close_everything(socket, oos, ois);
        }
    }

    public void sendForm(String message,  String type, String cleaner, Integer nums_of_beds, Integer floor, Double cost) throws IOException {
        sendString(message);
        sendNewRoom(type, cleaner, nums_of_beds, floor, cost);
    }

    private void sendNewRoom(String type, String cleaner, Integer nums_of_beds, Integer floor, Double cost) {
        try {
            oos.writeObject(type);
            oos.writeObject(cleaner);
            oos.writeObject(nums_of_beds);
            oos.writeObject(floor);
            oos.writeObject(cost);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close_everything(socket, oos, ois);
        }
    }

    public void sendString(String message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close_everything(socket, oos, ois);
        }
    }

    public void sendRoom(HotelRoom room) {
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

    public List<Booking> getForm() throws IOException, ClassNotFoundException {
        return converter3.list_to_bookings((List<String>) ois.readObject());
    }
}