package com.nuist.mybank.POJO.ResultBean;

public class GetGoodsFrNaResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"goods_name":"拿铁咖啡","public_time":1584688630000,"goods_pic":"images/bsk_ntcoffee.jpg","goods_money":19,"goods_describe":"拿铁咖啡是意大利浓缩咖啡(Espresso)与牛奶的经典混合，意大利人也很喜欢把拿铁作为早餐的饮料。意大利人早晨的厨房里，照得到阳光的炉子上通常会同时煮着咖啡和牛奶。喝拿铁的意大利人，与其说他们喜欢意大利浓缩咖啡，不如说他们喜欢牛奶，也只有Espresso才能给普普通通的牛奶带来让人难以忘怀的味道。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}}
     */

    private String message;
    private boolean success;
    private int code;
    private GoodsInfoBean data;

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

    public GoodsInfoBean getData() {
        return data;
    }

    public void setData(GoodsInfoBean data) {
        this.data = data;
    }

    public static class GoodsInfoBean {
        /**
         * goods_name : 拿铁咖啡
         * public_time : 1584688630000
         * goods_pic : images/bsk_ntcoffee.jpg
         * goods_money : 19.0
         * goods_describe : 拿铁咖啡是意大利浓缩咖啡(Espresso)与牛奶的经典混合，意大利人也很喜欢把拿铁作为早餐的饮料。意大利人早晨的厨房里，照得到阳光的炉子上通常会同时煮着咖啡和牛奶。喝拿铁的意大利人，与其说他们喜欢意大利浓缩咖啡，不如说他们喜欢牛奶，也只有Espresso才能给普普通通的牛奶带来让人难以忘怀的味道。
         * business : {"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}
         */

        private String goods_name;
        private long public_time;
        private String goods_pic;
        private double goods_money;
        private String goods_describe;
        private BusinessBean business;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public long getPublic_time() {
            return public_time;
        }

        public void setPublic_time(long public_time) {
            this.public_time = public_time;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public double getGoods_money() {
            return goods_money;
        }

        public void setGoods_money(double goods_money) {
            this.goods_money = goods_money;
        }

        public String getGoods_describe() {
            return goods_describe;
        }

        public void setGoods_describe(String goods_describe) {
            this.goods_describe = goods_describe;
        }

        public BusinessBean getBusiness() {
            return business;
        }

        public void setBusiness(BusinessBean business) {
            this.business = business;
        }

        public static class BusinessBean {
            /**
             * business_name : 必胜客美食
             * business_id : 100000006
             * business_cardno : 6230545656959565659
             * business_text : 必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。
             */

            private String business_name;
            private int business_id;
            private String business_cardno;
            private String business_text;

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public int getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(int business_id) {
                this.business_id = business_id;
            }

            public String getBusiness_cardno() {
                return business_cardno;
            }

            public void setBusiness_cardno(String business_cardno) {
                this.business_cardno = business_cardno;
            }

            public String getBusiness_text() {
                return business_text;
            }

            public void setBusiness_text(String business_text) {
                this.business_text = business_text;
            }
        }
    }
}
