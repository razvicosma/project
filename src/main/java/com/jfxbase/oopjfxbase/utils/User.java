package com.jfxbase.oopjfxbase.utils;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private Boolean admin_rights;
    private String profile_picture;
    private List<Booking> bookings = new ArrayList<>();

    public User(Integer id, String first_name, String last_name, String username, String password, Boolean admin_rights, String profile_picture) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.admin_rights = admin_rights;
        this.profile_picture = profile_picture;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin_rights() {
        return admin_rights;
    }

    public void setAdmin_rights(Boolean admin_rights) {
        this.admin_rights = admin_rights;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public void addBooking(Booking booking) { this.bookings.add(booking); }
}
