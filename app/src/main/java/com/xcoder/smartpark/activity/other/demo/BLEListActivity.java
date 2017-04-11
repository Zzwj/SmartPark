package com.xcoder.smartpark.activity.other.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkun on 17/3/6.
 */


public class BLEListActivity extends BaseActivity {

    private BLEListAdapter adapter;
    private LinearLayout other_bleactivity_black;
    private ListView other_blelist_lv;


    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        setContentView(R.layout.other_blelist_main);
        initdata();
    }

    private void initdata() {
        Bundle bundle = getIntent().getExtras();
        List<BLEEntity> BLEEntityList = (List<BLEEntity>) bundle.getSerializable("BLEEntityList");
        if(BLEEntityList != null && BLEEntityList.size() > 0) {
            LogUtils.d("BLEEntityList.size()  = " + BLEEntityList.size());
        }else {
            return;
        }

        other_bleactivity_black = (LinearLayout) findViewById(R.id.other_bleactivity_black);
        other_bleactivity_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        other_blelist_lv = (ListView) findViewById(R.id.other_blelist_lv);

        adapter = new BLEListAdapter(BLEListActivity.this);

        other_blelist_lv.setAdapter(adapter);
        adapter.setData(BLEEntityList);

    }

    @Override
    public void closeActivity() {

    }

    public class BLEListAdapter extends BaseAdapter{

        private Context context;
        private LayoutInflater inflater;
        private List<BLEEntity> BLEEntityList = new ArrayList();

        public BLEListAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        public void setData(List<BLEEntity> BLEEntityList) {
            this.BLEEntityList = BLEEntityList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return BLEEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return BLEEntityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.other_bleactivity_list_item,null);
                holder = new ViewHolder();
                holder.other_assetname_tv = (TextView) convertView.findViewById(R.id.other_assetname_tv);
                holder.other_assetnote_tv = (TextView) convertView.findViewById(R.id.other_assetnote_tv);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
            }

            holder.other_assetname_tv.setText( BLEEntityList.get(position).getASSETNAME());
            holder.other_assetnote_tv.setText( BLEEntityList.get(position).getNOTES());
            return convertView;
        }
    }

    public final class ViewHolder{
        public TextView other_assetname_tv;
        public TextView other_assetnote_tv;
    }


}
