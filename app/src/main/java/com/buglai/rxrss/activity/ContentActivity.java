package com.buglai.rxrss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buglai.rxrss.App;
import com.buglai.rxrss.R;
import com.buglai.rxrss.model.ContentBean;
import com.buglai.rxrss.model.Item;
import com.buglai.rxrss.net.APIJsoup;
import com.buglai.rxrss.net.RssApi;
import com.buglai.rxrss.utils.NetUtil;
import com.buglai.rxrss.utils.SPUtils;
import com.buglai.rxrss.utils.imageloader.ImageLoaderFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by buglai on 16-5-12.
 */
public class ContentActivity extends BaseActivity{

    public static String NEW_ITEM = "newInfoKey";
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final int backgroundColor = Color.argb(51, 0, 0, 0);
    private Item newsItem;
    private List<Item> mFavlist;

    boolean isFav;

    Toolbar toolbar;

    CollapsingToolbarLayout toolbarLayout;

    ImageView mHeader;

    TextView mTitle;

    ContentLoadingProgressBar mLoading;

    WebView mWebView;

    FloatingActionButton mFab;


    TextView mContentTv;

    TextView mAuthorTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_content;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        //覆盖掉变色状态栏的方法
        initStatusBar();
        Bundle bundle = getIntent().getExtras();
        newsItem = (Item) bundle.getSerializable(NEW_ITEM);
        init();
    }

    protected void initStatusBar() {
    }

    @Override
    public String getUmengPageName() {
        return "ContentActivity";
    }


    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mHeader = (ImageView) findViewById(R.id.iv_news);
        mTitle = (TextView) findViewById(R.id.id_detail_tv_title);
        mLoading = (ContentLoadingProgressBar) findViewById(R.id.id_detail_load_news);
        mWebView = (WebView) findViewById(R.id.id_detail_wv_news);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mContentTv = (TextView) findViewById(R.id.id_detail_tv);
        mAuthorTv = (TextView)findViewById(R.id.id_detail_tv_author);



        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ViewCompat.setTransitionName(mHeader, VIEW_NAME_HEADER_IMAGE);

        toolbarLayout.setTitle("");
        toolbarLayout.setTitleEnabled(true);
        mLoading.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mLoading.show();
        mHeader.setBackgroundColor(backgroundColor);

        isFav = (Boolean) SPUtils.get(getApplicationContext(), "" + newsItem.getLink(), false);
        mFab.setImageResource(isFav ? R.drawable.ic_star_full : R.drawable.ic_star_null);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFav = (Boolean) SPUtils.get(getApplicationContext(), "" + newsItem.getLink(), false);
                //如果传过来的信息是空，就不能收藏
                if (newsItem == null) {
//                    Snackbar.make(view, "轮播图不能收藏，别问为什么", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (isFav) {
                    SPUtils.remove(getApplicationContext(), newsItem.getLink() + "");
                    Snackbar.make(view, "取消成功", Snackbar.LENGTH_SHORT).show();
                    mFab.setImageResource(R.drawable.ic_star_null);

                    handleFav(newsItem, false);
                } else {
                    SPUtils.put(getApplicationContext(),newsItem.getLink() + "", true);
                    Snackbar.make(view, "收藏成功", Snackbar.LENGTH_SHORT).show();
                    mFab.setImageResource(R.drawable.ic_star_full);
                    handleFav(newsItem, true);
                }
            }
        });

        initNewsContent();

    }

    private void initNewsContent() {
        ContentBean contentBean = App.cacheHelper.getAsSerializable(newsItem.getLink() + "");
        if (contentBean != null) {
            handleContent(contentBean);
            return;
        }

        Observable.create(new Observable.OnSubscribe<ContentBean>() {
            @Override
            public void call(Subscriber<? super ContentBean> subscriber) {
                ContentBean contentBean = APIJsoup.getNewsContent(newsItem);
                subscriber.onNext(contentBean);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) .subscribe(new Observer<ContentBean>() {
            @Override
            public void onNext(ContentBean contentBean) {
                App.cacheHelper.put(newsItem.getLink() + "", contentBean);
                handleContent(contentBean);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ContentBean contentBean = new ContentBean();
                if (newsItem.getIcon()!=null){
                    contentBean.setImage(newsItem.getIcon());
                }
                if (newsItem.getImage() != null){
                    contentBean.setImage(newsItem.getImage());
                }
                contentBean.setTitle(newsItem.getTitle());
                contentBean.setDate(newsItem.getPubDate());
                contentBean.setContent(newsItem.getDescription());
                Toast.makeText(ContentActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                handleContent(contentBean);
            }
        });

    }

    /**
     * 处理收藏的方法
     *
     * @param data 收藏的实体
     * @param flag false 取消
     */
    public void handleFav(Item data, boolean flag) {
        int pos = -1;
        mFavlist = App.cacheHelper.getAsSerializable(RssApi.KEY_FAV_NEWS);

        if (mFavlist == null) mFavlist = new ArrayList<>();
        if (data == null) return;
        if (flag) {
            if (mFavlist.contains(data)) return;
            mFavlist.add(data);
        } else {
            for (Item bean : mFavlist) {
                if (bean.getLink().equals(data.getLink())) {
                    pos = mFavlist.indexOf(bean);
                }
            }
            if (pos >= 0)
                mFavlist.remove(pos);
        }
        App.cacheHelper.put(RssApi.KEY_FAV_NEWS, (Serializable) mFavlist);
    }

    private void handleContent(ContentBean contentBean) {

        mLoading.hide();
        mWebView.setVisibility(View.GONE);

        mContentTv.setText("\u3000\u3000"+contentBean.getContent());
        mAuthorTv.setText("by"+" "+contentBean.getAuthor());
        mTitle.setText(contentBean.getTitle());
        toolbarLayout.setTitle(contentBean.getTitle());

        //如果是无图模式，仅仅在无线的条件加载图片
        if ((Boolean) SPUtils.get(this, RssApi.SET_IMAGE, true)) {
            if (NetUtil.getNetWorkType(this)== NetUtil.NETWORKTYPE_WIFI) {
                if(contentBean.getImage()!= null){
                    ImageLoaderFactory.getLoader().displayImage(mHeader, contentBean.getImage(), null);
                }
            }
        } else {
            if(contentBean.getImage()!= null){
                ImageLoaderFactory.getLoader().displayImage(mHeader, contentBean.getImage(), null);
            }
        }

//        StringBuilder sb = new StringBuilder();
//        sb.append("<div class=\"img-wrap\">")
//                .append("<h1 class=\"headline-title\">")
//                .append(contentBean.getTitle()).append("</h1>")
//                .append("<span class=\"img-source\">")
//                .append(contentBean.getImage()).append("</span>")
//                .append("<img src=\"").append(contentBean)
//                .append("\" alt=\"\">")
//                .append("<div class=\"img-mask\"></div>");
//        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
//                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
//                + contentBean.getContent().replace("<div class=\"img-place-holder\">", "");
//        mWebView.getSettings().setDefaultFontSize(16);
//        mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
//        mWebView.setDrawingCacheEnabled(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "Share from Rx Rss:" + newsItem.getTitle()+". "+newsItem.getLink());
        startActivity(Intent.createChooser(intent, newsItem.getTitle()));
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
