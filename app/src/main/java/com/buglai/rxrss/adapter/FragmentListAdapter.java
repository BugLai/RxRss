package com.buglai.rxrss.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.buglai.rxrss.fragment.SubscribeFragment;

/**
 * Created by buglai on 5/19/16.
 */
public class FragmentListAdapter extends FragmentStatePagerAdapter {

    private  String[] mTitles;

    public FragmentListAdapter(FragmentManager fm, String[] titles ) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SubscribeFragment.newInstance(position,mTitles[position]);
    }


    @Override
    public int getCount() {
        return mTitles==null?0:mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
