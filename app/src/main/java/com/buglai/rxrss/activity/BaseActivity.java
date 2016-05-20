package com.buglai.rxrss.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.buglai.rxrss.App;
import com.buglai.rxrss.R;
import com.buglai.rxrss.utils.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by buglai on 5/18/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onCreated(savedInstanceState);
        ((App) getApplication()).addActivity(this);
        initStatusBar();
    }

    protected void initStatusBar() {
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
    }

    public Toolbar initToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(getUmengPageName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageStart(getUmengPageName());
    }

    public abstract  String getUmengPageName();

    protected abstract int getLayoutId();

    protected abstract void onCreated(Bundle savedInstanceState);

}
