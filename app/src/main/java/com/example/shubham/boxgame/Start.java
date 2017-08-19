package com.example.shubham.boxgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Start extends AppCompatActivity implements View.OnClickListener {
    private InterstitialAd interstitialAd;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();
    }

    private void initView() {

        btnStart = (Button) findViewById(R.id.btn_start);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitalAd();
            }
        });

        btnStart.setOnClickListener(this);
    }

    public void displayInterstitalAd() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
            finish();
        }
    }
}
