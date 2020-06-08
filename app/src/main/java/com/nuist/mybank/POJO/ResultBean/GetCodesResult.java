package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetCodesResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"goods_name":"花果山风景区单人票","goods_money":58.8,"account_id":100000003,"trans_time":1585904552000,"goods_code":"LENB7A","status":"0"},{"goods_name":"金龙鱼葵花籽油","goods_money":89.9,"account_id":100000003,"trans_time":1585904474000,"goods_code":"U50AXY","status":"0"}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<GoodsCodeBean> data;

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

    public List<GoodsCodeBean> getData() {
        return data;
    }

    public void setData(List<GoodsCodeBean> data) {
        this.data = data;
    }

    public static class GoodsCodeBean {
        /**
         * goods_name : 花果山风景区单人票
         * goods_money : 58.8
         * account_id : 100000003
         * trans_time : 1585904552000
         * goods_code : LENB7A
         * status : 0
         */

        private String goods_name;
        private double goods_money;
        private int account_id;
        private long trans_time;
        private String goods_code;
        private String status;//0未取货，1已取货

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(double goods_money) {
            this.goods_money = goods_money;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public long getTrans_time() {
            return trans_time;
        }

        public void setTrans_time(long trans_time) {
            this.trans_time = trans_time;
        }

        public String getGoods_code() {
            return goods_code;
        }

        public void setGoods_code(String goods_code) {
            this.goods_code = goods_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
