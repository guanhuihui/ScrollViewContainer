package com.lr.ghp.scrollviewcontainer.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lr.ghp.scrollviewcontainer.R;
import com.lr.ghp.scrollviewcontainer.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollViewFragment extends Fragment implements View.OnTouchListener{


    public ScrollViewFragment() {
        // Required empty public constructor
    }
    @InjectView(R.id.scrollView)ScrollView scrollView;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_scroll, container, false);
        ButterKnife.inject(this,view);
        mainActivity= (MainActivity) getActivity();
        mainActivity.setScrollViewSlip(true);
        scrollView.smoothScrollTo(0, 0);
        scrollView.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollView.smoothScrollTo(0,0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()){
            case R.id.scrollView:
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        int scrollY=view.getScrollY();
                        if(scrollY==0){
                            mainActivity.setScrollViewSlip(true);
                        }else{
                            mainActivity.setScrollViewSlip(false);
                        }
                        break;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
