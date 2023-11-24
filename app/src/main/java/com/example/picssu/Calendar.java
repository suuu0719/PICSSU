package com.example.picssu;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calendar extends AppCompatActivity {

    CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        cal=findViewById(R.id.cal);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            }
        });

        //백버튼 누르면 액티비티 종료
        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> finish());
    }
}