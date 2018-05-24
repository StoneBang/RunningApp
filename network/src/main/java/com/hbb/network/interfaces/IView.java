package com.hbb.network.interfaces;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/14.
 */

public interface IView {

    //create method  you want
    void  success(HashMap<String, Object> object);
    void  fail(Object object);
}
