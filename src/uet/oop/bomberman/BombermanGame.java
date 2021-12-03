package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {

    public static final int GAME_OFFSET = 48;
    
    public static final int WIDTH = 31 / 2;

    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;

    private Canvas canvas;

    private Board board;

    private AnchorPane menuPane;

    private Scene menuScene;

    private Stage menuStage;

    private boolean paused = false;

    private boolean menu = true;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        board = new Board();
        canvas = new Canvas(WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE + GAME_OFFSET);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                board.getBomber().eventHandler(event);
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                board.getBomber().eventHandler(event);
            }
        });


        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

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
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.getCamera().centerEntity(board.getBomber());
        board.render(gc);
    }
}
