package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by cweaver on 12/9/2015.
 */
public class WinningChristmasTree extends ActivatableSprite {

    public WinningChristmasTree(Context context) {
        super(context, R.drawable.christmas_tree, 2);
        boundingLeft = -80;
        boundingTop = -180;
        boundingRight = -80;
        boundingBottom = -10;
    }

    @Override
    public void activate(Context context) {
        appearance = BitmapFactory.decodeResource(context.getResources(), R.drawable.gift);
        appearance = Bitmap.createScaledBitmap(appearance,
                appearance.getWidth() / 3,
                appearance.getHeight() / 3,
                false);
        frameCount = 2;
        moveDown(frameHeight-appearance.getHeight());
        moveRight((frameWidth-appearance.getWidth()/2)/2);
        frameWidth = appearance.getWidth() / 2;
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
}
