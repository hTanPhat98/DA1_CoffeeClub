/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.KhuVuc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class KhuVucDAO extends CoffeShopSysDAO<KhuVuc, String>{
    
    String INSERT_SQL = "INSERT INTO KHUVUC (MaKV, TenKV, TienIch) VALUES (?, ?, ?)";
    String UPDATE_SQL = "UPDATE KHUVUC SET TenKV=?, TienIch=? WHERE MaKV=?";
    String DELETE_SQL = "DELETE FROM KHUVUC WHERE MaKV=?";
    String SELECT_ALL_SQL = "SELECT * FROM KHUVUC ";
    String SELECT_BY_ID_SQL = "SELECT * FROM KHUVUC WHERE MaKV=?";
    
    @Override
    public void insert(KhuVuc entity) {
        JdbcHelper.update(INSERT_SQL, 
                entity.getMaKV(),
                entity.getTenKV(),
                entity.getTienIch()
                );
    }

    @Override
    public void update(KhuVuc entity) {
        JdbcHelper.update(UPDATE_SQL, 
                entity.getTenKV(),
                entity.getTienIch(),
                entity.getMaKV()
                );
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<KhuVuc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhuVuc selectById(String key) {
        List<KhuVuc> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<KhuVuc> selectBySql(String sql, Object... args) {
        List<KhuVuc> list = new ArrayList<KhuVuc>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhuVuc entity = new KhuVuc();
                entity.setMaKV(rs.getString("MaKV"));
                entity.setTenKV(rs.getString("TenKV"));
                entity.setTienIch(rs.getString("TienIch"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
