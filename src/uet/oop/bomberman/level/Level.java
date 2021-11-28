package uet.oop.bomberman.level;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.tiles.*;
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
        try {
            String path = "res/levels/Level" + level + ".txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line = buffReader.readLine().trim();
            String[] str = line.split(" ");
            width = Integer.parseInt(str[2]);
            height = Integer.parseInt(str[1]);
            maps = new char[width][height];
            tiles = new Tile[width][height];

            for (int i = 0; i < height; i++) {
                line = buffReader.readLine();
                if (line != null) {
                    for (int j = 0; j < width; j++) {
                        maps[j][i] = line.charAt(j);
                    }
                }
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0 ; j < height; j++) {
                    switch (maps[i][j]) {
                        case '#': {
                            tiles[i][j] = new Wall(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.wall.getFxImage(), board);
                            break;
                        }
                        case 'p': {
                            tiles[i][j] = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                            board.addCharacter(new Bomber(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), board));
                            break;
                        }
                        case '*': {
                            tiles[i][j] = new Brick(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                    new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board));
                            break;
                        }
                        case 'x': {
                            tiles[i][j] = new Brick(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board,
                                    new Portal(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.portal.getFxImage(), board));
                            break;
                        }
                        case '1': {
                            tiles[i][j] = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                            board.addCharacter(new Balloon(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), board, 1));
                            break;
                        }
                        default: {
                            tiles[i][j] = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grass.getFxImage(), board);
                            break;
                        }
                    }
                }
            }
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].render(gc);
            }
        }
    }

    public void update() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] instanceof Brick) {
                    if (((Brick) tiles[i][j]).isRemove()) {
                        tiles[i][j] = ((Brick) tiles[i][j]).getBelow();
                    }
                }
                tiles[i][j].update();
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        return tiles[x][y];
    }
}
