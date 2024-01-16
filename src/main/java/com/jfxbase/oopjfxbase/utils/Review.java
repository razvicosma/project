package com.jfxbase.oopjfxbase.utils;

import java.time.LocalDate;

public class Review {
    private Integer id;
    private Integer rating;
    private String comment;
    private LocalDate date;
    private String username;

    public Review(Integer id, Integer rating, String comment, LocalDate date, String username) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
