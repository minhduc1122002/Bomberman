package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uet.oop.bomberman.gui.Game;
import uet.oop.bomberman.gui.Menu;
import uet.oop.bomberman.level.Level;
import uet.oop.bomberman.sounds.Sound;

import java.io.InputStream;

public class BombermanGame extends Application {

    public static final int GAME_OFFSET = 48;

    public static final int WIDTH = 31 / 2;

    public static final int HEIGHT = 13;

    public static int TIME_INIT;

    private Menu menu;

    private Game game;

    public static Stage gameStage;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        Sound.playMenuMusic();
        menu = new Menu();
        game = new Game();

        stage.setScene(menu.getMenuScene());
        gameStage = stage;
        stage.show();

        menu.getStartButton().setOnMouseClicked(mouseEvent -> {
            Sound.playSound("menuClicked");
            Sound.stopMenuMusic();
            loop();
            game.handleEvent();
            gameStage.show();
        });

        menu.getStartButton().setOnMouseEntered(mouseEvent -> {
            Sound.playSound("menuEntered");
            menu.getStartButton().setGraphic(new ImageView(new Image("/buttons/start2.png")));
        });

        menu.getStartButton().setOnMouseExited(mouseEvent -> menu.getStartButton().setGraphic(new ImageView(new Image("/buttons/start1.png"))));

        menu.getExitButton().setOnMouseClicked(mouseEvent -> {
            Sound.playSound("menuClicked");
            System.out.println("Exit");
            System.exit(0);
        });

        menu.getExitButton().setOnMouseEntered(mouseEvent -> {
            Sound.playSound("menuEntered");
            menu.getExitButton().setGraphic(new ImageView(new Image("/buttons/exit2.png")));
        });

        menu.getExitButton().setOnMouseExited(mouseEvent -> menu.getExitButton().setGraphic(new ImageView(new Image("/buttons/exit1.png"))));
    }

    public void loop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (TIME_INIT < 3 * 60) {
                    Sound.stopBGMusic();
                    Sound.playStageStart();
                    gameStage.setScene(game.getLevelScene());
                    TIME_INIT++;
                } else if ((game.getBoard().getTime() == 0 && game.getBoard().hasEnemies())
                        || (game.getBoard().getBomber().getTimeAfterKill() == 0) && game.getBoard().getBomber().getLive() == 0) {
                    this.stop();
                    game.lose();
                    Sound.stopBGMusic();
                    Sound.playGameOver();
                    gameStage.setScene(game.getEndScene());
                    game.getBackToMenuButton().setOnMouseClicked(mouseEvent -> {
                        Sound.playSound("menuClicked");
                        TIME_INIT = 0;
                        BombermanGame.this.start(gameStage);
                    });
                    game.getBackToMenuButton().setOnMouseEntered(mouseEvent -> {
                        Sound.playSound("menuEntered");
                        Sound.stopGameOver();
                        game.getBackToMenuButton().setGraphic(new ImageView(new Image("/buttons/continue2.png")));
                    });

                    game.getBackToMenuButton().setOnMouseExited(mouseEvent -> game.getBackToMenuButton().setGraphic(new ImageView(new Image("/buttons/continue1.png"))));
                } else if(game.getBoard().isWin()) {
                    this.stop();
                    game.win();
                    Sound.stopBGMusic();
                    Sound.playGameOver();
                    gameStage.setScene(game.getEndScene());
                    game.getBackToMenuButton().setOnMouseClicked(mouseEvent -> {
                        Sound.playSound("menuClicked");
                        TIME_INIT = 0;
                        BombermanGame.this.start(gameStage);
                    });
                    game.getBackToMenuButton().setOnMouseEntered(mouseEvent -> {
                        Sound.playSound("menuEntered");
                        Sound.stopGameOver();
                        game.getBackToMenuButton().setGraphic(new ImageView(new Image("/buttons/continue3.png")));
                    });

                    game.getBackToMenuButton().setOnMouseExited(mouseEvent -> game.getBackToMenuButton().setGraphic(new ImageView(new Image("/buttons/continue1.png"))));
                } else {
                    Sound.playBGMusic();
                    gameStage.setScene(game.getGameScene());
                    game.render();
                    game.update();
                }
            }
        };
        timer.start();
    }

    public static Button createButton(String path, int x, int y, String backgroundColor) {
        InputStream inputStream = BombermanGame.class.getResourceAsStream(path);
        javafx.scene.image.Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        Button button = new Button("", imageView);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle("-fx-background-color: #" + backgroundColor + "; ");
        return button;
    }

}