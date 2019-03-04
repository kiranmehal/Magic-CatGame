package com.aman.catgame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GameEngine MagicCat;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize the GameEngine object
        // Pass it the screen size (height & width)

        MagicCat = new GameEngine(this, size.x, size.y);
        setContentView(MagicCat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MagicCat.startGame();
    }

    // Stop the thread in GameEngine
    @Override
    protected void onPause() {
        super.onPause();
        MagicCat.pauseGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        String DEBUG_TAG = "ON TOUCH EVENT";
        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                for(int i=0;i<MagicCat.ghosts.size();i++){
                    MagicCat.ghosts.remove(i);
                    MagicCat.score += 10;

                }
               // MagicCat.startGame();

                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_UP) :
                MagicCat.spawnGhost();
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
