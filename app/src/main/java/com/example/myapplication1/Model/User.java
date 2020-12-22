package com.example.myapplication1.Model;

import java.util.Objects;

public class User{

    private String name, email, phone_number, password, token;
    private int id;

    public User() {
    }

    public User(String name, String email, String phone_number, String password, String token) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.token = token;
        this.id = Math.abs(this.hashCode());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getName().equals(user.getName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPhone_number(), user.getPhone_number()) &&
                getPassword().equals(user.getPassword()) &&
                getToken().equals(user.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPhone_number(), getPassword(), getToken());
    }
}
