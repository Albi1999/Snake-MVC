package com.maggioli.snake.Controller;

import com.maggioli.snake.Model.*;
import com.maggioli.snake.View.MainView;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller{

    protected static GameState state;
    private boolean up, down, right, left, pause, resume, start;
    private boolean keyActive;
    private int dx, dy;
    private int speedConstraint;
    private int speedPointsConstraint;
    private final Snake snake;
    private final SnakePart head;
    private final MainView view;
    private final Board board;

    public Controller() {
        state = GameState.Started;
        up = down = right = left = pause = resume = start = false;
        view = new MainView();
        snake = view.getSnake();
        head = snake.getHead();
        board = view.getBoard();
        keyActive = true;
        resume();
    }
    
    private void movement(Scene scene) {

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    if (!down && keyActive && state == GameState.Running) {
                        up = true;
                        left = false;
                        right = false;
                        keyActive = false;
                    }
                    break;
                case DOWN:
                    if (!up && keyActive && (left || right) && state == GameState.Running) {
                        down = true;
                        left = false;
                        right = false;
                        keyActive = false;
                    }
                    break;
                case LEFT:
                    if (!right && keyActive && state == GameState.Running) {
                        left = true;
                        up = false;
                        down = false;
                        keyActive = false;
                    }
                    break;
                case RIGHT:
                    if (!left && keyActive && state == GameState.Running) {
                        right = true;
                        up = false;
                        down = false;
                        keyActive = false;
                    }
                    break;
                case SPACE: // pause or resume game
                    if (state == GameState.Running || state == GameState.Paused) {
                        if (!pause) {
                            pause = true;
                            resume = false;
                        } else {
                            resume = true;
                            pause = false;
                            resume();
                        }
                    }
                    break;
                case ENTER: {
                    if (state == GameState.Started)
                        start = true;
                    if (state == GameState.Finished) {
                        start = true;
                        resume();
                    }
                }
                break;
                case ESCAPE:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(_ -> {
        });
    }
    
    private void move(int dx, int dy) {

        if(dx != 0 || dy != 0) {

            SnakePart prev = new SnakePart(head.getX(), head.getY()), next = new SnakePart(head.getX(), head.getY());

            head.setX(head.getX()+(dx*GameObject.SIZE));

            // Case when head goes too right or left on the screen
            if(head.getX() > MainView.WIDTH) {
                head.setX(GameObject.SIZE/2);
            }
            else if(head.getX() < 0) {
                head.setX(MainView.WIDTH - GameObject.SIZE/2);
            }

            head.setY(head.getY()+(dy*GameObject.SIZE));

            // Case when head goes too high or too low on the screen
            if(head.getY() > MainView.HEIGHT) {
                if ((head.getX() != GameObject.SIZE / 2 && head.getX() != MainView.HEIGHT - GameObject.SIZE / 2) || head.getY() != MainView.HEIGHT + GameObject.SIZE / 2) {
                    head.setY(GameObject.SIZE/2);
                }
            }
            else if(head.getY() < 0) {
                head.setY(MainView.HEIGHT - GameObject.SIZE/2);
            }

            for(int i = 1; i < snake.getSize(); ++i) {

                next.setX( snake.getSnakePart(i).getX());
                next.setY( snake.getSnakePart(i).getY());

                snake.getSnakePart(i).setX(prev.getX());
                snake.getSnakePart(i).setY(prev.getY());
                prev.setX(next.getX());
                prev.setY(next.getY());
            }
        }
    }

    private void resume(){

        new AnimationTimer(){

            int i=0;
            @Override
            public void handle(long now) {

                // Up
                if(up && !down) {
                    dy = -1;
                    dx = 0;
                }
                // Down
                if(!up && down) {
                    dy = 1 ;
                    dx = 0;
                }
                // Left
                if(left && !right) {
                    dy = 0;
                    dx = -1;
                }
                // Right
                if(right && !left) {
                    dy = 0;
                    dx = 1;
                }
                // Game paused
                if(pause && !resume) {
                    state = GameState.Paused;
                    view.render();
                    stop();
                }
                // Game resumed
                if(resume && !pause) {
                    state = GameState.Running;
                    resume = false;
                }
                // Game started
                if(start && (state == GameState.Finished || state == GameState.Started)) {
                    restart();
                    start = false;
                }
                // Game finished
                if(state == GameState.Finished) {
                    stop();
                }
                // Game running
                if(state == GameState.Running) {
                    if(i==speedConstraint) {
                        move(dx, dy);
                        keyActive = true;
                        i=0;
                    }
                    ++i;
                }

                update();
                view.render();
                movement(view.getScene());
            }
        }.start();

    }

    private void update() {
        board.updateFruit();
        board.checkEaten();
        board.updateScore();
        if(board.checkCollision() == GameState.Finished) {
            state = GameState.Finished;
        }

        // setting snake speed
        if(speedConstraint > 2 && board.getScore() >= speedPointsConstraint)
            speedConstraint = 2;
        if((speedConstraint == 2) && (board.getScore() - speedPointsConstraint) >= 10) {
            speedPointsConstraint += 30;
            speedConstraint = 3;
        }
    }


    private void restart() {
        state = GameState.Running;
        dx = dy = 0;
        up = down = left = right = false;
        speedConstraint = 3;
        speedPointsConstraint = 50;
    }

    public static GameState getState() {
        return state;
    }

    public Stage getStage() {
        return view.getStage();
    }
}