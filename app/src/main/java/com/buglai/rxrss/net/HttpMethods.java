package com.buglai.rxrss.net;



import com.buglai.rxrss.model.Rss;

import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by buglai on 5/18/16.
 */
public class HttpMethods {

    private Retrofit retrofit;
    private APIService apiService;

    public static final String BASE_URL = "http://www.ftchinese.com/rss/";

    private static final int DEFAULT_TIMEOUT = 30;

    private static HttpMethods instance;

    public HttpMethods(){

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static  HttpMethods getInstance() {
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null) {
                    instance = new HttpMethods();
                }
            }
        }
        return instance;
    }


     public void getHomeData(Subscriber<Rss> subscriber) {
         apiService.getStoryHome().subscribeOn(Schedulers.io())
                 .unsubscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


    public void getColumnData(Subscriber<Rss> subscriber,int columnId){
        switch (columnId){
            case RssApi.getStoryNews:

                apiService.getStoryNews().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryHotstoryby7day:

                apiService.getStoryHotstoryby7day().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryDiglossia:

                apiService.getStoryDiglossia().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryFirstTimeRead:

                apiService.getStoryFirstTimeRead().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;
            case RssApi.getStoryChinaPoint:

                apiService.getStoryChinaPoint().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryChinaHistory:

                apiService.getStoryChinaHistory().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryMorningAndNight:

                apiService.getStoryMorningAndNight().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;
            case RssApi.getStoryLifeStyle:

                apiService.getStoryLifeStyle().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryLitter:

                apiService.getStoryLitter().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;

            case RssApi.getStoryMartinWolf:

                apiService.getStoryMartinWolf().subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);

                break;


        }

    }
}


