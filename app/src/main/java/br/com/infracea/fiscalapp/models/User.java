package br.com.infracea.fiscalapp.models;

import com.google.firebase.database.DataSnapshot;

public class User {

    private String id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String birthDate;
    private double latitude;
    private double longitude;


    public static User userObj;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public static User getInstance () {
        if (userObj == null) {
            userObj = new User();
        }
        return userObj;
    }

    public void setNewCurrentUser(DataSnapshot snapshot) {
        name = snapshot.child("name").getValue().toString();
        id = snapshot.child("id").getValue().toString();
        cpf = snapshot.child("cpf").getValue().toString();
        email = snapshot.child("email").getValue().toString();
        phone = snapshot.child("phone").getValue().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
