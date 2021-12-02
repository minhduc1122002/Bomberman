package uet.oop.bomberman.entities.tiles.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.tiles.Tile;

public class Item extends Tile {

    private boolean isPoweredUp = false;

    public Item(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset);
    }

    public boolean isPoweredUp() {
        return isPoweredUp;
    }

    public void setPoweredUp(boolean poweredUp) {
        isPoweredUp = poweredUp;
    }
}
