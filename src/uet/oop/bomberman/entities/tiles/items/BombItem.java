package uet.oop.bomberman.entities.tiles.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sounds.Sound;

public class BombItem extends Item {
    public BombItem(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !isPoweredUp())
        {
            Sound.playSound("poweredUp");
            int newRate = getBoard().getBomber().getBombRate() + 1;
            getBoard().getBomber().setBombRate(newRate);
            Board.BOMB_RATE++;
            setPoweredUp(true);
            remove = true;
        }
        return false;
    }
}
