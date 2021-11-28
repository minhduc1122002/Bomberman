package uet.oop.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Tile {

    private int animate = 0;

    private boolean isBroken = false;

    private int timeAfter = 20;

    private Tile below;

    public Brick(int x, int y, Image img, Board board, Tile below) {
        super(x, y, img, board);
        this.below = below;
    }

    @Override
    public void update() {
        if (isBroken) {
            if (animate < 7500) {
                animate ++;
            } else {
                animate = 0;
            }
            this.setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1
                    , Sprite.brick_exploded2, animate, 30).getFxImage());
            if (timeAfter > 0) {
                timeAfter--;
            } else {
                remove = true;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        below.render(gc);
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset);
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Flame) {
            isBroken = true;
        }
        return true;
    }

    public Tile getBelow() {
        return below;
    }
}
