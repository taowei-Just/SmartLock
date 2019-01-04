package com.it_tao.smartlock.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.it_tao.smartlock.listener.GuidRecyleClickListener ;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class GuidRecyleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    
    public View view ;
    GuidRecyleClickListener clickListener;
    public GuidRecyleHolder(View itemView ,GuidRecyleClickListener clickListener) {
        super(itemView);
        this.view =itemView ;
        this.clickListener = clickListener ;
        view.setOnClickListener(this);
    }


    
    public void onClick(View v) {
        
        clickListener.onItemClick(v,getPosition());
        
    }

     
     
}
