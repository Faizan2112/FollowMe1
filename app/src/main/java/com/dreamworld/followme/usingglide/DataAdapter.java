package com.dreamworld.followme.usingglide;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dreamworld.followme.ListItem;
import com.dreamworld.followme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faizan on 12/06/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    List<Datamodel> datamodels ;
    Context mContext ;

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
        Datamodel dm =  datamodels.get(position);
    // holder.lHeadImage.setImageBitmap(datamodels.get(position).getmUrls());
       // holder.lDate.setText(dm.getmUrls());
       // holder.textViewUrl.setText(list.getUrl());
       Glide.with(mContext).load(String.valueOf(dm.getmUrls()))
              .thumbnail(0.5f)
              .crossFade()
              .diskCacheStrategy(DiskCacheStrategy.ALL)
              .into(holder.lHeadImage);
        Glide.with(mContext).load(String.valueOf(dm.getmUrls()))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.lMainImage);
    }

    @Override
    public int getItemCount() {
        return datamodels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lHeadImage;
        ImageView lMainImage;
        TextView lDate;
        ViewHolder(View itemView) {
            super(itemView);
            lHeadImage = (ImageView)itemView.findViewById(R.id.home_head_image);
            lMainImage = (ImageView)itemView.findViewById(R.id.home_main_image);
            lDate =(TextView)itemView.findViewById(R.id.main_date_time);

        }
    }
}
