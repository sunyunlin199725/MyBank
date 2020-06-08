package com.nuist.mybank.POJO;

public class BankCardView {
    private String bc_no;//卡号
    private String bank_name;//开户行编号
    private char bc_type;//卡类型


    public String getBc_no() {
        return bc_no;
    }

    public void setBc_no(String bc_no) {
        this.bc_no = bc_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public char getBc_type() {
        return bc_type;
    }

    public void setBc_type(char bc_type) {
        this.bc_type = bc_type;
    }

    @Override
    public String toString() {
        return "BankCardView{" +
                "bc_no='" + bc_no + '\'' +
                ", bank_no='" + bank_name + '\'' +
                ", bc_type=" + bc_type +
                '}';
    }
}
