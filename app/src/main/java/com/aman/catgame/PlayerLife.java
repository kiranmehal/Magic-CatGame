package com.aman.catgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerLife {
    Context c;
    int x,y,width;
    Bitmap life;
    Rect hitBox;

    public PlayerLife(Context context, int x, int y){
        this.x = x;
        this.y = y;
        this.life = BitmapFactory.decodeResource(context.getResources(), R.drawable.mushroom);
        this.life= Bitmap.createScaledBitmap(life,128 , 128, false);
        this.hitBox = new Rect(this.x, this.y, this.x + this.life.getWidth(), this.y + this.life.getHeight());
    }

    public void updateLifePosition() {

        this.hitBox.top = this.y;
        this.hitBox.left = this.x;
        this.hitBox.right = this.x + 128;
        this.hitBox.bottom = this.y + 128;
    }
}
