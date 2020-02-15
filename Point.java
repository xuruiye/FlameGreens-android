package com.example.greens;

public class Point {
    private int x;
    private int y;
    private int inflection = 0;
    private boolean visible;
    Point p = null;

    Point(){};
    Point(int y,int x,boolean visible){
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public Point getP() {
        return p;
    }

    public boolean isthis(Point p){
        boolean result = this.x==p.x&&this.y==p.y?true:false;
        return result;
    }

    public int getInflection() {
        return inflection;
    }

    public void setInflection(int inflection) {
        this.inflection = inflection;
    }

    public static boolean is_to_line(Point p1, Point p2){
        if(p1==null||p2==null)
            return true;
        return (p1.getX()==p2.getX() || p1.getY()==p2.getY())?true:false;
    }
}
