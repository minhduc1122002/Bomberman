package uet.oop.bomberman.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.AI;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends Character {

    protected int speed;

    protected final double MAX_STEPS;

    protected AI ai;

    protected final double rest;

    protected double steps;

    public static final int POINT = 10;

    public Enemy(int x, int y, Image img, Board board, int speed) {
        super(x, y, img, board);
        this.speed = speed;
        MAX_STEPS = (int) (Sprite.SCALED_SIZE / speed);
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;
    }

    public static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Rectangle getBoundary() {
        return new Rectangle(x + 2, y + 2, Sprite.SCALED_SIZE - 4, Sprite.SCALED_SIZE - 4);
    }

    public abstract void kill();

    public abstract void afterKill();

    public void render(GraphicsContext gc) {
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset + BombermanGame.GAME_OFFSET);
    }

    public abstract boolean collide(Entity e);

    public abstract void update();

    public abstract void move();
}
