package com.example.applic_client.models;

import java.io.Serializable;

public class HotelRoom implements Serializable {
    private Integer id;
    private String type;
    private boolean isBusy;
    private boolean isDirty;
    private Double cost;
    private Integer number_of_beds;
    private Integer floor;
    private String guest_name;
    private String cleaner;


    public HotelRoom(){
        this.id = 0;
        this.type="Standart";
        this.isBusy=false;
        this.isDirty=false;
        this.cost=500.0;
        this.number_of_beds=2;
        this.floor=1;
        this.guest_name="";
        this.cleaner="";
    }
    public HotelRoom(Integer id, String type, boolean isBusy, boolean isDirty, Double cost, Integer number_of_beds, Integer floor, String guest_name, String cleaner) {
        this.id = id;
        this.type = type;
        this.isBusy = isBusy;
        this.isDirty = isDirty;
        this.cost = cost;
        this.number_of_beds = number_of_beds;
        this.floor = floor;
        this.guest_name = guest_name;
        this.cleaner = cleaner;
    }

    public void print(){
        System.out.println("-".repeat(38));
        System.out.println(id);
        System.out.println(type);
        System.out.println(isBusy);
        System.out.println(isDirty);
        System.out.println(cost);
        System.out.println(number_of_beds);
        System.out.println(floor);
        System.out.println(guest_name);
        System.out.println(cleaner);
        System.out.println("-".repeat(38));

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getNumber_of_beds() {
        return number_of_beds;
    }

    public void setNumber_of_beds(Integer number_of_beds) {
        this.number_of_beds = number_of_beds;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }
}
