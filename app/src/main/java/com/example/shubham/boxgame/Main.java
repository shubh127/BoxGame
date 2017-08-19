package com.example.shubham.boxgame;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends AppCompatActivity {

    private TextView tvScoreLabel, tvStartLabel;
    private ImageView ivBox, ivOrange, ivBlack, ivPink;
    private FrameLayout fl;
    private int boxY, frameHeight, boxSize, screenWidth, screenHeight, orangeX, orangeY, blackX, blackY, pinkX, pinkY, score = 0, boxSpeed, orangeSpeed, blackSpeed, pinkSpeed;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private Boolean actionFlg = false, startFlg = false;

    private SoundPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        sound = new SoundPlayer(this);

        tvScoreLabel = (TextView) findViewById(R.id.tv_score_label);
        tvStartLabel = (TextView) findViewById(R.id.tv_start_label);
        fl = (FrameLayout) findViewById(R.id.fl);
        ivBox = (ImageView) findViewById(R.id.iv_box);
        ivOrange = (ImageView) findViewById(R.id.iv_orange);
        ivBlack = (ImageView) findViewById(R.id.iv_black);
        ivPink = (ImageView) findViewById(R.id.iv_pink);

        ivOrange.setX(-80);
        ivOrange.setY(-80);
        ivBlack.setX(-80);
        ivBlack.setY(-80);
        ivPink.setX(-80);
        ivPink.setY(-80);


        boxY = 500;

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        boxSpeed = Math.round(screenHeight / 70f);
        orangeSpeed = Math.round(screenHeight / 70f);
        pinkSpeed = Math.round(screenHeight / 50f);
        blackSpeed = Math.round(screenHeight / 60f);

        tvScoreLabel.setText("Score : 0");


    }

    public void changePos() {

        hitCheck();

        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - ivOrange.getHeight()));
        }
        ivOrange.setX(orangeX);
        ivOrange.setY(orangeY);

        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - ivBlack.getHeight()));
        }
        ivBlack.setX(blackX);
        ivBlack.setY(blackY);

        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - ivPink.getHeight()));
        }
        ivPink.setX(pinkX);
        ivPink.setY(pinkY);

        if (actionFlg == true) {
            boxY -= boxSpeed;
        } else {
            boxY += 20;
        }
        if (boxY < 0) {
            boxY = boxSpeed;
        }
        if (boxY > frameHeight - boxSize) {
            boxY = frameHeight - boxSize;
        }
        ivBox.setY(boxY);

        tvScoreLabel.setText("Score : " + score);
    }

    private void hitCheck() {
        int orangeCenterX, orangeCenterY;
        orangeCenterX = orangeX + ivOrange.getWidth() / 2;
        orangeCenterY = orangeY + ivOrange.getHeight() / 2;

        if (0 <= orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {
            score += 10;
            orangeX -= 100;
            sound.playHitSound();

        }

        int pinkCenterX, pinkCenterY;
        pinkCenterX = pinkX + ivPink.getWidth() / 2;
        pinkCenterY = pinkY + ivPink.getHeight() / 2;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {
            score += 30;
            pinkX -= 100;
            sound.playHitSound();

        }

        int blackCenterX, blackCenterY;
        blackCenterX = blackX + ivBlack.getWidth() / 2;
        blackCenterY = blackY + ivBlack.getHeight() / 2;

        if (0 <= blackCenterX && blackCenterX <= boxSize && boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {
            timer.cancel();
            timer = null;
            sound.playOverSound();

            Intent intent = new Intent(this, Result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (startFlg == false) {

            startFlg = true;
            frameHeight = fl.getHeight();
            boxY = (int) ivBox.getY();
            boxSize = ivBox.getHeight();

            tvStartLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        } else {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                actionFlg = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                actionFlg = false;
            }
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}


