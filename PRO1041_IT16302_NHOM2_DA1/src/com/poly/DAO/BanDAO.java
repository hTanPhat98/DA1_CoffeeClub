/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.Ban;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BanDAO extends CoffeShopSysDAO<Ban, String> {

    String INSERT_SQL = "INSERT INTO BAN (MaBan, TenBan, MaKV, GhepBan) VALUES (?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE BAN SET TenBan=?, MaKV=?, GhepBan=? WHERE MaBan=?";
    String UPDATE_GB="UPDATE BAN SET GhepBan=? WHERE MaBan=?";
    String DELETE_SQL = "DELETE FROM BAN WHERE MaBan=?";
    String SELECT_ALL_SQL = "SELECT * FROM BAN ";
    String SELECT_BY_ID_SQL = "SELECT * FROM BAN WHERE MaBan=?";
    String SELECT_BY_MaBan = "SELECT TenBan FROM BAN WHERE MaBan=?";
    

    @Override
    public void insert(Ban entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaBan(),
                entity.getTenBan(),
                entity.getMaKV(),
                entity.getGhepBan()
        );
    }

    @Override
    public void update(Ban entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenBan(),
                entity.getMaKV(),
                entity.getGhepBan(),
                entity.getMaBan()
        );
    }

    public void updateGB(String MaBG,String MaBan) {
        JdbcHelper.update(UPDATE_GB,
                MaBG,MaBan
        );
    }
    
    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<Ban> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Ban selectById(String key) {
        List<Ban> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Ban> findByIdKhuVuc(String makv) {
        String sql = "SELECT * FROM BAN where MaKV='" + makv + "'";
        return selectBySql(sql);
    }

    @Override
    protected List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Ban entity = new Ban();
                entity.setMaBan(rs.getString("MaBan"));
                entity.setTenBan(rs.getString("TenBan"));
                entity.setMaKV(rs.getString("MaKV"));
                entity.setGhepBan(rs.getString("GhepBan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String selectTenBan(String maban) {
        String mb = "";
        try {
            ResultSet rs = JdbcHelper.query(SELECT_BY_MaBan, maban);
            while (rs.next()) {
                mb=rs.getString("TenBan");
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            
        }
        return mb;
    }
    
}
