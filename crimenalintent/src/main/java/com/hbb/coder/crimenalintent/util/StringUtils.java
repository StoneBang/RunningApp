package com.hbb.coder.crimenalintent.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Thomsen on 2017/5/22.
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * 判断是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return TextUtils.isEmpty(buildString(obj));
    }



    /**
     * 判断是否为空,且不包含一些关键字,比如请选择,请输入,
     * @param obj
     * @return
     */
    public static boolean isEmptyWithNoHint(Object obj) {
        String s = buildString(obj);
        return TextUtils.isEmpty(s)||s.contains("请输入")||s.contains("请选择");
    }

    /**
     * 对象转字符串
     * @param objs
     * @return
     */
    public static String buildString(Object... objs) {

        StringBuilder builder = new StringBuilder();
        if (null != objs) {
            for (Object obj : objs) {
                if (null != obj) {
                    builder.append(obj);
                }
            }
        }
        return builder.toString();
    }

    public static int toInt(Object o) {
        int result = 0;
        try {
            result = Integer.parseInt(buildString(o));
        } catch (Exception e) {
//            LoggerUtils.error("toInt", e.getMessage());
        }
        return result;
    }

    public static float toFloat(Object o) {
        float result = 0;
        try {
            result = Float.parseFloat(buildString(o));
        } catch (Exception e) {
//            LoggerUtils.error("toFloat", e.getMessage());
        }
        return result;
    }

    public static double toDouble(Object o) {
        double result = 0;
        try {
            result = Double.parseDouble(buildString(o));
        } catch (Exception e) {
//            LoggerUtils.error("toDouble", e.getMessage());
        }
        return result;
    }

    public static boolean toBoolean(Object o) {
        boolean result = false;
        try {
            String s = buildString(o);
            result = Boolean.parseBoolean(s) || ("1".equals(s) ? true : false);
        } catch (Exception e) {
//            LoggerUtils.error("toBoolean", e.getMessage());
        }
        return result;
    }

    /**
     * 流转字符串
     * @param inputStream
     * @return
     */
    public static String convertStreamtoString(InputStream inputStream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            return result.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取md5字符串，十六进制
     * @param original
     * @return
     */
    public static String getMd5Hex(String original) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.update(original.getBytes());
//            return new BigInteger(1, messageDigest.digest()).toString(16);

            byte[] bytes = messageDigest.digest(original.getBytes());
            String temp = "";
            for (byte b : bytes) {
                temp = Integer.toHexString(b & 0xff); // 低8位
                if(temp.length() == 1) {
                    temp = "0" + temp;  // 补位
                }
                builder.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }



    /**
     * 限制小数点后面多少位
     * @param s
     * @param lowPrice
     * @param limitNumber
     * @return
     */
    @Nullable
    public static CharSequence limitDemicalPoint(CharSequence s, EditText lowPrice, int limitNumber) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") >limitNumber) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + limitNumber+1);
                lowPrice.setText(s);
                lowPrice.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            lowPrice.setText(s);
            lowPrice.setSelection(2);
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                lowPrice.setText(s.subSequence(0, 1));
                lowPrice.setSelection(1);
                return null;
            }
        }
        return s;
    }

    /**
     * 限制输入一个亿
     * @param s
     */
    private static  void limitInNumber(Editable s, String number) {
        double value = StringUtils.toDouble(s.toString());

        if (value >=StringUtils.toDouble(number)) {
            if (s.toString().startsWith(number)) {
                if (new BigDecimal(s.toString()).toString().contains(".")) {
                    if(s.toString().indexOf(".")==s.length()-1){
                        s.delete(number.length(), s.length());
                    }else{
                        s.delete(number.length()-1, s.toString().indexOf("."));
                    }
                }else{
                    s.delete(number.length(), s.length());
                }
            }else{
                if (new BigDecimal(s.toString()).toString().contains(".")) {
                    s.delete(number.length()-1, s.toString().indexOf("."));
                }else{
                    s.delete(number.length()-1, s.length());
                }
            }
        }
    }
}
