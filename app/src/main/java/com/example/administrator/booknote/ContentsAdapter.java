package com.example.administrator.booknote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/15.
 */
public class ContentsAdapter extends ArrayAdapter {
    private int resourceId;
    public ContentsAdapter(Context context, int textViewResourceId, List<ItemBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        ItemBean itemBean= (ItemBean) getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.acticvity_item,null);
        TextView contents=(TextView)view.findViewById(R.id.text);
        contents.setText(itemBean.getItemContents());
        return view;
    }
    public void clear(){
        super.clear();
    }
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
    }
}
