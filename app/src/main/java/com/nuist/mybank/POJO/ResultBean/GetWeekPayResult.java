package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetWeekPayResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"money":200,"days":"06"},{"money":166.7,"days":"03"},{"money":19,"days":"02"},{"money":300,"days":"01"},{"money":1000,"days":"31"}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<WeekPayBean> data;

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

    public List<WeekPayBean> getData() {
        return data;
    }

    public void setData(List<WeekPayBean> data) {
        this.data = data;
    }

    public static class WeekPayBean {
        /**
         * money : 200.0
         * days : 06
         */

        private float money;
        private String days;

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }
    }
}
