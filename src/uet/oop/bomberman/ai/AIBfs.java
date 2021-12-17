package uet.oop.bomberman.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.tiles.Brick;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AIBfs extends AI {
    protected Bomber bomber;
    protected Enemy enemy;
    protected Board board;

    public ArrayList<Integer> path = new ArrayList();
    public int height;
    public int width;

    public int[][] node;
    public int numberOfNode;
    public int[][] matrix;

    public AIBfs(Bomber bomber, Enemy e , Board b) {
        this.bomber = bomber;
        this.enemy = e;
        this.board = b;
        height = b.getLevel().getHeight();
        width = b.getLevel().getWidth();
        node = new int[height*width][4];
        numberOfNode = height * width;
        matrix = new int[height][width];
        getMatrix();
    }

    public void getMatrix() {
        int key = 1;
        char [][]map = board.getLevel().getMaps();
        for (int i = 0 ; i < height ; i++) {
            for ( int j = 0 ; j < width ; j++) {
                if (map[i][j] == '#') {
                    matrix[i][j] = 0;
                }
                else if (map[i][j] =='*' || map[i][j] == 'x' || map[i][j] == 'f' || map[i][j] == 'b' || map[i][j] == 's') {
                    matrix[i][j] = key * (-1);
                    key++;
                } else {
                    this.matrix[i][j] = key;
                    key++;
                }
            }
        }
    }

    public void updateDestroy_Brick() {
        if (Brick.brokenBrickX.isEmpty()) return;
        for (int i = 0; i < Brick.brokenBrickX.size(); i++){
            int x = Brick.brokenBrickX.get(i);
            int y = Brick.brokenBrickY.get(i);
            if (matrix [y][x] < 0 ){
                matrix [y][x] =  matrix [y][x] * (-1);
            }
        }
    }

    public void convertNearNodeMatrix() {
        for (int i = 1; i < height - 1; i++) {
            for ( int j = 1; j < width - 1; j++ ) {
                if (this.matrix[i][j] > 0) {
                    if (this.matrix[i][j-1] > 0) {
                        this.node[this.matrix[i][j]][0] = this.matrix[i][j-1];
                    } else {
                        this.node[this.matrix[i][j]][0] = 0;
                    }
                    if (this.matrix[i][j+1] > 0) {
                        this.node[this.matrix[i][j]][1] = this.matrix[i][j+1];
                    } else {
                        this.node[this.matrix[i][j]][1] = 0;
                    }
                    if (this.matrix[i-1][j] > 0) {
                        this.node[this.matrix[i][j]][2] = this.matrix[i-1][j];
                    } else {
                        this.node[this.matrix[i][j]][2] = 0;
                    }
                    if (this.matrix[i+1][j] > 0) {
                        this.node[this.matrix[i][j]][3] = this.matrix[i+1][j];
                    } else {
                        this.node[this.matrix[i][j]][3] = 0;
                    }
                }
            }
        }
    }

    public void updateMatrix(){
        int r = bomber.getFlameLength();
        int xe = this.enemy.getXTile();
        int ye = this.enemy.getYTile();
        for (int i = 0; i  < board.getBombs().size(); i++) {
            int xt =  board.getBombs().get(i).getXTile();
            int yt =  board.getBombs().get(i).getYTile();
            this.matrix[yt][xt] *= -1;
            for (int j = 1; j <= r; j++) {
                if (this.matrix[yt][xt + j] > 0 && yt != ye && xt + j != xe) {
                    this.matrix[yt][xt + j] *= -1;
                } else {
                    break;
                }
            }
            for (int j = 1; j <= r; j++) {
                if (this.matrix[yt][xt - j] > 0 && yt != ye && xt - j != xe) {
                    this.matrix[yt][xt - j] *= -1;
                } else {
                    break;
                }
            }
            for (int j = 1; j <= r; j++) {
                if ( this.matrix[yt + j][xt] > 0  && yt + j != ye && xt != xe ) {
                    this.matrix[yt + j][xt] *= -1;
                } else {
                    break;
                }
            }
            for (int j = 1; j <= r; j++){
                if (this.matrix[yt - j][xt] > 0 && yt - j != ye && xt != xe) {
                    this.matrix[yt - j][xt] *= -1;
                } else {
                    break;
                }
            }
        }
    }

    public int bfs(int start , int end) throws IllegalStateException {
        Queue<Integer> qNode = new LinkedList<Integer>();
        int [] parent = new int[numberOfNode + 1];
        boolean [] visted = new boolean[numberOfNode + 1];
        if (start < 0) start *= -1;
        if (end < 0) end *= -1;
        visted[start] = false;
        parent[start] = -1;
        parent[end] = -1;
        qNode.add(start);

        while (!qNode.isEmpty()) {
            int currentNode = qNode.poll();
            for (int i = 0; i < 4; i++) {
                if (!visted[node[currentNode][i]] && node[currentNode][i]!=0) {
                    visted[node[currentNode][i]] = true;
                    parent[node[currentNode][i]] = currentNode;
                    qNode.add(node[currentNode][i]);
                }
            }
        }
        int p = parent[end];
        if (p != -1) {
            path.add(end);
            path.add(p);
            while (p != start){
                p = parent[p];
                path.add(p);
            }
            return path.get(path.size()-2);
        }
        return -1;
    }

    public int calculateDirection() {
        getMatrix();
        updateMatrix();
        updateDestroy_Brick();
        convertNearNodeMatrix();
        int start = this.matrix[enemy.getYTile()][enemy.getXTile()];
        int end = this.matrix[bomber.getYTile()][bomber.getXTile()];
        int result = this.bfs(start, end);

        if (result == -1) {
            return random.nextInt(4);
            //return -1;
        }

        if (result - start == 1) return 0;
        if (start -  result == 1) return 1;
        if (start > result) return 2;
        if (start < result) return 3;

        return -1;
    }
}
