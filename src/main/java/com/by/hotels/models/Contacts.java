package com.by.hotels.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Contacts {
    private String phone;
    private String email;

    public Contacts() {
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Contacts)) return false;
        final Contacts other = (Contacts) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$phone = this.getPhone();
        final Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Contacts;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        return result;
    }

    public String toString() {
        return "Contacts(phone=" + this.getPhone() + ", email=" + this.getEmail() + ")";
    }
}
