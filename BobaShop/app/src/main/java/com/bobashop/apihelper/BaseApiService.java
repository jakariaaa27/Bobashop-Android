package com.bobashop.apihelper;


import com.bobashop.model.Akun;
import com.bobashop.model.ListInformasi;
import com.bobashop.model.ListMaps;
import com.bobashop.model.ResponseEventKlik;
import com.bobashop.model.ResponseGetEventList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("token") String token);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email);
    @GET("destination")
    Call<ListInformasi> getAllDestination();

    @GET("maps")
    Call<ListMaps> getAllMaps();

    @GET("detialDestination/{id}")
    Call<ListInformasi> getDestinationDetail(@Path("id") int id);

    @GET("ibu/{username}")
    Call<Akun> akunRequest(@Path("username") String username);

    @FormUrlEncoded
    @POST("jadwal/{username}")
    Call<ResponseEventKlik> getDetailEvent(@Path("username") String username, @Field("pelaksanaan") String pelaksanaan);

    @GET("profile/{id}")
    Call<ResponseBody> getProfile(@Path("id") int id);

    @GET("bayi/{id}")
    Call<ResponseBody> bayirequest(@Path("id") int id);

    @GET("dafbayi/{id}")
    Call<ResponseBody> dafbayirequest(@Path("id") int id);

    @GET("perkembangan/{id}")
    Call<ResponseBody> getPerkembangan(@Path("id") int id);

    @GET("tampil/jadwal/{username}")
    Call<ResponseGetEventList> getEventLists(@Path("username") String username);

    @FormUrlEncoded
    @PUT("password/{id}")
    Call<ResponseBody> prosesPassword (@Path("id") int id, @Field("password_lama") String password_lama,
                                                           @Field("password") String password);

    @FormUrlEncoded
    @POST("search")
    Call<ListInformasi> searchInformasi(@Field("search") String search);

    @FormUrlEncoded
    @PUT("profile/proses/{id}")
    Call<ResponseBody> prosesProfile (@Path("id") int id, @Field("no_kk") String no_kk,
                                      @Field("nama_ayah") String nama_ayah,
                                      @Field("nama_ibu") String nama_ibu,
                                      @Field("nik_ayah") String nik_ayah,
                                      @Field("nik_ibu") String nik_ibu,
                                      @Field("alamat") String alamat);

//    @GET("informasi")
//    Call<ListInformasi> getSemuaInformasi();

}
