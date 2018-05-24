package com.hbb.coder.citychoose.listener;


import com.hbb.coder.citychoose.bean.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
}
