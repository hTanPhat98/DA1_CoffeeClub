/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class NhanVienDAO extends CoffeShopSysDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO NHANVIEN (MaNV, TenNV, Hinh, CMND, DiaChi, GioiTinh, Email, DienThoai, NgaySinh, NgayVaoLam, ViTri) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NHANVIEN SET TenNV=?, Hinh=?, CMND=?, DiaChi=?, GioiTinh=?, Email=?, DienThoai=?, NgaySinh=?, NgayVaoLam=?, ViTri=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN ";
    String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MaNV=?";
    String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NHANVIEN WHERE MaNV LIKE ? OR TenNV Like ?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getTenNV(),
                entity.getHinhNV(),
                entity.getCmnd(),
                entity.getDiaChi(),
                entity.getGioiTinh(),
                entity.getEmail(),
                entity.getDienThoai(),
                entity.getNgaySinh(),
                entity.getNgayVaoLam(),
                entity.getViTri()
        );
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenNV(),
                entity.getHinhNV(),
                entity.getCmnd(),
                entity.getDiaChi(),
                entity.getGioiTinh(),
                entity.getEmail(),
                entity.getDienThoai(),
                entity.getNgaySinh(),
                entity.getNgayVaoLam(),
                entity.getViTri(),
                entity.getMaNV()
        );
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setHinhNV(rs.getString("Hinh"));
                entity.setCmnd(rs.getString("CMND"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setGioiTinh(rs.getString("GioiTinh"));
                entity.setEmail(rs.getString("Email"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                entity.setViTri(rs.getString("ViTri"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NhanVien> selectByKeyword(String keyword, String manh) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%");
    }

    public NhanVien selectFindNV(String keyword) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
