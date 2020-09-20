package com.gamecodeschool.c1tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class TDView extends SurfaceView implements Runnable {

    volatile  boolean playing;
    Thread gameThread = null;
    private PlayerShip player;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;

    // Make some random space dust
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    //for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    
    public TDView(Context context, int x, int y) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++) {
            // Where will the dust spawn?
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }
    }

    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {

        }
    }

    private void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            //clear screen
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            // White specs of dust
            paint.setColor(Color.argb(255, 255, 255, 255));
            //Draw the dust from our arrayList
            for (SpaceDust sd : dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            // Draw the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            canvas.drawBitmap
                    (enemy1.getBitmap(),
                            enemy1.getX(),
                            enemy1.getY(), paint);
            canvas.drawBitmap
                    (enemy2.getBitmap(),
                            enemy2.getX(),
                            enemy2.getY(), paint);
            canvas.drawBitmap
                    (enemy3.getBitmap(),
                            enemy3.getX(),
                            enemy3.getY(), paint);

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        if(player.getHitbox().intersect(enemy1.getHitBox())){
            enemy1.setX(-1000);
        }
        if(player.getHitbox().intersect(enemy2.getHitBox())){
            enemy2.setX(-1000);
        }
        if(player.getHitbox().intersect(enemy3.getHitBox())){
            enemy3.setX(-1000);
        }

        player.update();
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        for (SpaceDust sd : dustList) {
            sd.update(player.getSpeed());
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }
        catch(InterruptedException e) {

        }
    }
    // Make a new thread and start it
    // Execution moves to our R
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
                // Has the player touched the screen?
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
