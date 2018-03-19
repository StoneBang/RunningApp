package com.hbb.network.netmodel;

/**
 * Created by Administrator on 2018/3/4.
 */

public class CommonResponse<T>{


    /**
     * 响应是否成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private int errorCode;

    /**
     * 响应消息
     */
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return translateResult;
    }

    public void setData(T data) {
        this.translateResult = data;
    }


    /**

     * 响应实体
     */
    private T translateResult;


    public static class TranslateResultBean {
        /**
         * src : merry me
         * tgt : 我快乐
         */

        public String src;
        public String tgt;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTgt() {
            return tgt;
        }

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }
    }

//    private List<List<Translater.TranslateResultBean>> translateResult;

//    public List<List<Translater.TranslateResultBean>> getTranslateResult() {
//        return translateResult;
//    }
//
//    public void setTranslateResult(List<List<Translater.TranslateResultBean>> translateResult) {
//        this.translateResult = translateResult;
//    }


}
