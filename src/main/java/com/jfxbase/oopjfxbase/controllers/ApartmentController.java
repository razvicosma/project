package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.*;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ApartmentController extends SceneController implements Initializable {
    ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();
    @FXML
    private ListView<Review> review;
    @FXML
    private Label apartmentInfo;
    private Apartment apartment;
    private User user;
    private final List<LocalDate> bookedDates = new ArrayList<>();
    public ImageView imageView;
    public Integer currentPhoto = 0;
    public void onBackClick() {
        this.changeScene(SCENE_IDENTIFIER.LoggedIn,user,apartment);
    }
    public void setApartment(Apartment apartment) {
        imageView.setImage(new Image(apartment.getPhotos().get(0)));
        apartmentInfo.setText("Name: " + apartment.getName() + "\nDescription: " + apartment.getDescription() + "\nLocation: " + apartment.getLocation().getCountry() +
                ", " + apartment.getLocation().getCity() + ", " + apartment.getLocation().getAddress() +
                "\nPrice: " + apartment.getPrice() + " $ per night\n" + "Maximum number of guests is " + apartment.getNrOfGuests()
                + "\n" + apartment.getNrOfBedrooms() + " bedrooms\n" + apartment.getNrOfBathrooms() + " bathrooms\n" +
                "Amenities: " + apartment.getAmenities());
        this.apartment = apartment;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void nextImage() {
        if(apartment.getPhotos().size() > currentPhoto + 1) {
            currentPhoto++;
            imageView.setImage(new Image(apartment.getPhotos().get(currentPhoto)));
        }
    }
    public void prevImage() {
        if(currentPhoto > 0) {
            currentPhoto--;
            imageView.setImage(new Image(apartment.getPhotos().get(currentPhoto)));
        }
    }

    public Boolean bookingIsValid() {
        List<LocalDate> toBook = new ArrayList<>();
        toBook = selectedDates.get(0).datesUntil(selectedDates.get(1)).collect(Collectors.toList());
        toBook.add(selectedDates.get(1));
        for(LocalDate date : bookedDates) {
            if(toBook.contains(date))
                return false;
        }
        return true;
    }

    @FXML
    private DatePicker datePicker;

    @FXML
    public void getBooking() {
        if(selectedDates.size() == 2 && bookingIsValid()) {
            Booking booking = new Booking(0, user.getId(), apartment.getId(), selectedDates.get(0), selectedDates.get(1),
                    Period.between(selectedDates.get(0), selectedDates.get(1)).getDays() * apartment.getPrice(), null);
            DBeaver.makeBooking(new ActionEvent(), booking);
            apartment.addBooking(booking);
            user.addBooking(booking);
            this.changeScene(SCENE_IDENTIFIER.Apartment,user,apartment);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid booking date.");
            alert.show();
        }
    }
    @FXML
    public void resetDates() {
        selectedDates.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setOnAction(event -> {
            if (selectedDates.size() > 1){
                selectedDates.clear();
            }
            selectedDates.add(datePicker.getValue());}
        );

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean alreadySelected = selectedDates.contains(item);
                        LocalDate today = LocalDate.now();
                            for (Booking booking : apartment.getBookings()) {
                                List<LocalDate> dates = new ArrayList<>();
                                dates = booking.getCheckIn().datesUntil(booking.getCheckOut()).collect(Collectors.toList());
                                dates.add(booking.getCheckOut());
                                bookedDates.addAll(dates);
                            }
                        boolean alreadyBooked = bookedDates.contains(item);
                        setDisable(empty || alreadySelected || item.isBefore(today) || alreadyBooked || (selectedDates.size() == 1 && item.isBefore(selectedDates.get(0))));
                        setStyle(alreadySelected ? "-fx-background-color: #C06C84;" : "");
                    }
                };
            }

        });
        review.setCellFactory(param -> new ListCell<Review>() {
            @Override
            protected void updateItem(Review item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jfxbase/oopjfxbase/review-list-cell.fxml"));
                        AnchorPane cellLayout = loader.load();
                        ReviewListCellController reviewListCellController = loader.getController();
                        reviewListCellController.setReview(item);
                        setGraphic(cellLayout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @FXML
    public void setListView(ActionEvent event) {
        review.getItems().setAll(apartment.getReviews());
    }
}
