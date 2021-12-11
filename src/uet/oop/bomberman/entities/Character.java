package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public abstract class Character extends Entity {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;


    protected int direction;
    protected boolean alive = true;
    protected int timeAfterKill = 40;

    public Character(int x, int y, Image img, Board board) {
        super(x, y, img, board);
        direction = -1;
    }


    public abstract void kill();

    public abstract void afterKill();

    @Override
    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset + BombermanGame.GAME_OFFSET);
    }

    public abstract boolean collide(Entity e);

    public abstract void update();

    public abstract void move();
}
