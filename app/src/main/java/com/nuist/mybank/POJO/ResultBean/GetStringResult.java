package com.nuist.mybank.POJO.ResultBean;

public class GetStringResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : images/20200330125624_crop.jpg
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
