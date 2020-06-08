package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetGoodsResult {


    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"goods_name":"意式肉酱面","public_time":1585527218000,"goods_pic":"images/bsk_ysm.jpg","goods_money":28,"goods_describe":"意式肉酱面是一道美味佳肴，主料是意面，猪肉，番茄酱。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}},{"goods_name":"拿铁咖啡","public_time":1584688630000,"goods_pic":"images/bsk_ntcoffee.jpg","goods_money":19,"goods_describe":"拿铁咖啡是意大利浓缩咖啡(Espresso)与牛奶的经典混合，意大利人也很喜欢把拿铁作为早餐的饮料。意大利人早晨的厨房里，照得到阳光的炉子上通常会同时煮着咖啡和牛奶。喝拿铁的意大利人，与其说他们喜欢意大利浓缩咖啡，不如说他们喜欢牛奶，也只有Espresso才能给普普通通的牛奶带来让人难以忘怀的味道。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}},{"goods_name":"摩卡咖啡","public_time":1585379484000,"goods_pic":"images/bsk_mkcoffee.jpg","goods_money":20,"goods_describe":"摩卡咖啡（Mocca Cafe ）是由意大利浓缩咖啡、巧克力酱、鲜奶油和牛奶混合而成的一种古老的咖啡。摩卡咖啡其历史要追溯到咖啡的起源。它是由意大利浓缩咖啡、巧克力酱、鲜奶油和牛奶混合而成，摩卡得名于有名的摩卡港。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}},{"goods_name":"澳洲西冷牛排","public_time":1585710902000,"goods_pic":"images/bsk_niupai.jpg","goods_money":69.6,"goods_describe":"西冷牛排（Sirloin)，主要是由上腰部的脊肉构成，西冷牛排按质量的不同又可分为小块西冷牛排（entrecte）和大块西冷牛排（sirloin steak）。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}},{"goods_name":"花果山风景区单人票","public_time":1584603394000,"goods_pic":"images/hgs_mp.jpg","goods_money":58.8,"goods_describe":"连云港花果山自由行一日及多日游线路攻略","business":{"business_name":"花果山风景区","business_id":100000008,"business_cardno":"6215982920040006482","business_text":"花果山位于江苏省连云港市南云台山中麓。花果山景区是国家级云台山风景名胜区的核心景区、国家AAAAA级旅游景区、全国文明风景旅游区示范点、第二批国家重点风景名胜区、国家地质公园。"}},{"goods_name":"蓝月亮洗衣液","public_time":1583479501000,"goods_pic":"images/sg_lyl.jpg","goods_money":43.5,"goods_describe":"蓝月亮秉承一心一意做洗涤的理念,坚持以自动清洁、解放劳力为宗旨,将国际尖端技术融入中国人的生活,研制开发出高效、轻松、保护的洗衣产品。","business":{"business_name":"苏果超市","business_id":100000007,"business_cardno":"6215982920990003922","business_text":"苏果超市有限公司成立于1996年7月18日。其前身是江苏省果品食杂总公司下属的果品科。\u201c苏果\u201d二字,取自\u201c江苏省果品食杂总公司\u201d。"}},{"goods_name":"金龙鱼葵花籽油","public_time":1583998091000,"goods_pic":"images/sg_jly.jpg","goods_money":89.9,"goods_describe":"金龙鱼阳光葵花籽油就是非常好的选择,从种植、提炼、加工、运输等各个环节严格执行欧盟标准,并且每一瓶金龙鱼阳光葵花籽油100%来自于欧洲最美的阳光葵","business":{"business_name":"苏果超市","business_id":100000007,"business_cardno":"6215982920990003922","business_text":"苏果超市有限公司成立于1996年7月18日。其前身是江苏省果品食杂总公司下属的果品科。\u201c苏果\u201d二字,取自\u201c江苏省果品食杂总公司\u201d。"}},{"goods_name":"麻辣小龙虾比萨","public_time":1585797799000,"goods_pic":"images/bsk_pisa.jpg","goods_money":48.8,"goods_describe":"必胜客比萨必胜客的美味比萨有数十种之多,而等待一份比萨新鲜出炉的时间永远不会超过15分钟,并且你在各地的必胜客,品尝到的比萨口味都完全一致。","business":{"business_name":"必胜客美食","business_id":100000006,"business_cardno":"6230545656959565659","business_text":"必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。"}}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<GoodsInfoBean> data;

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

    public List<GoodsInfoBean> getData() {
        return data;
    }

    public void setData(List<GoodsInfoBean> data) {
        this.data = data;
    }

    public static class GoodsInfoBean {
        /**
         * goods_name : 意式肉酱面
         * public_time : 1585527218000
         * goods_pic : images/bsk_ysm.jpg
         * goods_money : 28.0
         * goods_describe : 意式肉酱面是一道美味佳肴，主料是意面，猪肉，番茄酱。
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
