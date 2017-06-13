package com.dreamworld.followme.usingglide;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamworld.followme.R;

import java.util.List;

/**
 * Created by faizan on 12/06/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    List<Datamodel> datamodels ;
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_main,parent,false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    // holder.lHeadImage.setImageBitmap(datamodels.get(position).getmUrls());

    }

    @Override
    public int getItemCount() {
        return 0;
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
