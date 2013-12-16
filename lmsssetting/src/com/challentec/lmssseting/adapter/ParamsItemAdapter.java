package com.challentec.lmssseting.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.bean.StateParamsItem;

/**
 * 楼层显示设置数据适配器
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class ParamsItemAdapter extends BaseAdapter {

	private Context context;
	private List<StateParamsItem> stateParamsItems;


	

	public ParamsItemAdapter(List<StateParamsItem> stateParamsItems, Context context) {
		this.stateParamsItems=stateParamsItems;
		this.context=context;
	
	}

	@Override
	public int getCount() {
		return stateParamsItems.size();
	}

	@Override
	public Object getItem(int position) {
		return stateParamsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView itemName;
		ImageView itemValue;
	

	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		StateParamsItem stateParamsItem = stateParamsItems.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.state_item,
					null);

			viewHolder = new ViewHolder();

			viewHolder.itemName = (TextView) convertView
					.findViewById(R.id.tv_itemname);
		
			
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.itemName.setText(stateParamsItem.getItemName());
		

		
		return convertView;

	}

	
}
