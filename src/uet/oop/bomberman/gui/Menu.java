package uet.oop.bomberman.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
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
        Image background = new Image("/background/background.png");
        BackgroundImage backMenu = new BackgroundImage(background, null, null, null, null);
        menuPane.setBackground(new Background(backMenu));
        startButton = BombermanGame.createButton("/buttons/start1.png", 200, 300, "ff90a3");
        menuPane.getChildren().add(startButton);
        exitButton = BombermanGame.createButton("/buttons/exit1.png", 200, 400, "ff90a3");
        menuPane.getChildren().add(exitButton);
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

    public Scene getMenuScene() {
        return menuScene;
    }
}