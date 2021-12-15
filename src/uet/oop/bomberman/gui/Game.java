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
    public static final String pathFont1 = "file:font/04B_30__.TTF";
    public static final String pathFont2 = "file:font/8-bitArcadeOut.ttf";
    public static final String pathFont3 = "file:font/8-bitArcadeIn.ttf";
    public static final int fontSize2 = 40;
    public static Text scorePlus;

    private Board board;
    private Canvas canvas;
    private GraphicsContext gc;
    private Group root;
    private Scene gameScene;
    private List<Text> textList;
    private Text score;
    private Text score2;

    private Text time;
    private Text time2;
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
        root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().addAll(textList);

        // Tao scene
        gameScene = new Scene(root);
        gameScene.setFill(Color.web("010017", 1.0));

        backToMenuButton = BombermanGame.createButton("/buttons/continue1.png",280 , 400, "4a0d4b");
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
        score2.setText(String.valueOf(board.getScore()));
        time.setText(String.valueOf(board.getTime() / 60));
        time2.setText(String.valueOf(board.getTime() / 60));
        level.setText(String.valueOf(board.getLevel().getLevel()));
        live.setFill(Color.web("a0331b", 1.0));
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
        Text finalScore = new Text(490, 230, score.getText());
        finalScore.setFont(Font.loadFont(pathFont1, 62));
        finalScore.setFill(Color.web("0x818181",1.0));
        endPane.getChildren().add(finalScore);

        endPane.getChildren().add(backToMenuButton);
    }

    public void win() {
        endPane = new Pane();
        endScene = new Scene(endPane, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        Image win = new Image("/background/lose.png");
        BackgroundImage backWin = new BackgroundImage(win, null, null, null, null);
        endPane.setBackground(new Background(backWin));
        Text finalScore = new Text(490, 230, score.getText());
        finalScore.setFont(Font.loadFont(pathFont1, 62));
        finalScore.setFill(Color.web("0x818181",1.0));
        endPane.getChildren().add(finalScore);

        backToMenuButton = BombermanGame.createButton("/buttons/continue1.png",280 , 400, "ea6f9d");
        endPane.getChildren().add(backToMenuButton);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.getCamera().centerEntity(board.getBomber());
        board.render(gc);
    }

    public void initScoreBar() {
        Text scoreField = new Text(30, 30, "Score ");
        scoreField.setFont(Font.loadFont(pathFont2, fontSize2));
        scoreField.setFill(Color.web("350924", 1.0));
        textList.add(scoreField);

        Text scoreField2 = new Text(30, 30, "Score ");
        scoreField2.setFont(Font.loadFont(pathFont3, fontSize2));
        scoreField2.setFill(Color.web("007257", 1.0));
        textList.add(scoreField2);

        score = new Text(150, 30, String.valueOf(board.getScore()));
        score.setFont(Font.loadFont(pathFont2, fontSize2));
        score.setFill(Color.web("350924", 1.0));
        textList.add(score);

        score2 = new Text(150, 30, String.valueOf(board.getScore()));
        score2.setFont(Font.loadFont(pathFont3, fontSize2));
        score2.setFill(Color.web("007257", 1.0));
        textList.add(score2);

        Text timeField = new Text(250, 30, "Time");
        timeField.setFont(Font.loadFont(pathFont2, fontSize2));
        timeField.setFill(Color.web("350924", 1.0));
        textList.add(timeField);

        Text timeField2 = new Text(250, 30, "Time");
        timeField2.setFont(Font.loadFont(pathFont3, fontSize2));
        timeField2.setFill(Color.web("007257", 1.0));
        textList.add(timeField2);

        time = new Text(345, 30, String.valueOf(board.getTime() / 60));
        time.setFont(Font.loadFont(pathFont2, fontSize2));
        time.setFill(Color.web("350924", 1.0));
        textList.add(time);

        time2 = new Text(345, 30, String.valueOf(board.getTime() / 60));
        time2.setFont(Font.loadFont(pathFont3, fontSize2));
        time2.setFill(Color.web("007257", 1.0));
        textList.add(time2);

        Text liveField = new Text(500, 30, "Live");
        liveField.setFont(Font.loadFont(pathFont2, fontSize2));
        liveField.setFill(Color.web("350924", 1.0));
        textList.add(liveField);

        Text liveField2 = new Text(500, 30, "Live");
        liveField2.setFont(Font.loadFont(pathFont3, fontSize2));
        liveField2.setFill(Color.web("007257", 1.0));
        textList.add(liveField2);

        live = new Text(590, 30,  "❤❤❤");
        live.setFont(new Font("Arial", 25));
        textList.add(live);

        scorePlus = new Text(0, 0, "");
        scorePlus.setFont(Font.loadFont(pathFont3, fontSize2));
        scorePlus.setFill(Color.web("0xffffff",1.0));
        textList.add(scorePlus);
    }

    public void initLevelBackground() {
        levelPane = new Pane();
        levelScene = new Scene(levelPane, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        Image levelImg = new Image("/background/level.png");
        BackgroundImage backLevel = new BackgroundImage(levelImg, null, null, null, null);
        levelPane.setBackground(new Background(backLevel));
        level = new Text(498, 210, String.valueOf(board.getLevel().getLevel()));
        level.setFont(Font.loadFont(pathFont1, 70));
        level.setFill(Color.web("0xffdfae",1.0));
        levelPane.getChildren().add(level);
    }

    public Board getBoard() {
        return board;
    }

    public Scene getEndScene() {
        return endScene;
    }
}