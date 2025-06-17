package com.maggioli.snake.Model;

import com.maggioli.snake.Controller.dto.GameConfig;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final ArrayList<Fruit> fruits;
    private int score, highScore;
    private final Snake snake;
    private final Random rand;

    public Board() {
        fruits = new ArrayList<>();
        score = 0;
        snake = new Snake();
        rand = new Random();
    }

    public boolean checkCollision() {
        SnakePart head = snake.getHead();
        int headX = head.getX();
        int headY = head.getY();

        // Check for collision with the body
        for(int i = 1; i < snake.getSize(); ++i) {
            int bodyX = snake.getSnakePart(i).getX();
            int bodyY = snake.getSnakePart(i).getY();

            if(bodyX == headX && bodyY == headY) {
                highScore = Math.max(highScore, score);
                reset();
                return true;
            }
        }
        return false;
    }

    public void checkEaten() {
        int headX = snake.getHead().getX();
        int headY = snake.getHead().getY();

        for(int i = 0; i < fruits.size(); ++i) {
            int foodX = fruits.get(i).getX();
            int foodY = fruits.get(i).getY();

            if(foodX == headX && foodY == headY) {
                removeFruit(i);
                snake.grow();
                ++score;
                break;
            }
        }
    }

    public void updateFruit() {
        if(fruits.isEmpty()) {
            int[] place = placeFruit();
            if(place[0] != 0 || place[1] != 0) {
                addFruit(place[0], place[1]);
            }
        }
    }

    private int[] placeFruit() {
        int[] point = new int[2];
        int gridWidth = GameConfig.WIDTH / GameConfig.CELL_SIZE;
        int gridHeight = GameConfig.HEIGHT / GameConfig.CELL_SIZE;

        int foodX, foodY;
        boolean validPosition;

        do {
            validPosition = true;
            foodX = (rand.nextInt(gridWidth) * GameConfig.CELL_SIZE) + GameConfig.CELL_SIZE / 2;
            foodY = (rand.nextInt(gridHeight) * GameConfig.CELL_SIZE) + GameConfig.CELL_SIZE / 2;

            // Avoid to place food on the snake
            for (int i = 0; i < snake.getSize(); ++i) {
                if (snake.getSnakePart(i).getX() == foodX && snake.getSnakePart(i).getY() == foodY) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
        point[0] = foodX;
        point[1] = foodY;
        return point;
    }

    public void addFruit(int foodX, int foodY) {
        fruits.add(new Fruit(foodX, foodY));
    }

    public void removeFruit(int i) {
        fruits.remove(i);
    }

    public void reset() {
        snake.setStart();
        fruits.clear();
        score = 0;
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}