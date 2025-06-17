package com.maggioli.snake.Model;

import java.util.ArrayList;
import java.util.Random;

import com.maggioli.snake.Controller.Controller;
import com.maggioli.snake.View.MainView;
import com.maggioli.snake.View.ScoreView;

public class Board {

    private static final int BWIDTH = MainView.WIDTH/GameObject.SIZE;
    private static final int BHEIGHT = MainView.HEIGHT/GameObject.SIZE;
    private final ArrayList<Fruit> fruits;
    private int score, highScore;
    private final Snake snake;
    private final SnakePart head;
    Random rand;
    private final ScoreView scoreView;

    public Board() {
        scoreView = new ScoreView();
        fruits = new ArrayList<>();
        score = 0;
        snake = new Snake();
        rand = new Random();
        head = snake.getHead();
    }

    public GameState checkCollision() {
        int headX, headY, helpX, helpY;
        headX = head.getX();
        headY = head.getY();
        for(int i = 1; i < snake.getSize(); ++i) {

            helpX = snake.getSnakePart(i).getX();
            helpY = snake.getSnakePart(i).getY();

            if(helpX == headX && helpY == headY) {
                highScore = score;
                reset();
                return GameState.Finished;
            }
        }
        return Controller.getState();
    }
    
    public void checkEaten() {
        int headX, headY, foodX, foodY;
        headX = head.getX();
        headY = head.getY();
        for(int i = 0; i < fruits.size(); ++i){

            foodX = fruits.get(i).getX();
            foodY = fruits.get(i).getY();

            if(foodX == headX && foodY == headY) {
                removeFruit(i);
                addLength();
                ++score;
            }
        }
    }
    
    public void updateFruit() {
        int foodX, foodY;
        int []place;

        if(fruits.isEmpty()) { // if there are no fruits on the board

            do {
                place = placeFruit();
                foodX = place[0];
                foodY = place[1];
            }while(foodX == 0 && foodY == 0);

            addFruit(foodX, foodY);
        }
    }

    private int[] placeFruit() {

        int []point = new int[2];
        int helpX, helpY, foodX, foodY;

        foodX = (rand.nextInt(BWIDTH) * GameObject.SIZE) + GameObject.SIZE / 2;
        foodY = (rand.nextInt(BHEIGHT) * GameObject.SIZE) + GameObject.SIZE / 2;

        for (int i = 0; i < snake.getSize(); ++i) {

            helpX = snake.getSnakePart(i).getX();
            helpY = snake.getSnakePart(i).getY();

            if (helpX == foodX && helpY == foodY) {
                break;
            }
        }
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

    public void updateScore() {
        scoreView.addScore(score);
    }
    
    public void addLength() {
        SnakePart b1 = snake.getSnakePart(snake.getSize()-1), b2 = snake.getSnakePart(snake.getSize()-2);
        if(b1.getX() > b2.getX())
            snake.addSnakePart(b1.getX()+GameObject.SIZE, b1.getY());
        else if(b1.getX() < b2.getX())
            snake.addSnakePart(b1.getX()-GameObject.SIZE, b1.getY());
        else if(b1.getY() >= b2.getY())
            snake.addSnakePart(b1.getX(), b1.getY()+GameObject.SIZE);
    }

    private void reset() {
        snake.setStart();
        fruits.clear();
        score = 0;
    }

    public ArrayList<Fruit> getFruits(){
        return fruits;
    }

    public Snake getSnake() {
        return snake;
    }

    public ScoreView getScoreView() {
        return scoreView;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}