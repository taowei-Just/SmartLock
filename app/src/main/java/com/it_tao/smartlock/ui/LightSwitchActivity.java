package com.it_tao.smartlock.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it_tao.smartlock.R;
import com.it_tao.smartlock.adapter.QQSettingAdapter;
import com.it_tao.smartlock.adapter.WechartSettingAdapter;
import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.service.accessibility.RedPackateAccessibility;
import com.it_tao.smartlock.utils.AppSettingUtil;

import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import huanyang.gloable.gloable.utils.ToastTools;


/**
 * Created by Tao on 2017/11/28 0028.
 */

public class LightSwitchActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_light_switch);
        initview();
    }

    private void initview() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
