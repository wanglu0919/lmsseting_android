package com.challentec.lmssseting.ui;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.challentec.lmssseting.app.R;

/**
 * 设置
 * @author wanglu 泰得利通
 *
 */
public class SettingActivity extends BaseActivity {

	private RelativeLayout setting_rl_floor_set;

	private RelativeLayout setting_rl_state_query;

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
	}

	@Override
	protected void initMainView(View mainView) {

		setting_rl_floor_set = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_floor_set);// 楼层设置
		setting_rl_state_query = (RelativeLayout) mainView
				.findViewById(R.id.setting_rl_state_query);// 状态查询
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
