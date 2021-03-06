package com.nuist.mybank.POJO.ResultBean;

public class GetOrderDetailResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"order_money":48.8,"account_id":100000001,"business_name":"必胜客美食","bc_no":"6228480395827245614","order_status":"0","order_time":1585917749000,"order_id":"0220200403204229"}
     */

    private String message;
    private boolean success;
    private int code;
    private OrderDetailBean data;

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

    public OrderDetailBean getData() {
        return data;
    }

    public void setData(OrderDetailBean data) {
        this.data = data;
    }

    public static class OrderDetailBean {
        /**
         * order_money : 48.8
         * account_id : 100000001
         * business_name : 必胜客美食
         * bc_no : 6228480395827245614
         * order_status : 0
         * order_time : 1585917749000
         * order_id : 0220200403204229
         */

        private double order_money;
        private int account_id;
        private String business_name;
        private String bc_no;
        private String order_status;
        private long order_time;
        private String order_id;

        public double getOrder_money() {
            return order_money;
        }

        public void setOrder_money(double order_money) {
            this.order_money = order_money;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getBc_no() {
            return bc_no;
        }

        public void setBc_no(String bc_no) {
            this.bc_no = bc_no;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(long order_time) {
            this.order_time = order_time;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        @Override
        public String toString() {
            return "OrderDetailBean{" +
                    "order_money=" + order_money +
                    ", account_id=" + account_id +
                    ", business_name='" + business_name + '\'' +
                    ", bc_no='" + bc_no + '\'' +
                    ", order_status='" + order_status + '\'' +
                    ", order_time=" + order_time +
                    ", order_id='" + order_id + '\'' +
                    '}';
        }
    }
}
