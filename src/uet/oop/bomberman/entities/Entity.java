package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {

    protected int x;

    protected int y;

    protected Image img;

    protected boolean remove = false;

    private Board board;

    public Entity(int x, int y, Image img, Board board) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.board = board;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void remove() {
        remove = true;
    }

    public boolean isRemove() {
        return remove;
    }

    public Board getBoard() {
        return board;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Rectangle getBoundary() {
        return new Rectangle(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public abstract void render(GraphicsContext gc);

    public abstract boolean collide(Entity e);

    public abstract void update();
}
