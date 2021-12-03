package uet.oop.bomberman.ai;

import java.util.Random;

public abstract class AI {

    protected static Random random = new Random();

    public abstract int calculateDirection();
}
