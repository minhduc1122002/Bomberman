package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Sound;

public class Bomber extends Character {

    private int flameLength;

    private int bombRate;

    private int speed;

    private int animate = 0;

    private int live;

    public Bomber(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        direction = -1;
        alive = true;
        live = 3;
        this.flameLength = Board.FLAME_LENGTH;
        this.bombRate = Board.BOMB_RATE;
        this.speed = Board.BOMBER_SPEED;
    }

    public int getLive() {
        return live;
    }

    public int getTimeAfterKill() {
        return timeAfterKill;
    }


    @Override
    public void kill() {
        if (!alive) return;
        alive = false;
        live--;
        Sound.playSound("lifeLost60");
    }

    @Override
    public void afterKill() {
        if (timeAfterKill > 0) {
            setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 60).getFxImage());
            timeAfterKill--;
        } else {
            reset();
        }
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

    @Override
    public Rectangle getBoundary() {
        return new Rectangle(x + 6, y + 6, Sprite.SCALED_SIZE - 24, Sprite.SCALED_SIZE - 12);
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Flame) {
            kill();
            return false;
        }
        if(e instanceof Enemy) {
            kill();
            return false;
        }
        return false;
    }

    /** test **/
    private void reset() {
        x = Sprite.SCALED_SIZE;
        y = Sprite.SCALED_SIZE;
        setImg(Sprite.player_right.getFxImage());
        speed = 3;
        bombRate = 1;
        flameLength = 1;
        Board.BOMBER_SPEED = 3;
        Board.BOMB_RATE = 1;
        Board.FLAME_LENGTH = 1;
        alive = true;
        timeAfterKill = 40;
        direction = -1;
    }

    public void setFlameLength(int flameLength) {
        this.flameLength = flameLength;
    }

    public int getFlameLength() {
        return flameLength;
    }

    public void setBombRate(int bombRate) {
        this.bombRate = bombRate;
    }

    public int getBombRate() {
        return bombRate;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void animation() {
        if (animate < 7500) {
            animate ++;
        } else {
            animate = 0;
        }
        if (alive) {
            if (direction == UP) {
                setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 30).getFxImage());
            } else if (direction == DOWN) {
                setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 30).getFxImage());
            } else if (direction == LEFT) {
                setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 30).getFxImage());
            } else if (direction == RIGHT) {
                setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 30).getFxImage());
            }
        }
    }

    @Override
    public void move() {
        if (direction == RIGHT) {
            int tx = (int) (speed + getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE, this).collide(this))) {
                x += speed;
            } else {
                x = (int) (tx * Sprite.SCALED_SIZE - getBoundary().getWidth() - 6 - 1);
            }
        } else if (direction == LEFT) {
            int tx = (int) (-speed + getBoundary().getX()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE, this).collide(this))
                    && !(getBoard().getEntityAt(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE , this).collide(this))) {
                x -= speed;
            } else {
                x = (tx * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 6);
            }
        } else if (direction == UP) {
            int ty = (int) (- speed + getBoundary().getY()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y -= speed;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 6);
            }
        } else if (direction == DOWN) {
            int ty = (int) (speed + getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE;
            if (!(getBoard().getEntityAt((int) getBoundary().getX() / Sprite.SCALED_SIZE, ty, this).collide(this))
                    && !(getBoard().getEntityAt((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty, this).collide(this))) {
                y += speed;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE - getBoundary().getHeight() - 6 - 1);
            }
        }
    }

    public void eventHandler(KeyEvent event) {

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case UP:
                case W: {
                    direction = UP;
                    break;
                }
                case DOWN:
                case S: {
                    direction = DOWN;
                    break;
                }
                case LEFT:
                case A: {
                    direction = LEFT;
                    break;
                }
                case RIGHT:
                case D: {
                    direction = RIGHT;
                    break;
                }
                case SPACE: {
                    int xt = (int) ((double) x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                    int yt = (int) ((double) y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
                    if (!(getBoard().getBombs().size() == bombRate)) {
                        Sound.playSound("bombDroppedsm");
                        getBoard().addBomb(new Bomb(xt * Sprite.SCALED_SIZE , yt * Sprite.SCALED_SIZE, Sprite.bomb.getFxImage(),getBoard()));
                    }
                    break;
                }
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case UP:
                case W: {
                    direction = -1;
                    setImg(Sprite.player_up.getFxImage());
                    break;
                }
                case DOWN:
                case S: {
                    direction = -1;
                    setImg(Sprite.player_down.getFxImage());
                    break;
                }
                case LEFT:
                case A: {
                    direction = -1;
                    setImg(Sprite.player_left.getFxImage());
                    break;
                }
                case RIGHT:
                case D: {
                    direction = -1;
                    setImg(Sprite.player_right.getFxImage());
                    break;
                }
            }
        }
    }
}
