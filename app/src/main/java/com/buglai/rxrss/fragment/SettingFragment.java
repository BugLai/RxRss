package com.buglai.rxrss.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buglai.rxrss.R;
import com.buglai.rxrss.net.RssApi;
import com.buglai.rxrss.utils.AppContextUtil;
import com.buglai.rxrss.utils.SPUtils;

/**
 * Created by buglai on 5/19/16.
 */
public class SettingFragment extends PreferenceFragment {

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_set);

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


    @Override
    protected void onCreated(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundResource(android.R.color.transparent);
        return view;
    }

    @Override
    public String getUmengPageName() {
        return "SettingFragment";
    }


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        //如果“保存个人信息”这个按钮被选中，将进行括号里面的操作

        if ("set_cache".equals(preference.getKey())) {
            boolean set_cache = preference.getSharedPreferences().getBoolean("set_cache", false);
            SPUtils.put(getActivity(), RssApi.SET_CACHE, set_cache);

        } else if ("set_image".equals(preference.getKey())) {
            boolean set_image = preference.getSharedPreferences().getBoolean("set_image", false);
            SPUtils.put(getActivity(), RssApi.SET_IMAGE, set_image);
        }  else if ("set_about".equals(preference.getKey())) {
            initAbout();
        } else if ("set_like".equals(preference.getKey())) {
            RateUs();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


    public void initAbout() {
        new AlertDialog.Builder(getActivity())
                .setTitle("当前版本：" + AppContextUtil.getAppVersion(getActivity()))
                .setMessage("应用采用RSS作为源数据 。\n my git：https://github.com/buglai \n my email：\n  buglai.dev@gmail.com")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }


    public void RateUs(){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://fir.im/rxrss"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);

    }


}