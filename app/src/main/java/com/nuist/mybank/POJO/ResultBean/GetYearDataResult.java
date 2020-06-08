package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetYearDataResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"money":3420,"years":"2020"}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<YearMapBean> data;

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

    public List<YearMapBean> getData() {
        return data;
    }

    public void setData(List<YearMapBean> data) {
        this.data = data;
    }

    public static class YearMapBean {
        /**
         * money : 3420.0
         * years : 2020
         */

        private double money;
        private String years;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getYears() {
            return years;
        }

        public void setYears(String years) {
            this.years = years;
        }
    }
}
