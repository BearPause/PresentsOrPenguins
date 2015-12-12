package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cweaver on 11/13/2015.
 */
public class GameView extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder holder;
    volatile boolean playing;
    Canvas canvas;
    Paint paint;
    long fps;
    boolean isMoving = false;
    float walkSpeedPerSecond = 200;
    int forestSize = 9;

    Bob bitmapBob;
    ActivatableSprite tree;
    SpriteButton searchButton;

    float historicalXPos = 0;
    float historicalYPos = 0;
    float cursorXPos = 0;
    float cursorYPos = 0;
    double angle = 0;
    int direction = 0;
    boolean hasWinningTree;

    MovementController mover;

    ArrayList<ActivatableSprite> terrain;

    public GameView(Context context) {
        super(context);

        mover = new MovementController();
        holder = getHolder();
        paint = new Paint();

        Point size = new Point();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        terrain = new ArrayList<>();

        bitmapBob = new Bob(context);
        bitmapBob.setLocation(new Point(size.x/2, 50));
        Point treeLocation;
        int forestHeight = (int)Math.round(Math.sqrt((double)forestSize));
        Random random = new Random();
        for (int i = 0; i < forestSize; i++) {
            int treeRow = (i / forestHeight);
            int treeColumn = (i % forestHeight);
            int treeSpaceHorizontal = size.x / forestHeight;
            int treeSpaceVertical = (size.y-300) / forestHeight;
            treeLocation = new Point((treeSpaceHorizontal * treeColumn) + 50, (treeSpaceVertical * treeRow) + 300);
            if((!hasWinningTree && random.nextInt(forestSize) == 0)
                    || (!hasWinningTree && i == forestSize - 1)) {
                tree = new WinningChristmasTree(context);
                hasWinningTree = true;
            } else {
                tree = new ChristmasTree(context);
            }
            tree.setLocation(treeLocation);
            tree.setFrameLength(300);
            terrain.add(tree);
        }
        searchButton = new SpriteButton(context,R.drawable.search_button,1);
        searchButton.setLocation(new Point(100, size.y - 400));
    }

    @Override
    public void run() {

        long timeThisFrame;

        while (playing) {
            long startFrameTime = System.currentTimeMillis();

            update();

            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        moveCharacter();
    }

    private void checkCollision() {
        if (isMoving) {
           bitmapBob.checkCollision(terrain);
        }
    }

    private void moveCharacter() {

        double diagonalMovement = 1.5;

        if (isMoving) {
            if (mover.get8Direction() == MovementController.UP) {
                bitmapBob.moveUp(getDeviceMovespeed());
                checkCollision();
            } else if (mover.get8Direction() == MovementController.UP_RIGHT) {
                bitmapBob.moveRight(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
                bitmapBob.moveUp(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
            } else if (mover.get8Direction() == MovementController.RIGHT) {
                bitmapBob.moveRight(getDeviceMovespeed());
                checkCollision();
            } else if (mover.get8Direction() == MovementController.DOWN_RIGHT) {
                bitmapBob.moveRight(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
                bitmapBob.moveDown(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
            } else if (mover.get8Direction() == MovementController.DOWN) {
                bitmapBob.moveDown(getDeviceMovespeed());
                checkCollision();
            } else if (mover.get8Direction() == MovementController.DOWN_LEFT) {
                bitmapBob.moveLeft(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
                bitmapBob.moveDown(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
            } else if (mover.get8Direction() == MovementController.LEFT) {
                bitmapBob.moveLeft(getDeviceMovespeed());
                checkCollision();
            } else {
                bitmapBob.moveLeft(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
                bitmapBob.moveUp(getDeviceMovespeed() / diagonalMovement);
                checkCollision();
            }
            direction = mover.get8Direction();
        }
    }

    public void draw() {

        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(45);

//            canvas.drawText("FPS:" + fps, 20, 40, paint);
//            canvas.drawText("Position:" + cursorXPos + ", " + cursorYPos, 20, 100, paint);
//            canvas.drawText("Current Frame:" + bitmapBob.getCurrentFrame(), 20, 140, paint);
//            canvas.drawText("Angle:" + angle + " " + direction, 20, 180, paint);
            canvas.drawBitmap(bitmapBob.appearance, bitmapBob.getFrameToDraw(), bitmapBob.getWhereToDraw(), paint);
//            canvas.drawRect(bitmapBob.getBoundingBox(), paint);
            for (ActivatableSprite tree : terrain) {
                canvas.drawBitmap(tree.appearance, tree.getFrameToDraw(), tree.getWhereToDraw(), paint);
            }
//            canvas.drawRect(tree.getBoundingBox(), paint);
            canvas.drawBitmap(searchButton.appearance, searchButton.getFrameToDraw(), searchButton.getWhereToDraw(), paint);


            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void searchTerrain() {
        for(ActivatableSprite terrainPiece : terrain) {
            if (!terrainPiece.isUsed()
                    && Rect.intersects(bitmapBob.boundingBox,terrainPiece.activationRange)) {
                terrainPiece.activate(getContext());
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error: ", "joining thread");
        }
    }

    public void resume () {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (searchButton.getWhereToDraw().contains((int)motionEvent.getX(), (int)motionEvent.getY())
                && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            searchTerrain();
        } else {
            mover.move(motionEvent);
            isMoving = mover.getIsMoving();
            bitmapBob.setMoving(mover.getIsMoving());
            cursorXPos = mover.getCursorXPos();
            cursorYPos = mover.getCursorYPos();
            historicalXPos = mover.getFirstXPos();
            historicalYPos = mover.getFirstYPos();
            angle = mover.getAngle();
        }

        return true;
    }

    public double getDeviceMovespeed() {
        return walkSpeedPerSecond / fps;
    }

}
