package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetPayResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"pay_no":"6217993000564826481","pay_id":100000002,"collection_no":"6228348815624812635","collection_id":100000003,"trans_money":300,"trans_time":1586481672000,"trans_id":"0120200410092111"},{"pay_no":"6230664731003158825","pay_id":100000003,"collection_no":"6217993000564826481","collection_id":100000002,"trans_money":50,"trans_time":1586481629000,"trans_id":"0120200410092028"}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<PayBean> data;

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

    public List<PayBean> getData() {
        return data;
    }

    public void setData(List<PayBean> data) {
        this.data = data;
    }

    public static class PayBean {
        /**
         * pay_no : 6217993000564826481
         * pay_id : 100000002
         * collection_no : 6228348815624812635
         * collection_id : 100000003
         * trans_money : 300.0
         * trans_time : 1586481672000
         * trans_id : 0120200410092111
         */

        private String pay_no;
        private int pay_id;
        private String collection_no;
        private int collection_id;
        private double trans_money;
        private long trans_time;
        private String trans_id;

        public String getPay_no() {
            return pay_no;
        }

        public void setPay_no(String pay_no) {
            this.pay_no = pay_no;
        }

        public int getPay_id() {
            return pay_id;
        }

        public void setPay_id(int pay_id) {
            this.pay_id = pay_id;
        }

        public String getCollection_no() {
            return collection_no;
        }

        public void setCollection_no(String collection_no) {
            this.collection_no = collection_no;
        }

        public int getCollection_id() {
            return collection_id;
        }

        public void setCollection_id(int collection_id) {
            this.collection_id = collection_id;
        }

        public double getTrans_money() {
            return trans_money;
        }

        public void setTrans_money(double trans_money) {
            this.trans_money = trans_money;
        }

        public long getTrans_time() {
            return trans_time;
        }

        public void setTrans_time(long trans_time) {
            this.trans_time = trans_time;
        }

        public String getTrans_id() {
            return trans_id;
        }

        public void setTrans_id(String trans_id) {
            this.trans_id = trans_id;
        }
    }
}
