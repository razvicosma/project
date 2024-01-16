package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import com.jfxbase.oopjfxbase.utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class HelloController extends SceneController {
    @FXML
    protected void onSignInClick(){
        this.changeScene(SCENE_IDENTIFIER.SignIn);
    }
    @FXML
    protected void onMainPageClick(){
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
    @FXML
    protected void onSignUpClick(){
        this.changeScene(SCENE_IDENTIFIER.SignUp);
    }

}