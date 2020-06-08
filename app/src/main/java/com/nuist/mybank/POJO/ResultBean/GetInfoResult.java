package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetInfoResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data:
     */

    private String message;
    private boolean success;
    private int code;
    private List<InfoBean> data;

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

    public List<InfoBean> getData() {
        return data;
    }

    public void setData(List<InfoBean> data) {
        this.data = data;
    }

    public static class InfoBean {
        /**
         * info_title :  网络金融部“社会招聘”
         * info_pic : images/youzhen_zp.png
         * info_time : 1585817753000
         * info_text : 我们的名片/构建智能高效的数字服务网络“金融+生活”双引擎驱动，为3亿客户提供线上优质服务体验，持续打造线上线下互融互通的智能化服务网络。
         拓展包罗万象的生态服务场景C端融入生活场景，B端拓展移动商圈，搭建会员增值服务平台，深度融入消费链、产业链、生活圈。
         打造无处不在的开放银行服务加快先进科技应用与专业金融能力融合，链接高频生活场景，构建无边界的开放式互联网金融服务。
         * bank : {"bank_no":"102","bank_name":"中国邮政储蓄银行","bank_address":"北京市西城区金融大街3号","bank_phone":"95580 ","bank_email":null,"bank_pc":"100004"}
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

        @Override
        public String toString() {
            return "InfoBean{" +
                    "info_title='" + info_title + '\'' +
                    ", info_pic='" + info_pic + '\'' +
                    ", info_time=" + info_time +
                    ", info_text='" + info_text + '\'' +
                    ", bank=" + bank +
                    '}';
        }

        public static class BankBean {
            /**
             * bank_no : 102
             * bank_name : 中国邮政储蓄银行
             * bank_address : 北京市西城区金融大街3号
             * bank_phone : 95580
             * bank_email : null
             * bank_pc : 100004
             */

            private String bank_no;
            private String bank_name;
            private String bank_address;
            private String bank_phone;
            private Object bank_email;
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

            public Object getBank_email() {
                return bank_email;
            }

            public void setBank_email(Object bank_email) {
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
