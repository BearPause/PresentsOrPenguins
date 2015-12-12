package com.bearlymade.cweaver.presentsorpenguins;

import android.view.MotionEvent;

/**
 * Created by cweaver on 11/13/2015.
 */
public class MovementController {

    public static final int UP = 0;
    public static final int UP_RIGHT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN_RIGHT = 3;
    public static final int DOWN = 4;
    public static final int DOWN_LEFT = 5;
    public static final int LEFT = 6;
    public static final int UP_LEFT = 7;

    volatile boolean isMoving = false;
    float cursorXPos = 0;
    float cursorYPos = 0;
    float firstXPos = 0;
    float firstYPos = 0;
    double angle = 0;

    public MovementController() {

    }

    public int move4Way(MotionEvent motionEvent) {
        move(motionEvent);
        return get4Direction();
    }

    public int move8Way(MotionEvent motionEvent) {
        move(motionEvent);
        return get8Direction();
    }

    public void move(MotionEvent motionEvent) {
        cursorXPos = motionEvent.getX();
        cursorYPos = motionEvent.getY();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                firstXPos = motionEvent.getX();
                firstYPos = motionEvent.getY();
                isMoving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                angle = calcAngle(cursorXPos - firstXPos, cursorYPos - firstYPos);
                break;
            case MotionEvent.ACTION_UP:
                isMoving = false;
                break;
        }
    }

    public int get4Direction() {
        if (angle < 135 && angle >= 45) {
            return DOWN;
        } else if (angle < 45 || angle >= 315) {
            return RIGHT;
        } else if (angle < 315 && angle >= 225) {
            return UP;
        } else {
            return LEFT;
        }
    }

    public int get8Direction() {
        if (angle < 112.5 && angle >= 67.5) {
            return DOWN;
        } else if (angle < 67.5 && angle >= 22.5) {
            return DOWN_RIGHT;
        } else if (angle < 22.5 || angle >= 337.5) {
            return RIGHT;
        } else if (angle < 337.5 && angle >= 292.5) {
            return UP_RIGHT;
        } else if (angle < 292.5 && angle >= 247.5) {
            return UP;
        } else if (angle < 247.5 && angle >= 202.5) {
            return UP_LEFT;
        } else if (angle < 202 && angle >= 157.5) {
            return LEFT;
        } else {
            return DOWN_LEFT;
        }
    }

    public double calcAngle(float x, float y) {
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if(x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    public double getAngle() {
        return angle;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public float getCursorXPos() {
        return cursorXPos;
    }

    public float getCursorYPos() {
        return cursorYPos;
    }

    public float getFirstYPos() {
        return firstYPos;
    }

    public float getFirstXPos() {
        return firstXPos;
    }
}
