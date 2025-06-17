package com.maggioli.snake.Model;

import javafx.scene.paint.Color;

public class SnakePart extends GameObject{

    public static final Color SNAKE_BODY_COLOR = Color.DARKBLUE;

    public static final Color SNAKE_HEAD_COLOR = Color.ROYALBLUE;

    public SnakePart(int x, int y) {
        super(x, y);
    }
}