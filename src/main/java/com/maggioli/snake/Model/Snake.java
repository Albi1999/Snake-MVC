package com.maggioli.snake.Model;

import java.util.ArrayList;
import com.maggioli.snake.View.MainView;

public class Snake {
    private static final int SIZE = 3;
    private final SnakePart head;

    private final ArrayList<SnakePart> body;

    private int size;
    private final int headX = MainView.WIDTH/2 + GameObject.SIZE/2;
    private final int headY = MainView.HEIGHT/2 + GameObject.SIZE/2;

    public Snake() {

        body = new ArrayList<>();
        head = new SnakePart(headX, headY);
        size = 0;
        setStart();
    }

    public void setStart() {
        if(size == 0) {
            body.add(head);
            ++size;
            for(int i = 1; i < SIZE; ++i) {
                addSnakePart(headX, headY + (i * GameObject.SIZE));
            }
        }
        else { // Game Reset
            body.clear();
            head.setX(headX);
            head.setY(headY);
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
}
