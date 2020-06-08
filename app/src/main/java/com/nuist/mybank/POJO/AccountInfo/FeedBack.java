package com.nuist.mybank.POJO.AccountInfo;

public class FeedBack {
     private String fb_text;
     private String fb_email;
	public String getFb_text() {
		return fb_text;
	}
	public void setFb_text(String fb_text) {
		this.fb_text = fb_text;
	}
	public String getFb_email() {
		return fb_email;
	}
	public void setFb_email(String fb_email) {
		this.fb_email = fb_email;
	}
	@Override
	public String toString() {
		return "FeedBack [fb_text=" + fb_text + ", fb_email=" + fb_email + "]";
	}
     
}
