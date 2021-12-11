package uet.oop.bomberman.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemy.Enemy;
import java.util.ArrayList;
import java.util.Arrays;

public class AIMediumHigh extends AI {

    protected Bomber bomber;
    protected Enemy enemy;
    protected Board board;
    int radius;

    public AIMediumHigh(Board board, Enemy e) {
        this.bomber = board.getBomber();
        this.enemy = e;
        this.board = board;
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

    public int detectBombinRanger(int  Xb  ,int  Yb ){
        radius = bomber.getFlameLength();
        int Xe = this.enemy.getXTile();
        int Ye = this.enemy.getYTile();
        // ngang
        if ( Yb == Ye ){
            //  bom o ben phai
            if ( (Xb- Xe) > 0 && ( Xb - Xe ) <= radius + 1 ){
                //  System.out.println("phai");
                return 0; // phai
            }
            // bom o ben trai
            if( (Xe- Xb) > 0 && ( Xe - Xb ) <= radius + 1 ){
                //    System.out.println("trai");
                return 4;// trai
            }
        }
        // doc
        if  ( Xb == Xe  ){

            //  bom oi duoi
            if ( (Yb- Ye) > 0 && ( Yb - Ye ) <= radius + 1 ){
                // System.out.println("duoi");
                return 6; // duoi
            }
            //  bom o ben tren
            if( (Ye- Yb) > 0 && ( Ye - Yb ) <= radius + 1 ){
                // System.out.println("tren");
                return 2;//tren
            }
        }


        // goc ben tren
        if ( (Ye - Yb > 0 ) && (Ye - Yb <=radius + 1 )  ){
            // bom o phai tren
            if ( (Xb - Xe) > 0 && ( Xb- Xe) <=radius + 1 ){
                //  System.out.println("phai tren");
                return 1;//phair tren
            }
            // bom o trai tren
            if ( (Xe - Xb) > 0 && ( Xe- Xb) <=radius + 1 ){
                //  System.out.println("trai tren");
                return 3; // trai tren
            }
        }
        //goc ben duoi
        if ( (Yb - Ye > 0) && ( Yb - Ye <=radius + 1) ){
            // xet goc duoi phai
            if ( (Xb - Xe) > 0 && ( Xb- Xe) <=radius + 1){
                // System.out.println("duoi phai");
                return 7;//duoi phai
            }
            // duoi trai
            if ( (Xe - Xb) > 0 && ( Xe- Xb) <=radius + 1){
                //  System.out.println("duoi trai");
                return 5; // trai duoi
            }
        }
        if (Xe == Xb && Yb == Ye) {
            return 8;
        }
        // khong co bom thi
        //   System.out.println("ko co");
        return -1;
    }

    @Override
    public int calculateDirection() {
        int Xe = this.enemy.getXTile();
        int Ye = this.enemy.getYTile();

        // top left
               /*
                    tl      tm      tr

                     l      m       r

                     bl     bm      br


               */
        // canGo  -1 tương ứng với cango[4]
        //0     1      2    3   4 (-1)
        boolean[] canGo = {true, true,true,true,true};

        ArrayList<Integer> way = new ArrayList<Integer>();

        int thread =0;

        // duyet toan bo list bom cua bang
        for (int i = 0; i < this.board.getBombs().size() ; i++){
            // phat hiện bom
            int Xb = this.board.getBombs().get(i).getXTile();
            int Yb = this.board.getBombs().get(i).getYTile();

            // xét những quả bom trong miền sét
            if ( this.detectBombinRanger(Xb, Yb)!=-1 ){
                thread++;
                // tùy trường hợp thì mình sẽ sét cách  hướng KHÔNG THỂ ĐI
                //  chú ý 4 thay cho -1;
                switch (this.detectBombinRanger(Xb, Yb) ){
                    case 0:
                        if ( Xb - Xe == this.radius + 1 ){
                            canGo[4]=canGo[0]=false;
                        }else{
                            System.out.println("hi");
                            canGo[4]=canGo[0]= false;
                        }

                        break;
                    case 1:
                        canGo[0]=canGo[2]= false;
                        break;
                    case 2:
                        if ( Ye - Yb == this.radius + 1){
                            canGo[4]=canGo[2]=false;
                        }else{
                            canGo[4]=canGo[2]= false;

                        }
                        break;
                    case 3:
                        canGo[1]=canGo[2]= false;
                        break;
                    case 4:
                        if ( Xe - Xb == this.radius + 1){
                            canGo[4]=canGo[1]=false;
                        }else{
                            canGo[4]=canGo[1]= false;
                        }
                        break;
                    case 5:
                        canGo[3]=canGo[1]= false;
                        break;
                    case 6:
                        if ( Yb - Ye == this.radius + 1){
                            canGo[4]=canGo[3]=false;
                        }else{
                            canGo[4]=canGo[3]= false;
                        }
                        break;
                    case 7:
                        canGo[0]=canGo[3]= false;
                        break;
                    case 8:
                        Arrays.fill(canGo, false);
                        break;
                }
            }

        }
        for ( int k =0  ; k < canGo.length ; k++){
            if ( canGo[k]==true ) {
                if ( k == 4 ){
                    way.add(-1); // chuyển 4 là -1
                }
                else{
                    way.add(k);
                }

            }
        }
        // nếu ko có nguy  hiểm
        if ( thread == 0 ){
            //  return -1;

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

        // trường hợp không có đường đi hợp lý
        // thì sẽ cho ramdo bừa
        if (way.size() == 0 ) {
            return -1;
        }
        // tồn tạ đường duy nhất
        if (way.size() == 1){
            //  System.out.println("di theo huong " + way.get(0) );
            return way.get(0);
        }
        int x = way.get(random.nextInt(way.size()));
        return x;
    }
}
