package com.buglai.rxrss.net;

import android.text.TextUtils;
import android.util.Log;

import com.buglai.rxrss.model.ContentBean;
import com.buglai.rxrss.model.Item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by buglai on 5/19/16.
 */
public class APIJsoup {

    public static ContentBean getNewsContent(Item item){
        Document document = null;
        ContentBean contentBean = new ContentBean();
        String avatar =null;
        String author = null;
        String date = null;
        String image = null;
        Elements contents;
        Element mainElement = null;

        StringBuilder content = new StringBuilder();
        try {
            document = Jsoup.connect(item.getLink()).timeout(10000).get();

            if (TextUtils.isEmpty(item.getLink())) {
                return null;
            } else {
                mainElement = document.select("div.inner").first();
                contents = mainElement.select("div#bodytext.content div.content-text p");
                for (int i=0;i<contents.size();i++){
                    content.append(contents.get(i).text().replaceAll("%","").trim()+"\r\n");
                }
                author = mainElement.select("div.byline a ").first().text();


                Log.i("author",author+"");

                contentBean.setAuthor(author);
                contentBean.setContent(content.toString());
                contentBean.setDate(item.getPubDate());

                contentBean.setTitle(item.getTitle());
                if (item.getIcon()!= null){
                    contentBean.setImage(item.getIcon());
                }
                if (item.getImage() != null){
                    contentBean.setImage(item.getImage());
                }


            }
            return contentBean;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBean;
    }
}
