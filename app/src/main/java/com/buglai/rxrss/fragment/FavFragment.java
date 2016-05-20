package com.buglai.rxrss.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.buglai.rxrss.App;
import com.buglai.rxrss.R;
import com.buglai.rxrss.activity.ContentActivity;
import com.buglai.rxrss.adapter.NewsListAdapter;
import com.buglai.rxrss.model.Item;
import com.buglai.rxrss.net.RssApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buglai on 5/18/16.
 */
public class FavFragment extends BaseFragment implements NewsListAdapter.OnItemClickListener{

    private RecyclerView mRv;
    private TextView mTv;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsListAdapter mNewsListAdapter;
    List<Item> storiesLocal = new ArrayList<>();

    public static FavFragment newInstance() {
        FavFragment fragment = new FavFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fav;
    }

    @Override
    public String getUmengPageName() {
        return "FavFragment";
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {

        mRv = (RecyclerView)mRootView.findViewById(R.id.id_fav_rv);
        mTv = (TextView) mRootView.findViewById(R.id.id_no_news);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(mLinearLayoutManager);
        mRv.setHasFixedSize(true);
        mRv.setItemAnimator(new DefaultItemAnimator());
        mNewsListAdapter = new NewsListAdapter(getActivity(), storiesLocal);
        mNewsListAdapter.setOnItemListener(FavFragment.this);
        mRv.setAdapter(mNewsListAdapter);
        updateFavNews();
    }


    public void updateFavNews() {
        List<Item> stories = App.cacheHelper.getAsSerializable(RssApi.KEY_FAV_NEWS);
        if (stories == null) {
            mTv.setVisibility(View.VISIBLE);
            Snackbar.make(mRv, "暂无收藏········", Snackbar.LENGTH_SHORT).show();
            return;
        }
        storiesLocal.clear();
        for (Item bean : stories) {
            storiesLocal.add(bean);
        }
        mNewsListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, Item item, int pos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContentActivity.NEW_ITEM, item);
        item.setIsRead(true);
        storiesLocal.set(pos, item);
        mNewsListAdapter.notifyItemChanged(pos);
        ContentActivity.startActivity(getActivity(), bundle);

    }

}
