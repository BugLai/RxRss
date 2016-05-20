package com.buglai.rxrss.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.buglai.rxrss.R;
import com.buglai.rxrss.adapter.FragmentListAdapter;

/**
 * Created by buglai on 5/19/16.
 */
public class ColumnFragment extends  BaseFragment {

    private final static String TAG = ColumnFragment.class.getSimpleName();

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private FragmentListAdapter mFragmentAdapter;
    private final static String POSITION = "position";
    private int position = 0;

    private  String[] mTitles;

    public static ColumnFragment newInstance(int postion) {
        ColumnFragment fragment = new ColumnFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, postion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getUmengPageName() {
        return "ColumnFragment";
    }




    @Override
    protected int getLayoutId() {
        return R.layout.fragment_column;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {

        mTablayout = (TabLayout) mRootView.findViewById(R.id.tabs);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.pager);

        mTitles = getResources().getStringArray(R.array.news_array);
        position = getArguments().getInt(POSITION);

        mFragmentAdapter = new FragmentListAdapter(getChildFragmentManager(),mTitles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);
    }

}
