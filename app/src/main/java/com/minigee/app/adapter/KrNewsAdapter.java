package com.minigee.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minigee.app.R;
import com.minigee.app.base.BaseUi;
import com.minigee.app.model.KrNews;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zhou on 2015-12-7.
 */
public class KrNewsAdapter extends RecyclerView.Adapter<KrNewsAdapter.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private List<KrNews> news = new ArrayList<KrNews>();
    private BaseUi ui;

    private OnRecyclerViewItemClickListener mListener;
    private OnRecyclerViewItemLongClickListener mLongClickListener;

    public interface OnRecyclerViewItemClickListener{
        public void onItemClick(View view,String argu);
    }

    public interface OnRecyclerViewItemLongClickListener{
        public void onItemLognClick(View view,String argu);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mListener){
        this.mListener = mListener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener mLongClickListener){
        this.mLongClickListener = mLongClickListener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        String argu = news.get(position).getFeedId();
        mListener.onItemClick(v,argu);
    }

    @Override
    public boolean onLongClick(View v) {
        int position = (int) v.getTag();
        String argu = news.get(position).getFeedId();
        mLongClickListener.onItemLognClick(v, argu);
        return true;
    }


    public KrNewsAdapter(BaseUi ui) {
        this.ui = ui;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ui).inflate(R.layout.kr_card,parent,false);
        ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemview.setTag(position);
        holder.user.setText(news.get(position).getUserName() + " " + news.get(position).getPublishTime());
        holder.columnName.setText(news.get(position).getColumnName());
        holder.title.setText(news.get(position).getTitle());

        x.image().bind(holder.featureImg,news.get(position).getFeatureImg());
        x.image().bind(holder.userAvatar,news.get(position).getUserAvatar(),new ImageOptions.Builder()
                .setCircular(true)
                .build());
    }

    @Override
    public int getItemCount() {
        return news.size();

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void update(ArrayList<KrNews> newslist,String lastId){
        if (lastId.equals("0")){
            this.news.clear();
        }
        this.news.addAll(newslist);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView user;
        public TextView columnName;
        public TextView title;
//        public CircleImageView userAvatar;
        public ImageView userAvatar;
        public ImageView featureImg;

        public View itemview;

        public ViewHolder(View itemView) {
            super(itemView);

            user = (TextView) itemView.findViewById(R.id.user);
            columnName = (TextView) itemView.findViewById(R.id.columnName);
            title = (TextView) itemView.findViewById(R.id.title);
            userAvatar = (ImageView) itemView.findViewById(R.id.userAvatar);
            featureImg = (ImageView) itemView.findViewById(R.id.featureImg);

            itemview = itemView;


        }

    }
}
