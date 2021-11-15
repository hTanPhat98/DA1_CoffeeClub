/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.DAO;

import com.poly.Helper.JdbcHelper;
import com.poly.Model.Menu;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patnight
 */
public class MenuDAO extends CoffeShopSysDAO<Menu, String> {

    String INSERT_SQL = "INSERT INTO MENU (MaMon, TenMon, Gia, HinhAnh, MaLoai) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE MENU SET TenMon=?, Gia=?, HinhAnh=?, MaLoai=? WHERE MaMon=?";
    String DELETE_SQL = "DELETE FROM MENU WHERE MaMon=?";
    String SELECT_ALL_SQL = "SELECT * FROM MENU ";
    String SELECT_BY_ID_SQL = "SELECT * FROM MENU WHERE MaMon=?";

    @Override
    public void insert(Menu entity) {
        JdbcHelper.update(INSERT_SQL, 
                entity.getMaMon(),
                entity.getTenMon(),
                entity.getGia(),
                entity.getHinhAnh(),
                entity.getMaLoai()
                );
    }

    @Override
    public void update(Menu entity) {
        JdbcHelper.update(UPDATE_SQL, 
                entity.getTenMon(),
                entity.getGia(),
                entity.getHinhAnh(),
                entity.getMaLoai(),
                entity.getMaMon()
                );
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<Menu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Menu selectById(String key) {
        List<Menu> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public List<Menu> SelectByIDmaloai(String maloaimon) {
        String sql = "SELECT * FROM MENU where MaLoai='" + maloaimon + "'";
        return selectBySql(sql);
    }
    @Override
    protected List<Menu> selectBySql(String sql, Object... args) {
        List<Menu> list = new ArrayList<Menu>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Menu entity = new Menu();
                entity.setMaMon(rs.getString("MaMon"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setGia(rs.getFloat("Gia"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                entity.setMaLoai(rs.getString("MaLoai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
