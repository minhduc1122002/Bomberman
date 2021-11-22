package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;

public class Grass extends Tile {

    public Grass(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void update() {

    }
}
