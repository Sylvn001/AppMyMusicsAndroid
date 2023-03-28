package br.unoeste.appmymusics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageview;
    private Handler handler;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageview = findViewById(R.id.imageView);
        handler = new Handler();
        handler.postDelayed(r=() -> {entrar();},5000);
        imageview.setOnClickListener(e -> {entrar();});
    }

    private void entrar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        handler.removeCallbacks(r);
    }
}