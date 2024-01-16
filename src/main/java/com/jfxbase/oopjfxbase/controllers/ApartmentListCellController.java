package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.Apartment;
import com.jfxbase.oopjfxbase.utils.ApplicationHandler;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.User;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ApartmentListCellController extends SceneController {
    @FXML
    private Label apartmentInfo;
    @FXML
    private ImageView apartmentImage;
    private User user;
    private Apartment apartment;
    public void setApartment(Apartment apartment) {
        apartmentInfo.setText(apartment.getName() + "\n\n" + apartment.getDescription() + "\n\nLocation: " +
                apartment.getLocation().getCountry() + ", " + apartment.getLocation().getCity() + ", " +
                apartment.getLocation().getAddress() + "\n\nPrice: " + apartment.getPrice() +
                " $ per night\n\nMaximum number of guests is " + apartment.getNrOfGuests());
        apartmentImage.setImage(new Image(apartment.getPhotos().get(0)));
        this.apartment = apartment;
    }
    public void showApartment() {
        this.changeScene(SCENE_IDENTIFIER.Apartment, user, apartment);
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
