package com.bearlymade.cweaver.presentsorpenguins;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by cweaver on 11/18/2015.
 */
public class ChristmasTree extends ActivatableSprite {

    public ChristmasTree(Context context) {
        super(context, R.drawable.christmas_tree, 2);
        boundingLeft = -80;
        boundingTop = -180;
        boundingRight = -80;
        boundingBottom = -10;
    }

    @Override
    public void activate(Context context) {
        appearance = BitmapFactory.decodeResource(context.getResources(), R.drawable.penguin);
        appearance = Bitmap.createScaledBitmap(appearance,
                appearance.getWidth() / 3,
                appearance.getHeight() / 3,
                false);
        frameCount = 2;
        moveDown(frameHeight-appearance.getHeight());
        moveRight((frameWidth - appearance.getWidth() / 2) / 2);
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

        boundingLeft = -40;
        boundingTop = -80;
        boundingRight = -40;
        boundingBottom = -10;

        boundingBox = new Rect(whereToDraw);
        setBoundingBox();
    }
}
