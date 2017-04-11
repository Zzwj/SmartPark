//package com.xcoder.smartpark.service.bus;
//
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.text.TextUtils;
//
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.fragment.BusFragment;
//import com.xcoder.smartpark.fragment.bus.BusHomeFragment;
//import com.xcoder.smartpark.fragment.bus.BusTemporaryFragment;
//import com.xcoder.smartpark.fragment.bus.BusWrokFragment;
//import com.xcoder.smartpark.view.bus.BusView;
//
///**
// * Created by xcoder_xz on 2016/12/9 0009.
// * 班车的view
// */
//
//public class BusService {
//    @Injection
//    BusView bv;
//        public String wrokTag = "上班";
//        public String homeTag = "回家";
//        public String temporaryTag ="临时";
//        private BusFragment busFragment;
//        private BusHomeFragment busHomeFragment;
//        private BusWrokFragment busWrokFragment;
//        private BusTemporaryFragment busTemporaryFragment;
//        private FragmentManager childFragmentManager;
//        private FragmentTransaction fragmentTransaction;
//
//    public void init(BusFragment busFragment) {
//        // bv.bus_main_tag_tv.setText(Utils.pxtodip(busFragment.getContext(), 174) + "--" + Utils.pxtodip(busFragment.getContext(), 48));
//
//        this.busFragment = busFragment;
//        childFragmentManager = busFragment.getChildFragmentManager();
//    }
//
//    /**
//     * 控制按钮栏
//     */
//    public void controlButton(){
//
//    }
//
//    /**
//     * 连个按钮的颜色控制，和fragment的切换
//     *
//     * @param tag
//     */
//    public void selectFragment(String tag) {
//        bv.bus_main_wrok_bt.setTextColor(busFragment.getResources().getColor(R.color.app_green_color));
//        bv.bus_main_wrok_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
//        bv.bus_main_home_bt.setTextColor(busFragment.getResources().getColor(R.color.app_green_color));
//        bv.bus_main_home_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
//        bv.bus_main_temporary_bt.setTextColor(busFragment.getResources().getColor(R.color.app_green_color));
//        bv.bus_main_temporary_bt.setBackgroundResource(R.drawable.bus_main_bt_border_shape);
//        //
//        fragmentTransaction = childFragmentManager.beginTransaction();
//        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
//        if (TextUtils.equals(tag, wrokTag)) {//点击上班
//            bv.bus_main_wrok_bt.setTextColor(busFragment.getResources().getColor(R.color.white));
//            bv.bus_main_wrok_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
//
//            if (busWrokFragment != null)
//                fragmentTransaction.show(busWrokFragment);
//            else {
//                busWrokFragment = new BusWrokFragment();
//                fragmentTransaction.add(R.id.bus_main_fl, busWrokFragment);
//            }
//
//        } else if(TextUtils.equals(tag, homeTag)){//点击回家
//            bv.bus_main_home_bt.setTextColor(busFragment.getResources().getColor(R.color.white));
//            bv.bus_main_home_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
//
//                /* 如果fragment1已经存在则将其显示出来 */
//            if (busHomeFragment != null)
//                fragmentTransaction.show(busHomeFragment);
//            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
//            else {
//                busHomeFragment = new BusHomeFragment();
//                fragmentTransaction.add(R.id.bus_main_fl, busHomeFragment);
//            }
//        }else if(TextUtils.equals(tag, temporaryTag)){//点击临时
//            bv.bus_main_temporary_bt.setTextColor(busFragment.getResources().getColor(R.color.white));
//            bv.bus_main_temporary_bt.setBackgroundResource(R.drawable.bus_main_bt_no_border_shape);
//
//            if (busTemporaryFragment != null)
//                fragmentTransaction.show(busTemporaryFragment);
//            else {
//                busTemporaryFragment = new BusTemporaryFragment();
//                fragmentTransaction.add(R.id.bus_main_fl, busTemporaryFragment);
//            }
//        }
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//
//    /**
//     * 隐藏fagment
//     */
//    public void hideFragment(FragmentTransaction ft) {
//        if (busWrokFragment != null) {
//            ft.hide(busWrokFragment);
//        }
//        if (busHomeFragment != null) {
//            ft.hide(busHomeFragment);
//        }
//        if(busTemporaryFragment!=null){
//            ft.hide(busTemporaryFragment);
//        }
//    }
//}
