/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Model;

/**
 *
 * @author Patnight
 */
public class Ban {
    private String maBan;
    private String tenBan;
    private String maKV;
    private String ghepBan;

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getMaKV() {
        return maKV;
    }

    public void setMaKV(String maKV) {
        this.maKV = maKV;
    }

    public String getGhepBan() {
        return ghepBan;
    }

    public void setGhepBan(String ghepBan) {
        this.ghepBan = ghepBan;
    }
    
    
    
    @Override
    public String toString() {
        return getTenBan()+"-"+getMaBan();
    }
    
    
}
