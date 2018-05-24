package com.hbb.coder.citychoose.listener;


import com.hbb.coder.citychoose.bean.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
