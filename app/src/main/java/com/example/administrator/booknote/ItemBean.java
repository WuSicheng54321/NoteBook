package com.example.administrator.booknote;

/**
 * Created by Administrator on 2015/11/15.
 */
public class ItemBean {
    private String ItemContents;
    public ItemBean(String _ItemContents){
        ItemContents=_ItemContents;
    }
    public String getItemContents(){
        return ItemContents;
    }
    public void setItemContents(String ItemContents){
        this.ItemContents=ItemContents;
    }
}
