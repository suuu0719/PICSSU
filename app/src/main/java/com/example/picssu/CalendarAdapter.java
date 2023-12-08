package com.example.picssu;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picssu.databinding.CalendarItemBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<CalendarItem> itemList;
    private final String TAG = "Calendar";
    private int clickedPosition=-1;
    private int todayPosition = -1; // 오늘 날짜의 위치를 저장할 변수
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CalendarAdapter(ArrayList<CalendarItem> itemList) {
        this.itemList = itemList;
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        private final CalendarItemBinding binding;

        public CalendarViewHolder(CalendarItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.calendarItem.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Update the clicked position
                    clickedPosition = position;
                    notifyDataSetChanged(); // Trigger re-binding to apply visual changes
                }
            });
        }

        public void bind(CalendarItem calendarItem) {
            if (calendarItem.getDay() == 0) {
                binding.calendarItemDate.setText("");
            } else {
                binding.calendarItemDate.setText(String.valueOf(calendarItem.getDay()));
            }

            // Apply visual effect for the clicked item
            if (getAdapterPosition() == clickedPosition) {
                binding.calendarItem.setBackgroundColor(Color.YELLOW);  // Set your desired background color
            } else {
                binding.calendarItem.setBackgroundColor(Color.TRANSPARENT);  // Default background
            }
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarItemBinding binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CalendarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        holder.bind(itemList.get(position));

        // 클릭한 아이템에 대한 배경색 변경
        if (position == clickedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.clickedColor));
        } else {
            // 클릭하지 않은 아이템의 배경색은 기본색으로 설정
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.defaultColor));
        }

        // 오늘날짜 색 변경
        if (position == todayPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.todayColor));
        } else if (position == clickedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.clickedColor));
        } else {
            // 클릭하지 않은 아이템의 배경색은 기본색으로 설정
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.defaultColor));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setClickedPosition(int position) {
        clickedPosition = position;
        notifyDataSetChanged(); // 데이터셋이 변경되었음을 알려 업데이트
    }

    public int findTodayPosition() {
        // 현재 날짜의 포지션을 찾아 반환
        int currentDay =Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getDay() == currentDay) {
                return i;
            }
        }
        return -1; // 현재 날짜가 데이터셋에 없을 경우
    }
    public void setTodayPosition(int position) {
        todayPosition = position;
        notifyDataSetChanged(); // 데이터셋이 변경되었음을 알려 업데이트
    }
}

