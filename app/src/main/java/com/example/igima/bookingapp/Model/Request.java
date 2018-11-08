package com.example.igima.bookingapp.Model;

import java.util.List;

public class Request {
    private String id;
    private String name;
    private String total;
    private List<Order> bands;

    public Request() {
    }

    public Request(String id, String name, String total, List<Order> bands) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.bands = bands;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getBands() {
        return bands;
    }

    public void setBands(List<Order> bands) {
        this.bands = bands;
    }
}
