package com.example.igima.bookingapp.Model;
//TODO:implement id
public class User {
    //Capital letters like in database
    private String Id;
    private String Name;
    private String Password;
    private String Nif;
    private String CardNumber;
    private String CardType;
    private String CardValidity;

    public User() {
    }

    public User(String id, String name, String password, String nif, String cardNumber, String cardType, String cardValidity) {
        Id = id;
        Name = name;
        Password = password;
        Nif = nif;
        CardNumber = cardNumber;
        CardType = cardType;
        CardValidity = cardValidity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public String getNif() {
        return Nif;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getCardType() {
        return CardType;
    }

    public String getCardValidity() {
        return CardValidity;
    }
}