package com.challentec.lmssseting.api;

import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.util.DataPaseUtil;
import com.challentec.lmssseting.util.LogUtil;

/**
 * 协议规则
 * 
 * @author wanglu 泰得利通
 * 
 */
public class Protocol {

	public static final String HEAD = "434c5443";// 协议头

	public static final String C_S_REGIST = "00"; // C->S注册

	public static final String S_C_REGIST = "12";// S->C 注册结果

	public static final String C_S_STATE = "15";// C-S申请实时状态

	public static final String S_C_STATE = "18";// S-C实时网络状态数据

	public static final String S_C_STATE_DATA = "17";// S-C实时网络状态数据

	public static final String C_S_BEAT = "04";// C-S心跳

	public static final String C_S_HAND = "19";// C-S操作

	/**
	 * 解析返回数据 wanglu 泰得利通
	 * 
	 * @param data
	 * @return
	 */
	public static ResponseData pase(String data) {

		ResponseData responseData = null;
		if (checkData(data)) {
			responseData = new ResponseData();
			int len = DataPaseUtil.hexStrToInt(data.substring(HEAD.length(),
					HEAD.length() + 4));// 长度
			String functionCode = data.substring(HEAD.length() + 4,
					HEAD.length() + 4 + 2);// 功能
			String serNumber = data.substring(HEAD.length() + 4 + 2,
					HEAD.length() + 4 + 2 + 14);
			String hexData = data.substring(HEAD.length() + 4 + 2 + 14);
			responseData.setLen(len);
			responseData.setFunctionCode(functionCode);
			responseData.setSerialNumber(serNumber);
			responseData.setHexdata(hexData);

			LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "返回数据长度:" + len);
			LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "数据功能码:" + functionCode);
			LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "序列:" + serNumber);
			LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "数据:" + hexData);

		}

		return responseData;

	}

	/**
	 * 检查数据 wanglu 泰得利通
	 * 
	 * @return
	 */
	private static boolean checkData(String data) {

		return data.startsWith(HEAD);

	}

}
