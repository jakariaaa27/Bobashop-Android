package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

public class Informasi {

    @SerializedName("pict")
    private String pict;

    @SerializedName("dest_id")
    private String dest_id;

    @SerializedName("dest_name")
    private String dest_name;

    @SerializedName("desc")
    private String desc;

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getDest_name() {
        return dest_name;
    }

    public void setDest_name(String dest_name) {
        this.dest_name = dest_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDest_id() {
        return dest_id;
    }

    public void setDest_id(String dest_id) {
        this.dest_id = dest_id;
    }

    @SerializedName("created_at")
    private String created_at;

}
