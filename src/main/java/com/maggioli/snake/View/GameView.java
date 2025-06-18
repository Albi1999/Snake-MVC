package com.maggioli.snake.View;

import com.maggioli.snake.Controller.dto.GameData;

import com.maggioli.snake.Controller.interfaces.FrameCallback;
import com.maggioli.snake.Controller.interfaces.InputHandler;
import javafx.stage.Stage;

public interface GameView {
    void initialize(InputHandler inputHandler);
    void render(GameData gameData);
    void startRenderLoop(FrameCallback callback);
    Stage getStage();
}