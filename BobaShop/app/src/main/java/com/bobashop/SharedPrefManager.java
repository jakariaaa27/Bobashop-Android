package com.bobashop;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_APP = "spApp";

    public static final String SP_ID = "spId";
    public static final String SP_NAMA = "spNama";
    public static final String SP_USERNAME = "spusername";
    public static final String SP_LEVEL = "spLevel";
    public static final String SP_TOKEN = "spToken";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }


    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }


    public String getSPUnem(){
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPLevel(){
        return sp.getString(SP_LEVEL, "");
    }


    public String getSPToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public int getSPId(){
        return sp.getInt(SP_ID, 0);
    }

    public Object SP_USERNAME() {
        return sp.getString(SP_USERNAME, "");
    }

    public Object SP_Nama() {
        return sp.getString(SP_NAMA, "");
    }
}
