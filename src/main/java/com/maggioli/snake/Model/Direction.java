package com.maggioli.snake.Model;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN) ||
                (this == DOWN && other == UP) ||
                (this == LEFT && other == RIGHT) ||
                (this == RIGHT && other == LEFT);
    }
}