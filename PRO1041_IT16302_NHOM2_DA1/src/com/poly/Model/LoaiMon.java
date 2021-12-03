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
public class LoaiMon {

    private String maLoai;
    private String tenLoai;

    public LoaiMon() {
    }
    
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return tenLoai+"-"+maLoai;
    }
    
    
}
