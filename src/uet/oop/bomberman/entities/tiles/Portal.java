package uet.oop.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public class Portal extends Tile {

    public Portal(int x, int y, Image image, Board board) {
        super(x, y, image, board);
    }

    @Override
    public void render(GraphicsContext gc) {
        Rectangle rect = this.getBoundary();
        int xOffset = getBoard().getCamera().getX();
        int yOffset = getBoard().getCamera().getY();
        gc.fillRect(rect.getX() - xOffset,
                rect.getY() - yOffset + BombermanGame.GAME_OFFSET,
                rect.getWidth(),
                rect.getHeight());
        gc.setFill(Color.BLACK);
        gc.drawImage(img, x - xOffset, y - yOffset + BombermanGame.GAME_OFFSET);
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber) {
            //if(_board.detectNoEnemies() == false)
            //return false;
                //if(_board.detectNoEnemies())
                getBoard().nextLevel();
            return true;
        }

        return false;
    }

    @Override
    public void update() {

    }
}
