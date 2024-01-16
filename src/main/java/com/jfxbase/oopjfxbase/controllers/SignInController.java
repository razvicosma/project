package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.DBeaver;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignInController extends SceneController {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;

    @FXML
    protected void onMainPageClick(){
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
    @FXML
    protected void onSignInClick(){
        this.changeScene(SCENE_IDENTIFIER.SignIn);
    }
    @FXML
    protected void onSignUpClick(){
        this.changeScene(SCENE_IDENTIFIER.SignUp);
    }

    public void getData(ActionEvent event) {
        DBeaver.signInUser(event, username.getText(), password.getText());
        username.clear();
        password.clear();
    }
}
