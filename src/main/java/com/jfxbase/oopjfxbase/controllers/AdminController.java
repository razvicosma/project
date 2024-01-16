package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.*;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AdminController extends SceneController implements Initializable {
    @FXML
    private ListView<User> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jfxbase/oopjfxbase/user-list-cell.fxml"));
                        AnchorPane cellLayout = loader.load();
                        UserListCellController cellController = loader.getController();
                        cellController.setUser(item);
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
        listView.getItems().setAll(DBeaver.adminData(event).getItems());
        listView.getItems().sort(Comparator.comparing(User::getId));
    }

    @FXML
    public void refreshListView() {
        listView.getItems().setAll(DBeaver.adminData(new ActionEvent()).getItems());
        listView.getItems().sort(Comparator.comparing(User::getId));
    }
    @FXML
    public void onRefreshClick() {
        refreshListView();
    }
    public void onBackClick() {
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
}
