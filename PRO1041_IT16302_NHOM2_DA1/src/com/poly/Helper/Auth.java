/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Helper;

import com.poly.DAO.AccountDAO;
import com.poly.Model.Account;




/**
 *
 * @author Patnight
 */
public class Auth {
    
    
    
    public static Account user = null;

    public static void clear() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }
    
    public static String nameNV(){
        AccountDAO dao=new AccountDAO();
        return dao.selectNameAccount(user.getMaNV());
    }
}
