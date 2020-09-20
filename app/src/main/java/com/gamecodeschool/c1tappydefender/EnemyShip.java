package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class EnemyShip{
    private Bitmap bitmap;
    private int x, y;
    private int speed = 1;
    // Detect enemies leaving the screen
    private int maxX;
    private int minX;
    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;
    private Rect hitBox;

    // Constructor
    public EnemyShip(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.enemy);
        minY = 0;
        minX = 0;
        maxX = screenX;
        maxY = screenY;

        Random generator = new Random();
        speed = generator.nextInt(6)+10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        hitBox = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());

    }

    public Bitmap getBitmap(){
        return bitmap;
    }
    public int getX() {
        return x;
    }
    // This is used by the TDView update() method to
    // Make an enemy out of bounds and force a re-spawn
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void update(int playerSpeed){
        x-= playerSpeed;
        x -= speed;

        //respawn when off screen
        if(x < minX-bitmap.getWidth()){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }
}
