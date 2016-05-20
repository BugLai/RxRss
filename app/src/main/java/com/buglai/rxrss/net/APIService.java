package com.buglai.rxrss.net;

import com.buglai.rxrss.model.Rss;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by buglai on 5/18/16.
 */
public interface APIService {

    @GET("feed")
    Observable<Rss> getStoryHome();

    @GET("news")
    Observable<Rss> getStoryNews();

    @GET("hotstoryby7day")
    Observable<Rss> getStoryHotstoryby7day();

    @GET("diglossia")
    Observable<Rss> getStoryDiglossia();

    @GET("column/007000005")
    Observable<Rss> getStoryFirstTimeRead();

    @GET("column/007000004")
    Observable<Rss> getStoryChinaPoint();

    @GET("column/007000007")
    Observable<Rss> getStoryChinaHistory();

    @GET("column/007000002")
    Observable<Rss> getStoryMorningAndNight();

    @GET("lifestyle")
    Observable<Rss> getStoryLifeStyle();

    @GET("letter")
    Observable<Rss> getStoryLitter();

    @GET("column/007000012")
    Observable<Rss> getStoryMartinWolf();



}
