package uet.oop.bomberman.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;

public class Game {
    private Board board;
    private Canvas canvas;
    private GraphicsContext gc;
    private Scene gameScene;
    private List<Text> textList;
    private Text score;
    private Text time;
    private Pane levelPane;
    private Scene levelScene;
    Text level;
    Text live;
    private Pane endPane;
    private Scene endScene;
    private Button backToMenuButton;

    public Game() {
        board = new Board();
        // Tao Canvas
        canvas = new Canvas(BombermanGame.WIDTH * Sprite.SCALED_SIZE,
                BombermanGame.HEIGHT * Sprite.SCALED_SIZE + GAME_OFFSET);
        gc = canvas.getGraphicsContext2D();

        initLevelBackground();
        textList = new ArrayList<>();
        initScoreBar();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().addAll(textList);

        // Tao scene
        gameScene = new Scene(root);

        backToMenuButton = BombermanGame.createButton("/buttons/continue1.png",150 , 400, "c0a2a8");
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public Scene getLevelScene() {
        return levelScene;
    }

    public Button getBackToMenuButton() {
        return backToMenuButton;
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

    public void update() {
        board.update();
        score.setText(String.valueOf(board.getScore()));
        time.setText(String.valueOf(board.getTime() / 60));
        level.setText(String.valueOf(board.getLevel().getLevel()));
        if (board.getBomber().getLive() == 3) live.setText("❤❤❤");
        else if (board.getBomber().getLive() == 2) live.setText("❤❤");
        else if (board.getBomber().getLive() == 1) live.setText("❤");
        else live.setText("");
    }

    public void lose() {
        endPane = new Pane();
        endScene = new Scene(endPane, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        Image lose = new Image("/background/lose.png");
        BackgroundImage backLose = new BackgroundImage(lose, null, null, null, null);
        endPane.setBackground(new Background(backLose));
        Text finalScore = new Text(490, 326, score.getText());
        finalScore.setFont(Font.loadFont("file:font/04B_30__.TTF", 62));
        finalScore.setFill(Color.web("0x818181",1.0));
        endPane.getChildren().add(finalScore);

        endPane.getChildren().add(backToMenuButton);
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

        Text liveField = new Text(450, 30, "Live:");
        liveField.setFont(Font.loadFont("file:font/04B_30__.TTF", 20));
        textList.add(liveField);

        live = new Text(530, 30,  "❤❤❤");
        live.setFont(new Font("Arial", 25));
        textList.add(live);
    }

    public void initLevelBackground() {
        levelPane = new Pane();
        levelScene = new Scene(levelPane, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        Image levelImg = new Image("/background/level.png");
        BackgroundImage backLevel = new BackgroundImage(levelImg, null, null, null, null);
        levelPane.setBackground(new Background(backLevel));
        level = new Text(498, 210, String.valueOf(board.getLevel().getLevel()));
        level.setFont(Font.loadFont("file:font/04B_30__.TTF", 70));
        level.setFill(Color.web("0xfedddc",1.0));
        levelPane.getChildren().add(level);
    }

    public Board getBoard() {
        return board;
    }

    public Scene getEndScene() {
        return endScene;
    }
}