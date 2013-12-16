package com.challentec.lmssseting.api;

import com.challentec.lmssseting.app.AppManager;
import com.challentec.lmssseting.util.DataPaseUtil;
import com.challentec.lmssseting.util.LogUtil;

/**
 * api数据构造 
 * @author wanglu 泰得利通
 *
 */
public class ClinetApi {

	
	/**
	 * 注册信息
	 *wanglu 泰得利通 
	 * @param user
	 */
	public static String getRegistData(String user){
		String userHex=DataPaseUtil.bytes2hexStr(user.getBytes());
		int len=userHex.length()/2+1;//长度
		String hexLen=DataPaseUtil.getHexLen(len);
		String apiData=Protocol.HEAD+hexLen+Protocol.C_S_REGIST+AppManager.getMac()+userHex+"00";
		LogUtil.i(LogUtil.LOG_TAG_WRITE_DATA, "注册数据"+apiData+"user"+user);
		return apiData;
	}
	
	
	/**
	 * 心跳
	 *wanglu 泰得利通 
	 * @return
	 */
	public static String getBeat(){
		
		
		
		String apiData=Protocol.HEAD+"0001"+Protocol.C_S_BEAT+AppManager.getMac()+"00";
		
		return apiData;
		
	}
}
