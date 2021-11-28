package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Camera {

    private int x;

    private int y;

    private Board board;

    public Camera(Board board, int x, int y) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public void centerEntity(Entity entity) {
        x = entity.getX() - BombermanGame.WIDTH * Sprite.SCALED_SIZE / 4;
        y = entity.getY() - BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4;
        if (x < 0) {
            x = 0;
        } else if (x > board.getLevel().getRealWidth() - BombermanGame.WIDTH * Sprite.SCALED_SIZE)  {
            x = board.getLevel().getRealWidth() - BombermanGame.WIDTH * Sprite.SCALED_SIZE;
        }
        else {
            x = entity.getX() - BombermanGame.WIDTH * Sprite.SCALED_SIZE / 4;
        }
        if (entity.getY() - BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4 < 0) {
            y = 0;
        } else if (y > board.getLevel().getRealHeight() - BombermanGame.HEIGHT * Sprite.SCALED_SIZE)  {
            y = board.getLevel().getRealHeight() - BombermanGame.HEIGHT * Sprite.SCALED_SIZE;
        } else {
            y = entity.getY() - BombermanGame.HEIGHT * Sprite.SCALED_SIZE / 4;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
