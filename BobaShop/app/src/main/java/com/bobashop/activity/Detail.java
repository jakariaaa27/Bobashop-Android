package com.bobashop.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobashop.R;
import com.bobashop.SharedPrefManager;
import com.bobashop.adapter.DetailAdapter;
import com.bobashop.adapter.InformasiAdapter;
import com.bobashop.apihelper.BaseApiService;
import com.bobashop.apihelper.UtilsApi;
import com.bobashop.model.Informasi;
import com.bobashop.model.ListInformasi;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivNone)
    ImageView ivNone;
    @BindView(R.id.tvNone)
    TextView tvNone;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;

    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    List<Informasi> semuaInformasiList = new ArrayList<>();
    DetailAdapter InfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        detailDes();
    }

    private void detailDes(){
       mApiService.getDestinationDetail(sharedPrefManager.getSPId()).enqueue(new Callback<ListInformasi>() {
           @Override
           public void onResponse(Call<ListInformasi> call, Response<ListInformasi> response) {
               semuaInformasiList = response.body().getSemuainformasi();

               if (semuaInformasiList != null) {
                   recyclerView.setVisibility(RecyclerView.VISIBLE);
                   shimmer.setVisibility(View.GONE);
                   tvNone.setVisibility(TextView.GONE);
                   ivNone.setVisibility(View.GONE);
                   InfoAdapter = new DetailAdapter(getApplicationContext(), semuaInformasiList);
                   recyclerView.setAdapter(InfoAdapter);
               } else {
                   recyclerView.setVisibility(RecyclerView.GONE);
                   shimmer.setVisibility(View.GONE);
                   ivNone.setVisibility(View.VISIBLE);
                   tvNone.setVisibility(TextView.VISIBLE);
                   tvNone.setText("Tidak ada Lapor");
               }
           }

           @Override
           public void onFailure(Call<ListInformasi> call, Throwable t) {
               recyclerView.setVisibility(View.GONE);
               shimmer.setVisibility(View.GONE);
               ivNone.setImageDrawable(getResources().getDrawable(R.drawable.error));
               ivNone.setVisibility(View.VISIBLE);
               tvNone.setVisibility(TextView.VISIBLE);
               tvNone.setText("Server tidak merespon");
           }
       });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}