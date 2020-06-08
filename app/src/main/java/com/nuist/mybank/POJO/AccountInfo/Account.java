package com.nuist.mybank.POJO.AccountInfo;

import java.util.List;

/**
 * 账户表
 * @author Return
 *
 */
public class Account {
   private int account_id;//账户ID,主键
   private String account_name;//账户名
   private char account_status;//账户状态：0-正常，1-销户
   private String user_header;//头像URL
   private String user_name;//登录名
   private String user_pwd;//登录密码
   private String user_idcard;//身份证号
   private String user_phone;//手机号
   private String user_email;//邮箱
   private String user_address;//家庭住址
   private List<BankCard> bankCards;//账户所绑定的银行卡
   
public String getUser_header() {
	return user_header;
}
public void setUser_header(String user_header) {
	this.user_header = user_header;
}
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
public char getAccount_status() {
	return account_status;
}
public void setAccount_status(char account_status) {
	this.account_status = account_status;
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
public List<BankCard> getBankCards() {
	return bankCards;
}
public void setBankCards(List<BankCard> bankCards) {
	this.bankCards = bankCards;
}
   
   
   
   
}
