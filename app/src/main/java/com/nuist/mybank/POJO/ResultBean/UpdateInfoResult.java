package com.nuist.mybank.POJO.ResultBean;

public class UpdateInfoResult {

    /**
     * message : 用户信息更新成功！
     * success : true
     * code : 10000
     * data : 1
     */

    private String message;
    private boolean success;
    private int code;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
