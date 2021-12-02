/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.Helper;

import java.awt.Color;
import static java.awt.Color.red;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Patnight
 */
public class Regex {

    private String kq = "";

    public String getKq() {
        return kq;
    }

    //NHAN VIEN VA TAI KHOAN
//    public boolean checkMaNV(JTextField txt) {
//        String text = txt.getText();
//        String rgx = "^[A-Z0-9]{5}$";
//        if (text.matches(rgx)) {
//            return true;
//        } else {
//            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
//            kq = kq + "Mã NV gồm chữ in hoa, số, tối đa 5 ký tự!\n";
//            return false;
//        }
//    }

    public boolean checkNameNV(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{5,30}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "Tên NV gồm chữ in hoa, chữ thường, giới hạn 5-30 ký tự!\n";
            return false;
        }
    }

    public boolean checkSDT(JTextField txt) {
        String text = txt.getText();
        String rgx = "^(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|084|085|081|082|092|056|058|099|059)[0-9]{7}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "SDT NV phải là đầu số ở Việt Nam, và không chứa ký tự chữ cái!\n";
            return false;
        }
    }

    public boolean checkEmail(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[a-zA-Z][a-zA-Z0-9_\\.]{2,32}@[a-zA-Z0-9]{2,10}(\\.[a-zA-Z0-9]{2,4}){1,2}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "Email không đúng định dạng!\n";
            return false;
        }
    }

    public boolean checkCMND(JTextField txt) {
        String text = txt.getText();
        String rgx1 = "^[0-9]{9}$";
        String rgx2 = "^[0-9]{12}$";
        boolean kt = false;
        if (text.matches(rgx1)) {
            kt = true;
        } else {
            kt = text.matches(rgx2);
        }

        if (kt) {
            return kt;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "CMND-CCCD không đúng định dạng!\n";
            return kt;
        }
    }

    public boolean checkAccount(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[a-zA-Z0-9]{6,15}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "Tên tài khoản không chúa dấu và kí tự đặc biệt, tối thiểu 6-15 kí tự!\n";
            return false;
        }
    }

    public boolean checkPassword(JTextField txt) {
        String text = txt.getText();
        String rgx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "Mật khẩu gồm ít nhất (1 số, 1 chữ IN HOA,1 ký tự ) tối thiểu 8-15 kí tự!\n";
            return false;
        }
    }
    
    //KHU VUC VA BAN
//    public boolean checkMaKV(JTextField txt) {
//        String text = txt.getText();
//        String rgx = "^[A-Z0-9]{3,5}$";
//        if (text.matches(rgx)) {
//            return true;
//        } else {
//            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
//            kq = kq + "Mã khu vực chỉ bao gồm kí tự in hoa không dấu và số, tối tiểu 3-5 kí tự!\n";
//            return false;
//        }
//    }

    public boolean checkTenKV(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,20}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Tên khu vực không chứa kí tự đặc biệt và tối thiểu 3-20 kí tự!\n";
            return false;
        }
    }

    public boolean checkTienIchKV(JTextArea txt) {
        String text = txt.getText();
        String rgx = "^[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{0,50}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Tên tiện ích không chứa kí tự đặc biệt và tối đa 50 kí tự!\n";
            return false;
        }
    }

    public boolean checkMaBan(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[A-Z0-9]{4,10}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Mã Bàn bao gồm kí tự in hoa và số, tối thiểu 4-10 kí tự!\n";
            return false;
        }
    }

    public boolean checkTenBan(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{6,8}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Tên Bàn không chứa kí tự đặc biệt, giới hạn 6-8 kí tự!\n";
            return false;
        }
    }
    //THUC DON
    public boolean checkMaLM(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[A-Z]{3,10}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0,red));
            kq = kq + "Mã loại món gồm chữ in hoa tối đa 3-10 ký tự!\n";
            return false;
        }
    }

    public boolean checkTenLM(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{5,20}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Tên món gồm chữ in hoa ,in thường giới hạn 5-20 ký tự!\n";
            return false;
        }
    }
//    public boolean checkMamon(JTextField txt){
//        String text = txt.getText();
//        String rgx = "^[A-Z0-9]{3,10}$";
//        if (text.matches(rgx)){
//            return true;
//        }else {
//            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
//            kq = kq + "Mã món bao gồm kí tự in hoa và số giới hạn 3-10 kí tự!\n";
//            return false;
//        }
//    }
    
    public boolean checkTenmon(JTextField txt){
        String text = txt.getText();
        String rgx = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,50}$";
        if (text.matches(rgx)){
            return true;
        }else {
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Tên món không chứa kí tự đặc biệt và số giới hạn 3-50 kí tự!\n";
            return false;
        }
    }
    
    public boolean checkDongia(JTextField txt){
        String text = txt.getText();
        String rgx = "^1[0-9]{4,5}$";
        if (text.matches(rgx)) {
            return true;
        }else{
            txt.setBorder(new MatteBorder(0, 0, 1, 0, red));
            kq = kq + "Đơn giá không chứa kí tự chữ và giá trị âm! ";
        }
        return false;
    }


}
