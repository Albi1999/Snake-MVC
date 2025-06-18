package com.maggioli.snake.Controller.dto;

import com.maggioli.snake.Model.GameState;

import java.util.List;

public record GameData(GameState state, PositionData headPosition, List<PositionData> snakePositions,
                       List<PositionData> fruitPositions, int score, int highScore) {
}