package com.maggioli.snake.View;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ScoreView {

    private final StackPane stack;
    private final Label score;
    public static final int SCORE_HEIGHT = 50;

    public ScoreView() {

        stack = new StackPane();
        stack.setStyle("-fx-background-color: "+MainView.SCENE_COLOR);
        Rectangle r = new Rectangle(MainView.WIDTH - (2*SCORE_HEIGHT), SCORE_HEIGHT);
        Color scoreFieldColor = Color.rgb(100, 60, 100);
        r.setFill(scoreFieldColor);

        double x = MainView.WIDTH;
        double y = SCORE_HEIGHT;
        double z = MainView.HEIGHT;

        Polygon t1 = new Polygon();
        t1.getPoints().addAll(0.0, y+z,
                    y, y+z,
                    y, z);

        Polygon t2 = new Polygon();
        t2.getPoints().addAll(x, y+z,
                    x-y, y+z,
                    x-y, z);

        t1.setFill(scoreFieldColor);
        t2.setFill(scoreFieldColor);

        Color scoreColor = Color.YELLOW;

        Label l1 = new Label("SCORE: ");
        l1.setFont(new Font(25));
        l1.setTextFill(scoreColor);

        score = new Label("0");
        score.setFont(new Font(25));
        score.setTextFill(scoreColor);

        Label l2 = new Label("Press SPACE to Pause");
        l2.setFont(new Font(13));
        l2.setTextFill(scoreColor);

        Label l3 = new Label("Press ESC to Exit");
        l3.setFont(new Font(13));
        l3.setTextFill(scoreColor);

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

    public void addScore(int scoreValue) {
        score.setText(Integer.toString(scoreValue));
    }

    public StackPane getStack() {
        return stack;
    }
}