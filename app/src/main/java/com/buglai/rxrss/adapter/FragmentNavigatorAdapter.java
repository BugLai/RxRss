package com.buglai.rxrss.adapter;

import android.support.v4.app.Fragment;

/**
 * https://github.com/Aspsine/FragmentNavigator
 * Created by aspsine on 16/3/31.
 */
public interface FragmentNavigatorAdapter {

    public Fragment onCreateFragment(int position);

    public String getTag(int position);

    public int getCount();
}
