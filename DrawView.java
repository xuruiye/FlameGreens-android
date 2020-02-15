package com.example.greens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class DrawView extends View {

    private Greens[][] greens = new Greens[10][8];
    private Point[][] node = new Point[10][8];
    private ArrayList<Point> clicks = new ArrayList<Point>();
    private Point linepath = null;
    private int padding = 10;
    private boolean isinit = false;
    boolean isSleep = false;

    public DrawView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //  Initialize greens
        if(!isinit)initializeGreens();

        //  Show view frame
        showViewFrame(canvas);

        //  Show greens and greens frame
        showGreens(canvas);

        //  Show join path line
        if(linepath!=null)showjoin(canvas);
        else if(isSleep){
            Sleep(200);
            isSleep=false;
        }
    }

    //  Initialize greens object
    public void initializeGreens(){
        Bitmap[] bitmap = new Bitmap[8];
        int k = 0;  //  bitmap index

        //  import image
        bitmap[0] =  BitmapFactory.decodeResource(getResources(),R.drawable.a);
        bitmap[1] =  BitmapFactory.decodeResource(getResources(),R.drawable.b);
        bitmap[2] =  BitmapFactory.decodeResource(getResources(),R.drawable.c);
        bitmap[3] =  BitmapFactory.decodeResource(getResources(),R.drawable.d);
        bitmap[4] =  BitmapFactory.decodeResource(getResources(),R.drawable.e);
        bitmap[5] =  BitmapFactory.decodeResource(getResources(),R.drawable.f);
        bitmap[6] =  BitmapFactory.decodeResource(getResources(),R.drawable.g);
        bitmap[7] =  BitmapFactory.decodeResource(getResources(),R.drawable.k);

        //  Initialize greens (x,y,image,number,visible)
        for(int i=1;i<9;i++) {
            for(int j=1;j<7;j++) {
                greens[i][j] = new Greens(padding + j*75,padding + i*75,true);
                greens[i][j].setImgAndNumber(bitmap[k++%8],(k-1)%8);
                node[i][j] = new Point(i,j,true);
            }
        }// end for i

        //  Initialize view border greens (0,x) and (x,0) and (x,max) and (max,x)
        for(int i=0;i<10;i++){  // column
            //  min column
            greens[i][0] = new Greens(0,i*75 + padding,false);
            node[i][0] = new Point(i,0,true);
            //  max column
            greens[i][7] = new Greens(7*75 + padding,i*75 + padding,false);
            node[i][7] = new Point(i,7,true);
        }// end for i
        for(int j=0;j<8;j++){   // row
            //  min row
            greens[0][j] = new Greens(j*75 + padding,0,false);
            node[0][j] = new Point(0,j,true);
            //  max row
            greens[9][j] = new Greens(j*75 + padding,9*75 + padding,false);
            node[9][j] = new Point(9,j,true);
        }
        greens[0][0].setXY(0,0);
        greens[9][0].setXY(0,9*75 + padding);
        //  Random greens
        Random ran = new Random();
        for(int i=1;i<9;i++) {
            for (int j = 1; j < 7; j++) {
                Greens.Swapimg(greens[i][j],greens[ran.nextInt(8) + 1][ran.nextInt(6) + 1]);
            }
        }
        isinit = true;
    }

    public void showViewFrame(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(padding);
        canvas.drawRect(greens[1][1].getX() - 10,greens[1][1].getY() - 10,greens[8][6].getX() + 80,greens[8][6].getY() + 80,paint);
    }

    public void showGreens(Canvas canvas){
        Paint paint = new Paint();
        for(int i=1;i<9;i++) {
            for(int j=1;j<7;j++){
                if(greens[i][j].isVisible())
                {
                    canvas.drawBitmap(greens[i][j].getImg(), greens[i][j].getX(),greens[i][j].getY(), paint);
                    showFrame(greens[i][j],canvas,Color.GREEN);
                }
            }
        }//end for i
        //  Show click greens frame
        for(int i=0;i<clicks.size();i++){
            showFrame(greens[clicks.get(i).getY()][clicks.get(i).getX()],canvas,Color.RED);
        }
    }

    public void showFrame(Greens gre, Canvas canvas, int color){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        canvas.drawRect(gre.getX() - 1,gre.getY() - 1,
                gre.getX() + gre.getWidth() + 1,gre.getY() + gre.getHeight() + 1,paint);
    }

    public void setClickXY(int x, int y){

        int indexX = (x - padding)/75;
        int indexY = (y - padding)/75;
        if(indexX<1 || indexY<1 || indexX>7 || indexY>9){   //  if out of range
            clicks.clear();
            invalidate();
            return;
        }
        else{
            clicks.add(node[indexY][indexX]);
            if(clicks.size()==2){
                if(clicks.get(0).isthis(clicks.get(1))){
                    clicks.clear();
                    invalidate();
                    return;
                }
                if(isJoin(clicks.get(1).getX(),clicks.get(1).getY(),clicks.get(0).getX(),clicks.get(0).getY())){
                    linepath = clicks.get(0).p;
                }
                else{
                    clicks.clear();
                    nodes_init();
                }
            }
            invalidate();
        }
    }
    
    //  ----------  algorithm -----------
    public boolean isJoin(int x1, int y1, int x2, int y2){
        if(greens[y1][x1].getNumber()!=greens[y2][x2].getNumber())
            return false;
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(node[y1][x1]);

        //  serch
        Point temp = new Point();
        while(!list.isEmpty()){
            temp = list.remove(0);

            //  Add this->p->p
            
            //  Add list
            for(int i = temp.getY()- 1 , j = temp.getX() - 1; i <= temp.getY() + 1 || j <= temp.getX() + 1; i+=2,j+=2){

                if((i==y2 && temp.getX()==x2)||(temp.getY()==y2&&j==x2)){
                    node[y2][x2].setP(temp);
                    list.clear();
                    return true;
                }

                if(i>=0 && i<10 && node[i][temp.getX()].getInflection()<=1) {
                    if (node[i][temp.getX()].isVisible() && !greens[i][temp.getX()].isVisible()){
                        list.add(node[i][temp.getX()]);
                        node[i][temp.getX()].setP(temp);
                        node[i][temp.getX()].setVisible(false);
                        if(Point.is_to_line(node[i][temp.getX()],node[i][temp.getX()].p.p)){
                            node[i][temp.getX()].setInflection(node[i][temp.getX()].getInflection());
                        }else{
                            node[i][temp.getX()].setInflection(node[i][temp.getX()].getInflection() + 1);
                        }
                    }
                }
                if(j>=0 && j<8 && node[temp.getY()][j].getInflection()<=1){
                    if(node[temp.getY()][j].isVisible() && !greens[temp.getY()][j].isVisible()) {
                        list.add(node[temp.getY()][j]);
                        node[temp.getY()][j].setP(temp);
                        node[temp.getY()][j].setVisible(false);
                        if(Point.is_to_line(node[temp.getY()][j],node[temp.getY()][j].p.p)){
                            node[temp.getY()][j].setInflection(node[temp.getY()][j].getInflection());
                        }else{
                            node[temp.getY()][j].setInflection(node[temp.getY()][j].getInflection() + 1);
                        }
                    }
                }
            }// end for i,j
        }// end while
        return false;
    }

    public void nodes_init(){
        for(int i=0;i<10;i++){
            for(int j=0;j<8;j++){
                node[i][j].setVisible(true);
            }
        }
    }

    public void showjoin(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        //  Show path line
        Point po = clicks.get(0);
        while(!linepath.isthis(po)){
            canvas.drawLine(greens[po.getY()][po.getX()].getX() + 37, greens[po.getY()][po.getX()].getY() + 37,
                    greens[po.p.getY()][po.p.getX()].getX() + 37, greens[po.p.getY()][po.p.getX()].getY() + 37, paint);
            po=po.p;
        }
        linepath  = linepath.p;
        if(linepath == null){
            greens[clicks.get(0).getY()][clicks.get(0).getX()].setVisible(false);
            greens[clicks.get(1).getY()][clicks.get(1).getX()].setVisible(false);
            clicks.clear();
            nodes_init();
            isSleep=true;
        }
        Sleep(100);
        invalidate();
    }

    public static void Sleep(int ms){
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

