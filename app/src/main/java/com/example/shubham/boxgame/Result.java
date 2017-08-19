package com.example.shubham.boxgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity implements View.OnClickListener {
    private TextView tvScoreLabel, tvHighScoreLabel;
    private Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
    }

    private void initViews() {
        tvScoreLabel = (TextView) findViewById(R.id.tv_score_label);
        tvHighScoreLabel = (TextView) findViewById(R.id.tv_high_score_label);
        btnTryAgain = (Button) findViewById(R.id.btn_try_again);

        Intent intent = getIntent();
        int Score = intent.getIntExtra("SCORE", 0);
        tvScoreLabel.setText(Score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int HighScore = settings.getInt("HIGH_SCORE", 0);
        if (Score > HighScore) {
            tvHighScoreLabel.setText("High SCore : " + Score);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", Score);
            editor.commit();

        } else {
            tvHighScoreLabel.setText("High Score : " + HighScore);
        }

        btnTryAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_try_again) {
            startActivity(new Intent(this, Start.class));
            finish();
        }
    }
}
