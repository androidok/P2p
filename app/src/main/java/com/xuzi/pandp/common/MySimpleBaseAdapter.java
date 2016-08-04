package com.xuzi.pandp.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xuzi on 8/4/2016.
 */
public abstract class MySimpleBaseAdapter<T> extends BaseAdapter {
    private List<T> list = null;

    public MySimpleBaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getYourView(position, convertView, parent);
    }

    protected abstract View getYourView(int position, View convertView, ViewGroup parent);


}
