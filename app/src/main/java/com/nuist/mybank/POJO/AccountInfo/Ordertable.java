package com.nuist.mybank.POJO.AccountInfo;

import java.sql.Timestamp;

public class Ordertable {
      private float order_money;
      private int account_id;
      private String business_name;
      private String bc_no;
      private char order_status;
      private Timestamp order_time;
      private String order_id;
	public float getOrder_money() {
		return order_money;
	}
	public void setOrder_money(float order_money) {
		this.order_money = order_money;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBc_no() {
		return bc_no;
	}
	public void setBc_no(String bc_no) {
		this.bc_no = bc_no;
	}
	public char getOrder_status() {
		return order_status;
	}
	public void setOrder_status(char order_status) {
		this.order_status = order_status;
	}
	public Timestamp getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	@Override
	public String toString() {
		return "Order [order_money=" + order_money + ", account_id=" + account_id + ", business_name=" + business_name
				+ ", bc_no=" + bc_no + ", order_status=" + order_status + ", order_time=" + order_time + ", order_id="
				+ order_id + "]";
	}
      
      
}
