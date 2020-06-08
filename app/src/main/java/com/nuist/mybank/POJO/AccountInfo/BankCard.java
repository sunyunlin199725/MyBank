package com.nuist.mybank.POJO.AccountInfo;
/**
 * 银行卡表
 * @author Return
 *
 */
public class BankCard {
   private String bc_no;//卡号，主键
   private char bc_type;//卡类型 1.储蓄卡2.信用卡
   private int user_id;//账户ID
   private Bank bank;//开户行 对应bank_no
   private double bc_money;//卡余额
   private String bc_pwd;//银行卡支付密码
public String getBc_no() {
	return bc_no;
}
public void setBc_no(String bc_no) {
	this.bc_no = bc_no;
}
public char getBc_type() {
	return bc_type;
}
public void setBc_type(char bc_type) {
	this.bc_type = bc_type;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public Bank getBank() {
	return bank;
}
public void setBank(Bank bank) {
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
   
}
