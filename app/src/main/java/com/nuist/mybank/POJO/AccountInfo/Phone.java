package com.nuist.mybank.POJO.AccountInfo;

public class Phone {
	 private String phone_number;
	 private Business business;
	 private double phone_money;
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public double getPhone_money() {
		return phone_money;
	}
	public void setPhone_money(double phone_money) {
		this.phone_money = phone_money;
	}
	@Override
	public String toString() {
		return "Phone [phone_number=" + phone_number + ", business=" + business + ", phone_money=" + phone_money + "]";
	}
	 

}
