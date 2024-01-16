package com.jfxbase.oopjfxbase.utils;

import com.jfxbase.oopjfxbase.Serendipity;
import com.jfxbase.oopjfxbase.controllers.AdminController;
import com.jfxbase.oopjfxbase.controllers.ApartmentController;
import com.jfxbase.oopjfxbase.controllers.LoggedInController;
import com.jfxbase.oopjfxbase.controllers.UserController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ApplicationHandler {
    private final HashMap<SCENE_IDENTIFIER, Pane> views = new HashMap<>();
    private Stage stage;

    private ApplicationHandler() {
    }

    public void startApplication(Stage stage) {
        this.initializeViews();

        this.stage = stage;
        this.stage.setTitle(Environment.APP_TITLE);
        this.stage.setFullScreen(Environment.IS_FULLSCREEN);
        this.stage.setScene(new Scene(this.views.get(SCENE_IDENTIFIER.HELLO), 1400, 800));
        this.stage.setMaximized(true);
        this.stage.show();

        Logger.info("Application started..");
    }

    public void changeScene(SCENE_IDENTIFIER newScene) {
        this.stage.getScene().setRoot(views.get(newScene));
    }

    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    private void initializeViews() {
        try {
            for (SCENE_IDENTIFIER value : SCENE_IDENTIFIER.values()) {
                this.views.put(value, FXMLLoader.load(Objects.requireNonNull(Serendipity.class.getResource(value.label))));
            }
        } catch (IOException | NullPointerException exception) {
            Logger.error("Could not initialize views. Please check the resources folder.");
            this.closeApplication();
        }
    }

    public static ApplicationHandler _instance = null;

    public static ApplicationHandler getInstance() {
        if (ApplicationHandler._instance == null) {
            ApplicationHandler._instance = new ApplicationHandler();
        }

        return ApplicationHandler._instance;
    }

    public void changeScene(SCENE_IDENTIFIER newScene, User user, Apartment apartment) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Serendipity.class.getResource(newScene.label)));
            Parent root = loader.load();

            if (newScene == SCENE_IDENTIFIER.LoggedIn) {
                LoggedInController loggedController = loader.getController();
                loggedController.setUserInformation(user);
                loggedController.setListView(new ActionEvent());
            } else if (newScene == SCENE_IDENTIFIER.Admin) {
                AdminController adminController = loader.getController();
                adminController.setListView(new ActionEvent());
            } else if (newScene == SCENE_IDENTIFIER.Apartment) {
                ApartmentController apartmentController = loader.getController();
                apartmentController.setApartment(apartment);
                apartmentController.setUser(user);
                apartmentController.setListView(new ActionEvent());
            } else if (newScene == SCENE_IDENTIFIER.User) {
                UserController userController = loader.getController();
                userController.setUser(user);
                userController.setListView(new ActionEvent());
            }
            this.stage.getScene().setRoot(root);

        } catch (IOException | NullPointerException exception) {
            Logger.error("Could not change scene. Please check the resources folder.");
            exception.printStackTrace();
        }
    }
}
