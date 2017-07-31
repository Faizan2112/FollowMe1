package com.dreamworld.followme.usingglide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.view.ScaleGestureDetector;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.dreamworld.followme.ListItem;
import com.dreamworld.followme.R;
import com.dreamworld.followme.glideutills.GlideCacheListener;
import com.dreamworld.followme.glideutills.GlideUtils;
import com.dreamworld.followme.glideutills.T.*;
import com.dreamworld.followme.testcode.NetworkUtill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_LONG;
import static com.bumptech.glide.util.ByteArrayPool.get;

/**
 * Created by faizan on 12/06/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
  /*  Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector SGD ;*/
    List<Datamodel> datamodels ;
    Context mContext ;
    private Datamodel dm;
    int lastPosition = -1;
    private OnItemClickListener onItemClickListener;
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



  public DataAdapter(Context context, String[] name , String[] urls ,Bitmap[] images)
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
     /*  Glide.with(mContext).load(String.valueOf(dm.getmUrls()))
              .thumbnail(0.5f)
              .crossFade()
               .transform(new CircleTransform(mContext))
              //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
              .into(holder.lHeadImage);

        Glide.with(mContext).load(String.valueOf(dm.getmUrls()))
                .placeholder(R.drawable.placeholder)
                //.thumbnail(0.5f)
                .crossFade()
                //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.lMainImage)

                ;*/


            GlideUtils.cacheImage(String.valueOf(dm.getmUrls()), mContext, new MyGlideCacheListener());
        Glide.with(mContext).load(GlideUtils.getCache(mContext, String.valueOf(dm.getmUrls()))).transform(new CircleTransform(mContext)).into(holder.lHeadImage);

        Glide.with(mContext).load(GlideUtils.getCache(mContext, String.valueOf(dm.getmUrls()))).centerCrop().into(holder.lMainImage);

            /*      FutureTarget<File> future = Glide.with(mContext)
                .load(dm.getmUrls())
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(dm);
            }
        };
       holder.lMainImage.setOnClickListener(listener);
        holder.lHeadImage.setOnClickListener(listener);
        if(position >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.bottom_from_up);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
        else
            {

                Animation animation = AnimationUtils.loadAnimation(mContext,
                        R.anim.up_from_bottom);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            }
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
         // lMainImage.setImageMatrix(matrix );
          //  SGD = new ScaleGestureDetector(mContext,new ScaleListener());

        }
    }

 /*   private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale*detector.getScaleFactor();
            scale = Math.max(0.1f,Math.min(scale,5f));
            matrix.setScale(scale,scale);
            return true;
        }
    }
     public boolean onTouchEvent(MotionEvent event)
     {
         SGD.onTouchEvent(event);
         return true ;
     }*/



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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
