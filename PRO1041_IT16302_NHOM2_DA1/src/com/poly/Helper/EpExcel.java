/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.Helper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.poly.UI.ThongBaoJDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author DELL
 */
public class EpExcel {

    public EpExcel() {
    }

    public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        for (int i = 0; i < model.getColumnCount(); i++) {
            bw.write(model.getColumnName(i) + "\t");
        }
        bw.write("\n");

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String st = model.getValueAt(i, j).toString() + "\t";
                bw.write(st);
            }
            bw.write("\n");
        }
        bw.close();
        new ThongBaoJDialog(null, true).alert(1, "Đã xuất Excel ra Màn Hình Chính!");
        System.out.println("Write out to " + file);
    }
}
