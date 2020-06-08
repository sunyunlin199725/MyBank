package com.nuist.mybank.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.zxing.common.StringUtils;
import com.nuist.mybank.LoginActivity;

/**
 *用于判断网络连接情况
 * @author Return
 */
public class CommonUtils {


	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}

	private static NetworkInfo getNetworkInfo(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * 获取跳转到登录界面的intent
	 * @param context
	 * @return
	 */
    public static Intent getloginIntent(Context context){
		Intent intent = new Intent(context, LoginActivity.class);
		return intent;
	}

	/**
	 * 保护用户隐私,实现用户名隐藏
	 * @param name
	 * @return
	 */
	public static String nameDesensitization(String name) {
		if (name == null || name.isEmpty()) {
			return "";
		}
		String myName = null;
		char[] chars = name.toCharArray();
		if (chars.length == 1) {
			myName = name;
		}
		if (chars.length == 2) {
			myName = name.replaceFirst(name.substring(1), "*");
		}
		if (chars.length > 2) {
			myName = name.replaceAll(name.substring(1, chars.length - 1), "*");
		}
		return myName;
	}

	/**
	 * 实现银行卡号中间数字隐藏
	 * @param cardNo
	 * @return
	 */
	public static String hideCardNo(String cardNo) {
		if(TextUtils.isEmpty(cardNo)) {
			return cardNo;
		}

		int length = cardNo.length();
		int beforeLength = 4;
		int afterLength = 4;
		//替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<length; i++) {
			if(i < beforeLength || i >= (length - afterLength)) {
				sb.append(cardNo.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}
	/**
	 * 方法描述 隐藏手机号中间位置字符，显示前三后三个字符*
	 */
	public static String hidePhoneNo(String phoneNo) {
		if(TextUtils.isEmpty(phoneNo)) {
			return phoneNo;
		}

		int length = phoneNo.length();
		int beforeLength = 3;
		int afterLength = 3;
		//替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<length; i++) {
			if(i < beforeLength || i >= (length - afterLength)) {
				sb.append(phoneNo.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}
	public static String hideIdCard(String idCardNum, int front, int end) {
		//身份证不能为空
		if (TextUtils.isEmpty(idCardNum)) {
			return null;
		}
		//需要截取的长度不能大于身份证号长度
		if ((front + end) > idCardNum.length()) {
			return null;
		}
		//需要截取的不能小于0
		if (front < 0 || end < 0) {
			return null;
		}
		//计算*的数量
		int asteriskCount = idCardNum.length() - (front + end);
		StringBuffer asteriskStr = new StringBuffer();
		for (int i = 0; i < asteriskCount; i++) {
			asteriskStr.append("*");
		}
		String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
		return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
	}
}
