package com.nuist.mybank.POJO.ResultBean;

public class ResultFileUpLoad {

    /**
     * message : 上传成功！
     * success : true
     * code : 10000
     * data : 上传文件的路径为： E:\apache-tomcat-8.0.53\webapps\mybank\images\morentouxiangnv.png
     */

    private String message;
    private boolean success;
    private int code;
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
