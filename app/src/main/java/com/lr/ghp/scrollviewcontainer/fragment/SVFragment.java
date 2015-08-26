package com.lr.ghp.scrollviewcontainer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lr.ghp.scrollviewcontainer.R;
import com.lr.ghp.scrollviewcontainer.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SVFragment extends Fragment {
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SVFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.text_vp)TextView text_vp;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view=inflater.inflate(R.layout.fragment_sv, container, false);
        ButterKnife.inject(this, view);
        mainActivity= (MainActivity) getActivity();
        mainActivity.setScrollViewSlip(true);
//        text_vp.setText("第二个界面\nvp"+getIndex()+"\n普通界面");
        text_vp.setText("第二个界面\nvp" + getArguments().getSerializable("index")+"\n普通界面");
        return view;
    }


}
