package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private boolean[]directions;

    private int speed = 2;

    public Bomber(int x, int y, Image img, Board board) {
        super( x, y, img, board);
        directions = new boolean[4];
        for (int i = 0; i < 4; i++) {
            directions[i] = false;
        }
    }

    @Override
    public void update() {
        move();
        animation();
    }

    @Override
    public Rectangle getBoundary() {
        return new Rectangle(x + 4, y + 4, Sprite.SCALED_SIZE - 20, Sprite.SCALED_SIZE - 8);
    }

    public void animation() {
        if (directions[UP]) {
            setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.getY(), 80).getFxImage());
        } else if (directions[DOWN]) {
            setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.getY(), 80).getFxImage());
        } else if (directions[LEFT]) {
            setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.getX(), 80).getFxImage());
        } else if (directions[RIGHT]) {
            setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.getX(), 80).getFxImage());
        }
    }

    public void move() {
        if (directions[RIGHT]) {
            int tx = (int) (speed + getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE;
            if (!collisionWithTile(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE) &&
                    !collisionWithTile(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE)){
                x += speed;
            } else {
                x = (int) (tx * Sprite.SCALED_SIZE - getBoundary().getWidth() - 4 - 1);
            }
        } else if (directions[LEFT]) {
            int tx = (int) (-speed + getBoundary().getX()) / Sprite.SCALED_SIZE;
            if (!collisionWithTile(tx, (int) (getBoundary().getY()) / Sprite.SCALED_SIZE) &&
                    !collisionWithTile(tx, (int) (getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE)){
                x -= speed;
            } else {
                x = (tx * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 4);
            }
        } else if (directions[UP]) {
            int ty = (int) (- speed + getBoundary().getY()) / Sprite.SCALED_SIZE;
            if (!collisionWithTile((int) (getBoundary().getX()) / Sprite.SCALED_SIZE, ty) &&
                    !collisionWithTile((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty)){
                y -= speed;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE - 4);
            }
        } else if (directions[DOWN]) {
            int ty = (int) (speed + getBoundary().getY() + getBoundary().getHeight()) / Sprite.SCALED_SIZE;
            if (!collisionWithTile((int) (getBoundary().getX()) / Sprite.SCALED_SIZE, ty) &&
                    !collisionWithTile((int) (getBoundary().getX() + getBoundary().getWidth()) / Sprite.SCALED_SIZE, ty)){
                y += speed;
            } else {
                y = (int) (ty * Sprite.SCALED_SIZE - getBoundary().getHeight() - 4 - 1);
            }
        }
    }

    public void eventHandler(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case W: {
                    directions[UP] = true;
                    break;
                }
                case S: {
                    directions[DOWN] = true;
                    break;
                }
                case A: {
                    directions[LEFT] = true;
                    break;
                }
                case D: {
                    directions[RIGHT] = true;
                    break;
                }
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case W: {
                    directions[UP] = false;
                    setImg(Sprite.player_up.getFxImage());
                    break;
                }
                case S: {
                    directions[DOWN] = false;
                    setImg(Sprite.player_down.getFxImage());
                    break;
                }
                case A: {
                    directions[LEFT] = false;
                    setImg(Sprite.player_left.getFxImage());
                    break;
                }
                case D: {
                    directions[RIGHT] = false;
                    setImg(Sprite.player_right.getFxImage());
                    break;
                }
            }
        }
    }
}
