package com.buglai.rxrss.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.buglai.rxrss.App;
import com.buglai.rxrss.R;
import com.buglai.rxrss.model.Item;
import com.buglai.rxrss.net.RssApi;
import com.buglai.rxrss.utils.DateUtil;
import com.buglai.rxrss.utils.SPUtils;
import com.buglai.rxrss.utils.imageloader.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by buglai on 16-5-10.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_NEWS = 0;

    private Context mContext;
    private List<Item> mNewsList;
    private long lastPos = -1;
    private boolean isAnim = true;


    OnItemClickListener itemClickListener;

    public NewsListAdapter(Context context, List<Item> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, Item bean, int pos);
    }

    public void setOnItemListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_NEWS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NEWS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
            return new NewsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Item news = mNewsList.get(position);
        if (news == null) {
            return;
        }
        startAnimator(holder.itemView, position);
        if (holder instanceof NewsViewHolder) {
            bindViewHolder((NewsViewHolder) holder, position, news);
        }
    }

    private void bindViewHolder(final NewsViewHolder holder, final int position, final Item news) {

        holder.mTvTitle.setText(news.getTitle());
        if (!news.isRead()) {
            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.textColorFirst_Day));
        } else {
            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.textColorThird_Day));
        }
//        boolean isFav = (Boolean) SPUtils.get(mContext.getApplicationContext(), "" + news.getGuid(), false);
//        if (isFav){
//            holder.mIvLike.setImageResource(R.drawable.ic_likeed);
//        }else{
//            holder.mIvLike.setImageResource(R.drawable.ic_like);
//        }
        Date date = DateUtil.getDate(String.valueOf(news.getPubDate()));
        holder.mTvDate.setText(date.toLocaleString());

        String image = news.getIcon();

        if (image != null && !image.equals("")) {
           ImageLoaderFactory.getLoader().displayImage(holder.mIvNews, image, options);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, mNewsList.get(position), position);
            }
        });

//        holder.mIvLike.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                boolean isFav = (Boolean) SPUtils.get(mContext.getApplicationContext(), "" + news.getGuid(), false);
//                if (isFav) {
//                    SPUtils.remove(mContext.getApplicationContext(), news.getGuid() + "");
//                    handleFav(news, false);
//                    holder.mIvLike.setImageResource(R.drawable.ic_likeed);
//                } else {
//                    holder.mIvLike.setImageResource(R.drawable.ic_like);
//                    SPUtils.put(mContext.getApplicationContext(),news.getGuid() + "", true);
//                    handleFav(news, true);
//                }
//
//            }
//        });



    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    private void startAnimator(View view, long position) {
        if (position > lastPos) {
            view.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.item_bottom_in));
            lastPos = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null && holder instanceof NewsViewHolder && ((NewsViewHolder) holder).mCvItem != null)
            ((NewsViewHolder) holder).mCvItem.clearAnimation();
    }

    public void changeData(List<Item> newsList) {
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    public void addData(List<Item> newsList) {
        if (mNewsList == null) {
            changeData(newsList);
        } else {
            mNewsList.addAll(newsList);
            notifyDataSetChanged();
        }
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public void setmNewsList(List<Item> mNewsList) {
        this.mNewsList = mNewsList;
    }

    public List<Item> getmNewsList() {
        return mNewsList;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView mCvItem;

        ImageView mIvNews;

        TextView mTvTitle;

        TextView mTvDate;

//        ImageView mIvLike;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mCvItem = (CardView) itemView.findViewById(R.id.cv_item);
            mIvNews = (ImageView) itemView.findViewById(R.id.iv_news);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
//            mIvLike = (ImageView) itemView.findViewById(R.id.iv_collect);
        }
    }



    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.default_icon)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(0))
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 处理收藏的方法
     *
     * @param data 收藏的实体
     * @param flag false 取消
     */
//    public void handleFav(Item data, boolean flag) {
//        int pos = -1;
//        List<Item>  mFavlist = App.cacheHelper.getAsSerializable(RssApi.KEY_FAV_NEWS);
//
//        if (mFavlist == null) mFavlist = new ArrayList<>();
//        if (data == null) return;
//        if (flag) {
//            if (mFavlist.contains(data)) return;
//            mFavlist.add(data);
//        } else {
//            for (Item bean : mFavlist) {
//                if (bean.getGuid().equals(data.getGuid())) {
//                    pos = mFavlist.indexOf(bean);
//                }
//            }
//            if (pos >= 0)
//                mFavlist.remove(pos);
//        }
//        App.cacheHelper.put(RssApi.KEY_FAV_NEWS, (Serializable) mFavlist);
//    }
}
