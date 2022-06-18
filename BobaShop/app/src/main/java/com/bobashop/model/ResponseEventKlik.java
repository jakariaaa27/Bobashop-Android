package com.bobashop.model;

import com.google.gson.annotations.SerializedName;

public class ResponseEventKlik {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private DataModel[] data;
    @SerializedName("msg")
    private String msg;
    public DataModel[] getData() {
        return data;
    }

    public void setData(DataModel[] data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
