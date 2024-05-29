package com.example.applic_client.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListToBookingConverter {
    public List<String> booking_to_list(List<Booking> booking){
        List<String> list = new ArrayList<>();
        for (Booking new_booking: booking){
            list.add(Integer.toString(new_booking.getId()));
            list.add(Integer.toString(new_booking.getRoom()));
            list.add(new_booking.getStartDate().toString());
            list.add(new_booking.getEndDate().toString());
            list.add(new_booking.getGuestName());
        }
        return list;
    }

    public Booking list_to_booking(List<String> str_list) {
        return new Booking(Integer.parseInt(str_list.get(0)), Integer.parseInt(str_list.get(1)),
                LocalDate.parse(str_list.get(2)), LocalDate.parse(str_list.get(3)),
                str_list.get(4));
    }
    public List<Booking> list_to_bookings(List<String> str_list){
        int n = str_list.size()/5;
        List<Booking> bookings_list = new ArrayList<>();
//        System.out.println("Before: " + str_list);
        for (int i=0; i<n; i++){
            bookings_list.add(new Booking(Integer.parseInt(str_list.get(i*5)), Integer.parseInt(str_list.get(i*5+1)),
                    LocalDate.parse(str_list.get(i*5+2)), LocalDate.parse(str_list.get(i*5+3)),
                    str_list.get(i*5+4)));
        }
//        System.out.println("After: " + app_list);
        return bookings_list;
    }
}
