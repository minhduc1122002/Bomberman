package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private int direction;

    private boolean last = false;

    private int animate = 0;

    public Flame(int x, int y, Image img, Board board, int direction, boolean last) {
        super(x, y, img, board);
        this.direction = direction;
        this.last = last;
    }

    @Override
    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset + BombermanGame.GAME_OFFSET);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof  Flame) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        Entity e = getBoard().getEntityAt(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
        e.collide(this);
        animate();
    }

    public void animate() {
        if (animate < 7500) {
            animate ++;
        } else {
            animate = 0;
        }
        if (last) {
            if (direction == RIGHT) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1
                        , Sprite.explosion_horizontal_right_last2, animate, 30).getFxImage());
            } else if (direction == LEFT) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1
                        , Sprite.explosion_horizontal_left_last2, animate, 30).getFxImage());
            } else if (direction == DOWN) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1
                        , Sprite.explosion_vertical_down_last2, animate, 30).getFxImage());
            } else if (direction == UP) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1
                        , Sprite.explosion_vertical_top_last2, animate, 30).getFxImage());
            }
        } else {
            if (direction == RIGHT || direction == LEFT) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1
                        , Sprite.explosion_horizontal2, animate, 30).getFxImage());
            } else if (direction == DOWN || direction == UP) {
                this.setImg(Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1
                        , Sprite.explosion_vertical2, animate, 30).getFxImage());
            }
        }
    }
}
