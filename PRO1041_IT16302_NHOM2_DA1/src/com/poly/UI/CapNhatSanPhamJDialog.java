/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.HoaDonCTDAO;
import com.poly.Model.HoaDonCT;
import com.poly.Model.HoaDonShow;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author phong
 */
public class CapNhatSanPhamJDialog extends javax.swing.JDialog {

    /**
     * Creates new form UpdateSanPhamJDialog
     */
    public CapNhatSanPhamJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }
    private HoaDonShow hds;
    BanHangJDialog athis;
    HoaDonCTDAO daohdct = new HoaDonCTDAO();
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    public CapNhatSanPhamJDialog(java.awt.Frame parent, boolean modal, BanHangJDialog bhjd, HoaDonShow HDCT) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.setHDCT(HDCT);
        athis = bhjd;

    }

    private void setHDCT(HoaDonShow HDCT) {
        txtTenSP.setText(HDCT.getTenMon());
        txtGiaTienSP.setText(currencyVN.format(HDCT.getDonGia()));
        spnSoLuong.setValue(HDCT.getSoLuong());
        hds = HDCT;
    }

    private void updateSL() {
        hds.setSoLuong((int) spnSoLuong.getValue());
        daohdct.updateSl(hds);
        HoaDonCT hdct = daohdct.selectById(hds.getMaHDCT());
        athis.fillTbHDdc(hdct.getMaHD());
        this.dispose();
    }

    private void deleteHDCT() {
        try {
            daohdct.delete(hds.getMaHDCT());
            HoaDonCT hdct = daohdct.selectById(hds.getMaHDCT());
            athis.fillTbHDdc(hdct.getMaHD());
            new ThongBaoJDialog(null, true).alert(1, "Xóa món thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Xóa món thất bại!");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlWall = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblHeader = new javax.swing.JLabel();
        pnlCapNhatSP = new javax.swing.JPanel();
        lblTenSP = new javax.swing.JLabel();
        lblGiaTien = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        txtGiaTienSP = new javax.swing.JTextField();
        spnSoLuong = new javax.swing.JSpinner();
        btnDone = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cập nhật sản phẩm");

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("CẬP NHẬT SẢN PHẨM");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(0, 0, 400, 50);

        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_update_400x50.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 400, 50);

        pnlCapNhatSP.setBackground(new java.awt.Color(255, 255, 255));

        lblTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenSP.setText("Tên sản phẩm:");

        lblGiaTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGiaTien.setText("Giá tiền:");

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoLuong.setText("Số lượng:");

        txtTenSP.setEditable(false);
        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtGiaTienSP.setEditable(false);
        txtGiaTienSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        spnSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        spnSoLuong.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        btnDone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/CNSP_done_x32.png"))); // NOI18N
        btnDone.setText("DONE");
        btnDone.setIconTextGap(10);
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/CNSP_close_x32.png"))); // NOI18N
        btnClose.setText("CLOSE");
        btnClose.setIconTextGap(10);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/close_red_x32.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCapNhatSPLayout = new javax.swing.GroupLayout(pnlCapNhatSP);
        pnlCapNhatSP.setLayout(pnlCapNhatSPLayout);
        pnlCapNhatSPLayout.setHorizontalGroup(
            pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenSP)
                    .addComponent(txtGiaTienSP)
                    .addGroup(pnlCapNhatSPLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenSP)
                            .addComponent(lblGiaTien)
                            .addComponent(lblSoLuong))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlCapNhatSPLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(spnSoLuong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        pnlCapNhatSPLayout.setVerticalGroup(
            pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblGiaTien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtGiaTienSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSoLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlCapNhatSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWallLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCapNhatSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCapNhatSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        this.updateSL();
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.deleteHDCT();
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CapNhatSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhatSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhatSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhatSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CapNhatSanPhamJDialog dialog = new CapNhatSanPhamJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDone;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlCapNhatSP;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JSpinner spnSoLuong;
    private javax.swing.JTextField txtGiaTienSP;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
