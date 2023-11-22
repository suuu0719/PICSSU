package com.example.picssu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class YesterdayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yesterday);

        View backView = findViewById(R.id.back);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동하는 코드
                Intent intent = new Intent(YesterdayActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료하고 MainActivity를 시작
            }
        });
    }
}
