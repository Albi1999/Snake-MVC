package com.maggioli.snake.View;

import java.util.ArrayList;

import com.maggioli.snake.Controller.Controller;
import com.maggioli.snake.Model.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView{

    public final static int WIDTH = 600;
    public final static int HEIGHT = 600;
    public final static String SCENE_COLOR = "yellow";
    private final Snake snake;
    private final Scene scene;
    private final Stage stage;
    private final ArrayList<Fruit> fruits;
    private final Board board;
    private Group g;
    private final Pane canvas;
    private final GridPane grid;
    private StackPane stack;

    public MainView() {
        board = new Board();
        snake = board.getSnake();
        fruits = board.getFruits();

        stage = new Stage();
        stage.setTitle("Snake Game");

        canvas = new Pane();
        canvas.setStyle("-fx-background-color: "+SCENE_COLOR);
        canvas.setPrefSize(WIDTH,HEIGHT);

        stack = new StackPane();
        grid = new GridPane();

        g = new Group();
        scene = new Scene(g, WIDTH, HEIGHT + ScoreView.SCORE_HEIGHT);
        scene.setFill(Color.web(SCENE_COLOR));

        render();
    }
    
    public void render() {

        GameState state = Controller.getState();
        switch(state) {

            case Started:
                isStarted();
                break;
            case Running:
                isRunning();
                break;
            case Paused:
                isPaused();
                break;
            case Finished:
                isFinished();
                break;
            default:
                break;
        }
    }
    
    public Scene getScene() {
        return stage.getScene();
    }

    public Stage getStage() {
        return stage;
    }
    private void isStarted() {

        g = new Group();
        Text largeText = new Text((double) WIDTH /2 - 170, (double) HEIGHT /2 - 30, "Snake Game");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
        Text smallText = new Text((double) WIDTH /2 - 130, (double) HEIGHT /2 + 20 , "Press ENTER to play");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText.setFill(Color.DARKGREEN);
        g.getChildren().addAll(smallText, largeText);
        scene.setRoot(g);
        stage.setScene(scene);
    }

    // View for Running
    private void isRunning() {

        grid.getChildren().clear();
        canvas.getChildren().clear();
        stack = board.getScoreView().getStack();

        int helpX, helpY, snakeY, snakeX;

        Circle c = new Circle(snake.getHead().getX() , snake.getHead().getY(), (double) GameObject.SIZE /2);
        c.setFill(ColorView.SNAKE_HEAD_COLOR);
        canvas.getChildren().add(c);

        Color bodyColor = ColorView.SNAKE_BODY_COLOR;

        for(int i = 1; i < snake.getSize(); ++i) {
            snakeX = snake.getSnakePart(i).getX();
            snakeY = snake.getSnakePart(i).getY();
            c = new Circle(snakeX , snakeY, (double) GameObject.SIZE /2);
            c.setFill(bodyColor);
            canvas.getChildren().add(c);
        }

        for (Fruit fruit : fruits) {
            helpX = fruit.getX();
            helpY = fruit.getY();
            c = new Circle(helpX, helpY, (double) GameObject.SIZE / 2);
            c.setFill(ColorView.FRUIT_COLOR);
            canvas.getChildren().add(c);
        }
        grid.add(stack, 0, 1);
        grid.add(canvas, 0, 0);

        scene.setRoot(grid);
        stage.setScene(scene);
    }

    // View for Paused
    private void isPaused() {

        g = new Group();
        Text largeText, smallText, text;

        largeText = new Text((double) WIDTH /2 - 190, (double) HEIGHT /2 - 30, "Game Paused");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
        smallText = new Text((double) WIDTH /2 - 190, (double) HEIGHT /2 + 30, "Press SPACE to resume");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        smallText.setFill(Color.DARKGREEN);

        Circle fruit = new Circle((double) WIDTH /2 - 200, (double) HEIGHT /2 + 150, (double) GameObject.SIZE /2, ColorView.FRUIT_COLOR);

        text = new Text((double) WIDTH /2 - 180, (double) HEIGHT /2 + 154, "- Eat a fruit for 1 point");
        text.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));

        g.getChildren().addAll(smallText, largeText, fruit, text);
        scene.setRoot(g);
        stage.setScene(scene);
    }

    // View for Finished
    private void isFinished() {

        g = new Group();
        Text largeText = new Text((double) WIDTH /2 - 220, (double) HEIGHT /2 - 60, "Game Over");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 70));
        largeText.setFill(Color.RED);
        Text largeText2 = new Text((double) WIDTH /2 - 170, (double) HEIGHT /2 + 20, "FINAL SCORE: " + board.getHighScore());
        largeText2.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 37));
        Text smallText = new Text((double) WIDTH /2 - 160, (double) HEIGHT /2 +100, "Press ENTER to replay");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText.setFill(Color.DARKGREEN);
        Text smallText2 = new Text((double) WIDTH /2 - 130, (double) HEIGHT /2 + 130 , " or ESCAPE to exit");
        smallText2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText2.setFill(Color.DARKGREEN);
        g.getChildren().addAll(smallText, largeText2, smallText2, largeText);
        scene.setRoot(g);
        stage.setScene(scene);
    }
}