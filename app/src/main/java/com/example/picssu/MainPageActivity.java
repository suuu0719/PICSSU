package com.example.picssu;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.picssu.databinding.ActivityMainpageBinding;
import com.example.picssu.databinding.ActivityMypageBinding;


public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainpageBinding binding = ActivityMainpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mypage.setOnClickListener(v -> { //마이페이지 실행
            startActivity(new Intent(MainPageActivity.this, MypageActivity.class));
        });

        binding.calender.setOnClickListener(v -> { //캘린더 실행
            startActivity(new Intent(MainPageActivity.this, Calendar.class));
        });
    }
}