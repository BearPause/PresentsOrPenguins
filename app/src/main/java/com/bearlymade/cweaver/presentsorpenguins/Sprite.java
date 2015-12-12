package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by cweaver on 11/18/2015.
 */
public class Sprite {

    Context context;

    Bitmap appearance;
    int xPosition = 100;
    int yPosition = 800;

    protected int frameWidth;
    protected int frameHeight;
    protected int frameCount;
    protected int currentFrame = 0;
    protected long lastFrameChangeTime = 0;
    protected int frameLengthInMilliseconds = 100;
    protected Rect frameToDraw;
    protected Rect whereToDraw;
    protected Rect boundingBox;

    protected CollisionDetector.Side movingDirection;

    protected int boundingLeft;
    protected int boundingTop;
    protected int boundingRight;
    protected int boundingBottom;

    protected boolean isBlockedTop;
    protected boolean isBlockedRight;
    protected boolean isBlockedBottom;
    protected boolean isBlockedLeft;

    public Sprite(Context context, int resourceId, int frames) {
        this.context = context;
        appearance = BitmapFactory.decodeResource(context.getResources(), resourceId);
        appearance = Bitmap.createScaledBitmap(appearance,
                appearance.getWidth() / 2,
                appearance.getHeight() / 2,
                false);
        frameCount = frames;
        frameWidth = appearance.getWidth() / frames;
        frameHeight = appearance.getHeight();
        frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);
        whereToDraw = new Rect(
                xPosition,
                yPosition,
                xPosition + frameWidth,
                yPosition + frameHeight);

        boundingBox = new Rect(whereToDraw);
    }

    public void moveLeft(double distance) {
        if (!isBlockedLeft) {
            setLocation(new Point(xPosition -= distance, yPosition));
            movingDirection = CollisionDetector.Side.LEFT;
            isBlockedRight = false;
        }
    }

    public void moveRight(double distance) {
        if (!isBlockedRight) {
            setLocation(new Point(xPosition += distance, yPosition));
            movingDirection = CollisionDetector.Side.RIGHT;
            isBlockedLeft = false;
        }
    }

    public void moveDown(double distance) {
        if (!isBlockedBottom) {
            setLocation(new Point(xPosition, yPosition += distance));
            movingDirection = CollisionDetector.Side.BOTTOM;
            isBlockedTop = false;
        }
    }

    public void moveUp(double distance) {
        if (!isBlockedTop) {
            setLocation(new Point(xPosition, yPosition -= distance));
            movingDirection = CollisionDetector.Side.TOP;
            isBlockedBottom = false;
        }
    }

    public void checkCollision(ArrayList<ActivatableSprite> terrain) {
        CollisionDetector.Side side = CollisionDetector.detectCollision(this, terrain);
        if (side == CollisionDetector.Side.TOP) {
            moveDown(10);
            isBlockedTop = true;
        } else if (side == CollisionDetector.Side.RIGHT) {
            moveLeft(10);
            isBlockedRight = true;
        } else if (side == CollisionDetector.Side.BOTTOM) {
            moveUp(10);
            isBlockedBottom = true;
        } else if (side == CollisionDetector.Side.LEFT) {
            moveRight(10);
            isBlockedLeft = true;
        }
    }

    public Rect getFrameToDraw() {

        long time = System.currentTimeMillis();
        if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
            lastFrameChangeTime = time;
            currentFrame++;
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

        return frameToDraw;
    }

    public void setFrameLength(int length) {
        frameLengthInMilliseconds = length;
    }

    public void setLocation(Point location) {
        xPosition = location.x;
        yPosition = location.y;

        whereToDraw.set(xPosition,
                yPosition,
                xPosition + frameWidth,
                yPosition + frameHeight);

        setBoundingBox();
    }

    protected void setBoundingBox() {
        boundingBox.set(whereToDraw.left-boundingLeft,whereToDraw.top-boundingTop,whereToDraw.right+boundingRight,whereToDraw.bottom+boundingBottom);
    }

    public Rect getBoundingBox() {
        return boundingBox;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }

    public boolean isBlockedLeft() {
        return isBlockedLeft;
    }

    public void setIsBlockedLeft(boolean isBlockedLeft) {
        this.isBlockedLeft = isBlockedLeft;
    }

    public boolean isBlockedTop() {
        return isBlockedTop;
    }

    public void setIsBlockedTop(boolean isBlockedTop) {
        this.isBlockedTop = isBlockedTop;
    }

    public boolean isBlockedRight() {
        return isBlockedRight;
    }

    public void setIsBlockedRight(boolean isBlockedRight) {
        this.isBlockedRight = isBlockedRight;
    }

    public boolean isBlockedBottom() {
        return isBlockedBottom;
    }

    public void setIsBlockedBottom(boolean isBlockedBottom) {
        this.isBlockedBottom = isBlockedBottom;
    }
}
