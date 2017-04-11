package com.xcoder.smartpark.service.other;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.adapter.recyclerview.RvItemListener;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.activity.other.PatrolManActivity;
import com.xcoder.smartpark.adapter.other.PathRecyclerViewAdapter;
import com.xcoder.smartpark.adapter.other.PatrolManAdapter;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusSubSiteMoudel;
import com.xcoder.smartpark.moudel.OtherPatrolManLineMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.other.PatrolManView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiangkun on 16/12/30.
 * 巡更员
 */

public class PatrolManService {

    @Injection
    private PatrolManView patrolManView;

    private PatrolManActivity patrolManActivity;

    private PathRecyclerViewAdapter pathRecyclerViewAdapter;

    private AppUser userInfo;

    private List<OtherPatrolManLineMoudle> listLine;

    private PatrolManAdapter patrolManAdapter;

    public RefreshAndload refreshAndload;

    private Timer timer;
    private TimerTask timerTask;

    public void init(PatrolManActivity patrolManActivity) {
        this.patrolManActivity = patrolManActivity;
        userInfo = ConfigSP.getUserInfo();
        initview();
        //initData();
    }


    public void initview() {
        try {
            //RecyclerView处理
            LinearLayoutManager layoutManagerpath = new LinearLayoutManager(patrolManActivity);
            layoutManagerpath.setOrientation(OrientationHelper.HORIZONTAL);//水平
            patrolManView.other_patrolman_main_rv.setLayoutManager(layoutManagerpath);
            //RecyclerView设置适配器
            pathRecyclerViewAdapter = new PathRecyclerViewAdapter();
            patrolManView.other_patrolman_main_rv.setAdapter(pathRecyclerViewAdapter);

            pathRecyclerViewAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
                @Override
                public void onItemClickListener(View view, int postion) {
                    pathRecyclerViewAdapter.indexPos = postion;
                    pathRecyclerViewAdapter.notifyDataSetChanged();
                    patrolManAdapter.setListData(listLine.get(postion).getPLIST());
                }
            });


            //添加头部必须在设置适配器之前
            patrolManView.other_patrolman_lv.addHeaderView(new View(patrolManActivity));
            refreshAndload = new RefreshAndload(patrolManActivity, patrolManView.other_patrolman_lv, patrolManView.other_patrolman_srl);
            refreshAndload.setOnRefreshListener(new RefreshAndload.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    patrolManAdapter.notifyDataSetChanged();
                    refreshAndload.stopRefreshAndLoad();
                }
            });

            refreshAndload.setOnLoadListener(new RefreshAndload.OnLoadListener() {
                @Override
                public void onLoad() {
                    refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
                }
            });

            //ListView处理
            patrolManAdapter = new PatrolManAdapter(patrolManActivity);
            patrolManView.other_patrolman_lv.setAdapter(patrolManAdapter);

        } catch (Exception e) {
            LogUtils.e("Exception:"+e.getMessage());
            e.printStackTrace();
        }

    }

    public void initData() {
        AppProgressDialog.showProgress(patrolManActivity);
        Rest rest = new Rest("/apppatrol/loadpatrolline.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.post("PatrolManActivityPatrolManActivityPatrolManActivity",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(patrolManActivity);
                try {
                    listLine = JSON.parseArray(jsonObject.getJSONArray("dataset_line").toString(), OtherPatrolManLineMoudle.class);
                    pathRecyclerViewAdapter.setData(listLine);
                    pathRecyclerViewAdapter.notifyDataSetChanged();

                    if(listLine != null && listLine.size() > 0) {
                        //默认显示第一个
                        pathRecyclerViewAdapter.indexPos = 0;
                        pathRecyclerViewAdapter.notifyDataSetChanged();
                        patrolManAdapter.setListData(listLine.get(0).getPLIST());
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception="+e);
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                ToastUtil.showToast(msg);
                AppProgressDialog.dismiss(patrolManActivity);
            }

            @Override
            public void onError(Exception exception) {
                ToastUtil.noNet();
                AppProgressDialog.dismiss(patrolManActivity);
            }
        });
    }


    public void getdate() {
        Rest rest = new Rest("/apppatrol/loadpatrolline.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.post("PatrolManActivity",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(patrolManActivity);
                try {
                    listLine = JSON.parseArray(jsonObject.getJSONArray("dataset_line").toString(), OtherPatrolManLineMoudle.class);
                    pathRecyclerViewAdapter.setData(listLine);
                    pathRecyclerViewAdapter.notifyDataSetChanged();

                    if(listLine != null && listLine.size() > 0) {
                        if(pathRecyclerViewAdapter.indexPos == -1) {
                            //用户没有点击，也没有被设置为默认值0
                            //默认显示第一个
                            pathRecyclerViewAdapter.indexPos = 0;
                            pathRecyclerViewAdapter.notifyDataSetChanged();
                            patrolManAdapter.setListData(listLine.get(0).getPLIST());
                        }else {
                            //这时不用设置indexPos
                            pathRecyclerViewAdapter.notifyDataSetChanged();
                            patrolManAdapter.setListData(listLine.get(pathRecyclerViewAdapter.indexPos).getPLIST());
                        }
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception="+e);
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }



    /**
     * 开始循环加载
     */
    public void startLoad() {
        try {
            if (timer == null) {
                timer = new Timer();
            } else {
                //从此计时器的任务队列中移除所有已取消的任务。
                timer.purge();
            }
            if (timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        getdate();
                    }
                };
            }
            if (timer != null && timerTask != null)
                timer.schedule(timerTask, 1000, 5000);//循环访问服务器，拿车的坐标
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止循环加载
     */
    public void stopLoad() {
        try {
            if (timer != null && timerTask != null) {
                timer.cancel();
                timer = null;
                timerTask = null;
                OkHttpUtils.getInstance().cancelTag("PatrolManActivity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
