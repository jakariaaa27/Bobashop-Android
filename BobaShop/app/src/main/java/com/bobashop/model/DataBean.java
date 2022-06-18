package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

public class DataBean {
    @SerializedName("ket_tambahan")
    String ket_tambahan;
    @SerializedName("pelaksanaan")
    String pelaksanaan;
    @SerializedName("nama_dinas")
    String nama_dinas;

    public void setPelaksanaan(String pelaksanaan) {
        this.pelaksanaan = pelaksanaan;
    }

    public String getKet_tambahan() {
        return ket_tambahan;
    }

    public void setKet_tambahan(String ket_tambahan) {
        this.ket_tambahan = ket_tambahan;
    }

    public String getNama_dinas() {
        return nama_dinas;
    }

    public void setNama_dinas(String nama_dinas) {
        this.nama_dinas = nama_dinas;
    }

    public String getPelaksanaan() {
        return pelaksanaan;
    }
}
