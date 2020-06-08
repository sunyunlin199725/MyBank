package com.nuist.mybank.POJO.AccountInfo;

import java.sql.Timestamp;

public class GoodsCode {
	   private String goods_name;
	   private float goods_money;
	   private int account_id;
	   private Timestamp trans_time;
	   private String goods_code;
	   private char status;
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public float getGoods_money() {
		return goods_money;
	}
	public void setGoods_money(float goods_money) {
		this.goods_money = goods_money;
	}
	
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public Timestamp getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(Timestamp trans_time) {
		this.trans_time = trans_time;
	}
	public String getGoods_code() {
		return goods_code;
	}
	public void setGoods_code(String goods_code) {
		this.goods_code = goods_code;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GoodCode [goods_name=" + goods_name + ", goods_money=" + goods_money + ", collection_id="
				+ account_id + ", trans_time=" + trans_time + ", goods_code=" + goods_code + ", status=" + status
				+ "]";
	}
	   
   
}
