package com.example.greens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Greens {
    private int x;
    private int y;
    private int frame_color = Color.GREEN;
    private Bitmap img;
    private int number;
    private boolean visible;

    Greens(){}
    Greens(int x, int y, boolean visible){
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public void setXY(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(int color) {
        this.frame_color = color;
    }

    public int getColor() {
        return frame_color;
    }

    public void setImgAndNumber(Bitmap img, int number) {
        this.img = img;
        this.number = number;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getWidth(){
        return img.getWidth();
    }

    public int getHeight(){
        return img.getHeight();
    }

    public int getNumber(){
        return number;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public static void Swapimg(Greens g1,Greens g2){
        Bitmap tempI = g1.getImg();
        int tempN = g1.getNumber();
        g1.setImgAndNumber(g2.getImg(),g2.getNumber());
        g2.setImgAndNumber(tempI,tempN);
    }

}
