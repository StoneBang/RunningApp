package com.hbb.coder.citychoose.bean;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class City {
    private String id;
    private String name;
    private String pinyin;
    private String province;
    private String code;
    private static Pattern sPattern = Pattern.compile("[a-zA-Z]");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public City() {
    }

    public City(String name, String province, String pinyin, String code) {
        this.name = name;
        this.province = province;
        this.pinyin = pinyin;
        this.code = code;
    }

    /***
     * 获取悬浮栏文本，（#、定位、热门 需要特殊处理）
     * @return
     */
    public String getSection() {
        if (TextUtils.isEmpty(pinyin)) {
            return "#";
        } else {
            String c = pinyin.substring(0, 1);

            Matcher m = sPattern.matcher(c);
            if (m.matches()) {
                return c.toUpperCase();
            } else if (TextUtils.equals(c, "定") || TextUtils.equals(c, "热")) {

                return pinyin;

            } else {

                return "#";
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
