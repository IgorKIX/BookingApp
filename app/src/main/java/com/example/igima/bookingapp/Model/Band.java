package com.example.igima.bookingapp.Model;

public class Band {
    private String Description, MenuId, Name, Price;

    public Band() {
    }

    public Band(String description, String menuId, String name, String price) {
        Description = description;
        MenuId = menuId;
        Name = name;
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
