package com.blackdev.misakoassignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<RegionData> dataList;
    private Activity currActivity;
    private Context context;
    private RoomDB database;
    private boolean isLoadingAdded = false;
    private static final int NORMAL_VIEW = 1;
    private static final int LOADING_VIEW = 0;

    public RecyclerViewAdapter(Context context, List<RegionData> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.currActivity = (Activity) context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL_VIEW:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_layout, parent, false);

                return new ViewHolder(view);
            case LOADING_VIEW:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.loader_layout,parent,false);
                return new ProgressHolder(view1);

            default: return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingAdded) {
            return position == dataList.size() - 1 ? LOADING_VIEW : NORMAL_VIEW;
        } else {
            return NORMAL_VIEW;
        }
    }

    public void addLoading() {
        isLoadingAdded = true;
        dataList.add(new RegionData());
        notifyItemInserted(dataList.size() - 1);
    }
    public void removeLoading() {
        isLoadingAdded = false;
        int position = dataList.size()-1;
        RegionData item = getItem(position);
        if (item != null) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }
    RegionData getItem(int position) {
        return dataList.get(position);
    }
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<RegionData> postItems) {
        int position = dataList.size();
        dataList.addAll(postItems);
        notifyItemRangeInserted(position,postItems.size());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Log.d("POSITION",position+" ");
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends BaseViewHolder {

        TextView country,capital,region,subRegion,population;
        ListView borderListView,languagesListView;
        ImageView flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flagImageView);
            country = itemView.findViewById(R.id.countryName);
            capital = itemView.findViewById(R.id.capitalName);
            region = itemView.findViewById(R.id.regionName);
            subRegion = itemView.findViewById(R.id.subRegionName);
            population = itemView.findViewById(R.id.population);
            borderListView = itemView.findViewById(R.id.bordersListView);
            languagesListView = itemView.findViewById(R.id.languageListView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            RegionData data = dataList.get(position);
            database  = RoomDB.getInstance(context);
            country.setText(data.getCountryName());
            capital.setText(data.getCapitalName());
            region.setText(data.getRegionName());
            subRegion.setText(data.getSubRegionName());
            population.setText(data.getPopulation());

            if(data.getBorders() != null && data.getBorders().size()!=0) {
                ArrayAdapter<String> borderList = new ArrayAdapter<>(context, R.layout.list_single_text_item, R.id.borderItemTextView, data.getBorders());
                borderListView.setAdapter(borderList);
            }else{
                borderListView.setVisibility(View.GONE);
            }
            if(data.getLanguages()!= null && data.getLanguages().size()!=0) {
                ArrayAdapter<String> languageList = new ArrayAdapter<>(context, R.layout.list_single_text_item_language, R.id.languageItemTextView, data.getLanguages());
                languagesListView.setAdapter(languageList);
            }else{
                languagesListView.setVisibility(View.GONE);
            }
            SvgLoader.pluck()
                    .with(currActivity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(data.getUrl(), flag);
            //Picasso.get().load(data.getUrl()).into(flag);
        }
    }
    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void clear() {
        }
    }


}
