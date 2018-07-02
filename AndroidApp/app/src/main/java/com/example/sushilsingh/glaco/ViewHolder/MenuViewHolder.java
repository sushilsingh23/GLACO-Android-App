package com.example.sushilsingh.glaco.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sushilsingh.glaco.Interface.ItemClickListener;
import com.example.sushilsingh.glaco.R;

/**
 * Created by Sushil Singh on 11/12/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
     }


    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false  );
    }
}
