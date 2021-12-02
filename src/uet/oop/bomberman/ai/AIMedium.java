package uet.oop.bomberman.ai;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;

public class AIMedium extends AI {
    Bomber bomber;
    Enemy enemy;

    public AIMedium(Bomber bomber, Enemy enemy) {
        this.bomber = bomber;
        this.enemy = enemy;
    }

    @Override
    public int calculateDirection() {
        if(bomber == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1)
                return v;
            else
                return calculateColDirection();

        } else {
            int h = calculateColDirection();
            if(h != -1)
                return h;
            else
                return calculateRowDirection();
        }
    }

    protected int calculateColDirection() {
        if(bomber.getXTile() < enemy.getXTile())
            return 1;
        else if(bomber.getXTile() > enemy.getXTile())
            return 0;

        return -1;
    }

    protected int calculateRowDirection() {
        if (bomber.getYTile() < enemy.getYTile())
            return 2;
        else if (bomber.getYTile() > enemy.getYTile())
            return 3;

        return -1;
    }
}
