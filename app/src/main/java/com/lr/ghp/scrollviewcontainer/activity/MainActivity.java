package com.lr.ghp.scrollviewcontainer.activity;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.lr.ghp.scrollviewcontainer.R;
import com.lr.ghp.scrollviewcontainer.fragment.ListViewFragment;
import com.lr.ghp.scrollviewcontainer.fragment.SVFragment;
import com.lr.ghp.scrollviewcontainer.fragment.ScrollViewFragment;
import com.lr.ghp.scrollviewcontainer.view.ScrollViewContainer;
import com.lr.ghp.scrollviewcontainer.view.ViewPagerIndicator;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class MainActivity extends ActionBarActivity {
    @Optional @InjectView(R.id.scrollViewContainer)ScrollViewContainer scrollViewContainer;
    @Optional @InjectView(R.id.individual_share_indicator) ViewPagerIndicator viewPagerIndicator;
    @Optional @InjectView(R.id.individual_share_vp)ViewPager individual_share_vp;
    @Optional @InjectView(R.id.topView)ScrollView topView;
    private List<String> mInformationDatas = Arrays.asList("新闻", "公告", "资金", "资料");

//    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setScrollViewSlip(true);
//        initListener();
        initFragment();
    }
    //设置scrollview是否能够上下滑动
    public void setScrollViewSlip(boolean scrollViewSlip) {
        scrollViewContainer.setScrollViewSlip(scrollViewSlip);
    }
    private void initListener(){
        //当topview跟ScrollViewContainer获取的topview不是一个view时，需要重写该方法
        topView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                ScrollView sv= (ScrollView) v;
                if (v.getScrollY() == (sv.getChildAt(0).getMeasuredHeight() - sv.getHeight()))
                    scrollViewContainer.setTopScrollViewSlip(true);
                else
                    scrollViewContainer.setTopScrollViewSlip(false);
                return false;
            }
        });
    }
    //添加第二个界面的fragment
    private void initFragment(){
//        fragmentList.add(new SVFragment());
//        fragmentList.add(new ScrollViewFragment());
//        fragmentList.add(new SVFragment());
//        fragmentList.add(new SVFragment());
//        FragmentPagerAdapter mAdapter=new FragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        individual_share_vp.setAdapter(mAdapter);
        viewPagerIndicator.setTabNum(4);//省略默认为4
        viewPagerIndicator.initView();
        //设置关联的ViewPager
        viewPagerIndicator.setViewPager(individual_share_vp, 0);
        viewPagerIndicator.setTabItemTitles(mInformationDatas);
        viewPagerIndicator.highLightTextView(0);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public String getPageTitle(int position) {
            return mInformationDatas.get(position);
        }

        @Override
        public int getCount() {
            return mInformationDatas.size();
        }

        @Override
        public Fragment getItem(int position) {
            return  getDetailFragemt(position);
        }

        @Override
        public Parcelable saveState() {
            return null;// java.lang.IllegalStateException: Fragment no longer
            // exists for key f1: index 1
        }

    }

    private Fragment getDetailFragemt(int postion){
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        switch (postion){
            case 0:
                fragment = new SVFragment();
                bundle.putSerializable("index", 0);
                break;
            case 1:
                fragment = new ScrollViewFragment();
                bundle.putSerializable("index", 1);
                break;
            case 2:
                fragment = new ListViewFragment();
                bundle.putSerializable("index", 2);
                break;
            case 3:
                fragment = new SVFragment();
                bundle.putSerializable("index", 3);
                break;
        }
        if(fragment!=null) fragment.setArguments(bundle);
        return fragment;
    }
}
