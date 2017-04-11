package com.xcoder.lib.adapter.recyclerview;

import android.support.annotation.LayoutRes;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装对数据操作
 * Created by xz on 2016/8/16 0016.
 */
public abstract class RvDataAdapter<D> extends RvBaseAdapter {

    protected List<D> mdata;

    @LayoutRes
    @Override
    public abstract int getItemLayout(int viewType);

    @Override
    public abstract void onBindViewHolder(RvViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public void setData(List<D> data) {
        this.mdata = data;
    }

    public void addData(List<D> data) {
        if (this.mdata == null) {
            this.mdata = new ArrayList<>();
        }
        this.mdata.addAll(data);
    }

    public boolean removeData(D data) {
        return this.mdata != null && this.mdata.remove(data);
    }

    public List<D> getData() {
        return this.mdata;
    }

    public D getItem(int position) {
        if (mdata != null && mdata.isEmpty()) {
            return mdata.get(position);
        }
        return null;
    }
}
