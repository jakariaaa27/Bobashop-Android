package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMaps {
    @SerializedName("AllMaps")
    private List<Maps> AllMaps;

    public List<Maps> getAllMaps() {
        return AllMaps;
    }

    public void setAllMaps(List<Maps> AllMaps) {
        this.AllMaps = AllMaps;
    }
}
