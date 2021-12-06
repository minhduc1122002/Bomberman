package uet.oop.bomberman.gui;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Canvas canvas;
    private GraphicsContext gc;
    private Scene gameScene;
    private Stage gameStage;
    private List<Text> textList;
    private Text score;
    private Text time;

    public Game() {
        board = new Board();
        // Tao Canvas
        canvas = new Canvas(BombermanGame.WIDTH * Sprite.SCALED_SIZE,
                BombermanGame.HEIGHT * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();

        textList = new ArrayList<>();
        initScoreBar();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().addAll(textList);

        // Tao scene
        gameScene = new Scene(root);

        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void handleEvent() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                board.getBomber().eventHandler(keyEvent);
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                board.getBomber().eventHandler(keyEvent);
            }
        });
    }

    public void loop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
    }

    public void update() {
        board.update();
        score.setText(String.valueOf(board.getScore()));
        time.setText(String.valueOf(board.getTime() / 60));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.getCamera().centerEntity(board.getBomber());
        board.render(gc);
    }

    public void initScoreBar() {
        Text scoreField = new Text(30, 30, "Score: ");
        scoreField.setFont(Font.loadFont("file:font/04B_30__.TTF", 20));
        textList.add(scoreField);
        score = new Text(130, 30, String.valueOf(board.getScore()));
        score.setFont(Font.loadFont("file:font/04B_30__.TTF", 20));
        textList.add(score);
        Text timeField = new Text(200, 30, "Time:    /180");
        timeField.setFont(Font.loadFont("file:font/04B_30__.TTF", 20));
        textList.add(timeField);
        time = new Text(280, 30,  String.valueOf(board.getTime() / 60));
        time.setFont(Font.loadFont("file:font/04B_30__.TTF", 20));
        textList.add(time);
    }
}