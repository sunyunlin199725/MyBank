package com.nuist.mybank.POJO.ResultBean;

public class GetAccountFromBcnoResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"account_id":100000001,"account_name":"孙运琳","account_status":"0","user_header":"images/20200330125624_crop.jpg","user_name":"Return","user_pwd":"syl199725","user_idcard":"320721199711253211","user_phone":"18252083818","user_email":"2213415008@qq.com","user_address":"江苏省连云港市","bankCards":null}
     */

    private String message;
    private boolean success;
    private int code;
    private AccountBean data;

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

    public AccountBean getData() {
        return data;
    }

    public void setData(AccountBean data) {
        this.data = data;
    }

    public static class AccountBean {
        /**
         * account_id : 100000001
         * account_name : 孙运琳
         * account_status : 0
         * user_header : images/20200330125624_crop.jpg
         * user_name : Return
         * user_pwd : syl199725
         * user_idcard : 320721199711253211
         * user_phone : 18252083818
         * user_email : 2213415008@qq.com
         * user_address : 江苏省连云港市
         * bankCards : null
         */

        private int account_id;
        private String account_name;
        private String account_status;
        private String user_header;
        private String user_name;
        private String user_pwd;
        private String user_idcard;
        private String user_phone;
        private String user_email;
        private String user_address;
        private Object bankCards;

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getAccount_status() {
            return account_status;
        }

        public void setAccount_status(String account_status) {
            this.account_status = account_status;
        }

        public String getUser_header() {
            return user_header;
        }

        public void setUser_header(String user_header) {
            this.user_header = user_header;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_pwd() {
            return user_pwd;
        }

        public void setUser_pwd(String user_pwd) {
            this.user_pwd = user_pwd;
        }

        public String getUser_idcard() {
            return user_idcard;
        }

        public void setUser_idcard(String user_idcard) {
            this.user_idcard = user_idcard;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public Object getBankCards() {
            return bankCards;
        }

        public void setBankCards(Object bankCards) {
            this.bankCards = bankCards;
        }
    }
}
