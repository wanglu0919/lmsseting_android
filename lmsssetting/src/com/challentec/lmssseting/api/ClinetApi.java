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
	
	/**
	 * 解锁电梯数据
	 * @param mbmac
	 * @param collectBoard
	 * @param isLock
	 * @return
	 */
	public static String getLockFoor(String mbmacHexStr,Integer collectBoard,boolean isLock){
		
		String mb=DataPaseUtil.bytes2hexStr(mbmacHexStr.getBytes());
		
		int len=mb.length()/2+1+1+3;//04 +mc+caij+suoti
		String hexLen=DataPaseUtil.getHexLen(len);
		String lock="01";//锁体
		if(!isLock){
			lock="03";//解锁
		}
		String collectBoardHex=DataPaseUtil.getHexStr(collectBoard, 4);
		String subCollectBoardHex=collectBoardHex.substring(2);//取后面3字节
		String apiData=Protocol.HEAD+hexLen+Protocol.C_S_HAND+AppManager.getMac()+"04"+mb+subCollectBoardHex+lock;
		
		LogUtil.i(LogUtil.LOG_TAG_WRITE_DATA, "锁体数据"+apiData+" mbmacHexStr"+mbmacHexStr+";subCollectBoardHex:"+subCollectBoardHex);
		
		return apiData;
				
		
	}
}
