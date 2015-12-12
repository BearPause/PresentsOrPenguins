package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Rect;

/**
 * Created by cweaver on 11/18/2015.
 */
public class Bob extends Sprite {

    private boolean isMoving;

    public Bob (Context context) {
        super(context, R.drawable.bob_walk_animation, 5);
        boundingLeft = -10;
        boundingTop = -10;
        boundingRight = -30;
        boundingBottom = -10;
    }

    public Rect getFrameToDraw() {
        if(isMoving) {
            super.getFrameToDraw();
        }

        return frameToDraw;
    }

    public void setMoving(boolean isPaused) {
        this.isMoving = isPaused;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
