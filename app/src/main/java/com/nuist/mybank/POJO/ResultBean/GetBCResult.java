package com.nuist.mybank.POJO.ResultBean;

public class GetBCResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"bc_no":"6228480395827245614","bc_type":"1","user_id":100000001,"bank":{"bank_no":"103","bank_name":"中国建设银行","bank_address":"北京市西城区金融大街25号","bank_phone":"95533","bank_email":"CCBCC.ZH@CCB.COM","bank_pc":"100033"},"bc_money":3636.74,"bc_pwd":"199725"}
     */

    private String message;
    private boolean success;
    private int code;
    private BcBean data;

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

    public BcBean getData() {
        return data;
    }

    public void setData(BcBean data) {
        this.data = data;
    }

    public static class BcBean {
        /**
         * bc_no : 6228480395827245614
         * bc_type : 1
         * user_id : 100000001
         * bank : {"bank_no":"103","bank_name":"中国建设银行","bank_address":"北京市西城区金融大街25号","bank_phone":"95533","bank_email":"CCBCC.ZH@CCB.COM","bank_pc":"100033"}
         * bc_money : 3636.74
         * bc_pwd : 199725
         */

        private String bc_no;
        private String bc_type;
        private int user_id;
        private BankBean bank;
        private double bc_money;
        private String bc_pwd;

        public String getBc_no() {
            return bc_no;
        }

        public void setBc_no(String bc_no) {
            this.bc_no = bc_no;
        }

        public String getBc_type() {
            return bc_type;
        }

        public void setBc_type(String bc_type) {
            this.bc_type = bc_type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public BankBean getBank() {
            return bank;
        }

        public void setBank(BankBean bank) {
            this.bank = bank;
        }

        public double getBc_money() {
            return bc_money;
        }

        public void setBc_money(double bc_money) {
            this.bc_money = bc_money;
        }

        public String getBc_pwd() {
            return bc_pwd;
        }

        public void setBc_pwd(String bc_pwd) {
            this.bc_pwd = bc_pwd;
        }

        public static class BankBean {
            /**
             * bank_no : 103
             * bank_name : 中国建设银行
             * bank_address : 北京市西城区金融大街25号
             * bank_phone : 95533
             * bank_email : CCBCC.ZH@CCB.COM
             * bank_pc : 100033
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
