package com.nuist.mybank.POJO.AccountInfo;

public class Business {
	private String business_name;
	private int business_id;
	private String business_cardno;
	private String business_text;
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public int getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(int business_id) {
		this.business_id = business_id;
	}
	public String getBusiness_cardno() {
		return business_cardno;
	}
	public void setBusiness_cardno(String business_cardno) {
		this.business_cardno = business_cardno;
	}
	public String getBusiness_text() {
		return business_text;
	}
	public void setBusiness_text(String business_text) {
		this.business_text = business_text;
	}
	@Override
	public String toString() {
		return "Business [business_name=" + business_name + ", business_id=" + business_id + ", business_cardno="
				+ business_cardno + ", business_text=" + business_text + "]";
	}

	
}
