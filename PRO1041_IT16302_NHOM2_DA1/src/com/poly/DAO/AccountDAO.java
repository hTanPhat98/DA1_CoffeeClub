/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.Account;
import com.poly.Model.TKQMK;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class AccountDAO extends CoffeShopSysDAO<Account, String> {

    String INSERT_SQL = "INSERT INTO ACCOUNT (UserName, Password, MaNV, VaiTro) VALUES (?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE ACCOUNT SET Password=?, MaNV=?, VaiTro=? WHERE UserName=?";
    String DELETE_SQL = "DELETE FROM ACCOUNT WHERE UserName=?";
    String SELECT_ALL_SQL = "SELECT * FROM ACCOUNT ";
    String SELECT_BY_ID_SQL = "SELECT * FROM ACCOUNT WHERE UserName=?";
    String SELECT_BY_EMAIL_ACC = "SELECT Email, acc.UserName, acc.VaiTro, acc.MaNV FROM NHANVIEN nv JOIN ACCOUNT acc ON nv.MaNV=acc.MaNV";
    String SELECT_NAME = "SELECT TenNV FROM NHANVIEN WHERE MaNV=?";
    String SELECT_BY_MaNV_SQL = "SELECT * FROM ACCOUNT WHERE MaNV=?";

    @Override
    public void insert(Account entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getUserName(),
                entity.getPassworld(),
                entity.getMaNV(),
                entity.isVaiTro()
        );
    }

    @Override
    public void update(Account entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getPassworld(),
                entity.getMaNV(),
                entity.isVaiTro(),
                entity.getUserName()
        );
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Account selectById(String key) {
        List<Account> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Account selectByManv(String key) {
        List<Account> list = this.selectBySql(SELECT_BY_MaNV_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public String selectNameAccount(String key) {
        String nameNV = null;
        try {
            ResultSet rs = JdbcHelper.query(SELECT_NAME, key);
            while (rs.next()) {                
                nameNV = rs.getString("TenNV");
            }
            rs.getStatement().getConnection().close();
            return nameNV;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TKQMK> selectEmail() {
        List<TKQMK> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(SELECT_BY_EMAIL_ACC);
            while (rs.next()) {
                TKQMK tkqmk = new TKQMK();
                tkqmk.setEmail(rs.getString("Email"));
                tkqmk.setUserName(rs.getString("UserName"));
                tkqmk.setVaiTro(rs.getBoolean("VaiTro"));
                tkqmk.setMaNV(rs.getString("MaNV"));
                list.add(tkqmk);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Account entity = new Account();
                entity.setUserName(rs.getString("UserName"));
                entity.setPassworld(rs.getString("Password"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
