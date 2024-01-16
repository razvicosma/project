package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class BookingListCellController extends SceneController{
    @FXML
    private Label label;
    private User user;
    private Booking booking;
    private Review review;
    @FXML
    private HBox hBox;
    @FXML
    private Slider slider;
    @FXML
    private TextField reviewField;
    @FXML
    private Label sentReview;

    public void setBooking(Booking booking) {
        label.setText("You have a reservation from " + booking.getCheckIn() + " to " + booking.getCheckOut() + " at the total price of " + booking.getTotalCost() +
                " $ at " + DBeaver.getApartmentName(new ActionEvent(), booking.getApartment_id()));
        this.booking = booking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }
    public void showReview(Booking booking) {
        LocalDate today = LocalDate.now();
        if((booking.getCheckOut().isBefore(today) || booking.getCheckOut().isEqual(today)) && booking.getReview() == null) {
            hBox.setVisible(true);
        } else {
            hBox.setVisible(false);
        }
    }
    public void sendReview() {
        Review review = new Review(null, Integer.valueOf((int) slider.getValue()), reviewField.getText(),LocalDate.now(),user.getUsername());
        DBeaver.makeReview(new ActionEvent(), review, this.booking.getId());
        this.review = review;
        System.out.println("Your review was successfully sent!");
        hBox.setVisible(false);
        booking.setReview(review);
        this.sentReview.setText(review.getRating() + "/10 " + review.getComment() + " written on " + review.getDate());
        sentReview.setVisible(true);
    }

    public Label getSentReview() {
        return sentReview;
    }

    public void setSentReview(Booking booking) {
        if(booking.getReview() != null) {
            this.sentReview.setText(booking.getReview().getRating() + "/10 " + booking.getReview().getComment() + " written on " + booking.getReview().getDate());
            this.sentReview.setVisible(true);
        }else {
            this.sentReview.setVisible(false);
        }
    }

    public Review getReview() {
        return review;
    }
}
