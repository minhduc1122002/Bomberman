package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.AIBfs;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.Game;
import uet.oop.bomberman.sounds.Sound;


public class Doll extends Enemy {

    private int animate = 0;

    private int finalAnimation = 40;

    public Doll(int x, int y, Image img, Board board, int speed) {
        super(x, y, img, board, speed);
        ai = new AIBfs(getBoard().getBomber(), this, board);
        timeAfterKill = 20;
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
        getBoard().addPoint(POINT * 2);
        Sound.playSound("enemyKilled60");
        Game.scorePlus.setText(String.valueOf(POINT * 2));
    }

    @Override
    public void afterKill() {
        if (timeAfterKill > 0) {
            setImg(Sprite.doll_dead.getFxImage());
            animate = 0;
            timeAfterKill--;
        } else {
            if (finalAnimation > 0) {
                setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60).getFxImage());
                finalAnimation--;
            } else {
                Game.scorePlus.setText("");
                remove();
            }
        }
        Game.scorePlus.setLayoutX(x - getBoard().getCamera().getX() + Sprite.SCALED_SIZE / 3);
        Game.scorePlus.setLayoutY(y - getBoard().getCamera().getY() + BombermanGame.GAME_OFFSET - Sprite.SCALED_SIZE / 3);
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
            direction = ai.calculateDirection();
            steps = MAX_STEPS;
        }
        if (direction == RIGHT) {
            int tx = (int) (speed + getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x += speed;
                steps -= 1;
            } else {
                x = (int) (tx * Sprite.SCALED_SIZE - getBoundary().getWidth() - 2 - 1);
                steps = 0;
            }
        } else if (direction == LEFT) {
            int tx = (int) (-speed + getBoundary().getX()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x -= speed;
                steps -= 1;
            } else {
                x = (tx * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 2);
                steps = 0;
            }
        } else if (direction == UP) {
            int ty = (int) (- speed + getBoundary().getY()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y -= speed;
                steps -= 1;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 2);
                steps = 0;
            }
        } else if (direction == DOWN) {
            int ty = (int) (speed + getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y += speed;
                steps -= 1;
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
                setImg(Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 30).getFxImage());
            } else if (direction == LEFT) {
                setImg(Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 30).getFxImage());
            } else if (direction == UP) {
                setImg(Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 30).getFxImage());
            } else if (direction == DOWN) {
                setImg(Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 30).getFxImage());
            } else if (direction == -1){
                setImg(Sprite.doll_left1.getFxImage());
            }
        }
    }
}
