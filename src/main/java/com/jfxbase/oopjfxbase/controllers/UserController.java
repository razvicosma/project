package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.ApplicationHandler;
import com.jfxbase.oopjfxbase.utils.Booking;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.User;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends SceneController implements Initializable {
    private User user;
    @FXML
    private ListView<Booking> bookings;
    @FXML
    private ImageView profilePhoto;
    @FXML
    private Label userInfo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        profilePhoto.setImage(new Image(user.getProfile_picture()));
        userInfo.setText("Last name: " + user.getLast_name() + "\nFirst name: " + user.getFirst_name() +
                "\nUsername: " + user.getUsername());
        this.user = user;
    }

    public ListView<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ListView<Booking> bookings) {
        this.bookings = bookings;
    }
    @FXML
    public void setListView(ActionEvent event) {
        bookings.getItems().setAll(user.getBookings());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookings.setCellFactory(param -> new ListCell<Booking>() {
            @Override
            protected void updateItem(Booking item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jfxbase/oopjfxbase/booking-list-cell.fxml"));
                        AnchorPane cellLayout = loader.load();
                        BookingListCellController bookingListCellController = loader.getController();
                        bookingListCellController.setBooking(item);
                        bookingListCellController.setUser(user);
                        bookingListCellController.setSentReview(item);
                        bookingListCellController.showReview(item);
                        setGraphic(cellLayout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void onBackClick() {
        this.changeScene(SCENE_IDENTIFIER.LoggedIn,user,null);
    }
}
