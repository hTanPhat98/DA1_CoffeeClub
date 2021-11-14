/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.HoaDonCT;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class HoaDonCTDAO extends CoffeShopSysDAO<HoaDonCT, Integer>{
    
    String INSERT_SQL = "INSERT INTO HOADONCT (MaHD, MaMon, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE HOADONCT SET MaHD=?, MaMon=?, SoLuong=?, DonGia=? WHERE MaHDCT=?";
    String DELETE_SQL = "DELETE FROM HOADONCT WHERE MaHDCT=?";
    String SELECT_ALL_SQL = "SELECT * FROM HOADONCT ";
    String SELECT_BY_ID_SQL = "SELECT * FROM HOADONCT WHERE MaHDCT=?";
    
    @Override
    public void insert(HoaDonCT entity) {
        JdbcHelper.update(INSERT_SQL, 
                entity.getMaHD(),
                entity.getMaMon(),
                entity.getSoLuong(),
                entity.getDonGia()
                );
    }

    @Override
    public void update(HoaDonCT entity) {
        JdbcHelper.update(INSERT_SQL, 
                entity.getMaHD(),
                entity.getMaMon(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getMaHDCT()
                );
    }

    @Override
    public void delete(Integer key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<HoaDonCT> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonCT selectById(Integer key) {
        List<HoaDonCT> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<HoaDonCT> selectBySql(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<HoaDonCT>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonCT entity = new HoaDonCT();
                entity.setMaHDCT(rs.getInt("MaHDCT"));
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaMon(rs.getString("MaMon"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getFloat("DonGia"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
