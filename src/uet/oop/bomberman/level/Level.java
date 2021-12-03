package uet.oop.bomberman.level;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.enemy.Doll;
import uet.oop.bomberman.entities.enemy.Kondoria;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.entities.tiles.*;
import uet.oop.bomberman.entities.tiles.items.BombItem;
import uet.oop.bomberman.entities.tiles.items.FlameItem;
import uet.oop.bomberman.entities.tiles.items.Item;
import uet.oop.bomberman.entities.tiles.items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Level {

    public static final int MAX_LEVEL = 2;

    private int width, height;

    private int level;

    private char [][] maps;

    private Board board;

    private Tile[][] tiles;

    public Level(Board board, int level) {
        this.level = level;
        this.board = board;
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            String path = "res/levels/Level" + level + ".txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line = buffReader.readLine().trim();
            String[] str = line.split(" ");
            width = Integer.parseInt(str[2]);
            height = Integer.parseInt(str[1]);
            maps = new char[height][width];
            tiles = new Tile[height][width];

            for (int i = 0; i < height; i++) {
                line = buffReader.readLine();
                if (line != null) {
                    for (int j = 0; j < width; j++) {
                        maps[i][j] = line.charAt(j);
                    }
                }
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addEntity() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (maps[i][j]) {
                    case '#': {
                        tiles[i][j] = new Wall(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.wall.getFxImage(), board);
                        break;
                    }
                    case 'p': {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        board.addCharacter(new Bomber(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), board));
                        break;
                    }
                    case '*': {
                        tiles[i][j] = new Brick(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board));
                        break;
                    }
                    case 'x': {
                        tiles[i][j] = new Brick(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                new Portal(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.portal.getFxImage(), board));
                        break;
                    }
                    case '1': {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        board.addCharacter(new Balloon(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), board, 2));
                        break;
                    }
                    case 'f': {
                        tiles[i][j] = new Brick(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                new FlameItem(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage(), board));
                        break;
                    }
                    case 'b': {
                        tiles[i][j] = new Brick(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                new BombItem(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.powerup_bombs.getFxImage(), board));
                        break;
                    }
                    case 's': {
                        tiles[i][j] = new Brick(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                new SpeedItem(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.powerup_speed.getFxImage(), board));
                        break;
                    }
                    case '2': {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        board.addCharacter(new Oneal(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), board, 2));
                        break;
                    }
                    case '3': {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        board.addCharacter(new Kondoria(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), board, 1));
                        break;
                    }
                    case '4': {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        board.addCharacter(new Doll(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), board, 2));
                        break;
                    }
                    default: {
                        tiles[i][j] = new Grass(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                        break;
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public int getRealWidth() {
        return width * Sprite.SCALED_SIZE;
    }

    public int getRealHeight() {
        return height * Sprite.SCALED_SIZE;
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j].render(gc);
            }
        }
    }

    public void update() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tiles[i][j] instanceof Brick) {
                    if (((Brick) tiles[i][j]).isRemove()) {
                        tiles[i][j] = ((Brick) tiles[i][j]).getBelow();
                    }
                }
                if (tiles[i][j] instanceof Item) {
                    if (((Item) tiles[i][j]).isRemove()) {
                        tiles[i][j] = new Grass(tiles[i][j].getX(), tiles[i][j].getY(), Sprite.grass.getFxImage(), board);
                    }
                }
                tiles[i][j].update();
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if ((x >= width || x < 0) || (y >= height || y < 0)) {
            return null;
        }
        return tiles[y][x];
    }

    public char[][] getMaps() {
        return maps;
    }
}
