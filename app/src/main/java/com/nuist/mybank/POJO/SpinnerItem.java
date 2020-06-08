package com.nuist.mybank.POJO;

public class SpinnerItem {
    private int bank_logo;
    private String bank_name;
    private String bank_no;

    public int getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(int bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    @Override
    public String toString() {
        return "SpinnerItem{" +
                "bank_logo=" + bank_logo +
                ", bank_name='" + bank_name + '\'' +
                ", bank_no='" + bank_no + '\'' +
                '}';
    }
}
