package com.maggioli.snake.Controller;

import com.maggioli.snake.Controller.dto.GameData;
import com.maggioli.snake.Controller.dto.PositionData;

import com.maggioli.snake.Controller.interfaces.FrameCallback;
import com.maggioli.snake.Controller.interfaces.InputHandler;

import com.maggioli.snake.Model.*;

import com.maggioli.snake.View.GameView;
import com.maggioli.snake.View.MainView;

import java.util.ArrayList;
import java.util.List;

public class Controller implements InputHandler, FrameCallback {
    private GameState currentState;
    private final Board gameBoard;
    private final Movement movement;
    private final GameView gameView = new MainView();
    private int frameCounter;
    private boolean keyActive;

    public Controller() {
        currentState = GameState.Started;
        gameBoard = new Board();
        movement = new Movement();
        keyActive = true;
        frameCounter = 0;
        gameView.initialize(this);
        gameView.startRenderLoop(this);
    }

    @Override
    public void handleKeyCode(String keyCode) {
        if ("ESCAPE".equals(keyCode)) {
            System.exit(0);
            return;
        }

        if (currentState == GameState.Running) {
            handleRunningInput(keyCode);
        } else if (currentState == GameState.Paused) {
            if ("SPACE".equals(keyCode)) {
                resumeGame();
            }
        } else if (currentState == GameState.Started || currentState == GameState.Finished) {
            if ("ENTER".equals(keyCode)) {
                startGame();
            }
        }
    }

    private void handleRunningInput(String keyCode) {
        if (!keyActive) {
            return;
        }

        switch (keyCode) {
            case "UP":
                movement.requestDirection(Direction.UP);
                keyActive = false;
                break;
            case "DOWN":
                if (movement.getCurrentDirection() == Direction.LEFT ||
                        movement.getCurrentDirection() == Direction.RIGHT) {
                    movement.requestDirection(Direction.DOWN);
                    keyActive = false;
                }
                break;
            case "LEFT":
                movement.requestDirection(Direction.LEFT);
                keyActive = false;
                break;
            case "RIGHT":
                movement.requestDirection(Direction.RIGHT);
                keyActive = false;
                break;
            case "SPACE":
                pauseGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFrame() {
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
        GameData viewData = prepareViewData();
        gameView.render(viewData);
    }

    private GameData prepareViewData() {
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

        return new GameData(
                currentState,
                headPosition,
                snakePositions,
                fruitPositions,
                gameBoard.getScore(),
                gameBoard.getHighScore()
        );
    }

    public GameView getGameView() {
        return gameView;
    }
}