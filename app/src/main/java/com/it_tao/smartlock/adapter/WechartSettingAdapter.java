package com.it_tao.smartlock.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.it_tao.smartlock.R;

import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import huanyang.gloable.gloable.utils.ToastTools;

import static com.it_tao.smartlock.R.id.et_deley_time;
import static com.tencent.bugly.crashreport.crash.c.e;


/**
 * Created by Tao on 2017/12/4 0004.
 */

public class WechartSettingAdapter extends BaseAdapter {

    private final Context context;
    public String TAG = getClass().getName();

    String[] mSettings = new String[]{"延时跳转", "延时抢红包", "自动亮屏", "抢自己的红包"};
    private final SharedUtlis sharedUtlis;
    private int mTouchPossition;

    public WechartSettingAdapter(Context context) {

        this.context = context;
        sharedUtlis = new SharedUtlis(context, Shared.config);
    }

    @Override
    public int getCount() {
        return mSettings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.setting_list_item_layout, null);

        final CheckBox cb_time_delay = (CheckBox) view.findViewById(R.id.cb_time_delay);
        final CheckBox cb_time_random = (CheckBox) view.findViewById(R.id.cb_time_random);
        final EditText et_deley_time = (EditText) view.findViewById(R.id.et_deley_time);
        final TextView tv_item_title = (TextView) view.findViewById(R.id.tv_item_title);
        final TextView tv_random_text = (TextView) view.findViewById(R.id.tv_random_text);
        final View rl_time_random = view.findViewById(R.id.rl_time_random);
        final View rl_assign_delay = view.findViewById(R.id.rl_assign_delay);


        tv_item_title.setText(mSettings[position]);

        et_deley_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String string = et_deley_time.getText().toString();

               if (string != null && !string.isEmpty()&&cb_time_delay.isChecked()&&!cb_time_random.isChecked()) {
                    Integer integer = Integer.valueOf(string);
                    if (integer > 5 * 1000 && integer < 10 * 1000) {


                        ToastTools.showShort(context, "等那么长时间能抢到红包吗！");

                    } else if (integer >= 10 * 1000 && integer < 30 * 1000) {
                        ToastTools.showShort(context, "你这样抢红包黄花菜都凉了！");
                    } else if (integer >= 30 * 1000) {
                        ToastTools.showShort(context, "唉、这娃没救了，不管你了！");
                    }
                    if (position == 0) {
                        sharedUtlis.putString(Shared.wechartJumpTime, integer + "");
                    } else if (position == 1) {
                        sharedUtlis.putString(Shared.wechartRobTime, integer + "");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cb_time_delay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switchPosstion(position, isChecked);
                if (isChecked) {
                    tv_item_title.setTextColor(Color.RED);
                } else {
                    tv_item_title.setTextColor(Color.DKGRAY);
                }

                if (position < 2||position>3) {
                    if (isChecked) {
                        rl_assign_delay.setVisibility(View.VISIBLE);
                        rl_time_random.setVisibility(View.VISIBLE);
                    } else {
                        rl_assign_delay.setVisibility(View.GONE);
                        rl_time_random.setVisibility(View.GONE);
                    }
                }
            }
        });

        cb_time_random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_random_text.setTextColor(Color.RED);
                } else {
                    tv_random_text.setTextColor(Color.DKGRAY);
                }

                if (position == 0) {
                    if (isChecked) {
                        rl_assign_delay.setVisibility(View.GONE);

                    } else {
                        rl_assign_delay.setVisibility(View.VISIBLE);

                    }
                    sharedUtlis.putBoolean(Shared.isWechartRandomJumpTime, isChecked);


                } else if (position == 1) {

                    if (isChecked) {
                        rl_assign_delay.setVisibility(View.GONE);

                    } else {
                        rl_assign_delay.setVisibility(View.VISIBLE);

                    }

                    sharedUtlis.putBoolean(Shared.isWechartRandomRobTime, isChecked);

                }

            }
        });

        et_deley_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchPossition = position;

                return false;
            }
        });


        switch (position) {

            case 0:

                cb_time_delay.setChecked(sharedUtlis.getBoolean(Shared.isWechartJumpDelay, false));
                cb_time_random.setChecked(sharedUtlis.getBoolean(Shared.isWechartRandomJumpTime, false));
                et_deley_time.setText(sharedUtlis.getString(Shared.wechartJumpTime, ""));
                break;

            case 1:
                cb_time_delay.setChecked(sharedUtlis.getBoolean(Shared.isWechartRobDelay, false));
                cb_time_random.setChecked(sharedUtlis.getBoolean(Shared.isWechartRandomRobTime, false));
                et_deley_time.setText(sharedUtlis.getString(Shared.wechartRobTime, ""));
                break;
            case 2:
                cb_time_delay.setChecked(sharedUtlis.getBoolean(Shared.isWechartOpenLight, false));
                break;
            case 3:
                cb_time_delay.setChecked(sharedUtlis.getBoolean(Shared.isWechartSelf, false));
                break;
        }


        if (position == mTouchPossition) {


                et_deley_time.requestFocus();
                et_deley_time.setSelection(et_deley_time.length());

        } else {
            et_deley_time.clearFocus();
        }

        return view;
    }

    private void switchPosstion(int position, boolean isChecked) {


        switch (position) {

            case 0:
                sharedUtlis.putBoolean(Shared.isWechartJumpDelay, isChecked);

                LogUtil.e(TAG, " 标记 延时跳转  position " + position + " isChecked " + isChecked);
                break;
            case 1:
                sharedUtlis.putBoolean(Shared.isWechartRobDelay, isChecked);
                LogUtil.e(TAG, " 标记 延时开抢 position " + position + " isChecked " + isChecked);


                break;

            case 2:
                sharedUtlis.putBoolean(Shared.isWechartOpenLight, isChecked);
                LogUtil.e(TAG, " 标记 开启屏幕  position " + position + " isChecked " + isChecked);

                break;

            case 3:
                sharedUtlis.putBoolean(Shared.isWechartSelf, isChecked);

                LogUtil.e(TAG, " 标记 抢自己红包  position " + position + " isChecked " + isChecked);

                break;

        }


    }
}
