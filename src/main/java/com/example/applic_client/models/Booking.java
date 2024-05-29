package com.example.applic_client.models;

import java.io.Serializable;
import java.time.LocalDate;



//    public boolean checkAvailability(int room, Date startDate, Date endDate) {
//        for (Booking booking : bookings) {
//            if (booking.getRoom() == room) {
//                if (!(endDate.before(booking.getStartDate()) || startDate.after(booking.getEndDate()))) {
//                    return false; // Номер занят в указанный период времени
//                }
//            }
//        }
//        return true; // Номер доступен
//    }

public class Booking implements Serializable {
    private Integer id;
    private int room;
    private LocalDate startDate;
    private LocalDate endDate;
    private String guestName;

    public Booking(Integer id, int room, LocalDate startDate, LocalDate endDate, String guestName) {
        this.id = id;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestName = guestName;
    }

    public Integer getId() {
        return id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getGuestName() {
        return guestName;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", room=" + room +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", guestName='" + guestName + '\'' +
                '}';
    }
}


