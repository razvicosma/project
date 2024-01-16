package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.DBeaver;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignUpController extends SceneController {
    @FXML
    private MFXTextField first_name;
    @FXML
    private MFXTextField last_name;
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;
    @FXML
    private MFXPasswordField confirm_password;
    @FXML
    protected void onMainPageClick(){this.changeScene(SCENE_IDENTIFIER.HELLO); }
    @FXML
    protected void onSignInClick(){
        this.changeScene(SCENE_IDENTIFIER.SignIn);
    }
    @FXML
    protected void onSignUpClick(){
        this.changeScene(SCENE_IDENTIFIER.SignUp);
    }

    public void getData(ActionEvent event) {
        DBeaver.signUpUser(event, first_name.getText(), last_name.getText(), username.getText(), password.getText(), confirm_password.getText());
        first_name.clear();
        last_name.clear();
        username.clear();
        password.clear();
        confirm_password.clear();
    }
}
