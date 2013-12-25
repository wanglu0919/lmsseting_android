package com.challentec.lmssseting.api;

import java.util.ArrayList;
import java.util.List;

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
	public static List<ResponseData> pase(String data) {

		String recePackageData[] = data.split(HEAD);// 粘包处理

		List<ResponseData> repResponseDatas = new ArrayList<ResponseData>();

		if (recePackageData != null) {

			for (String recDta : recePackageData) {

				if (recDta.equals("")) {
					continue;
				}
				recDta = HEAD + recDta;
				ResponseData responseData = new ResponseData();
				int len = DataPaseUtil.hexStrToInt(recDta.substring(
						HEAD.length(), HEAD.length() + 4));// 长度
				String functionCode = recDta.substring(HEAD.length() + 4,
						HEAD.length() + 4 + 2);// 功能
				String serNumber = recDta.substring(HEAD.length() + 4 + 2,
						HEAD.length() + 4 + 2 + 14);
				String hexData = recDta.substring(HEAD.length() + 4 + 2 + 14);
				responseData.setLen(len);
				responseData.setFunctionCode(functionCode);
				responseData.setSerialNumber(serNumber);
				responseData.setHexdata(hexData);

				LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "返回数据长度:" + len);
				LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "数据功能码:" + functionCode);
				LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "序列:" + serNumber);
				LogUtil.i(LogUtil.LOG_TAG_REPONSE_DATA, "数据:" + hexData);

				repResponseDatas.add(responseData);

			}

		}

		return repResponseDatas;

	}

	/**
	 * 检查数据 wanglu 泰得利通
	 * 
	 * @return
	 */
	
	public static boolean checkData(String data) {

		return data.startsWith(HEAD);

	}

}
