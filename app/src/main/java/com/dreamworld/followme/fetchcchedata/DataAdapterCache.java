package com.dreamworld.followme.fetchcchedata;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dreamworld.followme.R;
import com.dreamworld.followme.glideutills.GlideCacheListener;
import com.dreamworld.followme.glideutills.GlideUtils;
import com.dreamworld.followme.usingglide.Datamodel;

import java.util.ArrayList;
import java.util.List;

import static com.dreamworld.followme.glideutills.GlideHelper.urlToImageView;

/**
 * Created by faizan on 12/06/2017.
 */

public class DataAdapterCache extends RecyclerView.Adapter<DataAdapterCache.ViewHolder> {

    List<Datamodel> datamodels ;
    Context mContext ;
    private Datamodel dm;

  /*  public CardAdapter(String[] names, String[] urls, Bitmap[] images){
        super();
        int check = urls.length -1;
        items = new ArrayList<ListItem>();
        for(int i =0; i<check; i++){
            ListItem item = new ListItem();
            item.setName(names[i]);
            item.setUrl(urls[i]);
            item.setImage(images[i]);
            items.add(item);
        }
    }*/

  public DataAdapterCache(Context context, String[] name , String[] urls , Bitmap[] images)
  {

      super();
      this.mContext = context;
      int check = urls.length -1;
      datamodels = new ArrayList<Datamodel>();
      for(int i = 0 ;i<check ;i++)
      {
          Datamodel datamodel = new Datamodel();
          datamodel.setmName(name[i]);
          datamodel.setmUrls(urls[i]);
          datamodel.setmImage(images[i]);
          datamodels.add(datamodel);

      }


  }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_main,parent,false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dm = datamodels.get(position);
        // holder.lHeadImage.setImageBitmap(datamodels.get(position).getmUrls());
        // holder.lDate.setText(dm.getmUrls());
        // holder.textViewUrl.setText(list.getUrl());
         GlideUtils.cacheImage(String.valueOf(dm.getmUrls()), mContext, new MyGlideCacheListener());
         Glide.with(mContext).load(GlideUtils.getCache(mContext, String.valueOf(dm.getmUrls()))).into(holder.lMainImage);
      /*  Glide.with(mContext)
                .load(Uri.parse(GlideUtils.getCache(mContext, String.valueOf(dm.getmUrls()))))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource){
                       *//* if (dialog.isShowing())
                            dialog.dismiss();*//*
                        return false;
                    }
                })
                .into(holder.lMainImage);
    }*/


            urlToImageView(mContext, holder.lMainImage, dm.getmUrls(), R.drawable.placeholder, false);

    }
    @Override
    public int getItemCount() {
        return datamodels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lHeadImage;
        ImageView lMainImage;
        TextView lDate;
        ViewHolder(View itemView ) {
            super(itemView);
            lHeadImage = (ImageView)itemView.findViewById(R.id.home_head_image);
            lMainImage = (ImageView)itemView.findViewById(R.id.home_main_image);
            lDate =(TextView)itemView.findViewById(R.id.main_date_time);

        }
    }

    private class MyGlideCacheListener implements GlideCacheListener {

        @Override
        public void success(String path) {

           // Toast.makeText(mContext,"sfsd" +path, LENGTH_LONG).show();
            //S//(mActivity, "缓存成功");
        }

        @Override
        public void error(Exception e) {
           // S(mActivity, "缓存失败");
            //Toast.makeText(mContext,"sfsd"+e, LENGTH_LONG).show();
        }
    }
}
