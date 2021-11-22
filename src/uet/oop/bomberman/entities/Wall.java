package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;

public class Wall extends Tile {

    public Wall(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void update() {

    }
}
