package com.dkit.oop.sd2.DTOs;


public class Rockets {
    private int rocket_id;
    private String rocket_name;
    private String manufacturer;
    private String date;
    private int payload_capacity;
    public Rockets(int rocket_id, String rocket_name, String manufacturer, String date, int payload_capacity){
        this.rocket_id = rocket_id;
        this.rocket_name = rocket_name;
        this.manufacturer = manufacturer;
        this.date = date;
        this.payload_capacity = payload_capacity;
    }
    public Rockets(String rocket_name, String manufacturer, String date, int payload_capacity){
        this.rocket_name = rocket_name;
        this.manufacturer = manufacturer;
        this.date = date;
        this.payload_capacity = payload_capacity;
    }
    public Rockets(){
        this.rocket_id = 0;
        this.rocket_name = "Starship";
        this.manufacturer = "SpaceX";
        this.date = "N/A";
        this.payload_capacity = 150000;
    }

    public int getRocket_id() {
        return rocket_id;
    }

    public void setRocket_id(int rocket_id) {
        this.rocket_id = rocket_id;
    }

    public String getRocket_name() {
        return rocket_name;
    }

    public void setRocket_name(String rocket_name) {
        this.rocket_name = rocket_name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayload_capacity() {
        return payload_capacity;
    }

    public void setPayload_capacity(int payload_capacity) {
        this.payload_capacity = payload_capacity;
    }

    @Override
    public String toString() {
        return "Rockets{" +
                "rocket_id=" + rocket_id +
                ", rocket_name='" + rocket_name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", date='" + date + '\'' +
                ", payload_capacity=" + payload_capacity +
                '}';
    }
}
