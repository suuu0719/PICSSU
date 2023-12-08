package com.example.picssu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.picssu.databinding.ActivityCalendarmainBinding;

import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarMain extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private ActivityCalendarmainBinding binding;
    private ArrayList<CalendarItem> dataSet;
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarmainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // 레이아웃 디자인
        int numberOfWeek = 7;
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), numberOfWeek);

        binding.calendar.calendarContents.setLayoutManager(layoutManager);

        setCalendar();
        setMonth();



        calendarAdapter.setOnItemClickListener(position -> {

            // 클릭한 아이템에 대한 처리
            CalendarItem clickedItem = dataSet.get(position);

            // 예시: 클릭한 날짜에 특별한 효과 부여
            if (clickedItem.getDay() != 0) {
                // 클릭한 날짜에 특별한 효과를 부여하고, 현재 날짜에 대한 효과를 초기화
                resetCurrentDateEffect();
                clickedItem.setClicked(true);
                calendarAdapter.notifyItemChanged(position);
            }
        });

        // 현재 날짜에 대한 효과 초기화
        resetCurrentDateEffect();

// 오늘 날짜에 대한 효과 부여
        int todayPosition = calendarAdapter.findTodayPosition();
        calendarAdapter.setTodayPosition(todayPosition);


        //이전 달 버튼 클릭
        binding.calendar.btnPrevMonth.setOnClickListener(view -> {
            calendar.add(java.util.Calendar.MONTH, -1); // 현재 월에서 1을 빼서 이전 달로 이동
            setCalendar();
            setMonth();
        });

        //다음 달 버튼 클릭
        binding.calendar.btnNextMonth.setOnClickListener(view -> {
            calendar.add(java.util.Calendar.MONTH, 1); // 현재 월에 1을 더해서 다음 달로 이동
            setCalendar();
            setMonth();
        });

    }

    private void resetCurrentDateEffect() {
        // 현재 날짜에 대한 효과 초기화
        int currentDatePosition = findCurrentDatePosition();
        if (currentDatePosition != -1) {
            dataSet.get(currentDatePosition).setClicked(false);
            calendarAdapter.notifyItemChanged(currentDatePosition);
        }
    }

    private int findCurrentDatePosition() {
        // 현재 날짜의 포지션을 찾아 반환
        int currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).getDay() == currentDay) {
                return i;
            }
        }
        return -1; // 현재 날짜가 데이터셋에 없을 경우
    }

    private void setCalendar() {
        dataSet = new ArrayList<>(); // dataSet 초기화
        //ArrayList<CalendarItem> dataSet = new ArrayList<>();
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int maxDay = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        int dayPadding = calendar.get(java.util.Calendar.DAY_OF_WEEK);

        // 1일이 해당하는 요일까지 패딩 (1일이 수요일이라면 일, 월, 화요일이 패딩)
        for (int i = 1; i < dayPadding; i++) {
            dataSet.add(new CalendarItem(0));
        }

        // 실제 캘린더에 들어갈 데이터 작성
        for (int i = 1; i <= maxDay; i++) {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, i);
            dataSet.add(new CalendarItem(i));
        }
        // binding
        calendarAdapter = new CalendarAdapter(dataSet); // calendarAdapter에 할당
        binding.calendar.calendarContents.setAdapter(calendarAdapter);
    }

    private void setMonth() {
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        String monthText = year + "-"+ month;
        binding.calendar.calendarMonth.setText(monthText);
    }
}