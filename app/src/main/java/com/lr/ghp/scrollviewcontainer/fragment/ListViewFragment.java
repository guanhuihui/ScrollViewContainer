package com.lr.ghp.scrollviewcontainer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.lr.ghp.scrollviewcontainer.R;
import com.lr.ghp.scrollviewcontainer.activity.MainActivity;
import com.lr.ghp.scrollviewcontainer.adapter.ListAdapter;
import com.lr.ghp.scrollviewcontainer.view.pullListView.OnPullListViewListener;
import com.lr.ghp.scrollviewcontainer.view.pullListView.PullListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {


    public ListViewFragment() {
        // Required empty public constructor
    }
    @InjectView(R.id.pullListView)PullListView pullListView;
    private MainActivity mainActivity;
    private ListAdapter listAdapter;
    private List<String> dataList=new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.inject(this,view);
        mainActivity= (MainActivity) getActivity();
        mainActivity.setScrollViewSlip(true);
        initView();
        initListener();
        getData();
        return view;
    }

    private void initView(){
        listAdapter=new ListAdapter(mainActivity,dataList);
        pullListView.setAdapter(listAdapter);
        TextView v=new TextView(mainActivity);
        v.setHeight(0);
        pullListView.addHeaderView(v, null, true);
    }

    private void initListener(){
        pullListView.setPullRefreshEnable(false);
        pullListView.setPullLoadEnable(true);
        pullListView.setOnPullListViewListener(new OnPullListViewListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
                getDataMore();
            }
        });
        pullListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int firstVisibleItem = pullListView.getFirstVisiblePosition();
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (firstVisibleItem == 0) {
                        if(view.getScrollY()==0){
                            mainActivity.setScrollViewSlip(true);
                        }else {
                            mainActivity.setScrollViewSlip(false);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {//listview滑到顶部
                    if(view.getScrollY()==0){
                        mainActivity.setScrollViewSlip(true);
                    }else{
                        mainActivity.setScrollViewSlip(false);
                    }
                } else {
                    mainActivity.setScrollViewSlip(false);
                }
            }
        });
    }
    private void getData(){
        dataList.clear();
        for(int i=0;i<10;i++){
            dataList.add("LIST"+" "+i);
        }
        pullListView.setCount(100);
        pullListView.setTotalItemCount(dataList.size());
        listAdapter.updateList(dataList);
        pullListView.stopRefresh();
    }
    private void getDataMore(){
        if(dataList.size()<100){
            int start=dataList.size();
            for(int i=0;i<10;i++){
                dataList.add("LIST" + " " + (i+start));
            }
        }
        pullListView.setCount(100);
        pullListView.setTotalItemCount(dataList.size());
        listAdapter.updateList(dataList);
        pullListView.stopLoadMore();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (listAdapter != null && listAdapter.getCount() > 0) {
                pullListView.setSelection(0);
                pullListView.scrollTo(0,0);
            }
        }
    }
}
