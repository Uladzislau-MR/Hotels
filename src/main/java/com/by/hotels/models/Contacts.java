package com.by.hotels.models;

import jakarta.persistence.Embeddable;



@Embeddable
public class Contacts {
    private String phone;
    private String email;

    public Contacts() {
    }

    public Contacts(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String toString() {
        return "Contacts(phone=" + this.getPhone() + ", email=" + this.getEmail() + ")";
    }
}
