package com.xcoder.lib.adapter.recyclerview;

import android.view.View;

/**
 * RecyclerView的条目事件
 * Created by xz on 2016/9/9 0009.
 */
public class RvItemListener {
    public interface onItemClickListener {
        public void onItemClickListener(View view, int postion);
    }

    public interface onLongItemClickListener {
        public void onLongItemClickListener(View view, int postion);
    }
}
