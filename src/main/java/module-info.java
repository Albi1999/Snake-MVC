module com.maggioli.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.maggioli.snake to javafx.fxml;
    exports com.maggioli.snake;
    exports com.maggioli.snake.Controller;
    opens com.maggioli.snake.Controller to javafx.fxml;
    exports com.maggioli.snake.Model;
    opens com.maggioli.snake.Model to javafx.fxml;
    exports com.maggioli.snake.View;
    opens com.maggioli.snake.View to javafx.fxml;
}