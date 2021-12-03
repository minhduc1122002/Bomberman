package uet.oop.bomberman.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.AIAdvance;
import uet.oop.bomberman.ai.AIMedium;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    private int animate = 0;

    private int finalAnimation = 40;

    public Oneal(int x, int y, Image img, Board board, int speed) {
        super(x, y, img, board, speed);
        ai = new AIAdvance(getBoard().getBomber(), this, board);
        timeAfterKill = 20;
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
    }

    @Override
    public void afterKill() {
        if (timeAfterKill > 0) {
            setImg(Sprite.oneal_dead.getFxImage());
            animate = 0;
            timeAfterKill--;
        } else {
            if (finalAnimation > 0) {
                setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60).getFxImage());
                finalAnimation--;
            } else {
                remove();
            }
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            kill();
            return false;
        }
        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return false;
        }
        return false;
    }

    @Override
    public void update() {
        if (alive) {
            move();
        } else {
            afterKill();
        }
        animation();
    }


    public void move() {
        if (steps <= 0) {
            direction = ai.calculateDirection();;
            steps = MAX_STEPS;
        }
        if (direction == RIGHT) {
            int tx = (int) (speed + getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x += speed;
                steps -= 1 + rest;
            } else {
                x = (int) (tx * Sprite.SCALED_SIZE - getBoundary().getWidth() - 2 - 1);
                steps = 0;
            }
        } else if (direction == LEFT) {
            int tx = (int) (-speed + getBoundary().getX()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x -= speed;
                steps -= 1 + rest;
            } else {
                x = (tx * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 2);
                steps = 0;
            }
        } else if (direction == UP) {
            int ty = (int) (- speed + getBoundary().getY()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y -= speed;
                steps -= 1 + rest;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 2);
                steps = 0;
            }
        } else if (direction == DOWN) {
            int ty = (int) (speed + getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y += speed;
                steps -= 1 + rest;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE - getBoundary().getHeight() - 2 - 1);
                steps = 0;
            }
        } else {
            steps = 0;
        }
    }

    public void animation() {
        if (animate < 7500) {
            animate ++;
        } else {
            animate = 0;
        }
        if (alive) {
            if (direction == RIGHT) {
                setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 30).getFxImage());
            } else if (direction == LEFT) {
                setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 30).getFxImage());
            } else if (direction == UP) {
                setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 30).getFxImage());
            } else if (direction == DOWN) {
                setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 30).getFxImage());
            } else {
                setImg(Sprite.oneal_left1.getFxImage());
            }
        }
    }
}
