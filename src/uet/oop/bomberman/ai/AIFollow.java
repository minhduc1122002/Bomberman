package uet.oop.bomberman.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;


public class AIFollow extends AI {

    protected Bomber bomber;
    protected Enemy enemy;
    protected Board board;

    public AIFollow(Board board, Enemy enemy) {
        bomber = board.getBomber();
        this.enemy = enemy;
        this.board = board;
    }

    @Override
    public int calculateDirection() {
        return findDirection();
    }

    public int findDirection() {
        if(bomber == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);
        if (vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1) {
                if (enemy.getYTile() % 2 == 0) {
                    enemy.setSpeed(2);
                    enemy.setMAX_STEPS(Sprite.SCALED_SIZE / 2);
                }
                else {
                    enemy.setSpeed(4);
                    enemy.setMAX_STEPS(Sprite.SCALED_SIZE / 4);
                }
                return v;
            }
            else {
                return calculateColDirection();
            }

        } else {
            int h = calculateColDirection();
            if(h != -1) {
                if (enemy.getXTile() % 2 == 0) {
                    enemy.setSpeed(2);
                    enemy.setMAX_STEPS(Sprite.SCALED_SIZE / 2);
                }
                else {
                    enemy.setSpeed(4);
                    enemy.setMAX_STEPS(Sprite.SCALED_SIZE / 4);
                }
                return h;
            }
            else {
                return calculateRowDirection();
            }
        }
    }

    public int calculateColDirection() {
        if(bomber.getXTile() < enemy.getXTile())
            return 1;
        else if(bomber.getXTile() > enemy.getXTile())
            return 0;

        return -1;
    }

    public int calculateRowDirection() {
        if(bomber.getYTile() < enemy.getYTile())
            return 2;
        else if(bomber.getYTile() > enemy.getYTile())
            return 3;
        return -1;
    }
}
