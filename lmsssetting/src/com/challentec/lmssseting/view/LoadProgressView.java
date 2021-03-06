package com.challentec.lmssseting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.challentec.lmssseting.app.R;

/**
 * 加载进度自定义控
 * 
 * @author  wanglu
 * 
 */
public class LoadProgressView extends RelativeLayout {
	//private ProgressBar pb_load;// 加载进度
	private TextView pb_loadtv;// 进度显示字样

	public LoadProgressView(Context context) {
		super(context);

	}

	public LoadProgressView(Context context, AttributeSet attrs) {

		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.load_pro_layout, this);
		
		pb_loadtv = (TextView) findViewById(R.id.pb_load_tv);

	}
	
	
	/**
	 * 设置加载当进度字�?
	 *@author 泰得利�? wanglu
	 * @param text 加载文字
	 */
	public void setProgressText(String text){
		pb_loadtv.setVisibility(View.VISIBLE);
		pb_loadtv.setText(text);
	}
	
	/**
	 * 显示或隐藏进度文字组�?
	 *@author 泰得利�? wanglu
	 * @param show true显示，false不显�?
	 */
	public void showProgessText(boolean show){
		if(show){
			pb_loadtv.setVisibility(View.VISIBLE);
			
		}else{
			pb_loadtv.setVisibility(View.GONE);
		}
		
	}

	/**
	 * 设置加载当进度字�?
	 *@author 泰得利�? wanglu
	 * @param stringId 资源id
	 */
	public void setProgressText(int  stringId){
		pb_loadtv.setText(this.getContext().getString(stringId));
	}
}
