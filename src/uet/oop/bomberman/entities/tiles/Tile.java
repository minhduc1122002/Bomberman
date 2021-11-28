package uet.oop.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;

public class Tile extends Entity {

    public Tile(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset);
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
