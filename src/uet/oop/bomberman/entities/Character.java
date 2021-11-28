package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;

public abstract class Character extends Entity {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    protected int direction = 0;

    protected boolean alive = true;
    protected int timeAfterKill = 40;

    public Character(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    public abstract void kill();

    public abstract void afterKill();

    public abstract void render(GraphicsContext gc) ;

    public abstract boolean collide(Entity e);

    public abstract void update();

    public abstract void move();
}
