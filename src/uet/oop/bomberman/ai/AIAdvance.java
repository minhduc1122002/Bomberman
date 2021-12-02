package uet.oop.bomberman.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.tiles.Brick;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AIAdvance extends AI {
    protected Bomber bomber;
    protected Enemy enemy;
    protected Board board;

    public ArrayList<Integer> path = new ArrayList();// đường đi ngắn nhất
    public int height; // cột dọc
    public int width;	// cột ngang

    public int[][] node;  //ma trận đỉnh kề
    public int numOfNode;	 // số lượng đỉnh tối đa
    public int[][] matrix;  // ma trận đỉnh gồm cái số nguyên âm 0 và nguyên dương. ma trận 2 chiều

    public AIAdvance(Bomber bomber, Enemy e , Board b) {
        this.bomber = bomber;
        this.enemy = e;
        this.board = b;
        height = b.getLevel().getHeight();
        width = b.getLevel().getWidth();
        node = new int[height*width][4];
        numOfNode = height * width;
        matrix = new int[height][width];
        getMatrix();
    }

    public void getMatrix() {
        int nameOfVertext = 1;
        char [][]map = board.getLevel().getMaps();
        for (int i = 0 ; i < height ; i++){
            for ( int j = 0 ; j < width ; j++){
                if (map[i][j] == '#'){
                    matrix[i][j] = 0;
                }
                else if (map[i][j] =='*' || map[i][j] == 'x' || map[i][j] == 'f' || map[i][j] == 'b' || map[i][j] == 's'){
                    matrix[i][j] = nameOfVertext * (-1);
                    nameOfVertext++;
                } else {
                    this.matrix[i][j] = nameOfVertext;
                    nameOfVertext++;
                }
            }
        }
        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }*/
    }

    public void updateDestroy_Brick() {
        if (Brick.Xgachvo.isEmpty()) return;
        for (int i = 0; i < Brick.Xgachvo.size(); i++ ){
            int Xgach = Brick.Xgachvo.get(i);
            int Ygach = Brick.Ygachvo.get(i);
            // kiểm tra bị phá chưa
            if (matrix [Ygach][Xgach] < 0 ){
                //nếu chưa phá ( tức đang âm ) thì cho nó  dương ( tức phá rồi)
                //System.out.println("Sout AI medium da doi thanh cong :  x = " + Xgach + " y = "+ Ygach + " dinh thu " + matrix [Ygach][Xgach]);
                matrix [Ygach][Xgach] =  matrix [Ygach][Xgach]*(-1);
            }
        }
    }

    public void convertNearNodeMatrix() {
        for (int i = 1; i < 12; i++) {
            for ( int j = 1; j < 30; j++ ) {
                if (this.matrix[i][j] > 0){
                    // cùng hàng

                    // bên trái
                    if (this.matrix[i][j-1] > 0){
                        this.node[ this.matrix[i][j] ][0]=this.matrix[i][j-1];
                    }else{
                        this.node[this.matrix[i][j]][0]=0; // không có đỉnh kể
                    }
                    //bên phải
                    if (this.matrix[i][j+1] > 0 ){
                        this.node[ this.matrix[i][j] ][1]=this.matrix[i][j+1];
                    }else{
                        this.node[this.matrix[i][j]][1]=0;
                    }
                    //cùng cột
                    //bên trên
                    if (this.matrix[i-1][j] > 0 ){
                        this.node[ this.matrix[i][j] ][2]=this.matrix[i-1][j];
                    }else{
                        this.node[this.matrix[i][j]][2]=0;
                    }
                    //bên dưới
                    if (this.matrix[i+1][j] > 0 ){
                        this.node[ this.matrix[i][j] ][3]=this.matrix[i+1][j];
                    }else{
                        this.node[this.matrix[i][j]][3]=0;
                    }
                }
            }
        }
    }

    public void updateMatrix(){
        //this._board.getBombs()
        //update vị chí bom
        int r = bomber.getFlameLength(); // bán kính vụ nổi
        // toa độ enemy
        int xe = this.enemy.getXTile();
        int ye = this.enemy.getYTile();

        for ( int i =0; i  < board.getBombs().size(); i++){
            // tọa độ quả bom đc đặt
            int xt =  board.getBombs().get(i).getXTile();
            int yt =  board.getBombs().get(i).getYTile();

            // làm tạm mất đỉnh mà quả bom đang ở ( làm âm nó đi)
            this.matrix[yt][xt] *=-1;
            // làm tạm mất các đỉnh trong vù ảnh hưởng của bom ( âm nó đi)

            // xét ngang
            //phải    && yt!=yb && xt+j != xb
            for (int j=1 ; j <= r;j++){
                if ( this.matrix[yt][xt+j] > 0 && yt!=ye && xt+j != xe   ){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người
                    this.matrix[yt][xt+j] *=-1;
                }else{
                    break;
                }
            }
            //trai
            for ( int j=1 ; j <= r; j++){
                if ( this.matrix[yt][xt-j] > 0 && yt!=ye && xt-j != xe){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt][xt-j] *=-1;

                }else{
                    break;
                }
            }
            // xet dọc
            //dưới
            for ( int j=1 ; j <= r;j++){
                if ( this.matrix[yt+j][xt] > 0  && yt+j != ye && xt!= xe ){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt+j][xt] *=-1;
                }else{
                    break;
                }
            }
            //trên
            for ( int j=1 ; j <= r;j++){
                if ( this.matrix[yt-j][xt] > 0 && yt-j != ye && xt != xe){
                    // tức là sẽ dùng việc bôi đen lại nếu enemy trong vùng ảnh hương
                    // dừng việc bôi đen khi găp người

                    this.matrix[yt-j][xt] *=-1;
                }else{
                    break;
                }
            }
        }
    }

    public int bfs(int start , int end) throws IllegalStateException { // exception khi que ko còn chỗ
        // tao cai  Queue node
        Queue<Integer> qNode = new LinkedList<Integer>();
        // khai báo
        int [] parent = new int[numOfNode + 1];
        boolean [] visted = new boolean[numOfNode + 1];

        // sét nhan cho đinh, xét đỉnh cha
        // trường hợp bommer hoặc người chơi trong  khu vực bom tức đỉnh âm thì sẽ đc xử lý ở đây.

        if (start < 0) start *=-1;
        if (end <0) end *=-1;

        visted[start] = false;
        parent[start] = -1; // sét mặc định đỉnh cha
        parent[end]=-1;

        // thêm đỉnh start vào đầu
        qNode.add(start);

        while (!qNode.isEmpty()){
            // dequeue phanaf tử đầu tiên ra
            int currentNode = qNode.poll();

            // duyệt toàn bộ đỉnh kể với current , nếu chưa visit thì dán cho là visit
            for ( int i = 0; i < 4; i++){
                if (!visted[node[currentNode][i]] && node[currentNode][i]!=0) {
                    // dán nhãn đã thăm
                    visted[node[currentNode][i]]= true;
                    // gán đỉnh cha
                    parent[node[currentNode][i]] = currentNode;
                    // cho vào queue
                    qNode.add(node[currentNode][i]);

                }

            }
        }
        // xuất đường đi ngắn nhất ra
        int p = parent[end];
        // thêm node cuối

        if (p != -1) {
            path.add(end);
            path.add(p);
            while ( p!=start){ // chu di den goc
                p = parent[p];
                path.add(p);
            }
//             for ( int i =0 ; i < path.size() ; i++ ){
//                System.out.println(path.get(i)+ " ");
//            }
            // trả về vị chí thứ 2 tức làm đỉnh kết tiếp start
            return path.get(path.size()-2);

        }

        // không tồn tại đường đi thì sẽ cho đứng im
        return -1;
    }

    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        getMatrix();
        updateMatrix();
        updateDestroy_Brick();
        convertNearNodeMatrix();
        // cập nhập liên tục


//            đỉnh bắt đầu
        int start = this.matrix[enemy.getYTile()][enemy.getXTile()];
//             toaj ddoo dinh ket thuc laf toa do cua boober
        int end = this.matrix[bomber.getYTile()][bomber.getXTile()];


//           trả về đỉnh cần đi tiếp
        int result = this.bfs(start, end);

        if (result == -1 ) //random.nextInt(4);

            return -1;  // nếu đứng -1 cho đứng im


        if ( result - start == 1 ) return 0; // ben phai
        if ( start -  result == 1) return 1; // ssang trai
        if ( start > result ) return 2; // len tren
        if ( start < result ) return 3; // duoi

        return -1;
    }
}
