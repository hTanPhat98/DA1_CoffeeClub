/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.HoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class HoaDonDAO extends CoffeShopSysDAO<HoaDon, Integer> {

    String INSERT_SQL = "INSERT INTO HOADON (MaBan, MaNV, NgayHD, Tongtien, Trangthai) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE HOADON SET MaBan=?, MaNV=?, NgayHD=?, Tongtien=?, Trangthai=? WHERE MaHD=?";
    String UPDATE_TT_SQL = "UPDATE HOADON SET Tongtien=?, Trangthai=? WHERE MaHD=?";
    String DELETE_SQL = "DELETE FROM HOADON WHERE MaHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM HOADON";
    String SELECT_BY_ID_SQL = "SELECT * FROM HOADON WHERE MaHD=?";
    String SELECT_BY_ID_SQL_MaHD = "SELECT * FROM HOADON WHERE MaBan=? AND TrangThai=0";
    String SELECT_ANY_SQL = "SELECT * FROM HOADON WHERE TrangThai=0 AND MaHD!=?";

    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaBan(),
                entity.getMaNV(),
                entity.getNgayHD(),
                entity.getTongTien(),
                entity.isTrangThai()
        );
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaBan(),
                entity.getMaNV(),
                entity.getNgayHD(),
                entity.getTongTien(),
                entity.isTrangThai(),
                entity.getMaHD()
        );
    }
    
    public void updateTT(float tien,int mhd,boolean tt) {
        JdbcHelper.update(UPDATE_TT_SQL,
                tien,
                tt,
                mhd
        );
    }
    
    @Override
    public void delete(Integer key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(Integer key) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaBan(rs.getString("MaBan"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayHD(rs.getDate("NgayHD"));
                entity.setTongTien(rs.getFloat("TongTien"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HoaDon selectByMahd(String key) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL_MaHD, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<HoaDon> selectANY(Integer MaHD) {
        return this.selectBySql(SELECT_ANY_SQL,MaHD);
    }
    
    
}
