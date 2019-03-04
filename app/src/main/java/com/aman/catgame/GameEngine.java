package com.aman.catgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.LinkedList;
import java.util.Random;


public class GameEngine extends SurfaceView implements Runnable{


    private Paint paint = new Paint();
    Context context;

    // Android debug variables
    final static String TAG= "Magic-Cat";

    // screen size
    int screenHeight;
    int screenWidth;


    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    //Sprites

    Player cat;
    LinkedList<PlayerLife> life= new LinkedList<PlayerLife>();
    LinkedList<Ghost> ghosts= new LinkedList<Ghost>();
    long score = 0;

    // Background image
    Bitmap background;
    int live = 5;

    public MediaPlayer kill;

    public GameEngine(Context context, int w, int h) {
        super(context);
        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;
        kill = MediaPlayer.create(context, R.raw.shotgun);

        // @TODO: Add your sprites
        this.spawnCat();
        this.spawnGhost();
        this.makeLive();

        // load the image
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        // resize the image to match the phone
        background = Bitmap.createScaledBitmap(background,this.screenWidth, this.screenHeight, false);

    }

    public void updatePositions() {
        //@TODO: Put your code to update the (x,y) positions of the sprites

        for(int i = 0;i<ghosts.size();i++){
            ghosts.get(i).updateGhostPosition();
            double a = (cat.xPosition - ghosts.get(i).xPosition);
            double b = (cat.yPosition - ghosts.get(i).yPosition);
            double distance = Math.sqrt((a * a) + (b * b));

            // 2. calculate the "rate" to move
            double xn = (a / distance);
            double yn = (b / distance);

            // 3. move the ghosts
            ghosts.get(i).xPosition = ghosts.get(i).xPosition + (int) (xn * 5);
            ghosts.get(i).yPosition = ghosts.get(i).yPosition + (int) (yn * 5);

            if (ghosts.get(i).getHitbox().intersect(cat.getHitbox())) {
                // reduce lives
                live--;
                ghosts.remove(i);
                kill.start();
                // reset ghost to original position
                spawnGhost();
            }
        }

        for(int i=0;i<life.size();i++){
            life.get(i).updateLifePosition();
            double c = (cat.xPosition - life.get(i).x);
            double d = (cat.yPosition - life.get(i).y);
            double distancel = Math.sqrt((c * c) + (d * d));
            double xnl = (c / distancel);
            double ynl = (d / distancel);

            life.get(i).x = life.get(i).x + (int) (xnl * 5);
            life.get(i).y = life.get(i).y + (int) (ynl * 5);

            if (life.get(i).hitBox.intersect(cat.getHitbox())) {
                // reduce lives
                live++;
                life.remove(i);
                // reset ghost to original position
            }
        }


    }

    public void drawPositions() {
        // @TODO:  Put code to actually draw the sprites on the screen

        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            canvas.drawBitmap(this.background, 0,0, paintbrush);

            if(live>0){
               // score++;
                canvas.drawBitmap(this.cat.getBitmap(), this.cat.getXPosition(), this.cat.getYPosition(), paintbrush);


                // show hitboxes
                paintbrush.setColor(Color.BLUE);
                paintbrush.setStyle(Paint.Style.STROKE);
                paintbrush.setStrokeWidth(5);
                Rect catHitbox = cat.getHitbox();
                canvas.drawRect(catHitbox.left, catHitbox.top, catHitbox.right, catHitbox.bottom, paintbrush);

                //Rect ghostHitbox = ghost.getHitbox();
                //canvas.drawRect(ghostHitbox.left, ghostHitbox.top, ghostHitbox.right, ghostHitbox.bottom, paintbrush);

                if(ghosts.size()>0){
                        for(int init = 0; init<ghosts.size();init++){
                        canvas.drawBitmap(ghosts.get(init).getBitmap(), ghosts.get(init).getXPosition(), ghosts.get(init).getYPosition(), paintbrush);
                        canvas.drawRect(ghosts.get(init).getHitbox().left, ghosts.get(init).getHitbox().top, ghosts.get(init).getHitbox().right, ghosts.get(init).getHitbox().bottom, paintbrush);
                    }
                }

                if(life.size()>0){
                    for(int init = 0; init<life.size();init++){
                        canvas.drawBitmap(life.get(init).life, life.get(init).x, life.get(init).y, paintbrush);
                        canvas.drawRect(life.get(init).hitBox.left, life.get(init).hitBox.top, life.get(init).hitBox.right, life.get(init).hitBox.bottom, paintbrush);
                    }
                }
                paintbrush.setTextSize(60);
                paintbrush.setColor(Color.RED);
                canvas.drawText("Score: " + score, 0, 50, paintbrush);
                canvas.drawText("Lives: " + live, 0, 130, paintbrush);



            } else if(live<=0 ) {
                paintbrush.setTextSize(60);
                paintbrush.setColor(Color.RED);
                //canvas.drawText("Win,Game Over", 100, 50, paintbrush);
                if (score > 300) {
                    canvas.drawText("Win,Game Over", 150, 250, paintbrush);
                   // magicCat =new GameEngine(this, size.x, size.y);
                    //canvas.drawText("Restart",500,500,paintbrush);

                    //gameIsRunning=false;
                }
            else
                    {
                    canvas.drawText(" You Lose, Game Over", 150, 250, paintbrush);
                    }
                kill.stop();
            }

            paint.setAntiAlias(true);
            paint.setStrokeWidth(6f);
            paint.setColor(Color.BLACK);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }

    }

    public void spawnCat() {
        //@TODO: Start the cat at  screen
        cat = new Player(this.getContext(), (screenWidth/2)-64, (screenHeight/2)-64);

    }
    public void spawnGhost(){
        double random = Math.random() * 5 + 1;
        Log.d("Gen","x"+random);
        for(int init = 0;init<(int)random;init++){
            ghosts.add(new Ghost(this.getContext(), genX(), genY()));
        }

    }

    public void makeLive(){

        life.add(new PlayerLife(this.getContext(), genX(), genY()));
    }

    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.drawPositions();
            this.setFPS();
        }
    }


    public void setFPS() {
        try {
            gameThread.sleep(50);
        }
        catch (Exception e) {

        }
    }

    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void reset(){
        live = 5;
    }

    public int genX(){
        Random r = new Random();
        boolean isOne = r.nextBoolean();


        double random = Math.random() * 500 + 1;
        Log.d("Gen","x"+random);
        double random2 = screenWidth -(Math.random() * 500 + 1);

        if (isOne) return (int)random;
        else return (int)random2;
    }

    public int genY(){
        double random = Math.random() * screenHeight + 1;
        Log.d("Gen","x"+random);
        return (int)random;
    }

}
