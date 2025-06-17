package com.maggioli.snake.Controller.dto;

import com.maggioli.snake.Model.Direction;
import com.maggioli.snake.Model.GameState;

import java.util.List;

public class GameViewData {
    private final GameState state;
    private final List<PositionData> snakePositions;
    private final PositionData headPosition;
    private final List<PositionData> fruitPositions;
    private final int score;
    private final int highScore;

    public GameViewData(GameState state, PositionData headPosition, List<PositionData> snakePositions,
                        List<PositionData> fruitPositions, int score, int highScore) {
        this.state = state;
        this.headPosition = headPosition;
        this.snakePositions = snakePositions;
        this.fruitPositions = fruitPositions;
        this.score = score;
        this.highScore = highScore;
    }

    public GameState getState() {
        return state;
    }

    public PositionData getHeadPosition() {
        return headPosition;
    }

    public List<PositionData> getSnakePositions() {
        return snakePositions;
    }

    public List<PositionData> getFruitPositions() {
        return fruitPositions;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}