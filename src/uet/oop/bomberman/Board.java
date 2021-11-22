package uet.oop.bomberman;


import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.level.Level;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {

    private List<Entity> entities = new ArrayList<>();

    private Level level;

    private Camera camera;

    public Board() {
        level = new Level(this,1);
        camera = new Camera(this, 0,0);
    }

    public Camera getCamera() {
        return camera;
    }

    public Level getLevel() {
        return level;
    }

    public void addBomber(Bomber bomber) {
        entities.add(bomber);
    }

    public void render(GraphicsContext gc) {
        level.render(gc, camera.getX(), camera.getY());
        entities.forEach(g -> g.render(gc, camera.getX(), camera.getY()));
    }

    public void update() {
        entities.forEach(g -> g.update());
    }

    public Bomber getBomber() {
        Iterator<Entity> itr = entities.iterator();

        Entity cur;
        while(itr.hasNext()) {
            cur = itr.next();

            if(cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }
}
