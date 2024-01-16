package com.jfxbase.oopjfxbase.utils;

import java.time.LocalDate;

public class Booking {
    private Integer id;
    private Integer user_id;
    private Integer apartment_id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer totalCost;
    private Review review;

    public Booking(Integer id, Integer user_id, Integer apartment_id, LocalDate checkIn, LocalDate checkOut, Integer totalCost, Review review) {
        this.id = id;
        this.user_id = user_id;
        this.apartment_id = apartment_id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.review = review;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(Integer apartment_id) {
        this.apartment_id = apartment_id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
