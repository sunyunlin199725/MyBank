package com.nuist.mybank.POJO.AccountInfo;
/**
 * 银行表
 * @author Return
 *
 */
public class Bank {
     private String bank_no;//银行代码，主键
     private String bank_name;//银行名称
     private String bank_address;//总部地址
     private String bank_phone;//联系电话
     private String bank_email;//电子邮箱
     private String bank_pc;//邮政编码
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
