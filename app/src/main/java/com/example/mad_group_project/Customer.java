package com.example.mad_group_project;

public class Customer {
     private String firstname;
     private String lastname;
     private String input_email;
     private String postal_address;
     private String phone_number;
     private String password;
     private String et_confirm_password;

    public Customer(String firstname, String lastname, String input_email, String postal_address, String phone_number, String password, String et_confirm_password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.input_email = input_email;
        this.postal_address = postal_address;
        this.phone_number = phone_number;
        this.password = password;
        this.et_confirm_password = et_confirm_password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInput_email() {
        return input_email;
    }

    public void setInput_email(String input_email) {
        this.input_email = input_email;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
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

    public String getEt_confirm_password() {
        return et_confirm_password;
    }

    public void setEt_confirm_password(String et_confirm_password) {
        this.et_confirm_password = et_confirm_password;
    }
}
