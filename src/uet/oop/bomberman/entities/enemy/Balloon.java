package uet.oop.bomberman.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {

    private int animate = 0;

    public Balloon(int x, int y, Image img, Board board, int speed) {
        super(x, y, img, board, speed);
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
    }

    @Override
    public void afterKill() {
        if (timeAfterKill > 0) {
            setImg(Sprite.movingSprite(Sprite.balloom_dead, Sprite.mob_dead1, Sprite.mob_dead2, animate, 60).getFxImage());
            timeAfterKill--;
        } else {
            remove();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset);
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
            animation();
        } else {
            afterKill();
        }
    }

    @Override
    public void move() {
        if (steps <= 0) {
            direction = rand(0, 3);
            steps = MAX_STEPS;
        }
        if (direction == RIGHT) {
            int tx = (int) (speed + getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x += speed;
                steps -= 1 + rest;
            } else {
                x = (int) (tx * Sprite.SCALED_SIZE - getBoundary().getWidth() - 4 - 1);
                steps = 0;
            }
        } else if (direction == LEFT) {
            int tx = (int) (-speed + getBoundary().getX()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x -= speed;
                steps -= 1 + rest;
            } else {
                x = (tx * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 4);
                steps = 0;
            }
        } else if (direction == UP) {
            int ty = (int) (- speed + getBoundary().getY()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y -= speed;
                steps -= 1 + rest;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 4);
                steps = 0;
            }
        } else if (direction == DOWN) {
            int ty = (int) (speed + getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y += speed;
                steps -= 1 + rest;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE - getBoundary().getHeight() - 4 - 1);
                steps = 0;
            }
        }
    }

    public void animation() {
        if (alive) {
            if (animate < 7500) {
                animate ++;
            } else {
                animate = 0;
            }
            if (direction == RIGHT) {
                setImg(Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 30).getFxImage());
            } else if (direction == LEFT) {
                setImg(Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 30).getFxImage());
            }
        }
    }
}
