package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.*;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class LoggedInController extends SceneController implements Initializable{

    @FXML
    private Label label_welcome;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<Apartment> listView;
    private User user;
    public MFXTextField searchBar;
    public TextField guestsField;
    public TextField from;
    public TextField to;
    private Integer sort = 0;
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public void priceAsc() {
        setSort(1);
    }
    public void priceDesc() {
        setSort(2);
    }
    public void nameAsc() {
        setSort(3);
    }
    public void nameDesc() {
        setSort(4);
    }
    public void setUserInformation(User user) {
        label_welcome.setText("Welcome " + user.getLast_name() + " " + user.getFirst_name() + "! ");
        imageView.setImage(new Image(user.getProfile_picture()));
        this.user = user;
    }
    public void showUser() {
        this.changeScene(SCENE_IDENTIFIER.User, user, null);
    }
    public void search() {
        refreshListView();
        Integer guests;
        if(!guestsField.getText().isEmpty())
            guests = Integer.valueOf(guestsField.getText());
        else
            guests = 0;
        Integer start,end;
        if(!from.getText().isEmpty())
            start = Integer.valueOf(from.getText());
        else
            start = 0;
        if(!to.getText().isEmpty())
            end = Integer.valueOf(to.getText());
        else
            end = Integer.MAX_VALUE;
        listView.getItems().removeIf(apartment -> (!apartment.getName().contains(searchBar.getText()) && !apartment.getLocation().getCity().contains(searchBar.getText())
        && !apartment.getLocation().getCountry().contains(searchBar.getText()) && !apartment.getTags().contains(searchBar.getText().toLowerCase())) ||
                apartment.getNrOfGuests() < guests || apartment.getPrice() < start || apartment.getPrice() > end);
        switch (sort) {
            case 1 -> listView.getItems().sort(Comparator.comparing(Apartment::getPrice)); //ascending
            case 2 -> listView.getItems().sort(Collections.reverseOrder(Comparator.comparing(Apartment::getPrice))); //descending
            case 3 -> listView.getItems().sort(Comparator.comparing(Apartment::getName)); //ascending
            case 4 -> listView.getItems().sort(Collections.reverseOrder(Comparator.comparing(Apartment::getName))); //descending
            default -> listView.getItems().sort(Comparator.comparing(Apartment::getId)); //ascending
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(param -> new ListCell<Apartment>() {
            @Override
            protected void updateItem(Apartment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jfxbase/oopjfxbase/apartment-list-cell.fxml"));
                        AnchorPane cellLayout = loader.load();
                        ApartmentListCellController apartmentListCellController = loader.getController();
                        apartmentListCellController.setApartment(item);
                        apartmentListCellController.setUser(user);
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
        listView.getItems().setAll(DBeaver.showApartments(event).getItems());
    }

    @FXML
    public void refreshListView() {
        listView.getItems().setAll(DBeaver.showApartments(new ActionEvent()).getItems());
    }
    public void onLogOutClick() {this.changeScene(SCENE_IDENTIFIER.HELLO);}

}
