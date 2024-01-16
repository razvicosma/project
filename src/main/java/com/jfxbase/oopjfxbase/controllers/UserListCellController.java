package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.DBeaver;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserListCellController extends SceneController {
    @FXML
    private Label userId;
    @FXML
    private Label userName;
    @FXML
    private Label userUsername;
    @FXML
    private Label userPassword;
    @FXML
    private Label userRights;
    @FXML
    private ImageView userImage;
    public void setUser(User user) {
        userId.setText("ID: " + user.getId());
        userName.setText("Name: " + user.getFirst_name() + " " + user.getLast_name());
        userUsername.setText(user.getUsername());
        userPassword.setText("password: " + user.getPassword());
        userRights.setText("admin rights: " + user.getAdmin_rights());
        userImage.setImage(new Image(user.getProfile_picture()));
    }
    public void onDeleteClick() {
        DBeaver.deleteUser(new ActionEvent(),this.userUsername.getText());
    }
}
