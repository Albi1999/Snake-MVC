package com.maggioli.snake.Controller;

import com.maggioli.snake.Controller.dto.GameViewData;
import com.maggioli.snake.Controller.dto.PositionData;
import com.maggioli.snake.Model.*;
import com.maggioli.snake.View.MainView;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private GameState currentState;
    private final Board gameBoard;
    private final Movement movement;
    private final MainView gameView;
    private int frameCounter;
    private boolean keyActive;

    public Controller() {
        currentState = GameState.Started;
        gameBoard = new Board();
        movement = new Movement();
        gameView = new MainView(this);
        keyActive = true;
        frameCounter = 0;

        setupInputHandling();
        startGameLoop();
    }

    private void setupInputHandling() {
        Scene scene = gameView.getScene();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
                return;
            }

            if (currentState == GameState.Running) {
                handleRunningInput(e.getCode());
            } else if (currentState == GameState.Paused) {
                if (e.getCode() == KeyCode.SPACE) {
                    resumeGame();
                }
            } else if (currentState == GameState.Started || currentState == GameState.Finished) {
                if (e.getCode() == KeyCode.ENTER) {
                    startGame();
                }
            }
        });
    }

    private void handleRunningInput(KeyCode code) {
        if (!keyActive) {
            return;
        }

        switch (code) {
            case UP:
                movement.requestDirection(Direction.UP);
                keyActive = false;
                break;
            case DOWN:
                if (movement.getCurrentDirection() == Direction.LEFT ||
                        movement.getCurrentDirection() == Direction.RIGHT) {
                    movement.requestDirection(Direction.DOWN);
                    keyActive = false;
                }
                break;
            case LEFT:
                movement.requestDirection(Direction.LEFT);
                keyActive = false;
                break;
            case RIGHT:
                movement.requestDirection(Direction.RIGHT);
                keyActive = false;
                break;
            case SPACE:
                pauseGame();
                break;
            default:
                break;
        }
    }

    private void startGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (currentState == GameState.Running) {
                    frameCounter++;

                    if (frameCounter >= movement.getSpeed()) {
                        movement.updateDirection();
                        movement.moveSnake(gameBoard.getSnake());
                        keyActive = true;
                        frameCounter = 0;

                        updateGameState();
                    }
                }

                renderGame();
            }
        }.start();
    }

    private void updateGameState() {
        gameBoard.updateFruit();
        gameBoard.checkEaten();

        if (gameBoard.checkCollision()) {
            currentState = GameState.Finished;
        }

        movement.updateSpeed(gameBoard.getScore());
    }

    public void startGame() {
        gameBoard.reset();
        movement.reset();
        currentState = GameState.Running;
    }

    public void pauseGame() {
        currentState = GameState.Paused;
    }

    public void resumeGame() {
        currentState = GameState.Running;
    }

    private void renderGame() {
        GameViewData viewData = prepareViewData();
        gameView.render(viewData);
    }

    private GameViewData prepareViewData() {
        Snake snake = gameBoard.getSnake();
        List<PositionData> snakePositions = new ArrayList<>();

        for (int i = 1; i < snake.getSize(); i++) {
            SnakePart part = snake.getSnakePart(i);
            snakePositions.add(new PositionData(part.getX(), part.getY()));
        }
        PositionData headPosition = new PositionData(snake.getHead().getX(), snake.getHead().getY());
        List<PositionData> fruitPositions = new ArrayList<>();
        for (Fruit fruit : gameBoard.getFruits()) {
            fruitPositions.add(new PositionData(fruit.getX(), fruit.getY()));
        }

        return new GameViewData(
                currentState,
                headPosition,
                snakePositions,
                fruitPositions,
                gameBoard.getScore(),
                gameBoard.getHighScore()
        );
    }

    public Stage getStage() {
        return gameView.getStage();
    }

    public int getHighScore() {
        return gameBoard.getHighScore();
    }
}