package com.challentec.lmssseting.listener;

import com.challentec.lmssseting.bean.ResponseData;
/**
 * 服务器返回数据消息监听
 * @author 泰得利通 wanglu
 *
 */
public interface AppMessageLinstener {

	/**
	 * 
	 *@author 泰得利通 wanglu
	 * @param responseData 返回数据对象
	 */
	public void onRespnseDataReceve(ResponseData responseData);
}
