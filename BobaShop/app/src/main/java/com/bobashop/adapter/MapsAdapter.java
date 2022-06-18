package com.bobashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bobashop.R;
import com.bobashop.apihelper.UtilsApi;
import com.bobashop.model.Maps;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MapsHolder> {
    List<Maps> AllMapsItemList;
    Context mContext;

    private static final int REQUEST_LOCATION_PERMISSION =1 ;
    private GoogleMap mMap;
    Marker marker;

    private MarkerOptions options = new MarkerOptions();

    public MapsAdapter(Context context, List<Maps> MapsList) {
        this.mContext = context;
        AllMapsItemList = MapsList;
    }

    @NonNull
    @Override
    public MapsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_maps, parent, false);
        return new MapsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MapsHolder holder, int position) {
        final Maps Map = AllMapsItemList.get(position);

    }

    @Override
    public int getItemCount() {
        return AllMapsItemList.size();
    }

    public class MapsHolder extends RecyclerView.ViewHolder {

        public MapsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
