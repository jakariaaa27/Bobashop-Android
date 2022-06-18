package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

public class Akun {
    @SerializedName("nama_ibu")
    private String nama_ibu;

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }
}
