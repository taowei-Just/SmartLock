package com.it_tao.smartlock.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.it_tao.smartlock.R;
import com.it_tao.smartlock.holder.GuidRecyleHolder;
import com.it_tao.smartlock.listener.GuidRecyleClickListener;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class GuidAdapter extends RecyclerView.Adapter<GuidRecyleHolder> {

    Context context;
    int count;
    GuidRecyleClickListener clickListener;
    private int wight;

    public GuidAdapter(Context context, int dataList) {
        this.context = context;
        this.count = dataList;
        wight = getDisplayWidth((Activity) context);
    }


    //创建holder 并把view存入holder
    public GuidRecyleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.guid_item_layout, null);

        GuidRecyleHolder recyleHolder = new GuidRecyleHolder(view, clickListener);


        return recyleHolder;
    }


    public void onBindViewHolder(GuidRecyleHolder viewHolder, int i) {

        if (viewHolder != null) {

            View itemView = viewHolder.view;

            itemView.setLayoutParams(new GridLayoutManager.LayoutParams(wight / 3, wight / 3));

            TextView tv_icon;
            ImageView iv_back;

            tv_icon = (TextView) itemView.findViewById(R.id.tv_icon);
            iv_back = (ImageView) itemView.findViewById(R.id.iv_background);

            switch (i) {

                case 0:

                    break;
                case 1:

                    tv_icon.setText("睡眠助手");
                    iv_back.setImageResource(R.mipmap.sleep);

                    break;   case 2:

                    tv_icon.setText("录音机");
                    iv_back.setImageResource(R.mipmap.recode);

                    break;

                case 3:

                    tv_icon.setText("红包助手");
                    iv_back.setImageResource(R.mipmap.read_packet);

                    break;
                case 4:

                    tv_icon.setText("手电筒");
                    iv_back.setImageResource(R.mipmap.light);

                    break;
                case 5:

                    tv_icon.setText("画画板");
                    iv_back.setImageResource(R.mipmap.icon);
                    break;

            }

        }


    }

    public long getItemId(int position) {
        return position;
    }


    public int getItemCount() {
        return count;
    }

    public void setOnItemListener(GuidRecyleClickListener listener) {

        this.clickListener = listener;
    }


    public int getDisplayWidth(Activity activity) {

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        int DisplayWidth = metric.widthPixels; // 屏幕宽度（像素）
        int DisplayHeight = metric.heightPixels; // 屏幕高度（像素）

        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

        Log.i("", "屏幕密度 density:" + density + "屏幕Dpi densityDpi:" + densityDpi
                + "屏幕宽度 DisplayWidth：" + DisplayWidth + "屏幕高度DisplayHeight："
                + DisplayHeight);

        return DisplayWidth;

    }


}
