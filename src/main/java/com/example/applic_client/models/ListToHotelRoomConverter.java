package com.example.applic_client.models;

import java.util.ArrayList;
import java.util.List;

public class ListToHotelRoomConverter {
    public List<String> room_to_list(HotelRoom room){
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(room.getId()));
        list.add(room.getType());
        list.add(Boolean.toString(room.isBusy()));
        list.add(Boolean.toString(room.isDirty()));
        list.add(Double.toString(room.getCost()));
        list.add(Integer.toString(room.getNumber_of_beds())); 
        list.add(Integer.toString(room.getFloor()));
        list.add(room.getGuest_name());
        list.add(room.getCleaner());
        return list;
    }

    public HotelRoom list_to_room(List<String> str_list) {
        return new HotelRoom(Integer.parseInt(str_list.get(0)),str_list.get(1), Boolean.parseBoolean(str_list.get(2)),
                Boolean.parseBoolean(str_list.get(3)), Double.parseDouble(str_list.get(4)),
                Integer.parseInt(str_list.get(5)), Integer.parseInt(str_list.get(6)), str_list.get(7), str_list.get(8));
    }
    public List<HotelRoom> list_to_rooms(List<String> str_list){
        int n = str_list.size()/9;
        List<HotelRoom> room_list = new ArrayList<>();
//        System.out.println("Before: " + str_list);
        for (int i=0; i<n; i++){
            room_list.add(new HotelRoom(Integer.parseInt(str_list.get(i*9)),str_list.get(i*9+1), Boolean.parseBoolean(str_list.get(i*9+2)),
                    Boolean.parseBoolean(str_list.get(i*9+3)), Double.parseDouble(str_list.get(i*9+4)),
                    Integer.parseInt(str_list.get(i*9+5)), Integer.parseInt(str_list.get(i*9+6)), str_list.get(i*9+7), str_list.get(i*9+8)));
        }
//        System.out.println("After: " + app_list);
        return room_list;
    }
}
