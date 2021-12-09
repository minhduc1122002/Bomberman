package uet.oop.bomberman.entities.tiles.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sounds.Sound;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !isPoweredUp())
        {
            Sound.playSound("poweredUp");
            int newSpeed = getBoard().getBomber().getSpeed() + 1;
            getBoard().getBomber().setSpeed(newSpeed);
            setPoweredUp(true);
            remove = true;
        }
        return false;
    }
}
