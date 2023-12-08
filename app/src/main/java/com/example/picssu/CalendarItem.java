package com.example.picssu;
public class CalendarItem {
    private int day;
    private boolean isClicked;

    public CalendarItem(int day) {
        this.day = day;
        this.isClicked = false;  // 초기값은 클릭되지 않은 상태
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}

