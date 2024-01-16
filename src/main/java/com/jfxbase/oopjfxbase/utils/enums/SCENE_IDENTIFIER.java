package com.jfxbase.oopjfxbase.utils.enums;

public enum SCENE_IDENTIFIER {
    HELLO("hello-view.fxml"),
    SignIn("sign-in-view.fxml"),
    SignUp("sign-up-view.fxml"),
    LoggedIn("logged-in-view.fxml"),
    Admin("admin-view.fxml"),
    Apartment("apartment-view.fxml"),
    User("user-view.fxml");

    public final String label;

    SCENE_IDENTIFIER(String label) {
        this.label = label;
    }
}
