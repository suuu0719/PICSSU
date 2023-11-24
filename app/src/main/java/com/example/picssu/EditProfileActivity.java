package com.example.picssu;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        // 백버튼 누르면 액티비티 종료
        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> finish());
    }
}