package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetMonthSumResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"months":"2020-04","money":3320},{"months":"2020-03","money":100}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<MonthSumBean> data;

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

    public List<MonthSumBean> getData() {
        return data;
    }

    public void setData(List<MonthSumBean> data) {
        this.data = data;
    }

    public static class MonthSumBean {
        /**
         * months : 2020-04
         * money : 3320.0
         */

        private String months;
        private double money;

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }
}
