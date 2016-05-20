package com.buglai.rxrss.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buglai.rxrss.App;
import com.buglai.rxrss.R;
import com.buglai.rxrss.activity.ContentActivity;
import com.buglai.rxrss.adapter.NewsListAdapter;
import com.buglai.rxrss.model.Item;
import com.buglai.rxrss.model.Rss;
import com.buglai.rxrss.net.HttpMethods;
import com.buglai.rxrss.net.RssApi;
import com.buglai.rxrss.utils.AppContextUtil;
import com.buglai.rxrss.utils.SPUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by buglai on 5/18/16.
 */
public class HomeFragment extends BaseFragment implements NewsListAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private ContentLoadingProgressBar mProgressBar;
    private TextView mTextNetwork;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsListAdapter mNewsListAdapter;
    List<Item> newsLocal = new ArrayList<>();

    public static HomeFragment newInstance(int pos) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public String getUmengPageName() {
        return "HomeFragment";
    }


    @Override
    protected void onCreated(Bundle savedInstanceState) {
        mRecycleView = (RecyclerView) mRootView.findViewById(R.id.id_news_rv);
        mTextNetwork = (TextView) mRootView.findViewById(R.id.id_news_textnet);
        mProgressBar = (ContentLoadingProgressBar) mRootView.findViewById(R.id.id_news_progress);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.id_news_fresh);

        //设置加载圈的背景颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置加载圈圈的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mProgressBar.show();

        ArrayList<Item> items = App.cacheHelper.getAsSerializable(RssApi.KEY_HOME_NEWS);
        if (items !=null){
            handleResultData(items);
            return;
        }
        initNetWorkData();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        initNetWorkData();
    }

    private void initNetWorkData(){

        if (! AppContextUtil.isNetworkAvailable(getActivity())){
            mProgressBar.hide();
            mTextNetwork.setVisibility(View.VISIBLE);
        }

        HttpMethods.getInstance().getHomeData(new Subscriber<Rss>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mProgressBar.hide();
                mTextNetwork.setText("Error:"+e);
                mTextNetwork.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(Rss rss) {
                List<Item> items = rss.getChannel().getItems();
                for (Item item:items) {

                    if (item.getDescription() != null){
                        Document desDoc = Jsoup.parse(item.getDescription());
                        if (desDoc != null && desDoc.select("img").first() != null){
                            String img1 = desDoc.select("img").first().attr("src");
                            item.setIcon(img1);
                        }
                    }

                    if(item.getContent() != null){
                        Document contentDoc = Jsoup.parse(item.getContent());
                        if (contentDoc != null && contentDoc.select("img").first()!= null){
                            String img2 = contentDoc.select("img").first().attr("src");
                            item.setIcon(img2);
                        }else if (contentDoc != null && contentDoc.select("iframe").first()!= null){
                            String img3 = contentDoc.select("iframe").first().attr("src");
                            item.setIcon(img3);

                        }
                    }
                }
                handleResultData(items);
                App.cacheHelper.put(RssApi.KEY_HOME_NEWS,(Serializable)items);
            }
        });

    }

    private void handleResultData(List<Item> itemList) {

        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.hide();
        //配置RecyclerView
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        newsLocal = itemList;
        mNewsListAdapter = new NewsListAdapter(getActivity(),newsLocal);
        if ((Boolean) SPUtils.get(getActivity(), RssApi.SET_ANIM, true)) {
            mNewsListAdapter.setAnim(true);
        }else{
            mNewsListAdapter.setAnim(false);
        }

        mNewsListAdapter.setOnItemListener(HomeFragment.this);
        mRecycleView.setAdapter(mNewsListAdapter);
    }

    @Override
    public void onItemClick(View view, Item item, int pos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContentActivity.NEW_ITEM, item);
        item.setIsRead(true);
        newsLocal.set(pos, item);
        mNewsListAdapter.notifyItemChanged(pos);
        ContentActivity.startActivity(getActivity(), bundle);

    }

}
