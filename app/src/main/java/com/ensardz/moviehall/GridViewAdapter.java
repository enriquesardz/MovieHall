package com.ensardz.moviehall;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Quique on 27/12/2016.
 */

public class GridViewAdapter extends ArrayAdapter<oGridItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<oGridItem> mGridData = new ArrayList<oGridItem>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<oGridItem> mGridData){
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<oGridItem> mGridData){
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        oGridItem item = mGridData.get(position);

        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder{
        ImageView imageView;
    }
}
