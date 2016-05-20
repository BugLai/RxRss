package com.buglai.rxrss.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.buglai.rxrss.R;
import com.buglai.rxrss.adapter.FragmentAdapter;
import com.buglai.rxrss.adapter.FragmentNavigator;
import com.buglai.rxrss.fragment.HomeFragment;
import com.buglai.rxrss.utils.AppContextUtil;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends BaseActivity  implements BottomNavigation.OnMenuItemSelectionListener{

    private Toolbar toolbar;
    private BottomNavigation mBottomNavigation;
    private static final int DEFAULT_POSITION = 0;
    private FragmentNavigator mNavigator;
    private int mCurrentPos = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentAdapter(), R.id.id_container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.onCreate(savedInstanceState);
        mNavigator.showFragment(DEFAULT_POSITION);

        mBottomNavigation = (BottomNavigation) findViewById(R.id.BottomNavigation);
        mBottomNavigation.setOnMenuItemClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigator.onSaveInstanceState(outState);
    }

    @Override
    public String getUmengPageName() {
        return "MainActivity";
    }

    @Override
    public void onMenuItemSelect(@IdRes int i, int index) {
        mCurrentPos = index;
        mNavigator.showFragment(index);
    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int index) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_main_share) {

            return true;
        }

        if (id == R.id.action_main_setting) {

            return true;
        }

        if (id == R.id.action_main_comment) {
            AppContextUtil.launchAppDetail(getPackageName(), "com.coolapk.market", MainActivity.this);
            return true;
        }


        if (id == R.id.action_main_top) {
            Fragment currentFragment = mNavigator.getCurrentFragment();
            if (currentFragment instanceof HomeFragment) {
//                ((HomeFragment) currentFragment).scrollTop();
            } else {
                Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
