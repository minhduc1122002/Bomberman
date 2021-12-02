package uet.oop.bomberman.ai;

public class AILow extends AI {

    @Override
    public int calculateDirection() {
        int rand = rand(0,3);
        return rand;
    }

    public int rand(int min, int max) {
        try {
            int range = max - min + 1;
            int randomNum = min + random.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
