package com.challentec.lmssseting.ui;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.GridView;

import com.challentec.lmsseting.bean.Floor;
import com.challentec.lmssseting.adapter.FloorItemAdapter;
import com.challentec.lmssseting.app.R;

/**
 * 楼层设置
 * 
 * @author wanglu 泰得利通
 * 
 */
public class FloorSetActivity extends BaseActivity {

	private GridView floor_gv_setting;
	private List<Floor> floors;
	private FloorItemAdapter floorItemAdapter;

	

	private void initData() {

		floors = new ArrayList<Floor>();

		for (int i = 1; i <= 40; i++) {

			Floor floor = new Floor();
			floor.setItemName(i + "");
			floor.setItemValue("");
			floors.add(floor);

		}

		floorItemAdapter = new FloorItemAdapter(floors, this);
		floor_gv_setting.setAdapter(floorItemAdapter);
	}

	

	@Override
	protected void initMainView(View mainView) {
		floor_gv_setting = (GridView) mainView.findViewById(R.id.floor_gv_setting);
		initData();
		
	}

	@Override
	protected CharSequence getTitleText() {
		return "楼层设置";
	}

	@Override
	protected int getMainViewLayoutId() {
		return R.layout.activity_floor_setting;
	}
}
