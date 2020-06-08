package com.nuist.mybank.POJO.AccountInfo;

import java.sql.Timestamp;

public class Transfer {
    private String pay_no;
    private int account_id;
    private String collection_no;
    private String collection_name;
    private float transfer_money;
    private String transfer_text;
    private Timestamp transfer_time;
    private String transfer_id;
	public String getPay_no() {
		return pay_no;
	}
	public void setPay_no(String pay_no) {
		this.pay_no = pay_no;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getCollection_no() {
		return collection_no;
	}
	public void setCollection_no(String collection_no) {
		this.collection_no = collection_no;
	}
	public String getCollection_name() {
		return collection_name;
	}
	public void setCollection_name(String collection_name) {
		this.collection_name = collection_name;
	}
	public float getTransfer_money() {
		return transfer_money;
	}
	public void setTransfer_money(float transfer_money) {
		this.transfer_money = transfer_money;
	}
	public Timestamp getTransfer_time() {
		return transfer_time;
	}
	public void setTransfer_time(Timestamp transfer_time) {
		this.transfer_time = transfer_time;
	}
	public String getTransfer_id() {
		return transfer_id;
	}
	public void setTransfer_id(String transfer_id) {
		this.transfer_id = transfer_id;
	}
	
	public String getTransfer_text() {
		return transfer_text;
	}
	public void setTransfer_text(String transfer_text) {
		this.transfer_text = transfer_text;
	}
	
	@Override
	public String toString() {
		return "Transfer [pay_no=" + pay_no + ", account_id=" + account_id + ", collection_no=" + collection_no
				+ ", collection_name=" + collection_name + ", transfer_money=" + transfer_money + ", transfer_time="
				+ transfer_time + ", transfer_id=" + transfer_id + "]";
	}
    
}
