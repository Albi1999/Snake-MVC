package com.maggioli.snake.View;

import com.maggioli.snake.Controller.dto.GameConfig;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ScoreView {

    private final StackPane stack;
    private final Label score;
    public static final int SCORE_HEIGHT = 50;

    public ScoreView() {
        stack = new StackPane();
        stack.setStyle("-fx-background-color: "+ ColorView.SCENE_COLOR);
        Rectangle r = new Rectangle(GameConfig.WIDTH - (2*SCORE_HEIGHT), SCORE_HEIGHT);
        r.setFill(ColorView.SCORE_FIELD_COLOR);

        double x = GameConfig.WIDTH;
        double y = SCORE_HEIGHT;
        double z = GameConfig.HEIGHT;

        Polygon t1 = new Polygon();
        t1.getPoints().addAll(0.0, y+z,
                y, y+z,
                y, z);

        Polygon t2 = new Polygon();
        t2.getPoints().addAll(x, y+z,
                x-y, y+z,
                x-y, z);

        t1.setFill(ColorView.SCORE_FIELD_COLOR);
        t2.setFill(ColorView.SCORE_FIELD_COLOR);

        Label l1 = new Label("SCORE: ");
        l1.setFont(new Font(25));
        l1.setTextFill(ColorView.SCORE_TEXT_COLOR);

        score = new Label("0");
        score.setFont(new Font(25));
        score.setTextFill(ColorView.SCORE_TEXT_COLOR);

        Label l2 = new Label("Press SPACE to Pause");
        l2.setFont(new Font(13));
        l2.setTextFill(ColorView.SCORE_TEXT_COLOR);

        Label l3 = new Label("Press ESC to Exit");
        l3.setFont(new Font(13));
        l3.setTextFill(ColorView.SCORE_TEXT_COLOR);

        stack.getChildren().addAll(r, t1, t2, l1, score, l2, l3);
        stack.getChildren().get(1).setTranslateX(-(r.getWidth()/2+ (double) SCORE_HEIGHT /2));
        stack.getChildren().get(2).setTranslateX(r.getWidth()/2+ (double) SCORE_HEIGHT /2);
        stack.getChildren().get(3).setTranslateX(-30);
        stack.getChildren().get(4).setTranslateX(40);
        stack.getChildren().get(5).setTranslateX(150);
        stack.getChildren().get(5).setTranslateY(15);
        stack.getChildren().get(6).setTranslateX(-170);
        stack.getChildren().get(6).setTranslateY(15);
    }

    public void updateScore(int scoreValue) {
        score.setText(Integer.toString(scoreValue));
    }

    public StackPane getStack() {
        return stack;
    }
}