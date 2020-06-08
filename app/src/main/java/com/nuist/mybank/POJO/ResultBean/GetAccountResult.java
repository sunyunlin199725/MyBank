package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetAccountResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : {"account_id":100000001,"account_name":"孙运琳","account_status":"0","user_header":"images/header.png","user_name":"Return","user_pwd":"syl199725","user_idcard":"320721199711253211","user_phone":"18252083818","user_email":"2213415008@qq.com","user_address":"江苏省连云港市","bankCards":[{"bc_no":"6217993000208468077","bc_type":"1","user_id":100000001,"bank":{"bank_no":"102","bank_name":"中国邮政储蓄银行","bank_address":"北京市西城区金融大街3号","bank_phone":"95580 ","bank_email":null,"bank_pc":"100004"},"bc_money":9845.54,"bc_pwd":"199725"},{"bc_no":"6228480395827290473","bc_type":"1","user_id":100000001,"bank":{"bank_no":"101","bank_name":"中国农业银行","bank_address":"北京建国门内大街69号","bank_phone":"95599","bank_email":"95599@abchina.com","bank_pc":"100005"},"bc_money":456.24,"bc_pwd":"199725"}]}
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
         * user_header : images/header.png
         * user_name : Return
         * user_pwd : syl199725
         * user_idcard : 320721199711253211
         * user_phone : 18252083818
         * user_email : 2213415008@qq.com
         * user_address : 江苏省连云港市
         * bankCards : [{"bc_no":"6217993000208468077","bc_type":"1","user_id":100000001,"bank":{"bank_no":"102","bank_name":"中国邮政储蓄银行","bank_address":"北京市西城区金融大街3号","bank_phone":"95580 ","bank_email":null,"bank_pc":"100004"},"bc_money":9845.54,"bc_pwd":"199725"},{"bc_no":"6228480395827290473","bc_type":"1","user_id":100000001,"bank":{"bank_no":"101","bank_name":"中国农业银行","bank_address":"北京建国门内大街69号","bank_phone":"95599","bank_email":"95599@abchina.com","bank_pc":"100005"},"bc_money":456.24,"bc_pwd":"199725"}]
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
        private List<BankCardsBean> bankCards;

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

        public List<BankCardsBean> getBankCards() {
            return bankCards;
        }

        public void setBankCards(List<BankCardsBean> bankCards) {
            this.bankCards = bankCards;
        }

        @Override
        public String toString() {
            return "AccountBean{" +
                    "account_id=" + account_id +
                    ", account_name='" + account_name + '\'' +
                    ", account_status='" + account_status + '\'' +
                    ", user_header='" + user_header + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_pwd='" + user_pwd + '\'' +
                    ", user_idcard='" + user_idcard + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    ", user_email='" + user_email + '\'' +
                    ", user_address='" + user_address + '\'' +
                    ", bankCards=" + bankCards +
                    '}';
        }

        public static class BankCardsBean {
            /**
             * bc_no : 6217993000208468077
             * bc_type : 1
             * user_id : 100000001
             * bank : {"bank_no":"102","bank_name":"中国邮政储蓄银行","bank_address":"北京市西城区金融大街3号","bank_phone":"95580 ","bank_email":null,"bank_pc":"100004"}
             * bc_money : 9845.54
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
}
