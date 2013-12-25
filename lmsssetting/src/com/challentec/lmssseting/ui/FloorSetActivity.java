package com.challentec.lmssseting.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.challentec.lmssseting.adapter.FloorItemAdapter;
import com.challentec.lmssseting.api.ClinetApi;
import com.challentec.lmssseting.api.Protocol;
import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.bean.Floor;
import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.net.SynTask;
import com.challentec.lmssseting.util.DataPaseUtil;
import com.challentec.lmssseting.util.UIHelper;

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
	private ProgressDialog pd_save;
	private SynTask msynTask;

	@Override
	protected void addListeners() {
		// TODO Auto-generated method stub
		super.addListeners();

		floor_gv_setting.setOnItemClickListener(new FloorItemClickListener());
	}

	private class FloorItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> daAdapterView, View view,
				int position, long id) {
			Floor floor = floors.get(position);
			floor.setHightLinght(!floor.isHightLinght());
			floorItemAdapter.notifyDataSetChanged();// 通知数据更新
		}

	}

	private void initData() {

		floors = new ArrayList<Floor>();

		for (int i = 1; i <= 40; i++) {

			Floor floor = new Floor();
			floor.setItemName(i + "");
			floor.setItemValue(DataPaseUtil.getHexStr(i, 1));
			floors.add(floor);

		}

		floorItemAdapter = new FloorItemAdapter(floors, this);
		floor_gv_setting.setAdapter(floorItemAdapter);
	}

	@Override
	protected void initMainView(View mainView) {
		msynTask = new SynTask(appContext);
		floor_gv_setting = (GridView) mainView
				.findViewById(R.id.floor_gv_setting);
		showSaveButton(true);// 显示保存按钮
		pd_save = new ProgressDialog(this);
		pd_save.setTitle("提示");
		initData();

	}

	@Override
	protected void onSave() {

		String floorStr = getFloorHexStr();
		if (floorStr.equals("")) {
			UIHelper.showToask(appContext, "请选择您要保存的楼层");
			return;
		}
		pd_save.setMessage("正在保存楼层数据");
		pd_save.show();

		String mbmack = getIntent().getExtras().getString(
				SettingActivity.INTENT_MBMAC_KEY);
		String collectBoard = getIntent().getExtras().getString(
				SettingActivity.INTENT_COLLECTBOAR_KEY);

		String apiData = ClinetApi.getFloorSetData(
				DataPaseUtil.bytes2hexStr(mbmack.getBytes()),
				Integer.parseInt(collectBoard), floorStr);
		msynTask.writeData(apiData);

	}

	/**
	 * 获取要保存的楼层 wanglu 泰得利通
	 * 
	 * @return
	 */
	private String getFloorHexStr() {
		StringBuffer sbBuffer = new StringBuffer("");
		for (Floor floor : floors) {

			if (floor.isHightLinght()) {
				sbBuffer.append(floor.getItemValue());
			}

		}

		return sbBuffer.toString();

	}

	@Override
	protected CharSequence getTitleText() {
		return "楼层设置";
	}

	@Override
	protected int getMainViewLayoutId() {
		return R.layout.activity_floor_setting;
	}

	@Override
	protected void onReceveData(ResponseData responseData) {

		if (responseData.equals(Protocol.C_S_HAND)) {

			String resultCode = responseData.getHexdata();

			String handCode = resultCode.substring(0, 2);
			if (handCode.equals("00")) {// 锁锑或解锁
				pd_save.dismiss();

				String code = resultCode.substring(2);

				if (code.equals("01")) {
					UIHelper.showToask(appContext, "保存成功!");

				} else if (code.equals("02")) {
					UIHelper.showToask(appContext, "保存失败!");

				}
			}

		}
	}
}
