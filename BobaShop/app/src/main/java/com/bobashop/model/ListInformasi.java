package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListInformasi {
    @SerializedName("semuainformasi")
    private List<Informasi> semuainformasi;

    public List<Informasi> getSemuainformasi() {
        return semuainformasi;
    }

    public void setSemuainformasi(List<Informasi> semuainformasi) {
        this.semuainformasi = semuainformasi;
    }
}
