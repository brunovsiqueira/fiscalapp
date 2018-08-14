package br.com.infracea.fiscalapp.models;

public class User {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String birthDate;
    private String id;

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
}
