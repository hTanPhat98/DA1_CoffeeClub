/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.LoaiMonDAO;
import com.poly.DAO.MenuDAO;
import com.poly.Helper.Auth;
import com.poly.Helper.MsgBox;
import com.poly.Model.LoaiMon;
import com.poly.Model.Menu;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class ThucDonJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ThucDonJDialog
     */
    public ThucDonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        this.setLocationRelativeTo(null);
        this.indexM = -1;
        this.indexLM = -1;
        this.loadToListLM();
        this.updateStatusMon();
        this.updateStatusLoaiMon();
    }

    int indexM = -1, indexLM = -1;
    MenuDAO daomenu = new MenuDAO();
    List<Menu> listmn = new ArrayList();
    LoaiMonDAO daoloaimon = new LoaiMonDAO();
    String itermlistloai = "";

    // TABLE AND LIST
    private void fillToTableMenu() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiMon.getModel();
        model.setRowCount(0);
        try {
            List<Menu> list = daomenu.selectAll();
            for (Menu mon : list) {
                LoaiMon listlm = daoloaimon.selectById(mon.getMaLoai());
                Object[] row = {
                    mon.getMaMon(),
                    mon.getTenMon(),
                    mon.getGia(),
                    listlm.getMaLoai()};
                Menu listmenu = new Menu();
                listmenu.setMaMon(mon.getMaMon());
                listmenu.setTenMon(mon.getTenMon());
                listmenu.setGia(mon.getGia());
                listmenu.setMaLoai(mon.getMaLoai());
                listmn.add(listmenu);
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void loadToListLM() {
        fillToTableMenu();
        DefaultListModel model = new DefaultListModel();

        try {
            itermlistloai = "";
            List<LoaiMon> list = daoloaimon.selectAll();
            cboDanhMuc.removeAllItems();
            for (LoaiMon lm : list) {
                model.addElement(lm.getTenLoai() + "-" + lm.getMaLoai());
                cboDanhMuc.addItem(lm.getTenLoai() + "-" + lm.getMaLoai());
                itermlistloai += lm.getTenLoai() + "-" + lm.getMaLoai() + "chiakey";
            }
            lstLoaiMon.setModel(model);
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    private void setmodelMon(Menu model) {
        txtMaMon.setText(model.getMaMon());
        txtTenMon.setText(model.getTenMon());
        txtDonGia.setText(String.valueOf(model.getGia()));
        String[] key = itermlistloai.split("chiakey");
        for (int i = 0; i < key.length; i++) {
            if (key[i].indexOf(String.valueOf(model.getMaLoai())) != -1) {
                cboDanhMuc.setSelectedItem(key[i]);
            }
        }
    }

    private Menu getmodelMon() {
        Menu menu = new Menu();
        menu.setMaMon(txtMaMon.getText());
        menu.setTenMon(txtTenMon.getText());
        menu.setGia(Float.valueOf(txtDonGia.getText()));
        menu.setHinhAnh(lblAnhMon.getToolTipText());
        String key = (String) cboDanhMuc.getSelectedItem();
        String[] keys = key.split("-");
        menu.setMaLoai(keys[1]);
        System.out.println(keys[1]);
        return menu;

    }

    private void editMon() {
        try {
            String mnu = (String) tblLoaiMon.getValueAt(this.indexM, 0);
            Menu model = daomenu.selectById(mnu);
            if (model != null) {
                this.setmodelMon(model);
                updateStatusMon();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "thông báo lỗi truy vấn");
        }
    }

    private void clearMon() {
        this.setmodelMon(new Menu());
        this.indexM = -1;
        this.updateStatusMon();
    }

    private void updateStatusMon() {
        boolean edit = (this.indexM >= 0);
        //  txtMaMon.setEditable(!edit);
        btnSaveM.setEnabled(!edit);
        btnUpdateM.setEnabled(edit);
        btnDeleteM.setEnabled(edit);
    }

    private void updateStatusLoaiMon() {
        boolean edit = (this.indexLM >= 0);
        btnSaveLM.setEnabled(!edit);
        btnUpdateLM.setEnabled(edit);
        btnDeleteLM.setEnabled(edit);
    }

    private void setmodelLoai(LoaiMon lm) {
        txtMaLoaiMon.setText(lm.getMaLoai());
        txtTenLoaiMon.setText(lm.getTenLoai());
    }

    LoaiMon getLoaiMon() {
        LoaiMon model = new LoaiMon();
        model.setMaLoai(txtMaLoaiMon.getText());
        model.setTenLoai(txtTenLoaiMon.getText());

        return model;
    }

    private void clearLoaiMon() {
        this.setmodelLoai(new LoaiMon());
        this.indexLM=-1;
        this.updateStatusLoaiMon();
    }

    private void editLoaiMon() {
        try {
            String lm = (String) lstLoaiMon.getSelectedValue();
            LoaiMon model = daoloaimon.selectById(lm);
            if (model != null) {
                this.setmodelLoai(model);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "thông báo lỗi truy vấn");
        }
    }

    private void editListDanhMuc() {
        String maloai = (String) lstLoaiMon.getSelectedValue();
        String[] key = maloai.split("-");
        DefaultTableModel modelbang = (DefaultTableModel) tblLoaiMon.getModel();
        modelbang.setRowCount(0);
        try {
            LoaiMon model = daoloaimon.selectById(key[1]);
            if (model != null) {
                this.setmodelLoai(model);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        try {
            List<Menu> list = (List<Menu>) daomenu.SelectByIDmaloai(key[1]);
            for (Menu m : list) {
                Object[] row = {
                    m.getMaMon(),
                    m.getTenMon(),
                    m.getGia(),
                    m.getMaLoai()
                };
                modelbang.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    private void clickListLM(MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            this.indexLM = lstLoaiMon.getSelectedIndex();
            this.editListDanhMuc();
            this.updateStatusLoaiMon();
        }
    }
    
    private void clickTableMenu(MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            this.indexM = tblLoaiMon.getSelectedRow();
            this.editMon();
            this.updateStatusMon();
        }
    }

    //CONTROL
    private void insertMon() {
        String cbo = (String) cboDanhMuc.getSelectedItem();
        String[] maLoai = cbo.split("-");
        List<Menu> list = daomenu.SelectByIDmaloai(maLoai[1]);
        Menu menu = getmodelMon();
        try {
            daomenu.insert(menu);
            this.fillToTableMenu();
            this.clearMon();
            new ThongBaoJDialog(null, true).alert(1, "Thêm mới thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Thêm mới thất bại!");
            e.printStackTrace();
        }
    }

    private void updateMon() {
        String cbo = (String) cboDanhMuc.getSelectedItem();
        String[] maLoai = cbo.split("-");
        List<Menu> list = daomenu.SelectByIDmaloai(maLoai[1]);
        try {
            Menu mn = getmodelMon();
            daomenu.update(mn);
            this.fillToTableMenu();
            new ThongBaoJDialog(null, true).alert(1, "Cập nhật thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Cập nhật thất bại!");
        }
    }

    private void deleteMon() {
        if (!Auth.isManager()) {
            new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xóa!");
        } else {
            if (new ThongBaoJDialog(null, true).confirm("Bạn thực sự muốn xóa món ăn này?")) {
                String maloaimon = txtMaMon.getText();
                try {
                    daomenu.delete(maloaimon);
                    this.fillToTableMenu();
                    this.clearMon();
                    new ThongBaoJDialog(null, true).alert(1, "Xóa thành công!");
                } catch (Exception e) {
                    new ThongBaoJDialog(null, true).alert(2, "Xóa thất bại!");
                }
            }
        }
    }

    private void insertLoaiMon() {
        LoaiMon lm = getLoaiMon();
        try {
            daoloaimon.insert(lm);
            this.loadToListLM();
            this.clearLoaiMon();
            new ThongBaoJDialog(null, true).alert(1, "Thêm mới thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Thêm mới thất bại!");
            e.printStackTrace();
        }
    }

    private void updateLoaiMon() {
        try {
            LoaiMon lm = getLoaiMon();
            daoloaimon.update(lm);
            this.loadToListLM();
            new ThongBaoJDialog(null, true).alert(1, "Cập nhật thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Cập nhật thất bại!");
        }
    }

    private void deleteLoaiMon() {
        if (!Auth.isManager()) {
            new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xóa!");
        } else {
            if (new ThongBaoJDialog(null, true).confirm("Bạn thực sự muốn xóa món ăn này?")) {
                String maloaimon = txtMaLoaiMon.getText();
                try {
                    daoloaimon.delete(maloaimon);
                    this.loadToListLM();
                    this.clearLoaiMon();
                    new ThongBaoJDialog(null, true).alert(1, "Xóa thành công!");
                } catch (Exception e) {
                    new ThongBaoJDialog(null, true).alert(2, "Xóa thất bại!");
                }
            }
        }
    }

    //CONTROL
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
        lblHeaderBackground = new javax.swing.JLabel();
        pnlMon = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTenMon = new javax.swing.JLabel();
        lblDonGia = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        lblAnhMon = new javax.swing.JLabel();
        txtMaMon = new javax.swing.JTextField();
        txtTenMon = new javax.swing.JTextField();
        cboDanhMuc = new javax.swing.JComboBox<>();
        lblMaMon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlControl1 = new javax.swing.JPanel();
        btnNewM = new javax.swing.JButton();
        btnSaveM = new javax.swing.JButton();
        btnUpdateM = new javax.swing.JButton();
        btnDeleteM = new javax.swing.JButton();
        txtDonGia = new javax.swing.JTextField();
        pnlLoaiMon = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstLoaiMon = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiMon = new javax.swing.JTable();
        pnlLoaiMonBox = new javax.swing.JPanel();
        lblMaLoaiMon = new javax.swing.JLabel();
        lblTenLoaiMon = new javax.swing.JLabel();
        txtMaLoaiMon = new javax.swing.JTextField();
        txtTenLoaiMon = new javax.swing.JTextField();
        pnlControl2 = new javax.swing.JPanel();
        btnNewLM = new javax.swing.JButton();
        btnSaveLM = new javax.swing.JButton();
        btnUpdateLM = new javax.swing.JButton();
        btnDeleteLM = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setPreferredSize(new java.awt.Dimension(1600, 50));
        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("THỰC ĐƠN");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(10, 0, 1580, 50);

        lblHeaderBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeaderBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1920x100.png"))); // NOI18N
        lblHeaderBackground.setToolTipText("");
        pnlHeader.add(lblHeaderBackground);
        lblHeaderBackground.setBounds(0, 0, 1600, 50);

        pnlMon.setBackground(new java.awt.Color(255, 255, 255));
        pnlMon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MÓN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblLogo.setBackground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/logo-horizontal-black_350x70.png"))); // NOI18N

        lblTenMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenMon.setText("Tên món:");

        lblDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn giá:");

        lblDanhMuc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDanhMuc.setText("Danh mục:");

        lblAnhMon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhMon.setText("Ảnh");
        lblAnhMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMaMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaMon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTenMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenMon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        cboDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "PHIN Coffee", "Espresso Coffee", "Trà", "Bánh mì", "Bánh ngọt", "Thức uống" }));

        lblMaMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaMon.setText("Mã món:");

        pnlControl1.setBackground(new java.awt.Color(255, 255, 255));
        pnlControl1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 20));

        btnNewM.setBackground(new java.awt.Color(255, 255, 255));
        btnNewM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewM.setText("Tạo mới");
        btnNewM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnNewM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMActionPerformed(evt);
            }
        });
        pnlControl1.add(btnNewM);

        btnSaveM.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSaveM.setText("Thêm");
        btnSaveM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnSaveM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMActionPerformed(evt);
            }
        });
        pnlControl1.add(btnSaveM);

        btnUpdateM.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdateM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateM.setText("Sửa");
        btnUpdateM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnUpdateM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMActionPerformed(evt);
            }
        });
        pnlControl1.add(btnUpdateM);

        btnDeleteM.setBackground(new java.awt.Color(255, 255, 255));
        btnDeleteM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteM.setText("Xóa");
        btnDeleteM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnDeleteM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMActionPerformed(evt);
            }
        });
        pnlControl1.add(btnDeleteM);

        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDonGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlMonLayout = new javax.swing.GroupLayout(pnlMon);
        pnlMon.setLayout(pnlMonLayout);
        pnlMonLayout.setHorizontalGroup(
            pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlControl1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMonLayout.createSequentialGroup()
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDanhMuc)
                            .addComponent(lblMaMon)
                            .addComponent(lblTenMon)
                            .addComponent(lblDonGia))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenMon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaMon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboDanhMuc, 0, 360, Short.MAX_VALUE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlMonLayout.setVerticalGroup(
            pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMonLayout.createSequentialGroup()
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMaMon)
                            .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTenMon)
                            .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(pnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControl1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pnlLoaiMon.setBackground(new java.awt.Color(255, 255, 255));

        lstLoaiMon.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "LOẠI MÓN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        lstLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lstLoaiMon.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Tất cả", "PHIN Coffee", "Espresso Coffee", "Trà", "Bánh mì", "Bánh ngọt", "Thức uống", " ", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstLoaiMon.setAlignmentX(1.0F);
        lstLoaiMon.setAlignmentY(1.0F);
        lstLoaiMon.setFocusable(false);
        lstLoaiMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstLoaiMonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lstLoaiMon);

        tblLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLoaiMon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã món", "Tên", "Đơn giá", "Mã loại món"
            }
        ));
        tblLoaiMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiMonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiMon);

        javax.swing.GroupLayout pnlLoaiMonLayout = new javax.swing.GroupLayout(pnlLoaiMon);
        pnlLoaiMon.setLayout(pnlLoaiMonLayout);
        pnlLoaiMonLayout.setHorizontalGroup(
            pnlLoaiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiMonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlLoaiMonLayout.setVerticalGroup(
            pnlLoaiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiMonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoaiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pnlLoaiMonBox.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoaiMonBox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LOẠI MÓN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblMaLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaLoaiMon.setText("Mã loại món:");

        lblTenLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenLoaiMon.setText("Tên loại món:");

        txtMaLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaLoaiMon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTenLoaiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenLoaiMon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        pnlControl2.setBackground(new java.awt.Color(255, 255, 255));
        pnlControl2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 20));

        btnNewLM.setBackground(new java.awt.Color(255, 255, 255));
        btnNewLM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewLM.setText("Tạo mới");
        btnNewLM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnNewLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewLMActionPerformed(evt);
            }
        });
        pnlControl2.add(btnNewLM);

        btnSaveLM.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveLM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSaveLM.setText("Thêm");
        btnSaveLM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnSaveLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLMActionPerformed(evt);
            }
        });
        pnlControl2.add(btnSaveLM);

        btnUpdateLM.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdateLM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateLM.setText("Sửa");
        btnUpdateLM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnUpdateLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateLMActionPerformed(evt);
            }
        });
        pnlControl2.add(btnUpdateLM);

        btnDeleteLM.setBackground(new java.awt.Color(255, 255, 255));
        btnDeleteLM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteLM.setText("Xóa");
        btnDeleteLM.setPreferredSize(new java.awt.Dimension(200, 40));
        btnDeleteLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLMActionPerformed(evt);
            }
        });
        pnlControl2.add(btnDeleteLM);

        javax.swing.GroupLayout pnlLoaiMonBoxLayout = new javax.swing.GroupLayout(pnlLoaiMonBox);
        pnlLoaiMonBox.setLayout(pnlLoaiMonBoxLayout);
        pnlLoaiMonBoxLayout.setHorizontalGroup(
            pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiMonBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoaiMonBoxLayout.createSequentialGroup()
                        .addGroup(pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenLoaiMon)
                            .addComponent(lblMaLoaiMon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLoaiMon)
                            .addComponent(txtTenLoaiMon)))
                    .addComponent(pnlControl2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLoaiMonBoxLayout.setVerticalGroup(
            pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiMonBoxLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMaLoaiMon)
                    .addComponent(txtMaLoaiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlLoaiMonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTenLoaiMon)
                    .addComponent(txtTenLoaiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(pnlControl2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiMonBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlLoaiMon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLoaiMon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addComponent(pnlMon, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlLoaiMonBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)))
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

    private void btnNewMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMActionPerformed
        this.clearMon();
    }//GEN-LAST:event_btnNewMActionPerformed

    private void btnSaveMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMActionPerformed
        this.insertMon();
    }//GEN-LAST:event_btnSaveMActionPerformed

    private void btnUpdateMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMActionPerformed
        this.updateMon();
    }//GEN-LAST:event_btnUpdateMActionPerformed

    private void btnDeleteMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMActionPerformed
        this.deleteMon();
    }//GEN-LAST:event_btnDeleteMActionPerformed

    private void lstLoaiMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstLoaiMonMouseClicked
        this.clickListLM(evt);
    }//GEN-LAST:event_lstLoaiMonMouseClicked

    private void tblLoaiMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiMonMouseClicked
        this.clickTableMenu(evt);
    }//GEN-LAST:event_tblLoaiMonMouseClicked

    private void btnNewLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewLMActionPerformed
        this.clearLoaiMon();
    }//GEN-LAST:event_btnNewLMActionPerformed

    private void btnSaveLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLMActionPerformed
        this.insertLoaiMon();
    }//GEN-LAST:event_btnSaveLMActionPerformed

    private void btnUpdateLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateLMActionPerformed
        this.updateLoaiMon();
    }//GEN-LAST:event_btnUpdateLMActionPerformed

    private void btnDeleteLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLMActionPerformed
        this.deleteLoaiMon();
    }//GEN-LAST:event_btnDeleteLMActionPerformed

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
            java.util.logging.Logger.getLogger(ThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThucDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThucDonJDialog dialog = new ThucDonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDeleteLM;
    private javax.swing.JButton btnDeleteM;
    private javax.swing.JButton btnNewLM;
    private javax.swing.JButton btnNewM;
    private javax.swing.JButton btnSaveLM;
    private javax.swing.JButton btnSaveM;
    private javax.swing.JButton btnUpdateLM;
    private javax.swing.JButton btnUpdateM;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnhMon;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblHeaderBackground;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaLoaiMon;
    private javax.swing.JLabel lblMaMon;
    private javax.swing.JLabel lblTenLoaiMon;
    private javax.swing.JLabel lblTenMon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<String> lstLoaiMon;
    private javax.swing.JPanel pnlControl1;
    private javax.swing.JPanel pnlControl2;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlLoaiMon;
    private javax.swing.JPanel pnlLoaiMonBox;
    private javax.swing.JPanel pnlMon;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JTable tblLoaiMon;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaLoaiMon;
    private javax.swing.JTextField txtMaMon;
    private javax.swing.JTextField txtTenLoaiMon;
    private javax.swing.JTextField txtTenMon;
    // End of variables declaration//GEN-END:variables
}
