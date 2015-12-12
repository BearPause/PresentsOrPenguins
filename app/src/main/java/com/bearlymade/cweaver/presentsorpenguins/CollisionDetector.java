package com.bearlymade.cweaver.presentsorpenguins;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by cweaver on 11/20/2015.
 */
public class CollisionDetector {

    public enum Side {TOP, RIGHT, BOTTOM, LEFT};

    public CollisionDetector() {

    }

    public static Side detectCollision(Sprite character, ArrayList<ActivatableSprite> terrains) {

        for (Sprite terrain : terrains) {
            if (Rect.intersects(terrain.boundingBox, character.getBoundingBox())) {
                return character.movingDirection;
            }
        }
        return null;
    }
}
