/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Model;

import java.util.Date;

/**
 *
 * @author Patnight
 */
public class HoaDon {
    private int maHD;
    private String maBan;
    private String maNV;
    private Date ngayHD;
    private float tongTien;
    private boolean trangThai;

    public HoaDon() {
    }
    
    
    
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(Date ngayHD) {
        this.ngayHD = ngayHD;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public HoaDon(String maBan, String maNV, Date ngayHD, float tongTien, boolean trangThai) {
        this.maBan = maBan;
        this.maNV = maNV;
        this.ngayHD = ngayHD;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
    
    
}
