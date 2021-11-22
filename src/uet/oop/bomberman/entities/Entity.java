package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    private Board board;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img, Board board) {
        this.x = xUnit;
        this.y = yUnit;
        this.img = img;
        this.board = board;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
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

    public boolean intersects(Entity e) {
        return e.getBoundary().intersects(this.getBoundary().getLayoutBounds());
    }

    protected boolean collisionWithTile(int x, int y){
        return board.getLevel().getTileAt(x, y).isSolid();
    }

    public void render(GraphicsContext gc, int xOffset, int yOffset) {
        /*Rectangle rect = this.getBoundary();
        gc.fillRect(rect.getX() - xOffset,
                rect.getY() - yOffset,
                rect.getWidth(),
                rect.getHeight());
        gc.setFill(Color.GREEN);*/
        gc.drawImage(img, x - xOffset, y - yOffset);
    }
    public abstract void update();
}
