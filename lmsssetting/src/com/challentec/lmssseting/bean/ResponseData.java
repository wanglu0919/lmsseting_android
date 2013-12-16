package com.challentec.lmssseting.bean;

import java.io.Serializable;

/**
 * 服务器返回数据封装 
 * @author lu.wang
 *
 */
public class ResponseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3920990126856174738L;
	private int len;//长度
	private String functionCode;//功能
	private String serialNumber;//序列号
	private String hexdata;//数据
	public String getHexdata() {
		return hexdata;
	}
	public void setHexdata(String hexdata) {
		this.hexdata = hexdata;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	

	
}
