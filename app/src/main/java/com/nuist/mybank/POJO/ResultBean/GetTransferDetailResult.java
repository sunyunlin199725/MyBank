package com.nuist.mybank.POJO.ResultBean;

public class GetTransferDetailResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"pay_no":"6228480395827245614","account_id":100000001,"collection_no":"6228348815624812635","collection_name":"刘洋","transfer_money":200,"transfer_text":"","transfer_time":1586145793000,"transfer_id":"0120200406120313"}
     */

    private String message;
    private boolean success;
    private int code;
    private TransferDetailBean data;

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

    public TransferDetailBean getData() {
        return data;
    }

    public void setData(TransferDetailBean data) {
        this.data = data;
    }

    public static class TransferDetailBean {
        /**
         * pay_no : 6228480395827245614
         * account_id : 100000001
         * collection_no : 6228348815624812635
         * collection_name : 刘洋
         * transfer_money : 200.0
         * transfer_text :
         * transfer_time : 1586145793000
         * transfer_id : 0120200406120313
         */

        private String pay_no;
        private int account_id;
        private String collection_no;
        private String collection_name;
        private double transfer_money;
        private String transfer_text;
        private long transfer_time;
        private String transfer_id;

        public String getPay_no() {
            return pay_no;
        }

        public void setPay_no(String pay_no) {
            this.pay_no = pay_no;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getCollection_no() {
            return collection_no;
        }

        public void setCollection_no(String collection_no) {
            this.collection_no = collection_no;
        }

        public String getCollection_name() {
            return collection_name;
        }

        public void setCollection_name(String collection_name) {
            this.collection_name = collection_name;
        }

        public double getTransfer_money() {
            return transfer_money;
        }

        public void setTransfer_money(double transfer_money) {
            this.transfer_money = transfer_money;
        }

        public String getTransfer_text() {
            return transfer_text;
        }

        public void setTransfer_text(String transfer_text) {
            this.transfer_text = transfer_text;
        }

        public long getTransfer_time() {
            return transfer_time;
        }

        public void setTransfer_time(long transfer_time) {
            this.transfer_time = transfer_time;
        }

        public String getTransfer_id() {
            return transfer_id;
        }

        public void setTransfer_id(String transfer_id) {
            this.transfer_id = transfer_id;
        }
    }
}
