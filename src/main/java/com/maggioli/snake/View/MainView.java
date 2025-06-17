package com.maggioli.snake.View;

import com.maggioli.snake.Controller.Controller;
import com.maggioli.snake.Controller.dto.GameViewData;
import com.maggioli.snake.Controller.dto.PositionData;
import com.maggioli.snake.Controller.dto.GameConfig;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView {
    private final Scene scene;
    private final Stage stage;
    private final Pane canvas;
    private final GridPane grid;
    private final ScoreView scoreView;
    private Group rootGroup;

    public MainView(Controller controller) {

        stage = new Stage();
        stage.setTitle("Snake Game");

        canvas = new Pane();
        canvas.setStyle("-fx-background-color: " + ColorView.SCENE_COLOR);
        canvas.setPrefSize(GameConfig.WIDTH, GameConfig.HEIGHT);

        grid = new GridPane();
        scoreView = new ScoreView();

        rootGroup = new Group();
        scene = new Scene(rootGroup, GameConfig.WIDTH, GameConfig.HEIGHT + ScoreView.SCORE_HEIGHT);
        scene.setFill(ColorView.BACKGROUND_COLOR);

        stage.setScene(scene);
        stage.setResizable(false);
    }

    public void render(GameViewData viewData) {
        switch(viewData.getState()) {
            case Started:
                renderStartScreen();
                break;
            case Running:
                renderRunningGame(viewData);
                break;
            case Paused:
                renderPauseScreen();
                break;
            case Finished:
                renderGameOverScreen(viewData.getHighScore());
                break;
        }
    }

    private void renderStartScreen() {
        rootGroup = new Group();

        Text largeText = new Text((double) GameConfig.WIDTH /2 - 170, (double) GameConfig.HEIGHT /2 - 30, "Snake Game");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));

        Text smallText = new Text((double) GameConfig.WIDTH /2 - 130, (double) GameConfig.HEIGHT /2 + 20, "Press ENTER to play");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText.setFill(ColorView.SMALL_TEXT_COLOR);

        rootGroup.getChildren().addAll(smallText, largeText);
        scene.setRoot(rootGroup);
    }

    private void renderRunningGame(GameViewData viewData) {
        grid.getChildren().clear();
        canvas.getChildren().clear();
        StackPane scoreStack = scoreView.getStack();

        // Snake Head
        Circle headCircle = new Circle(
                viewData.getHeadPosition().getX(),
                viewData.getHeadPosition().getY(),
                (double) GameConfig.CELL_SIZE /2
        );
        headCircle.setFill(ColorView.SNAKE_HEAD_COLOR);
        canvas.getChildren().add(headCircle);

        // Snake Body
        for(PositionData position : viewData.getSnakePositions()) {
            Circle bodyPart = new Circle(
                    position.getX(),
                    position.getY(),
                    (double) GameConfig.CELL_SIZE /2
            );
            bodyPart.setFill(ColorView.SNAKE_BODY_COLOR);
            canvas.getChildren().add(bodyPart);
        }

        // Fruits
        for(PositionData position : viewData.getFruitPositions()) {
            Circle fruitCircle = new Circle(
                    position.getX(),
                    position.getY(),
                    (double) GameConfig.CELL_SIZE / 2
            );
            fruitCircle.setFill(ColorView.FRUIT_COLOR);
            canvas.getChildren().add(fruitCircle);
        }

        scoreView.updateScore(viewData.getScore());

        grid.add(scoreStack, 0, 1);
        grid.add(canvas, 0, 0);

        scene.setRoot(grid);
    }

    private void renderPauseScreen() {
        rootGroup = new Group();

        Text largeText = new Text((double) GameConfig.WIDTH /2 - 190, (double) GameConfig.HEIGHT /2 - 30, "Game Paused");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));

        Text smallText = new Text((double) GameConfig.WIDTH /2 - 190, (double) GameConfig.HEIGHT /2 + 30, "Press SPACE to resume");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        smallText.setFill(ColorView.SMALL_TEXT_COLOR);

        Circle fruit = new Circle(
                (double) GameConfig.WIDTH /2 - 200,
                (double) GameConfig.HEIGHT /2 + 150,
                (double) GameConfig.CELL_SIZE /2,
                ColorView.FRUIT_COLOR
        );

        Text text = new Text(
                (double) GameConfig.WIDTH /2 - 180,
                (double) GameConfig.HEIGHT /2 + 154,
                "- Eat a fruit for 1 point"
        );
        text.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 13));

        rootGroup.getChildren().addAll(smallText, largeText, fruit, text);
        scene.setRoot(rootGroup);
    }

    private void renderGameOverScreen(int highScore) {
        rootGroup = new Group();

        Text largeText = new Text(
                (double) GameConfig.WIDTH /2 - 220,
                (double) GameConfig.HEIGHT /2 - 60,
                "Game Over"
        );
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 70));
        largeText.setFill(ColorView.GAME_OVER_COLOR);

        Text scoreText = new Text(
                (double) GameConfig.WIDTH /2 - 170,
                (double) GameConfig.HEIGHT /2 + 20,
                "FINAL SCORE: " + highScore
        );
        scoreText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 37));

        Text replayText = new Text(
                (double) GameConfig.WIDTH /2 - 160,
                (double) GameConfig.HEIGHT /2 + 100,
                "Press ENTER to replay"
        );
        replayText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        replayText.setFill(ColorView.SMALL_TEXT_COLOR);

        Text exitText = new Text(
                (double) GameConfig.WIDTH /2 - 130,
                (double) GameConfig.HEIGHT /2 + 130,
                " or ESCAPE to exit"
        );
        exitText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        exitText.setFill(ColorView.END_TEXT_COLOR);

        rootGroup.getChildren().addAll(replayText, scoreText, exitText, largeText);
        scene.setRoot(rootGroup);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }
}