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

import com.bobashop.SharedPrefManager;
import com.bobashop.activity.Detail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bobashop.R;
import com.bobashop.apihelper.UtilsApi;
import com.bobashop.model.Informasi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformasiAdapter extends RecyclerView.Adapter<InformasiAdapter.InformasiHolder> {
    List<Informasi> semuaInformasiItemList;
    Context mContext;
    String urlFotoInformasi = UtilsApi.BASE_URL_API+"../images/destination/";

    public InformasiAdapter(Context context, List<Informasi> laporList) {
        this.mContext = context;
        sharedPrefManager = new SharedPrefManager(mContext);
        semuaInformasiItemList = laporList;
    }

    SharedPrefManager sharedPrefManager;

    @NonNull
    @Override
    public InformasiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_informasi, parent, false);
        return new InformasiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InformasiHolder holder, int position) {
        final Informasi Info = semuaInformasiItemList.get(position);

        String fotoLaporURL = urlFotoInformasi +Info.getPict();
        Glide.with(mContext)
                .load(fotoLaporURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pbLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivFoto);

        holder.tvJudul.setText(Info.getDest_name());
//        holder.tvKeterangan.setText(Info.getKeterangan());
        holder.tvKeterangan.setText(Html.fromHtml(Info.getDesc()).toString());

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
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

        holder.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Read More", Toast.LENGTH_SHORT).show();
                int id = Integer.parseInt(Info.getDest_id());
                sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID,id);
                Intent intent = new Intent(mContext, Detail.class);
                Intent new_intent = Intent.createChooser(intent, "Share Using");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(new_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return semuaInformasiItemList.size();
    }

    public class InformasiHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFoto)
        ImageView ivFoto;
        @BindView(R.id.pbLoading)
        ProgressBar pbLoading;
        @BindView(R.id.tvJudul)
        TextView tvJudul;
        @BindView(R.id.tvKeterangan)
        TextView tvKeterangan;
        @BindView(R.id.btnShare)
        Button btnShare;
        @BindView(R.id.btnRead)
        Button btnRead;

        public InformasiHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
