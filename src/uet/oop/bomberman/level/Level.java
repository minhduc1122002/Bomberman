package uet.oop.bomberman.level;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Level {
    private int width, height;

    private int level;

    private char [][] maps;

    private Board board;

    private Tile[][] tiles;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRealWidth() {
        return width * Sprite.SCALED_SIZE;
    }

    public int getRealHeight() {
        return height * Sprite.SCALED_SIZE;
    }

    public Level(Board board, int level) {
        this.level = level;

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
                            tiles[i][j] = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grasstest.getFxImage(), board);
                            board.addBomber(new Bomber(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), board));
                            break;
                        }
                        case '*': {
                            tiles[i][j] = new Brick(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.brick.getFxImage(), board);
                            break;
                        }
                        default: {
                            tiles[i][j] = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grasstest.getFxImage(), board);
                            break;
                        }
                    }
                }
            }
            this.board = board;
            fileReader.close();
            buffReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void render(GraphicsContext gc, int xOffset, int yOffset) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].render(gc, xOffset, yOffset);
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        return tiles[x][y];
    }
}
