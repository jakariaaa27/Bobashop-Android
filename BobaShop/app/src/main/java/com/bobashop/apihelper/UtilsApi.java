package com.bobashop.apihelper;

public class UtilsApi {
    // 10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://ereminder.dermayu.id/api/";
//    public static final String BASE_URL_API = "http://192.168.100.27/e-gov_kmipn/public/api/";
        public static final String BASE_URL_API = "http://192.168.208.121/bobashop/public/api/";
//    public static final String BASE_URL_API = "http://192.168.8.100/tugas-akhir/public/api/";
//    public static final String BASE_URL_API = "http://192.168.43.221/tugas-akhir/public/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
