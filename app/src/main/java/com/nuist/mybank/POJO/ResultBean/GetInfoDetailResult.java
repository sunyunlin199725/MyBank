package com.nuist.mybank.POJO.ResultBean;

public class GetInfoDetailResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"info_title":"农行三月攻略","info_pic":"images/nongye_sygl.png","info_time":1584693917000,"info_text":"热门活动缴费赢好礼：完成任意一笔缴费任务即可参与抽奖哦（党费除外）；账户贵金属交易大赛进行中：·新签约农行掌银账户贵金属即可参与抽奖·账户金交易每达到4次，即可获得1次抽奖机会（每固限两次哦）；电子社保卡签发赢好礼：客户在我行掌银首次申领电子社保卡后即可参与抽奖","bank":{"bank_no":"101","bank_name":"中国农业银行","bank_address":"北京建国门内大街69号","bank_phone":"95599","bank_email":"95599@abchina.com","bank_pc":"100005"}}
     */

    private String message;
    private boolean success;
    private int code;
    private infoBean data;

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

    public infoBean getData() {
        return data;
    }

    public void setData(infoBean data) {
        this.data = data;
    }

    public static class infoBean {
        /**
         * info_title : 农行三月攻略
         * info_pic : images/nongye_sygl.png
         * info_time : 1584693917000
         * info_text : 热门活动缴费赢好礼：完成任意一笔缴费任务即可参与抽奖哦（党费除外）；账户贵金属交易大赛进行中：·新签约农行掌银账户贵金属即可参与抽奖·账户金交易每达到4次，即可获得1次抽奖机会（每固限两次哦）；电子社保卡签发赢好礼：客户在我行掌银首次申领电子社保卡后即可参与抽奖
         * bank : {"bank_no":"101","bank_name":"中国农业银行","bank_address":"北京建国门内大街69号","bank_phone":"95599","bank_email":"95599@abchina.com","bank_pc":"100005"}
         */

        private String info_title;
        private String info_pic;
        private long info_time;
        private String info_text;
        private BankBean bank;

        public String getInfo_title() {
            return info_title;
        }

        public void setInfo_title(String info_title) {
            this.info_title = info_title;
        }

        public String getInfo_pic() {
            return info_pic;
        }

        public void setInfo_pic(String info_pic) {
            this.info_pic = info_pic;
        }

        public long getInfo_time() {
            return info_time;
        }

        public void setInfo_time(long info_time) {
            this.info_time = info_time;
        }

        public String getInfo_text() {
            return info_text;
        }

        public void setInfo_text(String info_text) {
            this.info_text = info_text;
        }

        public BankBean getBank() {
            return bank;
        }

        public void setBank(BankBean bank) {
            this.bank = bank;
        }

        public static class BankBean {
            /**
             * bank_no : 101
             * bank_name : 中国农业银行
             * bank_address : 北京建国门内大街69号
             * bank_phone : 95599
             * bank_email : 95599@abchina.com
             * bank_pc : 100005
             */

            private String bank_no;
            private String bank_name;
            private String bank_address;
            private String bank_phone;
            private String bank_email;
            private String bank_pc;

            public String getBank_no() {
                return bank_no;
            }

            public void setBank_no(String bank_no) {
                this.bank_no = bank_no;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_address() {
                return bank_address;
            }

            public void setBank_address(String bank_address) {
                this.bank_address = bank_address;
            }

            public String getBank_phone() {
                return bank_phone;
            }

            public void setBank_phone(String bank_phone) {
                this.bank_phone = bank_phone;
            }

            public String getBank_email() {
                return bank_email;
            }

            public void setBank_email(String bank_email) {
                this.bank_email = bank_email;
            }

            public String getBank_pc() {
                return bank_pc;
            }

            public void setBank_pc(String bank_pc) {
                this.bank_pc = bank_pc;
            }
        }
    }
}
