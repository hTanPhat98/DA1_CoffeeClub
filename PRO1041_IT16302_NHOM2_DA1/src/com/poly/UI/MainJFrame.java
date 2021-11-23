package com.poly.UI;

import com.poly.Helper.Auth;
import com.poly.Helper.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author phong
 */
public class MainJFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrameNew
     */
    public MainJFrame() {
        initComponents();
        init();
    }

    private void init() {
        this.setIconImage(XImage.getAppIcon());
        this.setLocationRelativeTo(null);
        //Đồng hồ
        new Timer(1000, new ActionListener() {
            SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");

            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText("Thời gian: " + formatTime.format(new Date()));
            }
        }).start();
        //Ngày tháng
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        lblLich.setText("Hôm nay: " + formatDate.format(date));
        this.thongtinUser();
        
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
        jpnMenu = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblLogo = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        lblViTri = new javax.swing.JLabel();
        btnBanHang = new javax.swing.JButton();
        btnThucDon = new javax.swing.JButton();
        btnKhuVuc = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        jpnBackground = new javax.swing.JPanel();
        lblLich = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        lblBackgrounf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Management");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        jpnMenu.setBackground(new java.awt.Color(51, 51, 51));
        jpnMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpnMenu.setForeground(new java.awt.Color(51, 51, 51));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/logo-horizontal-white_350x70.png"))); // NOI18N

        lblTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNhanVien.setText("Tên: Nguyen Van A");

        lblViTri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblViTri.setForeground(new java.awt.Color(255, 255, 255));
        lblViTri.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblViTri.setText("Vị trí: Nhân Viên");

        btnBanHang.setBackground(new java.awt.Color(255, 255, 255));
        btnBanHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_pos_x32.png"))); // NOI18N
        btnBanHang.setText("BÁN HÀNG");
        btnBanHang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnBanHang.setFocusable(false);
        btnBanHang.setIconTextGap(10);
        btnBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBanHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBanHangMouseExited(evt);
            }
        });
        btnBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangActionPerformed(evt);
            }
        });

        btnThucDon.setBackground(new java.awt.Color(255, 255, 255));
        btnThucDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThucDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_menu_x32.png"))); // NOI18N
        btnThucDon.setText("THỰC ĐƠN");
        btnThucDon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnThucDon.setFocusable(false);
        btnThucDon.setIconTextGap(10);
        btnThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThucDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThucDonMouseExited(evt);
            }
        });
        btnThucDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThucDonActionPerformed(evt);
            }
        });

        btnKhuVuc.setBackground(new java.awt.Color(255, 255, 255));
        btnKhuVuc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnKhuVuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_table_x32.png"))); // NOI18N
        btnKhuVuc.setText("KHU VỰC - BÀN");
        btnKhuVuc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnKhuVuc.setFocusable(false);
        btnKhuVuc.setIconTextGap(10);
        btnKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKhuVucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKhuVucMouseExited(evt);
            }
        });
        btnKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhuVucActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(255, 255, 255));
        btnThongKe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_chart_x32.png"))); // NOI18N
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnThongKe.setFocusable(false);
        btnThongKe.setIconTextGap(10);
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongKeMouseExited(evt);
            }
        });
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_employee_x32.png"))); // NOI18N
        btnNhanVien.setText("NHÂN VIÊN");
        btnNhanVien.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnNhanVien.setFocusable(false);
        btnNhanVien.setIconTextGap(10);
        btnNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNhanVienMouseExited(evt);
            }
        });
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_logout_x32.png"))); // NOI18N
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnDangXuat.setFocusable(false);
        btnDangXuat.setIconTextGap(10);
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseExited(evt);
            }
        });
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnAbout.setBackground(new java.awt.Color(255, 255, 255));
        btnAbout.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/home_info_x32.png"))); // NOI18N
        btnAbout.setText("THÔNG TIN");
        btnAbout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.black, java.awt.Color.black));
        btnAbout.setFocusable(false);
        btnAbout.setIconTextGap(10);
        btnAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAboutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAboutMouseExited(evt);
            }
        });
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnMenuLayout = new javax.swing.GroupLayout(jpnMenu);
        jpnMenu.setLayout(jpnMenuLayout);
        jpnMenuLayout.setHorizontalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jpnMenuLayout.createSequentialGroup()
                        .addComponent(lblTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblViTri, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addComponent(btnThucDon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKhuVuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBanHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnMenuLayout.setVerticalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnThucDon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jpnBackground.setBackground(new java.awt.Color(255, 255, 255));
        jpnBackground.setPreferredSize(new java.awt.Dimension(920, 720));
        jpnBackground.setLayout(null);

        lblLich.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLich.setForeground(new java.awt.Color(255, 255, 255));
        lblLich.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLich.setText("Hôm nay: dd/MM/yyyy");
        jpnBackground.add(lblLich);
        lblLich.setBounds(710, 10, 150, 30);

        lblDongHo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDongHo.setText("Thời gian: hh:mm:ss");
        jpnBackground.add(lblDongHo);
        lblDongHo.setBounds(710, 50, 150, 30);

        lblBackgrounf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBackgrounf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/background_main_920x720.png"))); // NOI18N
        jpnBackground.add(lblBackgrounf);
        lblBackgrounf.setBounds(0, 0, 920, 720);

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(jpnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jpnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangActionPerformed
        this.openBanHang();
    }//GEN-LAST:event_btnBanHangActionPerformed

    private void btnThucDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThucDonActionPerformed
        this.openThucDon();
    }//GEN-LAST:event_btnThucDonActionPerformed

    private void btnKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhuVucActionPerformed
        this.openKhuVuc();
    }//GEN-LAST:event_btnKhuVucActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        this.openThongKe();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        this.openNhanVien();
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        this.openDangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        this.openThongTin();
    }//GEN-LAST:event_btnAboutActionPerformed

    //HOVER
    private void setEntered(JButton btn) {
        btn.setBackground(new Color(51, 153, 255));
        //btn.setBackground(new Color(145, 102, 72));
        btn.setForeground(Color.WHITE);
    }

    private void setDefaultButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
    }

    //Entered EVENT
    private void btnBanHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBanHangMouseEntered
        this.setEntered(btnBanHang);
    }//GEN-LAST:event_btnBanHangMouseEntered

    private void btnThucDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThucDonMouseEntered
        this.setEntered(btnThucDon);
    }//GEN-LAST:event_btnThucDonMouseEntered

    private void btnKhuVucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhuVucMouseEntered
        this.setEntered(btnKhuVuc);
    }//GEN-LAST:event_btnKhuVucMouseEntered

    private void btnThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseEntered
        this.setEntered(btnThongKe);
    }//GEN-LAST:event_btnThongKeMouseEntered

    private void btnNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhanVienMouseEntered
        this.setEntered(btnNhanVien);
    }//GEN-LAST:event_btnNhanVienMouseEntered

    private void btnDangXuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseEntered
        this.setEntered(btnDangXuat);
    }//GEN-LAST:event_btnDangXuatMouseEntered

    private void btnAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAboutMouseEntered
        this.setEntered(btnAbout);
    }//GEN-LAST:event_btnAboutMouseEntered

    //Exited EVENT
    private void btnBanHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBanHangMouseExited
        this.setDefaultButton(btnBanHang);
    }//GEN-LAST:event_btnBanHangMouseExited

    private void btnThucDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThucDonMouseExited
        this.setDefaultButton(btnThucDon);
    }//GEN-LAST:event_btnThucDonMouseExited

    private void btnKhuVucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhuVucMouseExited
        this.setDefaultButton(btnKhuVuc);
    }//GEN-LAST:event_btnKhuVucMouseExited

    private void btnThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseExited
        this.setDefaultButton(btnThongKe);
    }//GEN-LAST:event_btnThongKeMouseExited

    private void btnNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhanVienMouseExited
        this.setDefaultButton(btnNhanVien);
    }//GEN-LAST:event_btnNhanVienMouseExited

    private void btnDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseExited
        this.setDefaultButton(btnDangXuat);
    }//GEN-LAST:event_btnDangXuatMouseExited

    private void btnAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAboutMouseExited
        this.setDefaultButton(btnAbout);
    }//GEN-LAST:event_btnAboutMouseExited

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
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnKhuVuc;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnThucDon;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpnBackground;
    private javax.swing.JPanel jpnMenu;
    private javax.swing.JLabel lblBackgrounf;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblLich;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblViTri;
    private javax.swing.JPanel pnlWall;
    // End of variables declaration//GEN-END:variables

    private void openBanHang() {
        if (Auth.isLogin()) {
            new BanHangJDialog(this, true).setVisible(true);
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Vui Lòng Đăng Nhập!!");
        }
    }

    private void openThucDon() {
        if (Auth.isLogin()) {
            if (Auth.isManager()) {
                new ThucDonJDialog(this, true).setVisible(true);
            }else{
                new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xem!!");
            }
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Vui Lòng Đăng Nhập!!");
        }
    }

    private void openKhuVuc() {
        if (Auth.isLogin()) {
            if (Auth.isManager()) {
                new KhuVucVaBanJDialog(this, true).setVisible(true);
            }else{
                new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xem!!");
            }
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Vui Lòng Đăng Nhập!!");
        }
    }

    private void openThongKe() {
        if (Auth.isLogin()) {
            if (Auth.isManager()) {
                new ThongKeJDialog(this, true).setVisible(true);
            }else{
                new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xem!!");
            }
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Vui Lòng Đăng Nhập!!");
        }
    }

    private void openNhanVien() {
        if (Auth.isLogin()) {
            if (Auth.isManager()) {
                new NhanVienJDialog(this, true).setVisible(true);
            }else{
                new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xem!!");
            }
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Vui Lòng Đăng Nhập!!");
        }
    }

    private void openDangXuat() {
        Auth.clear();
        this.dispose();
        new DangNhapJDialog(this, true).setVisible(true);
        this.thongtinUser();
    }

    private void openThongTin() {
        new AboutJDialog(this, true).setVisible(true);
    }

    private void thongtinUser() {
        if (Auth.user != null) {
            lblTenNhanVien.setText("Tên: " + Auth.nameNV());
            String vt = Auth.isManager() ? "Quản lý" : "Thu Ngân";
            lblViTri.setText("Vị trí: " + vt);
        }
    }
}
