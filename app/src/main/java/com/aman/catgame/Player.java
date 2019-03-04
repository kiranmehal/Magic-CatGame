package com.aman.catgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    int xPosition;
    int yPosition;
    Bitmap image;
    private Rect hitBox;


    public Player(Context context, int x, int y) {
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat);
        this.image= Bitmap.createScaledBitmap(image,128 , 128, false);
        this.xPosition = x;
        this.yPosition = y;
        this.hitBox = new Rect(this.xPosition, this.yPosition, this.xPosition + this.image.getWidth(), this.yPosition + this.image.getHeight());
    }

    public void setXPosition(int x) {
        this.xPosition = x;
    }
    public void setYPosition(int y) {
        this.yPosition = y;
    }
    public int getXPosition() {
        return this.xPosition;
    }
    public int getYPosition() {
        return this.yPosition;
    }
    public Rect getHitbox() {
        return this.hitBox;
    }

    public Bitmap getBitmap() {
        return this.image;
    }

}
