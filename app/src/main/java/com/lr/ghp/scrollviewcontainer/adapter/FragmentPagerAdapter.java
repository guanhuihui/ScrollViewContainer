package com.lr.ghp.scrollviewcontainer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.lr.ghp.scrollviewcontainer.fragment.SVFragment;

import java.util.ArrayList;

/**
 * Created by jimubox on 5/19/2015.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        SVFragment fragment= (SVFragment) fragments.get(position);
        fragment.setIndex(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
