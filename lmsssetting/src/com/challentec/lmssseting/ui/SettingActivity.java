package com.challentec.lmssseting.ui;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.view.SwitchButton;

/**
 * 设置
 * @author wanglu 泰得利通
 *
 */
public class SettingActivity extends BaseActivity {

	private RelativeLayout setting_rl_floor_set;

	private RelativeLayout setting_rl_state_query;
	private TextView setting_tv_lock_floor;
	private SwitchButton setting_wb_lock_floor;

	@Override
	protected void addListeners() {
		super.addListeners();
		setting_rl_floor_set.setOnClickListener(new OnClickListener() {//楼层设置

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(SettingActivity.this,
						FloorSetActivity.class);
				startActivity(intent);

			}
		});

		setting_rl_state_query.setOnClickListener(new OnClickListener() {//状态查询

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(SettingActivity.this,
						StateQuearyActivity.class);
				startActivity(intent);

			}
		});
		
		
		setting_wb_lock_floor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateLockTextView(isChecked);
			}
		});
	}

	@Override
	protected void initMainView(View mainView) {

		setting_rl_floor_set = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_floor_set);// 楼层设置
		setting_rl_state_query = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_state_query);// 状态查询
		setting_wb_lock_floor=(SwitchButton) mainView.findViewById(R.id.setting_wb_lock_floor);
		setting_tv_lock_floor=(TextView) mainView.findViewById(R.id.setting_tv_lock_floor);//锁体控制
		updateLockTextView(setting_wb_lock_floor.isChecked());
		
	}
	
	private void updateLockTextView(boolean isLockFloor){
		
		if(isLockFloor){
			setting_tv_lock_floor.setText("锁梯");	
		}else{
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
}
