package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.Review;
import com.jfxbase.oopjfxbase.utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReviewListCellController extends SceneController {
    @FXML
    private Label label;
    public void setReview(Review review) {
        label.setText(review.getRating() + "/10 " + review.getComment() + " written on " + review.getDate() + " by " + review.getUsername());
    }
}
