package com.maggioli.snake;

import com.maggioli.snake.Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller setUpGame = new Controller();

        primaryStage = setUpGame.getStage();

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}