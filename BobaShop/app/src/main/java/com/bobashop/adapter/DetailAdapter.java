package com.bobashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bobashop.R;
import com.bobashop.activity.Detail;
import com.bobashop.apihelper.UtilsApi;
import com.bobashop.model.Informasi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailDestinationHolder> {
    List<Informasi> semuaInformasiItemList;
    Context mContext;
    String urlFotoInformasi = UtilsApi.BASE_URL_API+"../images/destination/";

    public DetailAdapter(Context context, List<Informasi> laporList) {
        this.mContext = context;
        semuaInformasiItemList = laporList;
    }

    @NonNull
    @Override
    public DetailDestinationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detaildes, parent, false);
        return new DetailDestinationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DetailDestinationHolder holder, int position) {
        final Informasi Info = semuaInformasiItemList.get(position);

        String fotoLaporURL = urlFotoInformasi +Info.getPict();
        Glide.with(mContext)
                .load(fotoLaporURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pbLoading2.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pbLoading2.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivFoto2);

        holder.tvJudul2.setText(Info.getDest_name());
//        holder.tvKeterangan.setText(Info.getKeterangan());
        holder.tvKeterangan2.setText(Html.fromHtml(Info.getDesc()).toString());

        holder.btnShare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String body = Info.getDest_name();
                String sub = Info.getDesc();
                String urlString = "http://192.168.1.232/bobashop/public/destination/detail/"+Info.getDest_id();
                URL myURL = null;
                try {
                    myURL = new URL(urlString);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,body);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Destination : " + body + " \nLink : "+ myURL);
                Intent new_intent = Intent.createChooser(shareIntent, "Share Using");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(new_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return semuaInformasiItemList.size();
    }

    public class DetailDestinationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFoto2)
        ImageView ivFoto2;
        @BindView(R.id.pbLoading2)
        ProgressBar pbLoading2;
        @BindView(R.id.tvJudul2)
        TextView tvJudul2;
        @BindView(R.id.tvKeterangan2)
        TextView tvKeterangan2;
        @BindView(R.id.btnShare2)
        Button btnShare2;

        public DetailDestinationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
