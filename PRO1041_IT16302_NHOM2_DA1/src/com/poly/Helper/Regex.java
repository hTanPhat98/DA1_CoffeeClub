/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.Helper;

import java.awt.Color;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Patnight
 */
public class Regex {

    private String kq = "";

    public String getKq() {
        return kq;
    }

    public boolean checkMaNV(JTextField txt) {
        String text = txt.getText();
        String rgx = "^[A-Z0-9]{5}$";
        if (text.matches(rgx)) {
            return true;
        } else {
            txt.setBorder(new CompoundBorder(new LineBorder(Color.red, 1), new EmptyBorder(1, 4, 1, 1)));
            kq = kq + "Mã NV gồm chữ in hoa, số, tối đa 5 ký tự!\n";
            return false;
        }
    }

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
            kq = kq + "Acc không đúng định dạng!\n";
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
            kq = kq + "Password gồm ít nhất (1 số, 1 chữ IN HOA,1 ký tự ) tối thiểu 8-15 kí tự!\n";
            return false;
        }
    }

}
