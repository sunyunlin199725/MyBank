package com.nuist.mybank.POJO.ResultBean;

public class GetPhoneResult {
    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"phone_number":"18252083818","business":{"business_name":"中国移动","business_id":100000010,"business_cardno":"6215485212362455666","business_text":"中国移动通信集团有限公司（英文名称：China Mobile Communications Group Co.,Ltd，简称\u201c中国移动\u201d、\u201cCMCC\u201d或\u201c中国移动通信\u201d、\u201c中移动\u201d）是按照国家电信体制改革的总体部署，于2000年4月20日成立的中央企业。"},"phone_money":52.4}
     */

    private String message;
    private boolean success;
    private int code;
    private PhoneBean data;

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

    public PhoneBean getData() {
        return data;
    }

    public void setData(PhoneBean data) {
        this.data = data;
    }

    public static class PhoneBean {
        /**
         * phone_number : 18252083818
         * business : {"business_name":"中国移动","business_id":100000010,"business_cardno":"6215485212362455666","business_text":"中国移动通信集团有限公司（英文名称：China Mobile Communications Group Co.,Ltd，简称\u201c中国移动\u201d、\u201cCMCC\u201d或\u201c中国移动通信\u201d、\u201c中移动\u201d）是按照国家电信体制改革的总体部署，于2000年4月20日成立的中央企业。"}
         * phone_money : 52.4
         */

        private String phone_number;
        private BusinessBean business;
        private double phone_money;

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public BusinessBean getBusiness() {
            return business;
        }

        public void setBusiness(BusinessBean business) {
            this.business = business;
        }

        public double getPhone_money() {
            return phone_money;
        }

        public void setPhone_money(double phone_money) {
            this.phone_money = phone_money;
        }

        public static class BusinessBean {
            /**
             * business_name : 中国移动
             * business_id : 100000010
             * business_cardno : 6215485212362455666
             * business_text : 中国移动通信集团有限公司（英文名称：China Mobile Communications Group Co.,Ltd，简称“中国移动”、“CMCC”或“中国移动通信”、“中移动”）是按照国家电信体制改革的总体部署，于2000年4月20日成立的中央企业。
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
