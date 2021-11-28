package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;

public class Grass extends Tile {

    public Grass(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
