package com.lr.ghp.scrollviewcontainer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.lr.ghp.scrollviewcontainer.R;
import com.lr.ghp.scrollviewcontainer.adapter.FragmentPagerAdapter;
import com.lr.ghp.scrollviewcontainer.fragment.SVFragment;
import com.lr.ghp.scrollviewcontainer.view.ScrollViewContainer;
import com.lr.ghp.scrollviewcontainer.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class MainActivity extends ActionBarActivity {
    @Optional @InjectView(R.id.scrollViewContainer)ScrollViewContainer scrollViewContainer;
    @Optional @InjectView(R.id.individual_share_indicator) ViewPagerIndicator viewPagerIndicator;
    @Optional @InjectView(R.id.individual_share_vp)ViewPager individual_share_vp;
    private List<String> mInformationDatas = Arrays.asList("新闻", "公告", "资金", "资料");

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();
    }
    //设置scrollview是否能够上下滑动
    public void setScrollViewSlip(boolean scrollViewSlip) {
        scrollViewContainer.setScrollViewSlip(scrollViewSlip);
    }
    //添加第二个界面的fragment
    private void initFragment(){
        fragmentList.add(new SVFragment());
        fragmentList.add(new SVFragment());
        fragmentList.add(new SVFragment());
        fragmentList.add(new SVFragment());
        FragmentPagerAdapter mAdapter=new FragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        individual_share_vp.setAdapter(mAdapter);
        viewPagerIndicator.setTabNum(4);//省略默认为4
        viewPagerIndicator.initView();
        //设置关联的ViewPager
        viewPagerIndicator.setViewPager(individual_share_vp, 0);
        viewPagerIndicator.setTabItemTitles(mInformationDatas);
        viewPagerIndicator.highLightTextView(0);
    }
}
