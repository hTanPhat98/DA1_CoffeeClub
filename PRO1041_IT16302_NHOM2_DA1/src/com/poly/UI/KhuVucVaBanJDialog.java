/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.BanDAO;
import com.poly.DAO.KhuVucDAO;
import com.poly.Helper.MsgBox;
import com.poly.Model.Ban;
import com.poly.Model.KhuVuc;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class KhuVucVaBanJDialog extends javax.swing.JDialog {

    public KhuVucVaBanJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
        this.setLocationRelativeTo(null);
        fillToListKV();
        updateStatutTable();
        updateStatutKV();
    }

    int index = -1;
    KhuVucDAO daokv = new KhuVucDAO();
    BanDAO daoban = new BanDAO();
    List<Ban> listBan = new ArrayList();
    String itemliskhuvuc = "";

    // TABLE AND LIST
    private void fillToTableBan() {
        DefaultTableModel model = (DefaultTableModel) tblQuanLyKhuVucBan.getModel();
        model.setRowCount(0);
        try {
            List<Ban> list = daoban.selectAll();
            for (Ban ban : list) {
                KhuVuc listkv = daokv.selectById(ban.getMaKV());
                Object[] row = {
                    ban.getMaBan(),
                    ban.getTenBan(),
                    listkv.getTenKV(),
                    listkv.getTienIch()};
                Ban banla = new Ban();
                banla.setMaBan(ban.getMaBan());
                banla.setTenBan(ban.getTenBan());
                banla.setMaKV(ban.getMaKV());
                listBan.add(banla);

                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "thông báo lỗi truy vấn");
        }
    }

    void fillToListKV() {
        fillToTableBan();
        DefaultListModel model = new DefaultListModel();

        try {
            itemliskhuvuc = "";
            List<KhuVuc> list = daokv.selectAll();
            cboKhuVucBan.removeAllItems();
            for (KhuVuc kv : list) {
                model.addElement(kv.getTenKV() + "-" + kv.getMaKV());
                cboKhuVucBan.addItem(kv.getTenKV() + "-" + kv.getMaKV());
                itemliskhuvuc += kv.getTenKV() + "-" + kv.getMaKV() + "chiakey";
            }
            lstKhuVucBan.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "thông báo lỗi truy vấn");
        }
    }

    void setModelBan(Ban model) {

        txtMaBan.setText(model.getMaBan());
        txtTenBan.setText(model.getTenBan());

        String[] key = itemliskhuvuc.split("chiakey");
        for (int i = 0; i < key.length; i++) {
            if (key[i].indexOf(model.getMaKV()) != -1) {
                cboKhuVucBan.setSelectedItem(key[i]);
            }
        }
    }

    Ban getModelBan() {
        Ban model = new Ban();
        String key = (String) cboKhuVucBan.getSelectedItem();
        String[] keys = key.split("-");
        model.setMaBan(txtMaBan.getText());
        model.setTenBan(txtTenBan.getText());

        model.setMaKV(keys[1]);
        System.out.println(keys[1]);
        return model;

    }

    void editBan() {
        try {
            String kvb = (String) tblQuanLyKhuVucBan.getValueAt(this.index, 0);
            Ban model = daoban.selectById(kvb);
            if (model != null) {
                this.setModelBan(model);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "thông báo lỗi truy vấn");
        }
    }

    void setModelkhuvuc(KhuVuc kv) {
        txtMaKV.setText(kv.getMaKV());
        txtTenKV.setText(kv.getTenKV());
        txtTienIch.setText(kv.getTienIch());
    }

    KhuVuc getModelKhuvuc() {
        KhuVuc model = new KhuVuc();
        model.setMaKV(txtMaKV.getText());
        model.setTenKV(txtTenKV.getText());
        model.setTienIch(txtTienIch.getText());
        return model;

    }

    //Click evt
    void clickEvtListKhuVuc() {
        String makv = (String) lstKhuVucBan.getSelectedValue();
        String[] key = makv.split("-");
        DefaultTableModel modelbang = (DefaultTableModel) tblQuanLyKhuVucBan.getModel();
        modelbang.setRowCount(0);

        try {
            KhuVuc model = daokv.selectById(key[1]);
            if (model != null) {
                this.setModelkhuvuc(model);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }

        try {

            List<Ban> list = daoban.findByIdKhuVuc(key[1]);
            for (Ban b : list) {
                KhuVuc listkv = daokv.selectById(b.getMaKV());
                Object[] row = {
                    b.getMaBan(),
                    b.getTenBan(),
                    listkv.getTenKV(),
                    listkv.getTienIch()
                };
                modelbang.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        
        this.index=lstKhuVucBan.getSelectedIndex();
        updateStatutKV();
    }

    private void clickTableKVB(MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            this.index = tblQuanLyKhuVucBan.getSelectedRow();
            this.editBan();
            updateStatutTable();
        }
    }

    // CONTROL
    private void clearFormKV() {
        KhuVuc kv = new KhuVuc();
        kv.setMaKV("");
        kv.setTenKV("");
        kv.setTienIch("");
        this.setModelkhuvuc(kv);
        //this.fillTableBan();
        this.index = -1;
        updateStatutKV();
    }

    private void clearFormBan() {
        Ban ban = new Ban();
        ban.setMaBan("");
        ban.setTenBan("");
        ban.setMaKV("");
        this.setModelBan(ban);
        cboKhuVucBan.setSelectedItem(null);
        this.index = -1;
        updateStatutTable();
    }

    void insertKhuvuc() {
        KhuVuc kv = getModelKhuvuc();

        try {
            daokv.insert(kv);
            this.fillToListKV();

            System.out.println("Saved");

        } catch (Exception e) {
            System.out.println("Save failed");

        }
    }

    void deleteKhuVuc() {
        int confirm = JOptionPane.showConfirmDialog(null, "Thao tác sẽ xóa mọi dữ liệu về khu vực, xác nhận xóa hoặc không?");

        if (confirm == JOptionPane.YES_OPTION) {
            String makv = txtMaKV.getText();
            try {
                daokv.delete(makv);
                this.fillToListKV();
                System.out.println("Deleted");
                clearFormKV();

            } catch (Exception e) {
                System.out.println("Delete failed!");
            }
        }
    }

    void updateKhuvuc() {
        KhuVuc kv = getModelKhuvuc();

        try {
            daokv.update(kv);
            this.fillToListKV();
            System.out.println("Updated");

        } catch (Exception e) {
            System.out.println("Udapte failed!");

        }
    }

    void insertBan() {
        String cbo = (String) cboKhuVucBan.getSelectedItem();
        String[] makhuvuc = cbo.split("-"); //xử cắt chuỗi lấy makv tại cboKhuVucBan
        List<Ban> list = daoban.findByIdKhuVuc(makhuvuc[1]);

        if (list.size() == 12) {
            System.out.println("Khu vực này đã đạt số bàn tối đa!");
        } else if (list.size() < 12) {

            Ban model = getModelBan();

            try {
                daoban.insert(model);
                fillToTableBan();
                System.out.println("Saved");
            } catch (Exception e) {
                System.out.println("Save failed");
            }
        }
    }

    void updateBan() {
        String cbo = (String) cboKhuVucBan.getSelectedItem();
        String[] makhuvuc = cbo.split("-"); //xử cắt chuỗi lấy makv tại cboKhuVucBan
        List<Ban> list = daoban.findByIdKhuVuc(makhuvuc[1]);

        if (list.size() == 12) {
            System.out.println("Khu vực này đã đạt số bàn tối đa!");
        } else if (list.size() < 12) {

            Ban ban = getModelBan();

            try {
                daoban.update(ban);
                fillToTableBan();
                System.out.println("Updated");
            } catch (Exception e) {
                System.out.println("Update failed");
            }
        }
    }

    void deleteban() {
        int confirm = JOptionPane.showConfirmDialog(null, "Thao tác sẽ xóa mọi dữ liệu của bàn, xác nhận xóa hoặc không?");

        if (confirm == JOptionPane.YES_OPTION) {
            String maban = txtMaBan.getText();
            try {
                daoban.delete(maban);
                this.fillToListKV();
                System.out.println("Deleted");
                clearFormBan();

            } catch (Exception e) {
                System.out.println("Delete failed!");
            }
        }
    }

    //STATUT
    void updateStatutTable() {
        boolean edit = (this.index >= 0);
        txtMaBan.setEditable(!edit);
        btnSaveTbl.setEnabled(!edit);
        btnUpdateTbl.setEnabled(edit);
        btnDeleteTbl.setEnabled(edit);
    }

    void updateStatutKV() {
        boolean edit = (this.index >= 0);

        txtMaKV.setEditable(!edit);
        btnSaveKV.setEnabled(!edit);
        btnUpdateKV.setEnabled(edit);
        btnDeleteKV.setEnabled(edit);
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
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstKhuVucBan = new javax.swing.JList<>();
        btnAll = new javax.swing.JButton();
        pnlTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQuanLyKhuVucBan = new javax.swing.JTable();
        pnlControl = new javax.swing.JPanel();
        pnlKhuVuc = new javax.swing.JPanel();
        txtMaKV = new javax.swing.JTextField();
        txtTenKV = new javax.swing.JTextField();
        lblMaKV = new javax.swing.JLabel();
        lblTenKV = new javax.swing.JLabel();
        lblTienIch = new javax.swing.JLabel();
        pnlControl1 = new javax.swing.JPanel();
        btnNewKV = new javax.swing.JButton();
        btnSaveKV = new javax.swing.JButton();
        btnUpdateKV = new javax.swing.JButton();
        btnDeleteKV = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTienIch = new javax.swing.JTextArea();
        pnlBan = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        lblTenBan = new javax.swing.JLabel();
        lblKhuVucBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        txtTenBan = new javax.swing.JTextField();
        cboKhuVucBan = new javax.swing.JComboBox<>();
        pnlControl2 = new javax.swing.JPanel();
        btnNewTbl = new javax.swing.JButton();
        btnSaveTbl = new javax.swing.JButton();
        btnUpdateTbl = new javax.swing.JButton();
        btnDeleteTbl = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ KHU VỰC - BÀN");

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setBackground(new java.awt.Color(204, 204, 204));
        pnlHeader.setPreferredSize(new java.awt.Dimension(1280, 60));
        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("QUẢN LÝ KHU VỰC - BÀN");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(10, 4, 1260, 40);

        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1280x50.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 1280, 50);

        pnlList.setBackground(new java.awt.Color(255, 255, 255));
        pnlList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khu vực bàn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        pnlList.setPreferredSize(new java.awt.Dimension(150, 370));

        lstKhuVucBan.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        lstKhuVucBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lstKhuVucBan.setAlignmentX(1.0F);
        lstKhuVucBan.setAlignmentY(1.0F);
        lstKhuVucBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstKhuVucBanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstKhuVucBan);

        btnAll.setBackground(new java.awt.Color(255, 255, 255));
        btnAll.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAll.setText("Tất cả");
        btnAll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btnAll.setFocusable(false);
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        tblQuanLyKhuVucBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblQuanLyKhuVucBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã bàn", "Tên bàn", "Khu vực", "Tiện ích khu vực"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLyKhuVucBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLyKhuVucBanMouseClicked(evt);
            }
        });
        tblQuanLyKhuVucBan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblQuanLyKhuVucBanKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblQuanLyKhuVucBan);

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnlControl.setBackground(new java.awt.Color(255, 255, 255));

        pnlKhuVuc.setBackground(new java.awt.Color(255, 255, 255));
        pnlKhuVuc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "KHU VỰC", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        txtMaKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaKV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        txtTenKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenKV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        lblMaKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaKV.setText("Mã khu vực:");

        lblTenKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenKV.setText("Tên khu vực:");

        lblTienIch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTienIch.setText("Tiện ích:");

        pnlControl1.setBackground(new java.awt.Color(255, 255, 255));
        pnlControl1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnNewKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewKV.setText("Tạo mới khu vực");
        btnNewKV.setPreferredSize(new java.awt.Dimension(150, 40));
        btnNewKV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKVActionPerformed(evt);
            }
        });
        pnlControl1.add(btnNewKV);

        btnSaveKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSaveKV.setText("Thêm khu vực");
        btnSaveKV.setPreferredSize(new java.awt.Dimension(150, 40));
        btnSaveKV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveKVActionPerformed(evt);
            }
        });
        pnlControl1.add(btnSaveKV);

        btnUpdateKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateKV.setText("Sửa khu vực");
        btnUpdateKV.setPreferredSize(new java.awt.Dimension(150, 40));
        btnUpdateKV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKVActionPerformed(evt);
            }
        });
        pnlControl1.add(btnUpdateKV);

        btnDeleteKV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteKV.setText("Xóa khu vực");
        btnDeleteKV.setPreferredSize(new java.awt.Dimension(150, 40));
        btnDeleteKV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteKVActionPerformed(evt);
            }
        });
        pnlControl1.add(btnDeleteKV);

        txtTienIch.setColumns(20);
        txtTienIch.setRows(5);
        jScrollPane3.setViewportView(txtTienIch);

        javax.swing.GroupLayout pnlKhuVucLayout = new javax.swing.GroupLayout(pnlKhuVuc);
        pnlKhuVuc.setLayout(pnlKhuVucLayout);
        pnlKhuVucLayout.setHorizontalGroup(
            pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhuVucLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaKV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenKV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienIch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlKhuVucLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtMaKV))
                    .addGroup(pnlKhuVucLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenKV))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhuVucLayout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(pnlControl1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        pnlKhuVucLayout.setVerticalGroup(
            pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhuVucLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaKV)
                    .addComponent(lblMaKV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenKV)
                    .addComponent(lblTenKV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlKhuVucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTienIch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(pnlControl1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlBan.setBackground(new java.awt.Color(255, 255, 255));
        pnlBan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BÀN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaBan.setText("Mã bàn:");

        lblTenBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenBan.setText("Tên bàn:");

        lblKhuVucBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblKhuVucBan.setText("Khu vực bàn:");

        txtMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        txtTenBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));

        cboKhuVucBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnlControl2.setBackground(new java.awt.Color(255, 255, 255));
        pnlControl2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnNewTbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewTbl.setText("Tạo mới bàn");
        btnNewTbl.setPreferredSize(new java.awt.Dimension(150, 40));
        btnNewTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTblActionPerformed(evt);
            }
        });
        pnlControl2.add(btnNewTbl);

        btnSaveTbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSaveTbl.setText("Thêm bàn");
        btnSaveTbl.setPreferredSize(new java.awt.Dimension(150, 40));
        btnSaveTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTblActionPerformed(evt);
            }
        });
        pnlControl2.add(btnSaveTbl);

        btnUpdateTbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateTbl.setText("Cập nhật bàn");
        btnUpdateTbl.setPreferredSize(new java.awt.Dimension(150, 40));
        btnUpdateTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTblActionPerformed(evt);
            }
        });
        pnlControl2.add(btnUpdateTbl);

        btnDeleteTbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteTbl.setText("Xóa bàn");
        btnDeleteTbl.setPreferredSize(new java.awt.Dimension(150, 40));
        btnDeleteTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTblActionPerformed(evt);
            }
        });
        pnlControl2.add(btnDeleteTbl);

        javax.swing.GroupLayout pnlBanLayout = new javax.swing.GroupLayout(pnlBan);
        pnlBan.setLayout(pnlBanLayout);
        pnlBanLayout.setHorizontalGroup(
            pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKhuVucBan)
                    .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblMaBan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTenBan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20)
                .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaBan)
                    .addComponent(txtTenBan)
                    .addComponent(cboKhuVucBan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBanLayout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(pnlControl2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        pnlBanLayout.setVerticalGroup(
            pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBanLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKhuVucBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKhuVucBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlControl2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addComponent(pnlKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlKhuVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addComponent(pnlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void tblQuanLyKhuVucBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyKhuVucBanMouseClicked
        clickTableKVB(evt);
    }//GEN-LAST:event_tblQuanLyKhuVucBanMouseClicked

    private void lstKhuVucBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstKhuVucBanMouseClicked
        clickEvtListKhuVuc();
    }//GEN-LAST:event_lstKhuVucBanMouseClicked

    private void tblQuanLyKhuVucBanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblQuanLyKhuVucBanKeyReleased
        this.index = tblQuanLyKhuVucBan.getSelectedRow();
        this.editBan();
    }//GEN-LAST:event_tblQuanLyKhuVucBanKeyReleased

    //Control KV
    private void btnNewKVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKVActionPerformed
        clearFormKV();
    }//GEN-LAST:event_btnNewKVActionPerformed

    private void btnSaveKVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveKVActionPerformed
        insertKhuvuc();
    }//GEN-LAST:event_btnSaveKVActionPerformed

    private void btnUpdateKVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKVActionPerformed
        updateKhuvuc();
    }//GEN-LAST:event_btnUpdateKVActionPerformed

    private void btnDeleteKVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteKVActionPerformed
        deleteKhuVuc();
    }//GEN-LAST:event_btnDeleteKVActionPerformed

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        this.fillToTableBan();
    }//GEN-LAST:event_btnAllActionPerformed

    //Control ban
    private void btnNewTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTblActionPerformed
        clearFormBan();
    }//GEN-LAST:event_btnNewTblActionPerformed

    private void btnSaveTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTblActionPerformed
        insertBan();
    }//GEN-LAST:event_btnSaveTblActionPerformed

    private void btnUpdateTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTblActionPerformed
        updateBan();
    }//GEN-LAST:event_btnUpdateTblActionPerformed

    private void btnDeleteTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTblActionPerformed
        deleteban();
    }//GEN-LAST:event_btnDeleteTblActionPerformed

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
            java.util.logging.Logger.getLogger(KhuVucVaBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhuVucVaBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhuVucVaBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhuVucVaBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhuVucVaBanJDialog dialog = new KhuVucVaBanJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnDeleteKV;
    private javax.swing.JButton btnDeleteTbl;
    private javax.swing.JButton btnNewKV;
    private javax.swing.JButton btnNewTbl;
    private javax.swing.JButton btnSaveKV;
    private javax.swing.JButton btnSaveTbl;
    private javax.swing.JButton btnUpdateKV;
    private javax.swing.JButton btnUpdateTbl;
    private javax.swing.JComboBox<String> cboKhuVucBan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblKhuVucBan;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblMaKV;
    private javax.swing.JLabel lblTenBan;
    private javax.swing.JLabel lblTenKV;
    private javax.swing.JLabel lblTienIch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<String> lstKhuVucBan;
    private javax.swing.JPanel pnlBan;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlControl1;
    private javax.swing.JPanel pnlControl2;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlKhuVuc;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JTable tblQuanLyKhuVucBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMaKV;
    private javax.swing.JTextField txtTenBan;
    private javax.swing.JTextField txtTenKV;
    private javax.swing.JTextArea txtTienIch;
    // End of variables declaration//GEN-END:variables
}
