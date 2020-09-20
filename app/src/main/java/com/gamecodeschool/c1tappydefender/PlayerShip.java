package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerShip {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private boolean boosting;
    private Rect hitBox;

    private final int GRAVITY = -12;
    private int maxY;
    private int minY;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    public PlayerShip(Context context, int screenX, int screenY){
        int x = 50;
        int y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        boosting = false;
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        hitBox = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(){
        if(boosting){
            speed+=2;
        }
        else{
            speed-=5;
        }

        if(speed>MAX_SPEED){
            speed = MAX_SPEED;
        }
        if(speed<MIN_SPEED){
            speed = MIN_SPEED;
        }

        // move the ship up or down
        y -= speed + GRAVITY;
        // But don't let ship stray off screen
        if (y < minY) {
            y = minY;
        }

        if(y> maxY){
            y= maxY;
        }

        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
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

    public Rect getHitbox(){
        return hitBox;
    }

    public void setBoosting() {
        boosting = true;
    }
    public void stopBoosting() {
        boosting = false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
