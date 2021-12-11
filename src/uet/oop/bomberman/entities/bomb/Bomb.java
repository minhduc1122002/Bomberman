package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {

    private boolean isExploded = false;

    private int timeToExplode = 120;

    private int timeAfter = 20;

    private int animate;

    private List<Flame> flames = new ArrayList<>();

    private boolean canPass = false;

    public Bomb(int xUnit, int yUnit, Image img, Board board) {
        super(xUnit, yUnit, img, board);
    }

    @Override
    public void update() {
        if (animate < 7500) {
            animate ++;
        } else {
            animate = 0;
        }
        if (timeToExplode > 0) {
            timeToExplode--;
            this.setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 30).getFxImage());
        }
        else {
            if(!isExploded) {
                canPass = true;
                isExploded = true;
                Sound.playSound("bombExploded");
                Character a = getBoard().getCharacterAtExcluding(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
                if (a != null)  {
                    a.kill();
                }
                addFlame();
            }
            else {
                flames.forEach(Flame::update);
                this.setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 30).getFxImage());
            }
            if (timeAfter > 0) {
                timeAfter--;
            } else {
                remove = true;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        flames.forEach(g -> g.render(gc));
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.drawImage(img, x - xOffset, y - yOffset + BombermanGame.GAME_OFFSET);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            int diffX = e.getX() - this.getX();
            int diffY = e.getY() - this.getY();
            if (!(diffX > -e.getBoundary().getWidth() - 6 - 1 && diffX < Sprite.SCALED_SIZE - 6
                    && diffY > -e.getBoundary().getHeight() - 6 - 1 && diffY < Sprite.SCALED_SIZE - 6)) {
                canPass = true;
            }
            return canPass;
        }
        if (e instanceof Flame) {
            timeToExplode = 0;
        }
        if (e instanceof Bomb) {
            if (((Bomb) e).isExploded) {
                timeToExplode = 0;
            }
        }
        return true;
    }

    public void addFlame() {
        int length = getBoard().getBomber().getFlameLength();
        for (int i = 1; i <= length; i++) {
            if (i == length) {
                Entity e = getBoard().getEntityAt((x + Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE , y / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x + Sprite.SCALED_SIZE * i, y,
                        Sprite.explosion_horizontal_right_last.getFxImage(), getBoard(), Flame.RIGHT, true);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            } else {
                Entity e = getBoard().getEntityAt((x + Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE , y / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x + Sprite.SCALED_SIZE * i, y,
                        Sprite.explosion_horizontal.getFxImage(), getBoard(), Flame.RIGHT, false);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i <= length; i++) {
            if (i == length) {
                Entity e = getBoard().getEntityAt((x - Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE , y / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x - Sprite.SCALED_SIZE * i, y,
                        Sprite.explosion_horizontal_left_last.getFxImage(), getBoard(), Flame.LEFT, true);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            } else {
                Entity e = getBoard().getEntityAt((x - Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE , y / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x - Sprite.SCALED_SIZE * i, y,
                        Sprite.explosion_horizontal.getFxImage(), getBoard(), Flame.LEFT, false);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i <= length; i++) {
            if (i == length) {
                Entity e = getBoard().getEntityAt(x / Sprite.SCALED_SIZE , (y + Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x, y + Sprite.SCALED_SIZE * i,
                        Sprite.explosion_vertical_down_last.getFxImage(), getBoard(), Flame.DOWN, true);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            } else {
                Entity e = getBoard().getEntityAt(x / Sprite.SCALED_SIZE , (y + Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x, y + Sprite.SCALED_SIZE * i,
                        Sprite.explosion_vertical.getFxImage(), getBoard(), Flame.DOWN, false);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i <= length; i++) {
            if (i == length) {
                Entity e = getBoard().getEntityAt(x / Sprite.SCALED_SIZE , (y - Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x, y - Sprite.SCALED_SIZE * i,
                        Sprite.explosion_vertical_top_last.getFxImage(), getBoard(), Flame.UP, true);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            } else {
                Entity e = getBoard().getEntityAt(x / Sprite.SCALED_SIZE , (y - Sprite.SCALED_SIZE * i) / Sprite.SCALED_SIZE, null);
                Flame flame = new Flame(x, y - Sprite.SCALED_SIZE * i,
                        Sprite.explosion_vertical.getFxImage(), getBoard(), Flame.UP, false);
                if (!e.collide(flame)) {
                    flames.add(flame);
                } else {
                    break;
                }
            }
        }
    }

    public Flame flameAt(int x, int y) {
        for (int i = 0; i < flames.size(); i++) {
            if(flames.get(i).getX() == x && flames.get(i).getY() == y)
                return flames.get(i);
        }
        return null;
    }
}
