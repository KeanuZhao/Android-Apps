package com.finalp.keanu.mark;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.finalp.keanu.mark.Utils.FileUtils;

/**
 * Created by miaos on 2017/4/25.
 */
public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        final ImageView imageView = (ImageView) findViewById(R.id.welcomepg);
        imageView.setImageBitmap(FileUtils.readBitMap(this,R.drawable.welcomepage));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1000);
    }
}
