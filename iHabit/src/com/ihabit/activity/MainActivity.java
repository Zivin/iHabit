package com.ihabit.activity;

import com.ihabit.bean.HttpResult;
import com.ihabit.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		HttpResult<Boolean> result = HttpUtil.createHabit("test habit");
		Toast.makeText(getApplicationContext(), result.getCode(),
				Toast.LENGTH_SHORT).show();
	}
}
