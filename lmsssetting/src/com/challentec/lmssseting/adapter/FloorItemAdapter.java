package com.challentec.lmssseting.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.challentec.lmsseting.bean.Floor;
import com.challentec.lmssseting.app.R;

/**
 * 端口状态项目适配器
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class FloorItemAdapter extends BaseAdapter {

	private Context context;
	private List<Floor> floors;

	public FloorItemAdapter(List<Floor> floors, Context context) {
		this.floors = floors;
		this.context = context;
	}

	@Override
	public int getCount() {
		return floors.size();
	}

	@Override
	public Object getItem(int position) {
		return floors.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView itemName;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Floor floor = floors.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.floor_set_item, null);

			viewHolder = new ViewHolder();

			viewHolder.itemName = (TextView) convertView;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.itemName.setText(floor.getItemName());

		return convertView;

	}

}
