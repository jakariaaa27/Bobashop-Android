package com.bobashop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bobashop.R;
import com.bobashop.SharedPrefManager;
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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformasiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivNone)
    ImageView ivNone;
    @BindView(R.id.tvNone)
    TextView tvNone;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private Unbinder unbinder;
    private List<Informasi> semuaInformasiList = new ArrayList<>();
    private InformasiAdapter InfoAdaptre;
    private BaseApiService mApiService;
    private SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasi, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getActivity());
        mApiService = UtilsApi.getAPIService();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getActivity(),Search.class);
                    intent.putExtra("query",v.getText().toString().trim());
                    intent.putExtra("filter","lapor");
                    startActivity(intent);
                    etSearch.setText("");
                    etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });


        getResultInformasi();

        return view;

    }

    private void getResultInformasi() {
        ivNone.setVisibility(View.GONE);
        tvNone.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        mApiService.getAllDestination().enqueue(new Callback<ListInformasi>() {
            @Override
            public void onResponse(Call<ListInformasi> call, Response<ListInformasi> response) {
                semuaInformasiList = response.body().getSemuainformasi();

                if (semuaInformasiList != null) {
                    recyclerView.setVisibility(RecyclerView.VISIBLE);
                    shimmer.setVisibility(View.GONE);
                    tvNone.setVisibility(TextView.GONE);
                    ivNone.setVisibility(View.GONE);
                    InfoAdaptre = new InformasiAdapter(getActivity(), semuaInformasiList);
                    recyclerView.setAdapter(InfoAdaptre);

                } else {
                    recyclerView.setVisibility(RecyclerView.GONE);
                    shimmer.setVisibility(View.GONE);
                    ivNone.setImageDrawable(getResources().getDrawable(R.drawable.illu_informasi));
                    ivNone.setVisibility(View.VISIBLE);
                    tvNone.setVisibility(TextView.VISIBLE);
                    tvNone.setText("Tidak ada Informasi");
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ListInformasi> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                shimmer.setVisibility(View.GONE);
                ivNone.setImageDrawable(getResources().getDrawable(R.drawable.error));
                ivNone.setVisibility(View.VISIBLE);
                tvNone.setVisibility(TextView.VISIBLE);
                tvNone.setText("Server tidak merespon");
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getResultInformasi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
