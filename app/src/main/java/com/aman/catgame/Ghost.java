package com.aman.catgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Ghost {
    int xPosition;
    int yPosition;
    Bitmap image1;
    private Rect hitBox;


    public Ghost(Context context, int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        double random = Math.random() * 3 + 1;
        Log.d("Gen","x"+random);
        if((int)random == 1){
            this.image1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost1);
            this.image1= Bitmap.createScaledBitmap(image1,128 , 128, false);
        } else if ((int)random == 2){
            this.image1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.phantom);
            this.image1= Bitmap.createScaledBitmap(image1,128 , 128, false);
        } else{
            this.image1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost);
            this.image1= Bitmap.createScaledBitmap(image1,128 , 128, false);
        }
        this.hitBox = new Rect(this.xPosition, this.yPosition, this.xPosition + this.image1.getWidth(), this.yPosition + this.image1.getHeight());
    }
    public void updateGhostPosition() {

        this.hitBox.top = this.yPosition;
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + 128;
        this.hitBox.bottom = this.yPosition + 128;
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
        return this.image1;
    }

}

