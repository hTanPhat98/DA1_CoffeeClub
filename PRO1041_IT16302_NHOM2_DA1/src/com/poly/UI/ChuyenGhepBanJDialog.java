/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.BanDAO;
import com.poly.DAO.HoaDonCTDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.Helper.Auth;
import com.poly.Helper.XImage;
import com.poly.Model.Ban;
import com.poly.Model.HoaDon;
import com.poly.Model.HoaDonCT;
import com.poly.Model.HoaDonShow;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class ChuyenGhepBanJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ChuyenGhepBanJDialog
     */
    public ChuyenGhepBanJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public ChuyenGhepBanJDialog(java.awt.Frame parent, boolean modal, Integer MaHD, BanHangJDialog bhjd) {
        super(parent, modal);
        this.initComponents();
        this.init(MaHD, bhjd);
    }
    BanDAO daoban = new BanDAO();
    HoaDonDAO daohd = new HoaDonDAO();
    HoaDonCTDAO daohdct = new HoaDonCTDAO();
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    private BanHangJDialog athis;
    private HoaDon hd1, hd2;

    private void init(Integer MaHD, BanHangJDialog bhjd) {
        this.setIconImage(XImage.getAppIcon());
        this.setLocationRelativeTo(null);
        this.loadTTBan(MaHD);
        this.fillTable1(MaHD);
        this.loadDSBan(MaHD,0);
        this.athis = bhjd;
    }

    private void fillTable1(Integer MaHD) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonGoc.getModel();
        model.setRowCount(0);
        int i=1;
        try {
            List<HoaDonShow> list = daohdct.selectHDShow(MaHD);
            for (HoaDonShow hdct : list) {
                Object[] row = {
                    i,
                    hdct.getMaHDCT(),
                    hdct.getMaMon(),
                    hdct.getTenMon(),
                    currencyVN.format(hdct.getDonGia()),
                    hdct.getSoLuong(),};
                model.addRow(row);
                i++;
            }
        } catch (Exception e) {
        }
    }

    private void loadTTBan(Integer MaHD) {
        HoaDon hd = daohd.selectById(MaHD);
        hd1 = hd;
        txtBanDangChon.setText(daoban.selectTenBan(hd.getMaBan()));
        txtMaBan.setText(hd.getMaBan());
        txtMaHoaDon.setText(String.valueOf(hd.getMaHD()));
    }

    private void loadMon(int hdct) {
        HoaDonShow hds = daohdct.selecthdctShow(hdct);
        txtMon.setText(hds.getTenMon());
        SpinnerModel model = new SpinnerNumberModel(hds.getSoLuong(), 1, hds.getSoLuong(), 1);
        spnSoLuongChuyen.setModel(model);
        spnSoLuongChuyen.setValue(1);
    }

    private void loadDSBan(Integer maHD, int i) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenGhep.getModel();
        model.removeAllElements();
        List<HoaDon> listHD = daohd.selectAllcTT(maHD);
        List<Ban> listB = daoban.selectAll();
        boolean kt = false, kt1 = false;
        Ban bc = null;
        for (Ban b : listB) {
            if (b.getMaBan().equals(txtMaBan.getText())) {
                kt = true;
                bc = b;
                break;
            }
        }
        if (kt) {
            listB.remove(bc);
        }
        for (Ban b : listB) {
            if (listHD.isEmpty()) {
                if (!b.getMaBan().equals(txtMaBan.getText())) {
                    model.addElement(b.toString());
                }
            } else {
                for (HoaDon hd : listHD) {
                    if (b.getMaBan().equals(hd.getMaBan())) {
                        kt1 = true;
                        break;
                    } else {
                        kt1 = false;
                    }
                }
                if (kt1) {
                    model.addElement(b.toString() + "-(Có HD)");
                } else {
                    model.addElement(b.toString());
                }
            }
        }
        cboChuyenGhep.setSelectedIndex(i);
        this.loadMahdC(model.getElementAt(i).toString());
    }

    private void loadMahdC(String ttb) {
        String[] TTB = ttb.split("-");
        if (TTB.length == 3) {
            String maHD = TTB[1];
            HoaDon hd = daohd.selectByMahd(maHD);
            hd2 = hd;
            txtMaHoaDonBanChuyen.setText(hd.getMaHD() + "");
            this.fillTable2(hd.getMaHD());
            btnTaoHDMoi.setEnabled(false);
        } else {
            txtMaHoaDonBanChuyen.setText("Bàn Mới");
            DefaultTableModel model = (DefaultTableModel) tblHoaDonDaChuyen.getModel();
            model.setRowCount(0);
            btnTaoHDMoi.setEnabled(true);
        }
    }

    private void chuyenBan() {
        if (cboChuyenGhep.getSelectedItem() != null) {
            String ttb = cboChuyenGhep.getSelectedItem().toString();
            this.loadMahdC(ttb);
        }
    }

    private void fillTable2(Integer Mahd) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonDaChuyen.getModel();
        model.setRowCount(0);
        int i=1;
        try {
            List<HoaDonShow> list = daohdct.selectHDShow(Mahd);
            for (HoaDonShow hdct : list) {
                Object[] row = {
                    i,
                    hdct.getMaHDCT(),
                    hdct.getMaMon(),
                    hdct.getTenMon(),
                    currencyVN.format(hdct.getDonGia()),
                    hdct.getSoLuong(),};
                model.addRow(row);
                i++;
            }
        } catch (Exception e) {
        }
    }

    private void chuyenHdctR(int rows) {
        int mahdct = (int) tblHoaDonGoc.getValueAt(rows, 1);
        HoaDonCT hdct = daohdct.selectById(mahdct);
        hdct.setMaHD(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
        this.daohdct.update(hdct);
    }

    private void taoHDMoi() {
        String ttb = cboChuyenGhep.getSelectedItem().toString();
        int i=cboChuyenGhep.getSelectedIndex();
        String[] ttBSplit = ttb.split("-");
        HoaDon hdm = new HoaDon(ttBSplit[1], Auth.user.getMaNV(), new Date(), 0, false);
        daohd.insert(hdm);
        HoaDon hdmt = daohd.selectByMahd(ttBSplit[1]);
        txtMaHoaDonBanChuyen.setText(hdmt.getMaHD() + "");
        btnTaoHDMoi.setEnabled(false);
        this.loadDSBan(Integer.valueOf(txtMaHoaDon.getText()),i);
    }

    private void chuyenR() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Bàn mới chưa có hóa đơn không thể chuyên món!!!");
        } else {
            int rows = tblHoaDonGoc.getSelectedRow();
            if (rows == -1) {
                new ThongBaoJDialog(null, true).alert(2, "Chưa chọn món!!!");
            } else {
                int sld = (int) tblHoaDonGoc.getValueAt(rows, 5), sls;
                int slc = (int) spnSoLuongChuyen.getValue();
                if (slc < sld) {
                    int mahdct = (int) tblHoaDonGoc.getValueAt(rows, 1);
                    HoaDonCT hdct = daohdct.selectById(mahdct);
                    sls = sld - slc;
                    hdct.setSoLuong(sls);
                    daohdct.update(hdct);
                    hdct.setMaHD(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
                    hdct.setSoLuong(slc);
                    daohdct.insert(hdct);
                } else {
                    this.chuyenHdctR(rows);
                }
                fillTable1(Integer.valueOf(txtMaHoaDon.getText()));
                fillTable2(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
            }

        }
    }

    private void chuyenAllR() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Bàn mới chưa có hóa đơn không thể chuyên món!!!");
        } else {
            for (int i = 0; i < tblHoaDonGoc.getRowCount(); i++) {
                this.chuyenHdctR(i);
            }
            fillTable1(Integer.valueOf(txtMaHoaDon.getText()));
            fillTable2(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
        }
    }

    private void chuyenHdctL(int rows) {
        int mahdct = (int) tblHoaDonDaChuyen.getValueAt(rows, 1);
        HoaDonCT hdct = daohdct.selectById(mahdct);
        hdct.setMaHD(Integer.valueOf(txtMaHoaDon.getText()));
        daohdct.update(hdct);
    }

    private void chuyenL() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Bàn mới chưa có hóa đơn không thể chuyên món!!!");
        } else {
            int rows = tblHoaDonDaChuyen.getSelectedRow();
            if (rows == -1) {
                new ThongBaoJDialog(null, true).alert(2, "Chưa chọn món!!!");
            } else {
            int sld = (int) tblHoaDonDaChuyen.getValueAt(rows, 5), sls;
            int slc = (int) spnSoLuongChuyen.getValue();
            if (slc < sld) {
                int mahdct = (int) tblHoaDonDaChuyen.getValueAt(rows, 1);
                HoaDonCT hdct = daohdct.selectById(mahdct);
                sls = sld - slc;
                hdct.setSoLuong(sls);
                daohdct.update(hdct);
                hdct.setMaHD(Integer.valueOf(txtMaHoaDon.getText()));
                hdct.setSoLuong(slc);
                daohdct.insert(hdct);
            } else {
                chuyenHdctL(rows);
            }
            fillTable1(Integer.valueOf(txtMaHoaDon.getText()));
            fillTable2(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
            }
        }
    }

    private void chuyenAllL() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Bàn mới chưa có hóa đơn không thể chuyên món!!!");
        } else {
            for (int i = 0; i < tblHoaDonDaChuyen.getRowCount(); i++) {
                this.chuyenHdctL(i);
            }
            fillTable1(Integer.valueOf(txtMaHoaDon.getText()));
            fillTable2(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
        }
    }

    private void clickTblLeft() {
        btnMoveAllLeft.setEnabled(false);
        btnMoveLeft.setEnabled(false);
        btnMoveRight.setEnabled(true);
        btnMoveAllRight.setEnabled(true);
        int rows = tblHoaDonGoc.getSelectedRow();
        int hdct = (int) tblHoaDonGoc.getValueAt(rows, 1);
        this.loadMon(hdct);
    }

    private void clickTblRight() {
        btnMoveAllLeft.setEnabled(true);
        btnMoveLeft.setEnabled(true);
        btnMoveRight.setEnabled(false);
        btnMoveAllRight.setEnabled(false);
        int rows = tblHoaDonDaChuyen.getSelectedRow();
        int hdct = (int) tblHoaDonDaChuyen.getValueAt(rows, 1);
        this.loadMon(hdct);
    }

    private void chuyenDoiBan() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Chưa có hóa đơn trên bàn mới!!!");
        } else if (tblHoaDonGoc.getRowCount() == 0) {
            daohd.delete(Integer.valueOf(txtMaHoaDon.getText()));
            athis.resetForm();
            this.dispose();
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Danh sách của hóa đơn cần chuyển còn sản phẩm!!!");
        }

    }

    private void gopGhepBan() {
        if (tblHoaDonGoc.getRowCount() == 0 && tblHoaDonDaChuyen.getRowCount() == 0) {
            new ThongBaoJDialog(null, true).alert(2, "Không được phép ghép 2 hóa đơn trống!!!");
        } else if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Chưa có hóa đơn trên bàn mới!!!");
        } else if (tblHoaDonGoc.getRowCount() == 0 || tblHoaDonDaChuyen.getRowCount() == 0) {
            daoban.updateGB(hd2.getMaBan(), hd1.getMaBan());
            daoban.updateGB(hd1.getMaBan(), hd2.getMaBan());
            athis.resetForm();
            this.dispose();
        } else {
            new ThongBaoJDialog(null, true).alert(2, "1 trong 2 danh sách sản phẩm phải để trống!!!");
        }
    }

    private void chuyenMon() {
        athis.resetForm();
        this.dispose();
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
        pnlButtonBox = new javax.swing.JPanel();
        btnMoveRight = new javax.swing.JButton();
        btnMoveAllRight = new javax.swing.JButton();
        btnMoveLeft = new javax.swing.JButton();
        btnMoveAllLeft = new javax.swing.JButton();
        lblBanDangChon = new javax.swing.JLabel();
        lblMaBan = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        lblChuyenGhep = new javax.swing.JLabel();
        lblMaHoaDonBanChuyen = new javax.swing.JLabel();
        lblMon = new javax.swing.JLabel();
        lblSoLuongChuyen = new javax.swing.JLabel();
        txtBanDangChon = new javax.swing.JTextField();
        txtMaBan = new javax.swing.JTextField();
        txtMaHoaDon = new javax.swing.JTextField();
        txtMaHoaDonBanChuyen = new javax.swing.JTextField();
        txtMon = new javax.swing.JTextField();
        cboChuyenGhep = new javax.swing.JComboBox<>();
        spnSoLuongChuyen = new javax.swing.JSpinner();
        btnGopBan = new javax.swing.JButton();
        btnChuyenBan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonGoc = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonDaChuyen = new javax.swing.JTable();
        btnTaoHDMoi = new javax.swing.JButton();
        btnChuyenMon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chuyển bàn - ghép bàn");
        setResizable(false);

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("CHUYỂN BÀN - GHÉP BÀN");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(10, 0, 1260, 50);

        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1600x50.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 1290, 50);

        pnlButtonBox.setBackground(new java.awt.Color(255, 255, 255));

        btnMoveRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_next_x32.png"))); // NOI18N
        btnMoveRight.setBorderPainted(false);
        btnMoveRight.setContentAreaFilled(false);
        btnMoveRight.setEnabled(false);
        btnMoveRight.setFocusable(false);
        btnMoveRight.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_next_x32blue.png"))); // NOI18N
        btnMoveRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveRightActionPerformed(evt);
            }
        });

        btnMoveAllRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_last_x32.png"))); // NOI18N
        btnMoveAllRight.setBorderPainted(false);
        btnMoveAllRight.setContentAreaFilled(false);
        btnMoveAllRight.setEnabled(false);
        btnMoveAllRight.setFocusable(false);
        btnMoveAllRight.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_last_x32blue.png"))); // NOI18N
        btnMoveAllRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveAllRightActionPerformed(evt);
            }
        });

        btnMoveLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_prev_x32.png"))); // NOI18N
        btnMoveLeft.setBorderPainted(false);
        btnMoveLeft.setContentAreaFilled(false);
        btnMoveLeft.setEnabled(false);
        btnMoveLeft.setFocusable(false);
        btnMoveLeft.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_prev_x32blue.png"))); // NOI18N
        btnMoveLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveLeftActionPerformed(evt);
            }
        });

        btnMoveAllLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_first_x32.png"))); // NOI18N
        btnMoveAllLeft.setBorderPainted(false);
        btnMoveAllLeft.setContentAreaFilled(false);
        btnMoveAllLeft.setEnabled(false);
        btnMoveAllLeft.setFocusable(false);
        btnMoveAllLeft.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_first_x32blue.png"))); // NOI18N
        btnMoveAllLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveAllLeftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonBoxLayout = new javax.swing.GroupLayout(pnlButtonBox);
        pnlButtonBox.setLayout(pnlButtonBoxLayout);
        pnlButtonBoxLayout.setHorizontalGroup(
            pnlButtonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlButtonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMoveRight)
                    .addComponent(btnMoveAllRight)
                    .addComponent(btnMoveLeft)
                    .addComponent(btnMoveAllLeft))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlButtonBoxLayout.setVerticalGroup(
            pnlButtonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMoveRight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveAllRight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveAllLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        lblBanDangChon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBanDangChon.setText("Bàn đang chọn:");

        lblMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaBan.setText("Mã bàn:");

        lblMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHoaDon.setText("Mã hóa đơn:");

        lblChuyenGhep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblChuyenGhep.setText("Chuyển-ghép:");

        lblMaHoaDonBanChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHoaDonBanChuyen.setText("Mã hóa đơn bàn chuyển:");

        lblMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMon.setText("Món:");

        lblSoLuongChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoLuongChuyen.setText("Số lượng chuyển:");

        txtBanDangChon.setEditable(false);
        txtBanDangChon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaBan.setEditable(false);
        txtMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaHoaDonBanChuyen.setEditable(false);
        txtMaHoaDonBanChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMon.setEditable(false);
        txtMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboChuyenGhep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChuyenGhep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChuyenGhep.setFocusable(false);
        cboChuyenGhep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenGhepActionPerformed(evt);
            }
        });

        spnSoLuongChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        spnSoLuongChuyen.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spnSoLuongChuyen.setValue(1);

        btnGopBan.setBackground(new java.awt.Color(255, 255, 255));
        btnGopBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGopBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/table_combine_blue_x32.png"))); // NOI18N
        btnGopBan.setText("GỘP BÀN");
        btnGopBan.setFocusable(false);
        btnGopBan.setIconTextGap(10);
        btnGopBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGopBanActionPerformed(evt);
            }
        });

        btnChuyenBan.setBackground(new java.awt.Color(255, 255, 255));
        btnChuyenBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChuyenBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/table_move_blue_x32.png"))); // NOI18N
        btnChuyenBan.setText("CHUYỂN BÀN");
        btnChuyenBan.setFocusable(false);
        btnChuyenBan.setIconTextGap(10);
        btnChuyenBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenBanActionPerformed(evt);
            }
        });

        tblHoaDonGoc.setAutoCreateRowSorter(true);
        tblHoaDonGoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã HDCT", "Mã món", "Tên Món", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonGoc.setRowHeight(20);
        tblHoaDonGoc.getTableHeader().setReorderingAllowed(false);
        tblHoaDonGoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonGocMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDonGoc);
        if (tblHoaDonGoc.getColumnModel().getColumnCount() > 0) {
            tblHoaDonGoc.getColumnModel().getColumn(0).setMinWidth(50);
            tblHoaDonGoc.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblHoaDonGoc.getColumnModel().getColumn(0).setMaxWidth(50);
            tblHoaDonGoc.getColumnModel().getColumn(1).setMinWidth(60);
            tblHoaDonGoc.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblHoaDonGoc.getColumnModel().getColumn(1).setMaxWidth(60);
            tblHoaDonGoc.getColumnModel().getColumn(5).setMinWidth(60);
            tblHoaDonGoc.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblHoaDonGoc.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        tblHoaDonDaChuyen.setAutoCreateRowSorter(true);
        tblHoaDonDaChuyen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã HDCT", "Mã món", "Tên món", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonDaChuyen.setRowHeight(20);
        tblHoaDonDaChuyen.getTableHeader().setReorderingAllowed(false);
        tblHoaDonDaChuyen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonDaChuyenMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonDaChuyen);
        if (tblHoaDonDaChuyen.getColumnModel().getColumnCount() > 0) {
            tblHoaDonDaChuyen.getColumnModel().getColumn(0).setMinWidth(50);
            tblHoaDonDaChuyen.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblHoaDonDaChuyen.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        btnTaoHDMoi.setBackground(new java.awt.Color(255, 255, 255));
        btnTaoHDMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoHDMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue_x32.png"))); // NOI18N
        btnTaoHDMoi.setText("TẠO HÓA ĐƠN MỚI");
        btnTaoHDMoi.setFocusable(false);
        btnTaoHDMoi.setIconTextGap(10);
        btnTaoHDMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDMoiActionPerformed(evt);
            }
        });

        btnChuyenMon.setBackground(new java.awt.Color(255, 255, 255));
        btnChuyenMon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChuyenMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/table_move_hdct_blue_x32.png"))); // NOI18N
        btnChuyenMon.setText("CHUYỂN MÓN");
        btnChuyenMon.setFocusable(false);
        btnChuyenMon.setIconTextGap(10);
        btnChuyenMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addComponent(lblMaHoaDon)
                        .addContainerGap())
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBanDangChon)
                            .addComponent(lblMaBan)
                            .addComponent(lblMon)
                            .addComponent(lblSoLuongChuyen))
                        .addGap(8, 8, 8)
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlWallLayout.createSequentialGroup()
                                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlWallLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(spnSoLuongChuyen, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                            .addComponent(txtMon))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWallLayout.createSequentialGroup()
                                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtBanDangChon)
                                            .addComponent(txtMaBan))
                                        .addGap(123, 123, 123)))
                                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnlWallLayout.createSequentialGroup()
                                            .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblMaHoaDonBanChuyen)
                                                .addComponent(lblChuyenGhep))
                                            .addGap(20, 20, 20)
                                            .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cboChuyenGhep, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtMaHoaDonBanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnTaoHDMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlWallLayout.createSequentialGroup()
                                            .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2))
                                    .addGroup(pnlWallLayout.createSequentialGroup()
                                        .addGap(158, 158, 158)
                                        .addComponent(btnChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50))
                            .addGroup(pnlWallLayout.createSequentialGroup()
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlButtonBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 584, Short.MAX_VALUE))))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBanDangChon)
                            .addComponent(txtBanDangChon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaBan)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblChuyenGhep)
                            .addComponent(cboChuyenGhep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaHoaDonBanChuyen)
                            .addComponent(txtMaHoaDonBanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHoaDon)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHDMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlButtonBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMon)
                    .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSoLuongChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoLuongChuyen)
                    .addComponent(btnChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
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

    private void cboChuyenGhepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenGhepActionPerformed
        this.chuyenBan();
    }//GEN-LAST:event_cboChuyenGhepActionPerformed

    private void btnMoveAllRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveAllRightActionPerformed
        this.chuyenAllR();
    }//GEN-LAST:event_btnMoveAllRightActionPerformed

    private void tblHoaDonGocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonGocMousePressed
        this.clickTblLeft();
    }//GEN-LAST:event_tblHoaDonGocMousePressed

    private void tblHoaDonDaChuyenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonDaChuyenMousePressed
        this.clickTblRight();
    }//GEN-LAST:event_tblHoaDonDaChuyenMousePressed

    private void btnMoveRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveRightActionPerformed
        this.chuyenR();
    }//GEN-LAST:event_btnMoveRightActionPerformed

    private void btnMoveLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveLeftActionPerformed
        this.chuyenL();
    }//GEN-LAST:event_btnMoveLeftActionPerformed

    private void btnMoveAllLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveAllLeftActionPerformed
        this.chuyenAllL();
    }//GEN-LAST:event_btnMoveAllLeftActionPerformed

    private void btnChuyenBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenBanActionPerformed
        this.chuyenDoiBan();
    }//GEN-LAST:event_btnChuyenBanActionPerformed

    private void btnGopBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGopBanActionPerformed
        this.gopGhepBan();
    }//GEN-LAST:event_btnGopBanActionPerformed

    private void btnTaoHDMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDMoiActionPerformed
        this.taoHDMoi();
    }//GEN-LAST:event_btnTaoHDMoiActionPerformed

    private void btnChuyenMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenMonActionPerformed
        this.chuyenMon();
    }//GEN-LAST:event_btnChuyenMonActionPerformed

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
            java.util.logging.Logger.getLogger(ChuyenGhepBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChuyenGhepBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChuyenGhepBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChuyenGhepBanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChuyenGhepBanJDialog dialog = new ChuyenGhepBanJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnChuyenBan;
    private javax.swing.JButton btnChuyenMon;
    private javax.swing.JButton btnGopBan;
    private javax.swing.JButton btnMoveAllLeft;
    private javax.swing.JButton btnMoveAllRight;
    private javax.swing.JButton btnMoveLeft;
    private javax.swing.JButton btnMoveRight;
    private javax.swing.JButton btnTaoHDMoi;
    private javax.swing.JComboBox<String> cboChuyenGhep;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBanDangChon;
    private javax.swing.JLabel lblChuyenGhep;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblMaHoaDonBanChuyen;
    private javax.swing.JLabel lblMon;
    private javax.swing.JLabel lblSoLuongChuyen;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlButtonBox;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JSpinner spnSoLuongChuyen;
    private javax.swing.JTable tblHoaDonDaChuyen;
    private javax.swing.JTable tblHoaDonGoc;
    private javax.swing.JTextField txtBanDangChon;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaHoaDonBanChuyen;
    private javax.swing.JTextField txtMon;
    // End of variables declaration//GEN-END:variables
}
