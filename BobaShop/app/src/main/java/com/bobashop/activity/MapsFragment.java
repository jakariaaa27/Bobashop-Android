package com.bobashop.activity;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bobashop.R;
import com.bobashop.SharedPrefManager;
import com.bobashop.adapter.InformasiAdapter;
import com.bobashop.adapter.MapsAdapter;
import com.bobashop.apihelper.BaseApiService;
import com.bobashop.apihelper.UtilsApi;
import com.bobashop.model.Informasi;
import com.bobashop.model.ListInformasi;
import com.bobashop.model.ListMaps;
import com.bobashop.model.Maps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    Marker marker;

    private MarkerOptions options = new MarkerOptions();

    private Unbinder unbinder;
    private List<Maps> AllMapsList = new ArrayList<>();
    private MapsAdapter MapAdaptre;
    private BaseApiService mApiService;
    private SharedPrefManager sharedPrefManager;

    ArrayList<Double> latitude = new ArrayList<Double>();
    ArrayList<Double> longitude = new ArrayList<Double>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        unbinder = ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getActivity());
        mApiService = UtilsApi.getAPIService();


        /* This part asks for location access permission from the user. */

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        getMaps();


        return view;
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getMaps() {
        mApiService.getAllMaps().enqueue(new Callback<ListMaps>() {
            @Override
            public void onResponse(Call<ListMaps> call, Response<ListMaps> response) {
                AllMapsList = response.body().getAllMaps();

                if (AllMapsList != null) {
                    MapAdaptre = new MapsAdapter(getActivity(), AllMapsList);

                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getChildFragmentManager().findFragmentById(R.id.google_map);


                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {

                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            // When map is loaded
                            mMap = googleMap;

                            for (int i = 0; i < AllMapsList.size(); i++) {
//                        Log.e(TAG, "onResponse: "+AllMapsList.get(i).getLatitude());
                                latitude.add(Double.parseDouble(AllMapsList.get(i).getLatitude()));
                                longitude.add(Double.parseDouble(AllMapsList.get(i).getLongitude()));
                                name.add(AllMapsList.get(i).getDest_name());
                                type.add(AllMapsList.get(i).getType_id());
                            }


                            /* Geocoder derives the location name, the specific country and city of the user as a list. */
                            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                            try {
                                for (int i = 0; i < latitude.size(); i++) {

                                    List<Address> addresses = geocoder.getFromLocation(latitude.get(i), longitude.get(i), 1);
                                    String adress = addresses.get(0).getLocality() + " ";
                                    adress += addresses.get(0).getPostalCode();

                                    /* The latitude and longitude is combined and placed on the google map using a marker in the following part. */

                                    LatLng latLng = new LatLng(latitude.get(i), longitude.get(i));

                                    ArrayList<LatLng> coy = new ArrayList<LatLng>();
                                    coy.add(latLng);

                                    Log.d(TAG, "onLocationChanged: " + adress);

                                    for (LatLng point : coy) {
                                        if (marker != null) {
                                            marker.remove();
                                        }

                                        options.position(point);
                                        options.title(name.get(i));
                                        options.snippet("Location : " + adress);
                                        String type2 = type.get(i);
                                        if (type2.equals("1")){
                                            mMap.addMarker(options).setIcon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_bubble_tea));
                                        } else if(type2.equals("2")){
                                            mMap.addMarker(options).setIcon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_food_svgrepo_com));
                                        } else if(type2.equals("4")){
                                            mMap.addMarker(options).setIcon(BitmapFromVector(getActivity().getApplicationContext(), R.drawable.ic_cupcake_dessert_svgrepo_com));
                                        }
                                        mMap.setMaxZoomPreference(20);
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

                                        mMap.getUiSettings().setZoomControlsEnabled(true);
                                        mMap.getUiSettings().setMapToolbarEnabled(false);
                                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                            @Override
                                            public void onInfoWindowClick(Marker marker) {
                                                marker.showInfoWindow();
                                                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                                                StringBuilder strAppend = new StringBuilder();
                                                strAppend.append("");
                                                final String er = marker.getPosition().toString().replace("(", strAppend);
                                                final String er2 = er.replace(")", strAppend);

                                                StringBuilder strAppend2 = new StringBuilder();
                                                strAppend2.append("");

                                                final String newdata = er2.replace("lat/lng: ", strAppend2);

                                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
                                                        "http://maps.google.com/maps?q=loc:" + newdata));
                                                startActivity(intent);
                                            }
                                        });


                                    }

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                } else {
                }
            }

            @Override
            public void onFailure(Call<ListMaps> call, Throwable t) {
            }
        });
    }

}