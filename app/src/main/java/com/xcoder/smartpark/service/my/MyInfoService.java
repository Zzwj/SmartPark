package com.xcoder.smartpark.service.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.login.LoginActivity;
import com.xcoder.smartpark.activity.my.MyInfoActivity;
import com.xcoder.smartpark.adapter.my.MyInfoChooseAdapter;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.UserIdentity;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.my.MyInfoView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangkun on 16/12/16.
 */

public class MyInfoService {

    @Injection
    private MyInfoView myInfoView;

    private MyInfoActivity myInfoActivity;

    //用户性别
    private String gender = "";

    //服务器获取的公司、部门、职位信息列表
    private List<UserIdentity> companyList = null;
    private List<UserIdentity> deptList = null;
    private List<UserIdentity> roleList = null;
    //用户选择的公司、部门、职位信息
    private UserIdentity company = new UserIdentity();
    private UserIdentity dept = new UserIdentity();
    private UserIdentity role= new UserIdentity();

    //SP中存储的用户信息
    private AppUser appUser;

    //调用接口获取到的用户信息
    private Map infomap;
    //是否真正拍照
    private Boolean havepic = false;

    private String flag = "";

    public void init(MyInfoActivity myInfoActivity) {
        this.myInfoActivity = myInfoActivity;
        myInfoView.my_myinfo_gender_rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.my_myinfo_rb_male:
                        gender = "0";
                        break;
                    case R.id.my_myinfo_rb_female:
                        gender = "1";
                        break;
                }

            }
        });

        Intent intent = myInfoActivity.getIntent();
        Bundle bundle = intent.getExtras();
        if(null != bundle) {
            flag = bundle.getString("flag");
        }

        //如果不是新用户，公司、部门、职务、工号 是不可以修改的
        if(!"new".equals(flag)) {
//            myInfoView.my_myinfo_company_rl.setBackgroundColor(myInfoActivity.getResources().getColor(R.color.app_gray_color));
//            myInfoView.my_myinfo_department_rl.setBackgroundColor(myInfoActivity.getResources().getColor(R.color.app_gray_color));
//            myInfoView.my_myinfo_job_rl.setBackgroundColor(myInfoActivity.getResources().getColor(R.color.app_gray_color));
//            myInfoView.my_myinfo_id_rl.setBackgroundColor(myInfoActivity.getResources().getColor(R.color.app_gray_color));
            myInfoView.my_myinfo_company_rl.setClickable(false);
            myInfoView.my_myinfo_department_rl.setClickable(false);
            myInfoView.my_myinfo_job_rl.setClickable(false);
            myInfoView.my_myinfo_id_rl.setClickable(false);


            myInfoView.my_myinfo_number_et.setEnabled(false);
        }

        initData();
    }

    //初始化数据
    public void initData() {
        AppProgressDialog.showProgress(myInfoActivity);

        //获取登录后存储的appuser对象
        appUser = ConfigSP.getUserInfo();
        AppProgressDialog.dismiss(myInfoActivity);
        Rest rest0 = new Rest("/appuser/findUserByUId.nla",appUser.getUSER_ID(), appUser.getUSER_TOKEN());
        rest0.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(myInfoActivity);

                try {
                    JSONObject dataset = jsonObject.getJSONObject("dataset");
                    infomap = JSON.parseObject(dataset.toString(), Map.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(e);
                }
                initView();
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(myInfoActivity);
                ToastUtil.showToast(msg);

            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(myInfoActivity);
                LogUtils.e("exception:"+exception.getMessage());
                ToastUtil.showToast("未获取到用户信息，请填写");
            }
        });


        Rest rest = new Rest("/appuser/listJob.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(myInfoActivity);
                try{
                    JSONObject dataMapList = jsonObject.getJSONObject("dataMapList");
                    //部门列表
                    deptList = JSON.parseArray(dataMapList.getJSONArray("deptList").toString(), UserIdentity.class);
                    LogUtils.d("deptList="+deptList.size());
                    //公司列表
                    companyList = JSON.parseArray(dataMapList.getJSONArray("companyList").toString(), UserIdentity.class);
                    LogUtils.d("companyList="+companyList.size());
                    //职位列表
                    roleList = JSON.parseArray(dataMapList.getJSONArray("roleList").toString(), UserIdentity.class);
                    LogUtils.d("roleList="+roleList.size());

                }catch (Exception exc){
                    LogUtils.e("Exception:"+exc.getMessage());
                    onError(exc);
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(myInfoActivity);
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(myInfoActivity);
                LogUtils.e("exception:"+exception.getMessage());
                ToastUtil.showToast("网络繁忙，请稍后再试");
            }
        });

    }

    //初始化界面
    public void initView() {
        try {
            if(null == infomap) {
                return;
            }else {
                LogUtils.d("infomap === "+ infomap.toString());
            }
            Glide.with(myInfoActivity)
                    .load(AppNet.API_IMAGE + infomap.get("HEADIMGURL"))
                    .placeholder(R.drawable.my_defaulthead_ico)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            myInfoView.my_myinfo_header_iv.setImageDrawable(resource);
                        }
                    });

            myInfoView.my_myinfo_name_et.setText(infomap.get("USERNAME") == null ? "" :infomap.get("USERNAME").toString());

            if(infomap.get("SEX").toString().equals("0")) {
                LogUtils.d("sex = 0");
                myInfoView.my_myinfo_rb_male.setChecked(true);
                myInfoView.my_myinfo_rb_female.setChecked(false);
            }else {
                LogUtils.d("sex != 0");
                myInfoView.my_myinfo_rb_male.setChecked(false);
                myInfoView.my_myinfo_rb_female.setChecked(true);
            }

            myInfoView.my_myinfo_showcompany_tv.setText(infomap.get("COMPANY_NAME") == null ? "" :infomap.get("COMPANY_NAME").toString());
            company.setZD_ID(infomap.get("COMPANY_ID") == null ? "" : infomap.get("COMPANY_ID").toString().trim());

            myInfoView.my_myinfo_showdepartment_tv.setText(infomap.get("DEPT_NAME") == null ? "" :infomap.get("DEPT_NAME").toString());
            dept.setZD_ID(infomap.get("DEPT_ID") == null ? "" : infomap.get("DEPT_ID").toString());

            myInfoView.my_myinfo_showjob_tv.setText(infomap.get("ROLE_NAME") == null ? "" : infomap.get("ROLE_NAME").toString());
            role.setZD_ID(infomap.get("ROLE_ID") == null ? "" : infomap.get("ROLE_ID").toString());

            myInfoView.my_myinfo_number_et.setText(infomap.get("NUMBER") == null ? "" :infomap.get("NUMBER").toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Exception:"+e.toString());
        }
    }

    //抽取数组
    public String[] convertObjListtoStringArray(List<UserIdentity> list) {
        if(null == list || list.size() == 0) {
            return null;
        }
        List<String> mItems = new ArrayList<String>();
        for(int i=0; i<list.size(); i++) {
            mItems.add(list.get(i).getNAME());
        }
        LogUtils.d("mItems="+mItems.size());
        int size = mItems.size();
        return (String[])mItems.toArray(new String[mItems.size()]);
    }

    public void showdialog(final List<UserIdentity>  list, String tittle, final String flag) {
        MyInfoChooseAdapter myInfoChooseAdapter = new MyInfoChooseAdapter();
        myInfoChooseAdapter.setListData(list);

        View view = LayoutInflater.from(myInfoActivity).inflate(R.layout.my_myinfo_choose_list_main, null);
        ListView lv = (ListView) view.findViewById(R.id.my_myinfo_choose_lv);
        lv.setAdapter(myInfoChooseAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(myInfoActivity);
        builder.setTitle(tittle);
        builder.setView(view);
        final AlertDialog ad = builder.create();
        ad.show();

        WindowManager manager = myInfoActivity.getWindowManager();
        Display d = manager.getDefaultDisplay();
        Window window =ad.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.height = (int) (d.getHeight() * 0.5);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        ad.getWindow().setAttributes(params);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //ToastUtil.showToast("点击"+i);
                if(flag.equals("company")) {
                    company = list.get(i);
                    myInfoView.my_myinfo_showcompany_tv.setText(company.getNAME());
                }else if(flag.equals("dept")) {
                    dept = list.get(i);
                    myInfoView.my_myinfo_showdepartment_tv.setText(dept.getNAME());
                }else if(flag.equals("role")) {
                    role = list.get(i);
                    myInfoView.my_myinfo_showjob_tv.setText(role.getNAME());
                }
                ad.dismiss();
            }
        });
    }

    //选择公司
    public void chooseCompany() {
        showdialog(companyList, "请选择公司", "company");
    }

    //选择部门
    public void chooseDepartment() {
        showdialog(deptList, "请选择部门", "dept");
    }

    //选择职位
    public void chooseJob() {
        showdialog(roleList, "请选择职位", "role");
    }

    /**
     * 提交用户信息
     */
    public void submituserinfo() {

        try {
            String number = myInfoView.my_myinfo_number_et.getText().toString().trim();
            String name = myInfoView.my_myinfo_name_et.getText().toString().trim();

            if(number.equals("")|| name.equals("")) {
                ToastUtil.showToast("请填写完整信息");
                return;
            }

            AppProgressDialog.showProgress(myInfoActivity);

            Rest rest=new Rest("/appuser/editU.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
            rest.addParam("PHONE", appUser.getPHONE());
            rest.addParam("COMPANY_ID",company.getZD_ID());
            rest.addParam("DEPT_ID",dept.getZD_ID());
            rest.addParam("ROLE_ID",role.getZD_ID());
            rest.addParam("SEX",gender);
            rest.addParam("NUMBER",number);
            rest.addParam("USERNAME",name);//接口要求传这个
            rest.addParam("NAME",name);//不传这个就报错
            if(havepic) {
                File file = new File(getSDPath() +"/headpic/myheadpic.png");
                rest.post("myInfoActivity","file",file,callback);
            }else {
                rest.post(callback);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Exception:"+e.toString());
        }
    }

    Callback callback = new Callback() {
        @Override
        public void onSuccess(JSONObject jsonObject, String state, String msg) {
            AppProgressDialog.dismiss(myInfoActivity);
            try{
                JSONObject jb = jsonObject.getJSONObject("dataset");
                AppUser appUser = JSON.parseObject(jb.toString(), AppUser.class);
                LogUtils.d("appUser = " + appUser.toString());
                ConfigSP.saveUserInfo(appUser);

                AppUser au = ConfigSP.getUserInfo();

                LogUtils.d("spappUser = " + appUser.toString());

                if("new".equals(flag)) {
                    ToastUtil.showToast("提交成功,请等待管理员审核");
                    myInfoActivity.openActivity(LoginActivity.class);
                }else {
                    ToastUtil.showToast("提交成功");
                    myInfoActivity.finish();
                }

                EventBus.getDefault().post("刷新用户信息");

                //提交资料后需要提示用户正在审核
                //myInfoActivity.openActivity(MainActivity.class);
            }catch (Exception exc){
                LogUtils.d("Exception:"+exc.getMessage());
                onError(exc);
            }
        }

        @Override
        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
            AppProgressDialog.dismiss(myInfoActivity);
            ToastUtil.showToast(msg);
        }

        @Override
        public void onError(Exception exception) {
            AppProgressDialog.dismiss(myInfoActivity);
            ToastUtil.showToast("网络繁忙，请稍后再试");
        }
    };

    //拍照相关开始
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";

    //启动相机
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
        myInfoActivity.startActivityForResult(intent, PHOTOHRAPH);
    }

    //启动相册
    public void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        myInfoActivity.startActivityForResult(intent, PHOTOZOOM);
    }

    //activity中onActivityResult处理方法
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null){
            return;
        }

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件
                myInfoView.my_myinfo_header_iv.setImageBitmap(photo);
                try {
                    saveFile(photo,"myheadpic.png");
                    havepic = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e("IOException:"+e.getMessage());
                }
            }
        }

    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/headpic/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    //裁剪图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        myInfoActivity.startActivityForResult(intent, PHOTORESOULT);
    }
    //拍照相关结束

}
