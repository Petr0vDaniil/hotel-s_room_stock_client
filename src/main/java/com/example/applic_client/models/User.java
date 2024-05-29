package com.example.applic_client.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class User implements Runnable{
    protected ListToHotelRoomConverter converter2 = new ListToHotelRoomConverter();
    protected ListToBookingConverter converter3 = new ListToBookingConverter();
    protected Socket socket;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;



    public abstract void run();
    public User(Socket socket, String user_type, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            this.socket = socket;
            this.oos = oos;
            oos.writeObject(user_type);
            this.ois = ois;
        }
        catch(IOException e){
//            System.out.println("Error creating server");
            e.printStackTrace();
            close_everything(socket,oos,ois);
        }
    }
    public void stop_connection(){
        close_everything(socket, oos, ois);
    }

    public void close_everything(Socket socket, ObjectOutputStream bufferedWriter, ObjectInputStream bufferedReader){
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
