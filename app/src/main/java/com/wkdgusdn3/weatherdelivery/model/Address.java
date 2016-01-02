package com.wkdgusdn3.weatherdelivery.model;

/**
 * Created by HyunWoo on 2016-01-02.
 */
public class Address {
    String address;
    String code;

    Address(String address, String code) {
        this.address = address;
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}