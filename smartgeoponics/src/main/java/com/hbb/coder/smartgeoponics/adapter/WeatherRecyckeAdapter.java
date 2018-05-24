package com.hbb.coder.smartgeoponics.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.coder.smartgeoponics.R;
import com.hbb.coder.smartgeoponics.utils.CalendarUtils;
import com.hbb.coder.smartgeoponics.utils.DisplayUtils;
import com.hbb.coder.smartgeoponics.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/22.
 */

public class WeatherRecyckeAdapter extends RecyclerView.Adapter {


    public ArrayList<LinkedTreeMap<String, Object>> daily_forecast;

    public WeatherRecyckeAdapter(ArrayList<LinkedTreeMap<String, Object>> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = View.inflate(parent.getContext(), R.layout.item_weather_recycle, null);

        WeatherHolder weatherHolder = new WeatherHolder(inflate);

        return weatherHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof WeatherHolder) {
            ((WeatherHolder) holder).bindDate(daily_forecast.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return daily_forecast == null ? 0 : daily_forecast.size();
    }


    public class WeatherHolder extends RecyclerView.ViewHolder {

        public TextView week;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public TextView textView6;

        public WeatherHolder(View itemView) {
            super(itemView);
            week = itemView.findViewById(R.id.week);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            textView6 = itemView.findViewById(R.id.textView6);
            week.setLayoutParams(new LinearLayout.LayoutParams(
                    DisplayUtils.getScreenWidth(itemView.getContext())/7,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void bindDate(LinkedTreeMap<String, Object> weathDate) {

            //日期
            String date = StringUtils.buildString(weathDate.get("date"));
            //阴天
            String cond_txt_d = StringUtils.buildString(weathDate.get("cond_txt_d"));
            //最高温度
            String tmp_max = StringUtils.buildString(weathDate.get("tmp_max"));
            //最低温度
            String tmp_min = StringUtils.buildString(weathDate.get("tmp_min"));
            //风向
            String wind_dir = StringUtils.buildString(weathDate.get("wind_dir"));
            //风力等级
            String wind_sc = StringUtils.buildString(weathDate.get("wind_sc")+"级");

            String weekStr = CalendarUtils.getWeek(date);

            week.setText(weekStr);

            textView.setText(CalendarUtils.getFormatDate(date));

            textView2.setText(cond_txt_d);

            textView4.setText(StringUtils.buildString(tmp_min + "°/" + tmp_max + "°"));

            textView5.setText(wind_dir);

            textView6.setText(wind_sc);

        }
    }
}
