package com.nuist.mybank.POJO.AccountInfo;

import java.sql.Date;
/**
 * 自定义选择的日期
 * @author Return
 *
 */
public class CustomizeDate {
	private int account_id;
	private Date startDate;
	private Date endDate;
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "CustomizeDate [account_id=" + account_id + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	

}
