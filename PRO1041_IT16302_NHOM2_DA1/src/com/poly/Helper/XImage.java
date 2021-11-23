/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Helper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Patnight
 */
public class XImage {

    public static Image getAppIcon() {
        URL url = XImage.class.getResource("/com/poly/Icons/C_logo_x32.png");
        return new ImageIcon(url).getImage();
    }

    public static void savenv(File src) {
        File dst = new File("image/nhanvien", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void savemenu(File src) {
        File dst = new File("image/menu", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ImageIcon read(String fileName) {
        File defaut = new File("image", "add-photo.png");
        File path = new File("image", fileName);
        Image img = null;
        Image dfimg = null;
        try {
            BufferedImage dfimage = ImageIO.read(defaut);
            dfimg = dfimage;
            BufferedImage image = ImageIO.read(path);
            img = image;
            return new ImageIcon(img);
        } catch (IOException ex) {
            return new ImageIcon(dfimg);
        }
    }

    public static ImageIcon read(String fileName, int w, int h) {
        File defaut = new File("image", "kocogi.jpg");
        File path = new File("image/nhanvien", fileName);
        Image img = null;
        Image dfimg = null;
        try {
            BufferedImage dfimage = ImageIO.read(defaut);
            dfimg =dfimage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            BufferedImage image = ImageIO.read(path);
            img = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException ex) {
            return new ImageIcon(dfimg);
        }
    }
    
    public static ImageIcon readMess(String fileName) {
        File path = new File("src\\com\\poly\\Icons\\message", fileName);
        Image img = null;
        try {
            BufferedImage image = ImageIO.read(path);
            img = image;
            return new ImageIcon(img);
        } catch (IOException ex) {

        }
        return null;
    }
    
    public static ImageIcon readmon(String fileName, int w, int h) {
        File defaut = new File("image", "add-photo.png");
        File path = new File("image/menu", fileName);
        Image img = null;
        Image dfimg = null;
        try {
            BufferedImage dfimage = ImageIO.read(defaut);
            dfimg =dfimage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            BufferedImage image = ImageIO.read(path);
            img = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException ex) {
            return new ImageIcon(dfimg);
        }
    }

}
