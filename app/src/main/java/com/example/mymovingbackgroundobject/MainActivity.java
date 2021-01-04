package com.example.mymovingbackgroundobject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        this.loginBtn = (Button)findViewById(R.id.loginBtn);
        this.registerBtn = (Button)findViewById(R.id.registerBtn);

        Tools.setShrinkAnimationOnView(this.loginBtn, 7, 100);
        Tools.setShrinkAnimationOnView(this.registerBtn, 7, 100);
    }

    public void onBtnClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}