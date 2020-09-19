package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PlayerShip {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;

    public PlayerShip(Context context){
        int x = 50;
        int y = 50;
        speed = 50;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
    }

    public void update(){
        x++;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
