package com.maggioli.snake.Controller.dto;

public class PositionData {
    private final int x;
    private final int y;

    public PositionData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}