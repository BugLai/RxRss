package com.buglai.rxrss.adapter;

import android.support.v4.app.Fragment;

import com.buglai.rxrss.fragment.ColumnFragment;
import com.buglai.rxrss.fragment.FavFragment;
import com.buglai.rxrss.fragment.HomeFragment;
import com.buglai.rxrss.fragment.SettingFragment;

/**
 * https://github.com/Aspsine/FragmentNavigator
 * Created by aspsine on 16/3/31.
 */
public class FragmentAdapter implements FragmentNavigatorAdapter{
    private static final String TABS[] = {"首页", "专题", "收藏","设置"};

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position);
            case 1:
                return ColumnFragment.newInstance(0);
            case 2:
                return FavFragment.newInstance();
            case 3:
                return SettingFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public String getTag(int position) {
        return TABS[position];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
