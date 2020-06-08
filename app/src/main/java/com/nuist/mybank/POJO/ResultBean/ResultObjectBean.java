package com.nuist.mybank.POJO.ResultBean;

public class ResultObjectBean {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : null
     */

    private String message;
    private boolean success;
    private int code;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
