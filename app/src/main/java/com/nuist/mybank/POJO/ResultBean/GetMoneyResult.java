package com.nuist.mybank.POJO.ResultBean;

public class GetMoneyResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : 9119.12
     */

    private String message;
    private boolean success;
    private int code;
    private double data;

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

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
