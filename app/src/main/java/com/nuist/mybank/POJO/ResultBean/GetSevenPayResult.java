package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetSevenPayResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"trans_money":60,"days":"16"},{"trans_money":58.8,"days":"16"},{"trans_money":50,"days":"16"},{"trans_money":150,"days":"10"},{"trans_money":50,"days":"10"},{"trans_money":300,"days":"09"},{"trans_money":58.8,"days":"03"}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetSevenPayResult{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * trans_money : 60.0
         * days : 16
         */

        private double trans_money;
        private String days;

        public double getTrans_money() {
            return trans_money;
        }

        public void setTrans_money(double trans_money) {
            this.trans_money = trans_money;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "trans_money=" + trans_money +
                    ", days='" + days + '\'' +
                    '}';
        }
    }
}
