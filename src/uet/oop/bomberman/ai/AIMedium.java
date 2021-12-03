package uet.oop.bomberman.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;


public class AIMedium extends AI {
    Bomber _bomber;
    Enemy _e;

    public AIMedium(Bomber _bomber, Enemy _e) {
        this._bomber = _bomber;
        this._e = _e;
    }
    /**
     * tính toán tọa độ bomber so với con eneamy
     * @return hướn đi
     */

    public int calculateColDirection() {
        if(_bomber.getXTile() < _e.getXTile())
            return 1;
        else if(_bomber.getXTile() > _e.getXTile())
            return 0;

        return -1;
    }

    public int calculateRowDirection() {
        if(_bomber.getYTile() < _e.getYTile())
            return 2;
        else if(_bomber.getYTile() > _e.getYTile())
            return 3;
        return -1;
    }

    @Override
    public int calculateDirection() {
        if(_bomber == null)
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
}
