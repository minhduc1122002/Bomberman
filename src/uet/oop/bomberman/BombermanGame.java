package uet.oop.bomberman;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.gui.Game;
import uet.oop.bomberman.gui.Menu;

public class BombermanGame extends Application {

    public static final int GAME_OFFSET = 48;

    public static final int WIDTH = 31 / 2;

    public static final int HEIGHT = 13;

    private Menu menu;

    private Game game;

    private Stage gameStage;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        menu = new Menu();
        game = new Game();

        stage = menu.getMenuStage();
        gameStage = stage;
        stage.show();
        menu.getStartButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStage.setScene(game.getGameScene());
                gameStage.show();
                game.loop();
                game.handleEvent();
            }
        });

        menu.getStartButton().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu.getStartButton().setGraphic(new ImageView(new Image("/buttons/start2.png")));
            }
        });

        menu.getStartButton().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu.getStartButton().setGraphic(new ImageView(new Image("/buttons/start1.png")));
            }
        });

        menu.getExitButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Exit");
                System.exit(0);
            }
        });

        menu.getExitButton().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu.getExitButton().setGraphic(new ImageView(new Image("/buttons/exit2.png")));
            }
        });

        menu.getExitButton().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu.getExitButton().setGraphic(new ImageView(new Image("/buttons/exit1.png")));
            }
        });
    }

}