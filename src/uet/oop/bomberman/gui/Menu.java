package uet.oop.bomberman.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;

import java.io.InputStream;

import static uet.oop.bomberman.BombermanGame.*;

public class Menu {
    private Pane menuPane;
    private Scene menuScene;
    private Stage menuStage;
    private Button startButton;
    private Button exitButton;

    public Menu() {
        menuPane = new Pane();
        menuScene = new Scene(menuPane, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        menuStage = new Stage();
        menuStage.setScene(menuScene);
        Image back = new Image("/background/background.png");
        BackgroundImage backMenu = new BackgroundImage(back, null, null, null, null);
        menuPane.setBackground(new Background(backMenu));
        startButton = createButton("/buttons/start1.png", 200, 300);
        menuPane.getChildren().add(startButton);
        exitButton = createButton("/buttons/exit1.png", 200, 400);
        menuPane.getChildren().add(exitButton);
    }

    public Button createButton(String path, int x, int y) {
        InputStream inputStream = getClass().getResourceAsStream(path);
        javafx.scene.image.Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        Button button = new Button("", imageView);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle("-fx-background-color: #ff90a3; ");
        return button;
    }

    public Stage getMenuStage() {
        return menuStage;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

}