package com.challentec.lmssseting.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.challentec.lmssseting.api.ClinetApi;
import com.challentec.lmssseting.api.Protocol;
import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.net.SynTask;
import com.challentec.lmssseting.util.DataPaseUtil;
import com.challentec.lmssseting.util.UIHelper;
import com.challentec.lmssseting.view.SwitchButton;

/**
 * 设置
 * 
 * @author wanglu 泰得利通
 * 
 */
public class SettingActivity extends BaseActivity {

	private RelativeLayout setting_rl_floor_set;

	private RelativeLayout setting_rl_state_query;
	private TextView setting_tv_lock_floor;
	private SwitchButton setting_wb_lock_floor;

	private EditText setting_et_mbmack;// 主板
	private EditText setting_et_collectboard;// 采集板
	private SynTask msynTask;

	private ProgressDialog pd_load;
	private Button setting_btn_set;
	public static final String INTENT_MBMAC_KEY = "INTENT_MBMAC_KEY";// 主板
																		// Intent
																		// key
	public static final String INTENT_COLLECTBOAR_KEY = "INTENT_COLLECTBOAR_KEY";// 采集板Key

	@Override
	protected void addListeners() {
		super.addListeners();
		setting_rl_floor_set.setOnClickListener(new OnClickListener() {// 楼层设置

					@Override
					public void onClick(View v) {

						if (checkInput()) {

							String mbmack = setting_et_mbmack.getText()
									.toString();
							String collectBoard = setting_et_collectboard
									.getText().toString();
							Intent intent = new Intent(SettingActivity.this,
									FloorSetActivity.class);

							intent.putExtra(INTENT_MBMAC_KEY, mbmack);
							intent.putExtra(INTENT_COLLECTBOAR_KEY,
									collectBoard);

							startActivity(intent);
						}

					}
				});

		setting_rl_state_query.setOnClickListener(new OnClickListener() {// 状态查询

					@Override
					public void onClick(View v) {

						if (checkInput()) {
							Intent intent = new Intent(SettingActivity.this,
									StateQuearyActivity.class);
							startActivity(intent);
						}

					}
				});

		setting_wb_lock_floor
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						lockFloor(isChecked);

					}
				});

		setting_btn_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doSet();
			}
		});
	}

	private void doSet() {

		if (checkInput()) {
			pd_load.setMessage("正在设置...");
			pd_load.show();

			String mbmack = setting_et_mbmack.getText().toString();
			String collectBoard = setting_et_collectboard.getText().toString();
			String apiData = ClinetApi.getSetData(mbmack,
					DataPaseUtil.bytes2hexStr(mbmack.getBytes()),
					Integer.parseInt(collectBoard));

			msynTask.writeData(apiData);
		}

	}

	private void lockFloor(boolean isLock) {
		if (checkInput()) {
			if (isLock) {
				pd_load.setMessage("锁锑中...");
			} else {
				pd_load.setMessage("解锁中...");
			}
			pd_load.show();
			String mbmack = setting_et_mbmack.getText().toString();
			String collectBoard = setting_et_collectboard.getText().toString();
			String apiData = ClinetApi.getLockFoor(
					DataPaseUtil.bytes2hexStr(mbmack.getBytes()),
					Integer.parseInt(collectBoard), isLock);
			msynTask.writeData(apiData);
		}

	}

	@Override
	protected void initMainView(View mainView) {
		pd_load = new ProgressDialog(this);
		pd_load.setTitle("提示");
		setting_rl_floor_set = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_floor_set);// 楼层设置
		setting_rl_state_query = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_state_query);// 状态查询
		setting_wb_lock_floor = (SwitchButton) mainView
				.findViewById(R.id.setting_wb_lock_floor);
		setting_tv_lock_floor = (TextView) mainView
				.findViewById(R.id.setting_tv_lock_floor);// 锁体控制
		setting_et_mbmack = (EditText) mainView
				.findViewById(R.id.setting_et_mbmack);
		setting_et_collectboard = (EditText) mainView
				.findViewById(R.id.setting_et_collectboard);
		setting_btn_set = (Button) mainView.findViewById(R.id.setting_btn_set);// 设置
		setting_et_mbmack.setText("3040002");
		setting_et_collectboard.setText("12");

		updateLockTextView(setting_wb_lock_floor.isChecked());

		msynTask = new SynTask(appContext);

	}

	private void updateLockTextView(boolean isLockFloor) {

		if (isLockFloor) {
			setting_tv_lock_floor.setText("锁梯");
		} else {
			setting_tv_lock_floor.setText("解锁");
		}

	}

	@Override
	protected CharSequence getTitleText() {
		return "设置";
	}

	@Override
	protected int getMainViewLayoutId() {
		return R.layout.activity_setting_layout;
	}

	@Override
	protected void onReceveData(ResponseData responseData) {

		if (responseData.equals(Protocol.C_S_HAND)) {

			String resultCode = responseData.getHexdata();

			String handCode = resultCode.substring(0, 2);
			if (handCode.equals("04")) {// 锁锑或解锁
				pd_load.dismiss();

				String code = resultCode.substring(2);

				if (code.equals("01")) {
					UIHelper.showToask(appContext, "接收锁锑成功!");
					updateLockTextView(true);
				} else if (code.equals("02")) {
					UIHelper.showToask(appContext, "接收锁锑失败!");
					updateLockTextView(false);
				} else if (code.equals("03")) {
					UIHelper.showToask(appContext, "接收解锁成功!");
					updateLockTextView(false);
				} else if (code.equals("04")) {
					UIHelper.showToask(appContext, "接收解锁失败!");
					updateLockTextView(true);
				}
			} else if (handCode.equals("06")) {
				String code = resultCode.substring(2);
				pd_load.dismiss();
				if (code.equals("01")) {
					UIHelper.showToask(appContext, "配置成功!");

				} else if (code.equals("02")) {
					UIHelper.showToask(appContext, "配置失败!");

				}

			}

		}

	}

	private boolean checkInput() {
		if (setting_et_mbmack.getText().toString().equals("")) {

			UIHelper.showToask(this, "请输入主板信息");
			return false;
		} else if (setting_et_collectboard.getText().toString().equals("")) {
			UIHelper.showToask(this, "请输入采集板信息");
			return false;
		}

		return true;

	}
}
