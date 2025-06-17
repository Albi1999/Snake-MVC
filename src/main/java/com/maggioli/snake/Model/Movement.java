package com.maggioli.snake.Model;

import com.maggioli.snake.Controller.dto.GameConfig;

public class Movement {
    private Direction currentDirection = Direction.NONE;
    private Direction requestedDirection = Direction.NONE;
    private int speed = 5;
    private int speedPointsThreshold = 50;

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void requestDirection(Direction direction) {
        if (currentDirection != Direction.NONE && direction.isOpposite(currentDirection)) {
            return;
        }
        requestedDirection = direction;
    }

    public void updateDirection() {
        if (requestedDirection != Direction.NONE) {
            currentDirection = requestedDirection;
        }
    }

    public void moveSnake(Snake snake) {
        if (currentDirection == Direction.NONE) {
            return;
        }

        SnakePart head = snake.getHead();
        int newX = head.getX();
        int newY = head.getY();

        switch (currentDirection) {
            case UP:
                newY -= GameConfig.CELL_SIZE;
                break;
            case DOWN:
                newY += GameConfig.CELL_SIZE;
                break;
            case LEFT:
                newX -= GameConfig.CELL_SIZE;
                break;
            case RIGHT:
                newX += GameConfig.CELL_SIZE;
                break;
            case NONE:
                break;
        }

        if (newX > GameConfig.WIDTH) {
            newX = GameConfig.CELL_SIZE/2;
        } else if (newX < 0) {
            newX = GameConfig.WIDTH - GameConfig.CELL_SIZE/2;
        }

        if (newY > GameConfig.HEIGHT) {
            newY = GameConfig.CELL_SIZE/2;
        } else if (newY < 0) {
            newY = GameConfig.HEIGHT - GameConfig.CELL_SIZE/2;
        }

        int oldHeadX = head.getX();
        int oldHeadY = head.getY();

        head.setX(newX);
        head.setY(newY);

        SnakePart prev = new SnakePart(oldHeadX, oldHeadY);
        SnakePart next = new SnakePart(oldHeadX, oldHeadY);

        for (int i = 1; i < snake.getSize(); i++) {
            next.setX(snake.getSnakePart(i).getX());
            next.setY(snake.getSnakePart(i).getY());

            snake.getSnakePart(i).setX(prev.getX());
            snake.getSnakePart(i).setY(prev.getY());

            prev.setX(next.getX());
            prev.setY(next.getY());
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void updateSpeed(int score) {
        // Adjust snake speed based on score
        if (speed > 2 && score >= speedPointsThreshold) {
            speed = 2;
        }

        if (speed == 2 && (score - speedPointsThreshold) >= 10) {
            speedPointsThreshold += 30;
            speed = 3;
        }
    }

    public void reset() {
        currentDirection = Direction.NONE;
        requestedDirection = Direction.NONE;
        speed = 5;
        speedPointsThreshold = 50;
    }
}