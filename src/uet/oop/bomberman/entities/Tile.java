package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;

public class Tile extends Entity {
    public Tile(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    public boolean isSolid() {
        return false;
    }

    @Override
    public void update() {

    }
}
