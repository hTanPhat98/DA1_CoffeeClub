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
public class Account {

    private String userName;
    private String passworld;
    private String maNV;
    private boolean vaiTro;

    public Account() {
    }

    public Account(String userName, String passworld, String maNV, boolean vaiTro) {
        this.userName = userName;
        this.passworld = passworld;
        this.maNV = maNV;
        this.vaiTro = vaiTro;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "Account{" + "userName=" + userName + ", passworld=" + passworld + ", maNV=" + maNV + ", vaiTro=" + vaiTro + '}';
    }
    
    
}
