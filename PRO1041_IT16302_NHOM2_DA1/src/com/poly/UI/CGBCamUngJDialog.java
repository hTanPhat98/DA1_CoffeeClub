/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.BanDAO;
import com.poly.DAO.HoaDonCTDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.NhanVienDAO;
import com.poly.Helper.Auth;
import com.poly.Helper.XImage;
import com.poly.Model.Ban;
import com.poly.Model.HoaDon;
import com.poly.Model.HoaDonCT;
import com.poly.Model.NhanVien;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class CGBCamUngJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ChuyenGhepBanJDialog
     */
    public CGBCamUngJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public CGBCamUngJDialog(java.awt.Frame parent, boolean modal, Integer MaHD, BanHangCamUngJDialog bhjd) {
        super(parent, modal);
        this.initComponents();
        this.init(MaHD, bhjd);
    }

    BanDAO daoban = new BanDAO();
    HoaDonDAO daohd = new HoaDonDAO();
    HoaDonCTDAO daohdct = new HoaDonCTDAO();
    NhanVienDAO daonv = new NhanVienDAO();
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    private BanHangCamUngJDialog athis;
    private HoaDon hd1, hd2;

    private void init(Integer MaHD, BanHangCamUngJDialog bhjd) {
        this.addClosing();
        this.setIconImage(XImage.getAppIcon());
        this.setLocationRelativeTo(null);
        this.loadTTBan(MaHD);
        this.fillTable1(MaHD);
        this.loadDSBan(MaHD, 0);
        athis = bhjd;
    }

    private void fillTable1(Integer MaHD) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonGoc.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<HoaDonCT> list = daohdct.selectHDCTByHD(MaHD);
            for (HoaDonCT hdct : list) {
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
        Ban b = daoban.selectById(hd.getMaBan());
        hd1 = hd;
        txtBanDangChon.setText(daoban.selectTenBan(hd.getMaBan()));
        txtMaBan.setText(hd.getMaBan());
        txtMaHoaDon.setText(String.valueOf(hd.getMaHD()));
        if (!b.getGhepBan().equals("")) {
            btnGopBan.setEnabled(false);
        }
    }

    private void loadMon(int hdct) {
        HoaDonCT hds = daohdct.selectById(hdct);
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
            String maBan = TTB[1];
            Ban b = daoban.selectById(maBan);
            Ban b1 = daoban.selectById(txtMaBan.getText());
            if (!b.getGhepBan().equals("")) {
                btnChuyenBan.setEnabled(false);
                btnChuyenMon.setEnabled(false);
                btnGopBan.setEnabled(false);
            } else {
                btnChuyenBan.setEnabled(true);
                btnChuyenMon.setEnabled(true);
                if (b1.getGhepBan().equals("")) {
                    btnGopBan.setEnabled(true);
                } else {
                    btnGopBan.setEnabled(false);
                }
            }
            HoaDon hd = daohd.selectByMahd(maBan);
            hd2 = hd;
            txtMaHoaDonBanChuyen.setText(hd.getMaHD() + "");
            this.fillTable2(hd.getMaHD());
            btnTaoHDMoi.setEnabled(false);
        } else {
            String maBan = TTB[1];
            Ban b = daoban.selectById(maBan);
            if (b.getGhepBan().equals("")) {
                btnChuyenBan.setEnabled(true);
                btnChuyenMon.setEnabled(true);
            }
            txtMaHoaDonBanChuyen.setText("Bàn Mới");
            DefaultTableModel model = (DefaultTableModel) tblHoaDonDaChuyen.getModel();
            model.setRowCount(0);
            btnTaoHDMoi.setEnabled(true);
        }
    }

    private void chonBan() {
        if (cboChuyenGhep.getSelectedItem() != null) {
            String ttb = cboChuyenGhep.getSelectedItem().toString();
            this.loadMahdC(ttb);
        }
    }

    private void fillTable2(Integer Mahd) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonDaChuyen.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<HoaDonCT> list = daohdct.selectHDCTByHD(Mahd);
            for (HoaDonCT hdct : list) {
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
        int i = cboChuyenGhep.getSelectedIndex();
        NhanVien nv = daonv.selectById(Auth.user.getMaNV());
        String[] ttBSplit = ttb.split("-");
        HoaDon hdm = new HoaDon(ttBSplit[1], nv.getMaNV(), new Date(), 0, false, nv.getTenNV());
        daohd.insert(hdm);
        HoaDon hdmt = daohd.selectByMahd(ttBSplit[1]);
        txtMaHoaDonBanChuyen.setText(hdmt.getMaHD() + "");
        btnTaoHDMoi.setEnabled(false);
        this.loadDSBan(Integer.valueOf(txtMaHoaDon.getText()), i);
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
        Ban b = daoban.selectById(txtMaBan.getText());
        if (b.getGhepBan().equals("")) {
            btnMoveAllLeft.setEnabled(false);
            btnMoveLeft.setEnabled(false);
            btnMoveRight.setEnabled(true);
            btnMoveAllRight.setEnabled(true);
            int rows = tblHoaDonGoc.getSelectedRow();
            int hdct = (int) tblHoaDonGoc.getValueAt(rows, 1);
            this.loadMon(hdct);
        }
    }

    private void clickTblRight() {
        Ban b = daoban.selectById(cboChuyenGhep.getSelectedItem().toString().split("-")[1]);
        if (b.getGhepBan().equals("")) {
            btnMoveAllLeft.setEnabled(true);
            btnMoveLeft.setEnabled(true);
            btnMoveRight.setEnabled(false);
            btnMoveAllRight.setEnabled(false);
            int rows = tblHoaDonDaChuyen.getSelectedRow();
            int hdct = (int) tblHoaDonDaChuyen.getValueAt(rows, 1);
            this.loadMon(hdct);
        }
    }

    private void chuyenDoiBan() {
        if (txtMaHoaDonBanChuyen.getText().equals("Bàn Mới")) {
            new ThongBaoJDialog(null, true).alert(2, "Chưa có hóa đơn trên bàn mới!!!");
        } else {
            this.chuyenAllR();
            Ban b1 = daoban.selectById(txtMaBan.getText());

            HoaDon hd = daohd.selectById(Integer.valueOf(txtMaHoaDonBanChuyen.getText()));
            Ban b3 = daoban.selectById(hd.getMaBan());

            if (!b1.getGhepBan().equals("")) {

                Ban b2 = daoban.selectById(b1.getGhepBan());
                b2.setGhepBan(b3.getMaBan());
                daoban.update(b2);

                b3.setGhepBan(b1.getGhepBan());
                daoban.update(b3);

                b1.setGhepBan("");
                daoban.update(b1);
            }
            daohd.delete(Integer.valueOf(txtMaHoaDon.getText()));
            athis.resetForm();
            this.dispose();
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

    private void addClosing() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                athis.resetForm();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
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
        lblThoat = new javax.swing.JLabel();
        lblHeader = new javax.swing.JLabel();
        pnlButtonBox = new javax.swing.JPanel();
        btnMoveRight = new javax.swing.JButton();
        btnMoveAllRight = new javax.swing.JButton();
        btnMoveLeft = new javax.swing.JButton();
        btnMoveAllLeft = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        txtBanDangChon = new javax.swing.JTextField();
        txtMaBan = new javax.swing.JTextField();
        txtMaHoaDon = new javax.swing.JTextField();
        lblBanDangChon = new javax.swing.JLabel();
        txtMon = new javax.swing.JTextField();
        spnSoLuongChuyen = new javax.swing.JSpinner();
        lblMon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonGoc = new javax.swing.JTable();
        lblSoLuongChuyen = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cboChuyenGhep = new javax.swing.JComboBox<>();
        lblChuyenGhep = new javax.swing.JLabel();
        lblMaHoaDonBanChuyen = new javax.swing.JLabel();
        btnTaoHDMoi = new javax.swing.JButton();
        txtMaHoaDonBanChuyen = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonDaChuyen = new javax.swing.JTable();
        btnChuyenMon = new javax.swing.JButton();
        btnGopBan = new javax.swing.JButton();
        btnChuyenBan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chuyển bàn - ghép bàn");
        setUndecorated(true);
        setResizable(false);

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("CHUYỂN BÀN - GHÉP BÀN");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(620, 0, 340, 50);

        lblThoat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThoat.setForeground(new java.awt.Color(255, 255, 255));
        lblThoat.setText("<< Thoát");
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblThoatMousePressed(evt);
            }
        });
        pnlHeader.add(lblThoat);
        lblThoat.setBounds(10, 10, 210, 30);

        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1920x100.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 1600, 50);

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
                    .addComponent(btnMoveRight, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoveAllRight, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoveLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoveAllLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlButtonBoxLayout.setVerticalGroup(
            pnlButtonBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMoveRight, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveAllRight, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveAllLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaBan.setText("Mã bàn:");

        lblMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHoaDon.setText("Mã hóa đơn:");

        txtBanDangChon.setEditable(false);
        txtBanDangChon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaBan.setEditable(false);
        txtMaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblBanDangChon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBanDangChon.setText("Bàn đang chọn:");

        txtMon.setEditable(false);
        txtMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        spnSoLuongChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        spnSoLuongChuyen.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spnSoLuongChuyen.setValue(1);

        lblMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMon.setText("Món:");

        tblHoaDonGoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        tblHoaDonGoc.setRowHeight(40);
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
        }

        lblSoLuongChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoLuongChuyen.setText("Số lượng chuyển:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBanDangChon)
                            .addComponent(lblMaBan))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHoaDon)
                            .addComponent(txtMaBan)
                            .addComponent(txtBanDangChon)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMaHoaDon)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMon)
                    .addComponent(lblSoLuongChuyen))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMon)
                    .addComponent(spnSoLuongChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBanDangChon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBanDangChon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaBan)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSoLuongChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoLuongChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cboChuyenGhep.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboChuyenGhep.setFocusable(false);
        cboChuyenGhep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenGhepActionPerformed(evt);
            }
        });

        lblChuyenGhep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblChuyenGhep.setText("Chuyển-ghép:");

        lblMaHoaDonBanChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHoaDonBanChuyen.setText("Mã hóa đơn bàn chuyển:");

        btnTaoHDMoi.setBackground(new java.awt.Color(255, 255, 255));
        btnTaoHDMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoHDMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue_x32.png"))); // NOI18N
        btnTaoHDMoi.setText("TẠO HÓA ĐƠN MỚI");
        btnTaoHDMoi.setFocusable(false);
        btnTaoHDMoi.setIconTextGap(10);
        btnTaoHDMoi.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue2_x32.png"))); // NOI18N
        btnTaoHDMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDMoiActionPerformed(evt);
            }
        });

        txtMaHoaDonBanChuyen.setEditable(false);
        txtMaHoaDonBanChuyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tblHoaDonDaChuyen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        tblHoaDonDaChuyen.setRowHeight(40);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaHoaDonBanChuyen)
                            .addComponent(lblChuyenGhep))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboChuyenGhep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMaHoaDonBanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(btnTaoHDMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChuyenGhep, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChuyenGhep, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHoaDonBanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaHoaDonBanChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHDMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChuyenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGopBan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlButtonBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlWallLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWallLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlButtonBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(242, 242, 242))))
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
        this.chonBan();
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

    private void lblThoatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMousePressed
        this.dispose();
    }//GEN-LAST:event_lblThoatMousePressed

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
            java.util.logging.Logger.getLogger(CGBCamUngJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CGBCamUngJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CGBCamUngJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CGBCamUngJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CGBCamUngJDialog dialog = new CGBCamUngJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JLabel lblThoat;
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
