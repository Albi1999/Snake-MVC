package com.maggioli.snake.Model;

import com.maggioli.snake.Controller.dto.GameConfig;

import java.util.ArrayList;

public class Snake {
    private static final int INITIAL_SIZE = 3;
    private final SnakePart head;
    private final ArrayList<SnakePart> body;
    private int size;

    public Snake() {

        body = new ArrayList<>();
        int headX = GameConfig.WIDTH/2 + GameConfig.CELL_SIZE/2;
        int headY = GameConfig.HEIGHT/2 + GameConfig.CELL_SIZE/2;
        head = new SnakePart(headX, headY);
        size = 0;
        setStart();
    }

    public void setStart() {
        if(size == 0) {
            body.add(head);
            ++size;
            for(int i = 1; i < INITIAL_SIZE; ++i) {
                addSnakePart(head.getX(), head.getY() + (i * GameConfig.CELL_SIZE));
            }
        }
        else { // Game Reset
            body.clear();
            head.setX(GameConfig.WIDTH/2 + GameConfig.CELL_SIZE/2);
            head.setY(GameConfig.HEIGHT/2 + GameConfig.CELL_SIZE/2);
            size = 0;
            setStart();
        }
    }

    public int getSize() {
        return this.size;
    }

    public SnakePart getSnakePart(int i) {
        return body.get(i);
    }

    public SnakePart getHead() {
        return this.head;
    }

    public void addSnakePart(int x, int y) {
        body.add(new SnakePart(x,y));
        ++size;
    }

    public void grow() {
        SnakePart tail = body.get(size-1);
        SnakePart beforeTail = body.get(size-2);

        int newX = tail.getX();
        int newY = tail.getY();

        if(tail.getX() > beforeTail.getX())
            newX += GameConfig.CELL_SIZE;
        else if(tail.getX() < beforeTail.getX())
            newX -= GameConfig.CELL_SIZE;
        else if(tail.getY() > beforeTail.getY())
            newY += GameConfig.CELL_SIZE;
        else
            newY -= GameConfig.CELL_SIZE;

        addSnakePart(newX, newY);
    }

}