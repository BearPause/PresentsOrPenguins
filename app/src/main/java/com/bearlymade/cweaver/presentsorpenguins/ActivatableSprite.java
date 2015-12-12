package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by cweaver on 12/8/2015.
 */
public class ActivatableSprite extends Sprite {

    protected Rect activationRange;

    protected int activateLeft;
    protected int activateTop;
    protected int activateRight;
    protected int activateBottom;

    protected boolean isUsed;

    public ActivatableSprite(Context context, int resourceId, int frames) {
        super(context, resourceId, frames);
        activationRange = new Rect(whereToDraw);
        activateLeft = 10;
        activateTop = 10;
        activateRight = 10;
        activateBottom = 10;
        setActivationBox();
    }

    public void activate(Context context) {

    }

    public void setActivationBox() {
        activationRange.set(whereToDraw.left-activateLeft, whereToDraw.top-activateTop, whereToDraw.right+activateRight, whereToDraw.bottom+activateBottom);
    }

    @Override
    public void setLocation(Point location) {
        super.setLocation(location);
        setActivationBox();
    }

    public boolean isUsed() {
        return isUsed;
    }
}
