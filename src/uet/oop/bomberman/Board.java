package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.Game;
import uet.oop.bomberman.level.Level;
import uet.oop.bomberman.entities.Character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {

    private List<Character> characters = new ArrayList<>();

    private List<Bomb> bombs = new ArrayList<>();

    private Level level;

    private int score;

    private int time;

    private Camera camera;

    public static final int DEFAULT_TIME = 1;

    public Board() {
        level = new Level(this,1);
        level.addEntity();
        score = 0;
        time = DEFAULT_TIME * 60;
        camera = new Camera(this, 0,0);
    }

    public void nextLevel() {
        if (!(level.getLevel() + 1 > Level.MAX_LEVEL)) {
            clear();
            level = new Level(this, level.getLevel() + 1);
            BombermanGame.TIME_INIT = 0;
            level.addEntity();
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }

    public Level getLevel() {
        return level;
    }

    public void addPoint(int point) {
        score += point;
    }

    public int getTime() {
        return time;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public void render(GraphicsContext gc) {
        level.render(gc);
        bombs.forEach(g -> g.render(gc));
        characters.forEach(g -> g.render(gc));
    }

    public void update() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (bombs.get(i).isRemove()) {
                bombs.remove(i);
                i--;
            }
        }
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).update();
            if (characters.get(i).isRemove()) {
                characters.remove(i);
                i--;
            }
        }
        level.update();
        countDown();
    }

    private void countDown() {
        if (time != 0) {
            time--;
        }
    }

    public Bomber getBomber() {
        Iterator<Character> itr = characters.iterator();
        Entity cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if(cur instanceof Bomber) {
                return (Bomber) cur;
            }
        }
        return null;
    }

    public Character getCharacterAtExcluding(int x, int y, Character a) {
        Iterator<Character> itr = characters.iterator();
        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if (cur == a) {
                continue;
            }
            if (cur.getXTile() == x && cur.getYTile() == y) {
                if (a instanceof Enemy && cur instanceof Enemy) {
                    return null;
                }
                return cur;
            }
        }
        return null;
    }

    public void clear() {
        characters.clear();
        bombs.clear();
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public Bomb getBombAt(int x, int y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();
            if(b.getX() == x * Sprite.SCALED_SIZE && b.getY() == y * Sprite.SCALED_SIZE) {
                return b;
            }
        }
        return null;
    }

    public Flame getFlameAt(int x, int y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();
            Flame e = b.flameAt(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
            if(e != null) {
                return e;
            }
        }
        return null;
    }

    public Entity getEntityAt(int x, int y, Character except) {
        Entity e = null;
        e = getFlameAt(x, y);
        if (e != null) {
            return e;
        }
        e = getBombAt(x, y);
        if (e != null) {
            return e;
        }
        e = getCharacterAtExcluding(x, y, except);
        if (e != null) {
            return e;
        }
        e = level.getTileAt(x, y);
        return e;
    }

    public boolean hasEnemies() {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i) instanceof Enemy) return true;
        }
        return false;
    }

}