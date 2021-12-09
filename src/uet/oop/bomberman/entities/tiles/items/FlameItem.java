package uet.oop.bomberman.entities.tiles.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sounds.Sound;

public class FlameItem extends Item {
    public FlameItem(int x, int y, Image img, Board board) {
        super(x, y, img, board);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !isPoweredUp())
        {
            Sound.playSound("poweredUp");
            int newLength = getBoard().getBomber().getFlameLength() + 1;
            getBoard().getBomber().setFlameLength(newLength);
            setPoweredUp(true);
            remove = true;
        }
        return false;
    }
}
