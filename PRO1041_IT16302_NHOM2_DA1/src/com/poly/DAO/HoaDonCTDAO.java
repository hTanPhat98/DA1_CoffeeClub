/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.HoaDonCT;
//import com.poly.Model.HoaDonShow;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class HoaDonCTDAO extends CoffeShopSysDAO<HoaDonCT, Integer> {

    String INSERT_SQL = "INSERT INTO HOADONCT (MaHD, MaMon, SoLuong, DonGia, TenMon) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE HOADONCT SET MaHD=?, MaMon=?, SoLuong=?, DonGia=?, TenMon=? WHERE MaHDCT=?";
    String DELETE_SQL = "DELETE FROM HOADONCT WHERE MaHDCT=?";
    String SELECT_ALL_SQL = "SELECT * FROM HOADONCT ";
    String SELECT_BY_ID_SQL = "SELECT * FROM HOADONCT WHERE MaHDCT=?";
    
    //hoa don show
    String SELECT_HD_HDCT = "SELECT hdct.MaHDCT,hdct.MaMon,mon.TenMon,hdct.DonGia,hdct.SoLuong FROM HOADONCT hdct"
            + " JOIN HOADON hd on hdct.MaHD=hd.MaHD"
            + "	JOIN MENU mon on mon.MaMon =hdct.MaMon"
            + "	WHERE hd.MaHD=?";
    String SELECT_HDCT = "SELECT hdct.MaHDCT,hdct.MaMon,mon.TenMon,hdct.DonGia,hdct.SoLuong FROM HOADONCT hdct"
            + " JOIN HOADON hd on hdct.MaHD=hd.MaHD"
            + "	JOIN MENU mon on mon.MaMon =hdct.MaMon"
            + "	WHERE hdct.MaHDCT=?";
    
    String UPDATE_SL = "UPDATE HOADONCT SET SoLuong=? WHERE MaHDCT=?";
    String SELECT_ID_HD = "SELECT * FROM HOADONCT WHERE MaHD=?";

    @Override
    public void insert(HoaDonCT entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaHD(),
                entity.getMaMon(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTenMon()
        );
    }

    @Override
    public void update(HoaDonCT entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaHD(),
                entity.getMaMon(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getTenMon(),
                entity.getMaHDCT()
                
        );
    }

    public void updateSl(HoaDonCT entity) {
        JdbcHelper.update(UPDATE_SL,
                entity.getSoLuong(),
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
        List<HoaDonCT> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonCT entity = new HoaDonCT();
                entity.setMaHDCT(rs.getInt("MaHDCT"));
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaMon(rs.getString("MaMon"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getFloat("DonGia"));
                entity.setTenMon(rs.getString("TenMon"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    protected List<HoaDonShow> selectHDCT(String sql, Object... args) {
//        List<HoaDonShow> list = new ArrayList<>();
//        try {
//            ResultSet rs = JdbcHelper.query(sql, args);
//            while (rs.next()) {
//                HoaDonShow entity = new HoaDonShow();
//                entity.setMaHDCT(rs.getInt("MaHDCT"));
//                entity.setMaMon(rs.getString("MaMon"));
//                entity.setTenMon(rs.getString("TenMon"));
//                entity.setDonGia(rs.getFloat("DonGia"));
//                entity.setSoLuong(rs.getInt("SoLuong"));
//                list.add(entity);
//            }
//            rs.getStatement().getConnection().close();
//            return list;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<HoaDonShow> selectHDShow(Integer key) {
//        return this.selectHDCT(SELECT_HD_HDCT, key);
//    }
//
//    public HoaDonShow selecthdctShow(Integer key) {
//        List<HoaDonShow> list = this.selectHDCT(SELECT_HDCT, key);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }

    public List<HoaDonCT> selectHDCTByHD(Integer key) {
        return this.selectBySql(SELECT_ID_HD, key);
    }

}
