package com.challentec.lmssseting.ui;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.challentec.lmssseting.adapter.ParamsItemAdapter;
import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.bean.StateParamsItem;

/**
 * 状态查询
 * 
 * @author wanglu 泰得利通
 * 
 */
public class StateQuearyActivity extends BaseActivity {

	private GridView params_lv;
	private List<StateParamsItem> stateParamsItems;
	private ParamsItemAdapter paramsItemAdapter;
	private String itemName[] = new String[] { "安全回路", "厅门锁", "上强迫减速", "下强迫减速",
			"机房检修", "轿门锁", "锁梯信号", "轿顶检修", "上门区", "下门区", "运行", "消防", "上慢车限位",
			"下慢车限位", "抱闸", "警铃", "平层", "上行", "下行" };

	private String itemValue[] = new String[] { "正常", "厅门关闭", "不在顶层", "在底层",
			"检修状态", "轿门打开", "锁梯", "检修状态", "出上门区", "出下门区", "电梯停止", "消防状态",
			"在上限位", "在下限位", "抱闸松开", "警铃触发", "不在平层", "上行", "下行" };

	@Override
	protected void initMainView(View mainView) {
		params_lv = (GridView) mainView.findViewById(R.id.state_gv_setting);

		initData();
	}

	private void initData() {
		stateParamsItems = new ArrayList<StateParamsItem>();

		for (int i = 0; i < itemName.length; i++) {
			StateParamsItem stateParamsItem = new StateParamsItem();
			stateParamsItem.setItemName(itemName[i]);
			stateParamsItem.setItemValue(itemValue[i]);
			stateParamsItems.add(stateParamsItem);

		}

		paramsItemAdapter = new ParamsItemAdapter(stateParamsItems, this);
		params_lv.setAdapter(paramsItemAdapter);

	}

	@Override
	protected CharSequence getTitleText() {
		return "状态查询";
	}

	@Override
	protected int getMainViewLayoutId() {
		return R.layout.activity_state_query;
	}

	@Override
	protected void onReceveData(ResponseData responseData) {
		// TODO Auto-generated method stub
		
	}

}
