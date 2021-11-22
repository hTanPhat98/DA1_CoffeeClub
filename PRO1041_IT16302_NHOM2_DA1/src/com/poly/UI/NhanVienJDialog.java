/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.AccountDAO;
import com.poly.DAO.NhanVienDAO;
import com.poly.Helper.Auth;
import com.poly.Helper.XImage;
import com.poly.Model.Account;
import com.poly.Model.NhanVien;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class NhanVienJDialog extends javax.swing.JDialog {

    /**
     * Creates new form Employee
     */
    public NhanVienJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private int rownv = -1, w, h, rowacc = -1;
    NhanVienDAO daonv = new NhanVienDAO();
    AccountDAO daoac = new AccountDAO();
    JFileChooser fileChooser = new JFileChooser();
    SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");

    private void init() {
        setLocationRelativeTo(null);
        this.fillTableNV();
        this.fillTableAcc();
        this.fillComboBoxMaNV();
        rownv = -1;
        rowacc = -1;
        this.updateStatusNV();
        this.updateStatusAcc();
        w = lblAnhNhanVien.getWidth();
        h = lblAnhNhanVien.getHeight();
    }

    private void insertNV() {
        try {
            NhanVien nv = getFormNV();
            daonv.insert(nv);
            this.fillTableNV();
            this.clearFormNV();
            new ThongBaoJDialog(null, true).alert(1, "Thêm mới thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Thêm mới thất bại!");
        }
    }

    private void updateNV() {
        try {
            NhanVien nv = getFormNV();
            daonv.update(nv);
            this.fillTableNV();
            new ThongBaoJDialog(null, true).alert(1, "Cập nhật thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Cập nhật thất bại!");
        }
    }

    private void deleteNV() {
        if (!Auth.isManager()) {
            new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xóa!");
        } else {
            if (Auth.user.getMaNV().equalsIgnoreCase(txtMaNV.getText())) {
                new ThongBaoJDialog(null, true).alert(2, "Bạn không thể xóa chính bạn!");
            } else if (new ThongBaoJDialog(null, true).confirm("Bạn thực sự muốn xóa nhân viên này?")) {
                String manv = txtMaNV.getText();
                try {
                    daonv.delete(manv);
                    this.fillTableNV();
                    this.clearFormNV();
                    new ThongBaoJDialog(null, true).alert(1, "Xóa thành công!");
                } catch (Exception e) {
                    new ThongBaoJDialog(null, true).alert(2, "Xóa thất bại!");
                }
            }
        }
    }

    private void clearFormNV() {
        NhanVien nv = new NhanVien();
        nv.setHinhNV("add-photo.png");
        nv.setGioiTinh("");
        nv.setNgaySinh(new Date());
        nv.setNgayVaoLam(new Date());
        this.setFormNV(nv);
        this.rownv = -1;
        this.updateStatusNV();
    }

    private void fillTableNV() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        int i=1;
        try {
            String keyWord = txtTimKiemDS.getText();
            List<NhanVien> list = daonv.selectByKeyword(keyWord, keyWord);
            for (NhanVien nv : list) {
                Object[] row = {
                    i,
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getCmnd(),
                    nv.getDiaChi(),
                    nv.getGioiTinh(),
                    nv.getEmail(),
                    nv.getDienThoai(),
                    format.format(nv.getNgaySinh()),
                    format.format(nv.getNgayVaoLam()),
                    nv.getViTri(),
                    nv.getHinhNV()
                };
                model.addRow(row);
                i++;
            }

        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void timKiem() {
        this.fillTableNV();
        this.clearFormNV();
        rownv = -1;
        this.updateStatusNV();
    }

    private void timKiemCT() {
        if (!txtTiemKiemCN.getText().equals("")) {
            try {
                NhanVien nv = daonv.selectFindNV(txtTiemKiemCN.getText());
                this.editNVacc(nv.getMaNV());
                this.setFormNV(nv);
                this.updateStatusNV();
            } catch (Exception e) {
                this.clearFormNV();
            }
        } else {
            this.clearFormNV();
        }
    }

    private void setFormNV(NhanVien nv) {
        if (nv == null) {
            nv = new NhanVien();
            nv.setHinhNV("add-photo.png");
            nv.setGioiTinh("");
            nv.setNgaySinh(new Date());
            nv.setNgayVaoLam(new Date());
        }
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getTenNV());
        txtCMND.setText(nv.getCmnd());
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());
        txtSDT.setText(nv.getDienThoai());
        txtViTri.setText(nv.getViTri());
        switch (nv.getGioiTinh()) {
            case "Nam":
                rdoNam.setSelected(true);
                break;
            case "Nữ":
                rdoNu.setSelected(true);
                break;
            default:
                rdoKhac.setSelected(true);
                break;
        }
        dtcNgaySinh.setDate(nv.getNgaySinh());
        dtcNgayVaoLam.setDate(nv.getNgayVaoLam());
        if (nv.getHinhNV() != null) {
            lblAnhNhanVien.setToolTipText(nv.getHinhNV());
            lblAnhNhanVien.setIcon(XImage.read(nv.getHinhNV(),lblAnhNhanVien.getWidth(),lblAnhNhanVien.getHeight()));
        }
    }

    private NhanVien getFormNV() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        nv.setCmnd(txtCMND.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDienThoai(txtSDT.getText());
        nv.setViTri(txtViTri.getText());
        if (rdoNam.isSelected()) {
            nv.setGioiTinh(rdoNam.getText());
        } else if (rdoNu.isSelected()) {
            nv.setGioiTinh(rdoNu.getText());
        } else {
            nv.setGioiTinh(rdoKhac.getText());
        }
        nv.setNgaySinh(dtcNgaySinh.getDate());
        nv.setNgayVaoLam(dtcNgayVaoLam.getDate());
        nv.setHinhNV(lblAnhNhanVien.getToolTipText());
        return nv;
    }

    private void updateStatusNV() {
        boolean edit = (this.rownv >= 0);
        boolean first = (this.rownv == 0);
        boolean last = (this.rownv == tblNhanVien.getRowCount() - 1);

        txtMaNV.setEditable(!edit);
        btnSaveNV.setEnabled(!edit);
        btnUpdateNV.setEnabled(edit);
        btnDeleteNV.setEnabled(edit);
        btnTaoTK.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnFinal.setEnabled(edit && !last);
    }

    private void chonAnh() {
        fileChooser.setDialogTitle("Chọn hình nhân viên");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblAnhNhanVien.setIcon(icon);
            lblAnhNhanVien.setToolTipText(file.getName());
        }
    }

    private void editNV() {
        try {
            String manv = (String) tblNhanVien.getValueAt(this.rownv, 1);
            NhanVien nv = daonv.selectById(manv);
            if (nv != null) {
                this.setFormNV(nv);
                tabs.setSelectedIndex(0);
                this.updateStatusNV();
            }
            String tenAnh = nv.getHinhNV().equals("") ? "Not Image" : nv.getHinhNV();
            lblTenAnh.setText("Image Name: " + tenAnh);
            List<Account> list = daoac.selectAll();
            for (Account acc : list) {
                if (nv.getMaNV().equals(acc.getMaNV())) {
                    btnTaoTK.setEnabled(false);
                    break;
                } else {
                    btnTaoTK.setEnabled(true);
                }
            }

        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void firstNV() {
        this.rownv = 0;
        this.editNV();
    }

    private void prevNV() {
        if (this.rownv > 0) {
            this.rownv--;
            this.editNV();
        }
    }

    private void nextNV() {
        if (this.rownv < tblNhanVien.getRowCount() - 1) {
            this.rownv++;
            this.editNV();
        }
    }

    private void lastNV() {
        this.rownv = tblNhanVien.getRowCount() - 1;
        this.editNV();
    }

    private void insertAcc() {
        try {
            Account acc = getFormAcc();
            List<NhanVien> list = daonv.selectAll();
            boolean kt = false;
            for (NhanVien nv : list) {
                if (acc.getMaNV().equals(nv.getMaNV())) {
                    kt = true;
                    break;
                } else {
                    kt = false;
                }
            }
            if (kt) {
                daoac.insert(acc);
                this.fillTableAcc();
                this.clearFormNV();
                new ThongBaoJDialog(null, true).alert(1, "Thêm mới thành công!");
            } else {
                new ThongBaoJDialog(null, true).alert(2, "Mã nhân viên chưa có KHÔNG thể tạo tài khoản!!!");
            }
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Thêm mới thất bại");
        }
    }

    private void updateAcc() {
        try {
            Account acc = getFormAcc();
            daoac.update(acc);
            this.fillTableAcc();
            new ThongBaoJDialog(null, true).alert(1, "Cập nhật thành công!");
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Cập nhật thất bại");
        }
    }

    private void fillComboBoxMaNV() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaNV.getModel();
        model.removeAllElements();
        List<NhanVien> list = daonv.selectAll();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
    }

    private void deleteAcc() {
        NhanVien nvc = (NhanVien) cboMaNV.getSelectedItem();
        if (!Auth.isManager()) {
            new ThongBaoJDialog(null, true).alert(2, "Bạn không có quyền xóa!");
        } else {
            if (Auth.user.getMaNV().equalsIgnoreCase(nvc.getMaNV())) {
                new ThongBaoJDialog(null, true).alert(2, "Bạn không thể xóa chính bạn!");
            } else if (new ThongBaoJDialog(null, true).confirm("Bạn thực sự muốn xóa nhân viên này?")) {
                String manv = nvc.getMaNV();
                try {
                    daonv.delete(manv);
                    this.fillTableAcc();
                    this.clearFormAcc();
                    new ThongBaoJDialog(null, true).alert(1, "Xóa thành công!");
                } catch (Exception e) {
                    new ThongBaoJDialog(null, true).alert(2, "Xóa thất bại!");
                }
            }
        }
    }

    private void fillTableAcc() {
        DefaultTableModel model = (DefaultTableModel) tblAccount.getModel();
        model.setRowCount(0);
        int i=1;
        try {
            List<Account> list = daoac.selectAll();
            for (Account acc : list) {
                Object[] row = {
                    i,
                    acc.getMaNV(),
                    acc.getUserName(),
                    "********",
                    acc.isVaiTro() ? "Quản Lý" : "Nhân Viên"
                };
                model.addRow(row);
                i++;
            }

        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void setFormAcc(Account acc) {
        cboMaNV.setSelectedItem(acc.getMaNV());
        txtUsername.setText(acc.getUserName());
        txtPassword.setText(acc.getPassworld());
        rdoQuanLy.setSelected(acc.isVaiTro());
        rdoNhanVien.setSelected(!acc.isVaiTro());
    }

    private Account getFormAcc() {
        Account acc = new Account();
        acc.setMaNV(cboMaNV.getSelectedItem().toString());
        acc.setUserName(txtUsername.getText());
        acc.setPassworld(String.valueOf(txtPassword.getPassword()));
        acc.setVaiTro(rdoQuanLy.isSelected());
        return acc;
    }

    private void clearFormAcc() {
        this.setFormAcc(new Account());
        this.rowacc = -1;
        this.updateStatusAcc();
    }

    private void editAcc() {
        try {
            String username = (String) tblAccount.getValueAt(this.rowacc, 2);
            Account acc = daoac.selectById(username);
            if (acc != null) {
                cboMaNV.setEditable(true);
                this.setFormAcc(acc);
                this.updateStatusAcc();
            }
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void updateStatusAcc() {
        boolean edit = (this.rowacc >= 0);

//        cboMaNV.setEditable(!edit);
        btnSaveAcc.setEnabled(!edit);
        btnUpdateAcc.setEnabled(edit);
        btnDeleteAcc.setEnabled(edit);

    }

    private void editNVacc(String manv) {
        try {
            NhanVien nv = daonv.selectById(manv);
            if (nv != null) {
                this.setFormNV(nv);
                tabs.setSelectedIndex(0);
                this.updateStatusNV();
            }
            String tenAnh = nv.getHinhNV().equals("") ? "Not Image" : nv.getHinhNV();
            lblTenAnh.setText("Image Name: " + tenAnh);
            List<Account> list = daoac.selectAll();
            for (Account acc : list) {
                if (nv.getMaNV().equals(acc.getMaNV())) {
                    btnTaoTK.setEnabled(false);
                    break;
                } else {
                    btnTaoTK.setEnabled(true);
                }
            }
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void taoTaiKhoan() {
        boolean kt = false;
        List<NhanVien> list = daonv.selectAll();
        for (NhanVien nv : list) {
            if (txtMaNV.getText().equals(nv.getMaNV())) {
                this.clearFormAcc();
                cboMaNV.setSelectedItem(nv.getMaNV());
                tabs.setSelectedIndex(2);
                kt = true;
                break;
            }
        }
        if (kt) {
            new ThongBaoJDialog(null, true).alert(1, "Tạo tài khoản thành công!");
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Tạo tài khoản thất bại!");
        }
        this.updateStatusAcc();
    }

    private void xemTTCT() {
        String nvc = (String) cboMaNV.getSelectedItem();
        this.editNVacc(nvc);
    }

    private void clickTableNV(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.rownv = tblNhanVien.getSelectedRow();
            this.editNV();
            tabs.setSelectedIndex(0);
        }
    }

    private void clickTableAcc(MouseEvent evt) {
            this.rowacc = tblAccount.getSelectedRow();
            this.editAcc();
    }

    private void sort() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        int i=1;
        try {
            List<NhanVien> list = daonv.selectAll();
            switch (cboSort.getSelectedIndex()) {
                case 1:
                    Collections.sort(list, (NhanVien o1, NhanVien o2) -> o1.getTenNV().compareTo(o2.getTenNV()));
                    break;
                case 2:
                    Collections.sort(list, (NhanVien o1, NhanVien o2) -> o1.getNgaySinh().compareTo(o2.getNgaySinh()));
                    break;
                case 3:
                    Collections.sort(list, (NhanVien o1, NhanVien o2) -> o1.getNgayVaoLam().compareTo(o2.getNgayVaoLam()));
                    break;
                default:
                    Collections.sort(list, (NhanVien o1, NhanVien o2) -> o1.getMaNV().compareTo(o2.getMaNV()));
                    break;
            }
            for (NhanVien nv : list) {
                Object[] row = {
                    i,
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getCmnd(),
                    nv.getDiaChi(),
                    nv.getGioiTinh(),
                    nv.getEmail(),
                    nv.getDienThoai(),
                    nv.getNgaySinh(),
                    nv.getNgayVaoLam(),
                    nv.getViTri(),
                    nv.getHinhNV()
                };
                model.addRow(row);
                i++;
            }
        } catch (Exception e) {
            new ThongBaoJDialog(null, true).alert(2, "Thông báo lỗi truy vấn");
        }
    }

    private void chonMaNV() {
        List<Account> list = daoac.selectAll();
        boolean kt = false;
        for (Account acc : list) {
            if (cboMaNV.getSelectedItem() == null) {
            } else if (cboMaNV.getSelectedItem().toString().equals(acc.getMaNV())) {
                kt = true;
                break;
            } else {
                kt = false;
            }
        }
        if (kt) {
            btnSaveAcc.setEnabled(!kt);
            btnUpdateAcc.setEnabled(kt);
            btnDeleteAcc.setEnabled(kt);
        } else {
            btnSaveAcc.setEnabled(!kt);
            btnUpdateAcc.setEnabled(kt);
            btnDeleteAcc.setEnabled(kt);
        }
    }
    
    
    private void taoQRcode(){
        NhanVien nv=daonv.selectById(String.valueOf(cboMaNV.getSelectedItem()));
        Account acc=daoac.selectByManv(String.valueOf(cboMaNV.getSelectedItem()));
        new MaQRcodeJDialog(null, true, acc.getUserName(),acc.getPassworld(),nv.getEmail()).setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgGioiTinh = new javax.swing.ButtonGroup();
        btgVaiTro = new javax.swing.ButtonGroup();
        jPanel_Wall = new javax.swing.JPanel();
        HeaderJPanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblHeader = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        CapNhatJPanel = new javax.swing.JPanel();
        lblTimKiemCN = new javax.swing.JLabel();
        lblAnhNhanVien = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblCMND = new javax.swing.JLabel();
        lblViTri = new javax.swing.JLabel();
        lblNgayVaoLam = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtTiemKiemCN = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtCMND = new javax.swing.JTextField();
        txtViTri = new javax.swing.JTextField();
        dtcNgaySinh = new com.toedter.calendar.JDateChooser();
        dtcNgayVaoLam = new com.toedter.calendar.JDateChooser();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoKhac = new javax.swing.JRadioButton();
        DiaChiJScrollPane = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        InfoImageJPanel = new javax.swing.JPanel();
        lblTenAnh = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnNewNV = new javax.swing.JButton();
        btnSaveNV = new javax.swing.JButton();
        btnUpdateNV = new javax.swing.JButton();
        btnDeleteNV = new javax.swing.JButton();
        btnTaoTK = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnFinal = new javax.swing.JButton();
        DanhSachjPanel = new javax.swing.JPanel();
        lblTimKiemDS = new javax.swing.JLabel();
        lblSort = new javax.swing.JLabel();
        txtTimKiemDS = new javax.swing.JTextField();
        TableJScrollPane = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        cboSort = new javax.swing.JComboBox<>();
        TaiKhoanjPanel = new javax.swing.JPanel();
        lblMaNV_TK = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        btnNewAcc = new javax.swing.JButton();
        btnSaveAcc = new javax.swing.JButton();
        btnUpdateAcc = new javax.swing.JButton();
        btnDeleteAcc = new javax.swing.JButton();
        btnChiTietThongTin = new javax.swing.JButton();
        lblVaiTro = new javax.swing.JLabel();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        cboMaNV = new javax.swing.JComboBox();
        btnTaoQRCode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee");
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jPanel_Wall.setBackground(new java.awt.Color(255, 255, 255));

        HeaderJPanel.setBackground(new java.awt.Color(204, 204, 204));
        HeaderJPanel.setPreferredSize(new java.awt.Dimension(1280, 60));
        HeaderJPanel.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("QUẢN LÝ NHÂN VIÊN");
        HeaderJPanel.add(lblTitle);
        lblTitle.setBounds(10, 4, 1260, 40);

        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1280x50.png"))); // NOI18N
        HeaderJPanel.add(lblHeader);
        lblHeader.setBounds(0, 0, 1280, 50);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        CapNhatJPanel.setBackground(new java.awt.Color(255, 255, 255));
        CapNhatJPanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CapNhatJPanel.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTimKiemCN.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblTimKiemCN.setText("Tìm kiếm");

        lblAnhNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/add-photo.png"))); // NOI18N
        lblAnhNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        lblAnhNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNV.setText("Mã nhân viên:");
        lblMaNV.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNV.setText("Tên nhân viên:");
        lblTenNV.setPreferredSize(new java.awt.Dimension(100, 30));

        lblNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgaySinh.setText("Ngày sinh:");
        lblNgaySinh.setPreferredSize(new java.awt.Dimension(100, 30));

        lblGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGioiTinh.setText("Giới tính:");
        lblGioiTinh.setPreferredSize(new java.awt.Dimension(100, 30));

        lblSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSDT.setText("Số điện thoại:");
        lblSDT.setPreferredSize(new java.awt.Dimension(100, 30));

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setText("Email:");
        lblEmail.setPreferredSize(new java.awt.Dimension(100, 30));

        lblCMND.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCMND.setText("CMND/CCCD:");
        lblCMND.setPreferredSize(new java.awt.Dimension(100, 30));

        lblViTri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblViTri.setText("Vị trí:");
        lblViTri.setPreferredSize(new java.awt.Dimension(100, 30));

        lblNgayVaoLam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgayVaoLam.setText("Ngày vào làm:");
        lblNgayVaoLam.setPreferredSize(new java.awt.Dimension(100, 30));

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiaChi.setText("Địa chỉ:");

        txtTiemKiemCN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTiemKiemCN.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));
        txtTiemKiemCN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTiemKiemCNKeyReleased(evt);
            }
        });

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtTenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenNV.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtCMND.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCMND.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtViTri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViTri.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        dtcNgaySinh.setDateFormatString("dd/MM/yyyy");
        dtcNgaySinh.setFocusable(false);

        dtcNgayVaoLam.setDateFormatString("dd/MM/yyyy");
        dtcNgayVaoLam.setFocusable(false);

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        btgGioiTinh.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        btgGioiTinh.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        rdoKhac.setBackground(new java.awt.Color(255, 255, 255));
        btgGioiTinh.add(rdoKhac);
        rdoKhac.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKhac.setText("Khác");

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDiaChi.setRows(5);
        txtDiaChi.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        DiaChiJScrollPane.setViewportView(txtDiaChi);

        InfoImageJPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblTenAnh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenAnh.setText("Image name:");

        javax.swing.GroupLayout InfoImageJPanelLayout = new javax.swing.GroupLayout(InfoImageJPanel);
        InfoImageJPanel.setLayout(InfoImageJPanelLayout);
        InfoImageJPanelLayout.setHorizontalGroup(
            InfoImageJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoImageJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenAnh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InfoImageJPanelLayout.setVerticalGroup(
            InfoImageJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoImageJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenAnh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnNewNV.setBackground(new java.awt.Color(255, 255, 255));
        btnNewNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNewNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue_x32.png"))); // NOI18N
        btnNewNV.setText("Tạo mới");
        btnNewNV.setFocusable(false);
        btnNewNV.setIconTextGap(6);
        btnNewNV.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue2_x32.png"))); // NOI18N
        btnNewNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewNVActionPerformed(evt);
            }
        });

        btnSaveNV.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSaveNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_save_blue_x32.png"))); // NOI18N
        btnSaveNV.setText("Lưu  thông tin");
        btnSaveNV.setFocusable(false);
        btnSaveNV.setIconTextGap(6);
        btnSaveNV.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_save_blue2_x32.png"))); // NOI18N
        btnSaveNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveNVActionPerformed(evt);
            }
        });

        btnUpdateNV.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdateNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_update_blue_x32.png"))); // NOI18N
        btnUpdateNV.setText("Cập nhật");
        btnUpdateNV.setFocusable(false);
        btnUpdateNV.setIconTextGap(6);
        btnUpdateNV.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_update_blue2_x32.png"))); // NOI18N
        btnUpdateNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNVActionPerformed(evt);
            }
        });

        btnDeleteNV.setBackground(new java.awt.Color(255, 255, 255));
        btnDeleteNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_remove_blue_x32.png"))); // NOI18N
        btnDeleteNV.setText("Xóa thông tin");
        btnDeleteNV.setFocusable(false);
        btnDeleteNV.setIconTextGap(6);
        btnDeleteNV.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_remove_blue2_x32.png"))); // NOI18N
        btnDeleteNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNVActionPerformed(evt);
            }
        });

        btnTaoTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue_x32.png"))); // NOI18N
        btnTaoTK.setText("Tạo tài khoản");
        btnTaoTK.setFocusable(false);
        btnTaoTK.setIconTextGap(6);
        btnTaoTK.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue2_x32.png"))); // NOI18N
        btnTaoTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTKActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(255, 255, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_first_x32.png"))); // NOI18N
        btnFirst.setBorder(null);
        btnFirst.setContentAreaFilled(false);
        btnFirst.setFocusable(false);
        btnFirst.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_first_x32blue.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(255, 255, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_prev_x32.png"))); // NOI18N
        btnPrev.setBorder(null);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setFocusable(false);
        btnPrev.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_prev_x32blue.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_next_x32.png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.setContentAreaFilled(false);
        btnNext.setFocusable(false);
        btnNext.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_next_x32blue.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnFinal.setBackground(new java.awt.Color(255, 255, 255));
        btnFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_last_x32.png"))); // NOI18N
        btnFinal.setBorder(null);
        btnFinal.setContentAreaFilled(false);
        btnFinal.setFocusable(false);
        btnFinal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/control_last_x32blue.png"))); // NOI18N
        btnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnUpdateNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnDeleteNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNewNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnSaveNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTaoTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSaveNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdateNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTaoTK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CapNhatJPanelLayout = new javax.swing.GroupLayout(CapNhatJPanel);
        CapNhatJPanel.setLayout(CapNhatJPanelLayout);
        CapNhatJPanelLayout.setHorizontalGroup(
            CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAnhNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InfoImageJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CapNhatJPanelLayout.createSequentialGroup()
                                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                            .addComponent(txtMaNV)
                                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                                .addComponent(rdoNam)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoNu)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoKhac))
                                            .addComponent(txtSDT)
                                            .addComponent(dtcNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(60, 60, 60)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblViTri, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblDiaChi)
                                    .addComponent(lblNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(DiaChiJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(txtViTri, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(txtCMND, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(dtcNgayVaoLam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addComponent(lblTimKiemCN, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTiemKiemCN)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        CapNhatJPanelLayout.setVerticalGroup(
            CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimKiemCN)
                    .addComponent(txtTiemKiemCN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(dtcNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dtcNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rdoKhac)
                                            .addComponent(rdoNu)
                                            .addComponent(rdoNam)
                                            .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(DiaChiJScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addComponent(lblAnhNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InfoImageJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        tabs.addTab("CẬP NHẬT", CapNhatJPanel);

        DanhSachjPanel.setBackground(new java.awt.Color(255, 255, 255));
        DanhSachjPanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblTimKiemDS.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblTimKiemDS.setText("Tìm kiếm");

        lblSort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSort.setText("Sort:");

        txtTimKiemDS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimKiemDS.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));
        txtTimKiemDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemDSKeyReleased(evt);
            }
        });

        tblNhanVien.setAutoCreateRowSorter(true);
        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã NV", "Tên NV", "CMND", "Địa Chỉ", "Giới Tính", "Email", "Điện Thoại", "Ngày Sinh", "Ngày Vào Làm", "Vị Trí", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(20);
        tblNhanVien.getTableHeader().setResizingAllowed(false);
        tblNhanVien.getTableHeader().setReorderingAllowed(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        TableJScrollPane.setViewportView(tblNhanVien);

        cboSort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo mã nhân viên", "Theo họ tên", "Theo ngày sinh", "Ngày vào làm" }));
        cboSort.setFocusable(false);
        cboSort.setPreferredSize(new java.awt.Dimension(122, 30));
        cboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DanhSachjPanelLayout = new javax.swing.GroupLayout(DanhSachjPanel);
        DanhSachjPanel.setLayout(DanhSachjPanelLayout);
        DanhSachjPanelLayout.setHorizontalGroup(
            DanhSachjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DanhSachjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TableJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1255, Short.MAX_VALUE)
                    .addGroup(DanhSachjPanelLayout.createSequentialGroup()
                        .addComponent(lblTimKiemDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiemDS))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DanhSachjPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );
        DanhSachjPanelLayout.setVerticalGroup(
            DanhSachjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachjPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemDS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimKiemDS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(TableJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DanhSachjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSort)
                    .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        tabs.addTab("DANH SÁCH", DanhSachjPanel);

        TaiKhoanjPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblMaNV_TK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNV_TK.setText("Mã nhân viên:");

        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsername.setText("Username:");

        lblPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPassword.setText("Password:");

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1)));

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassword.setText("jPasswordField1");

        tblAccount.setAutoCreateRowSorter(true);
        tblAccount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã nhân viên", "Username", "Password", "Vai  trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAccount.setRowHeight(22);
        tblAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAccountMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblAccount);

        btnNewAcc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNewAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_new_blue_x32.png"))); // NOI18N
        btnNewAcc.setText("Tạo mới");
        btnNewAcc.setFocusable(false);
        btnNewAcc.setIconTextGap(6);
        btnNewAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewAccActionPerformed(evt);
            }
        });

        btnSaveAcc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSaveAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_save_blue_x32.png"))); // NOI18N
        btnSaveAcc.setText("Lưu thông tin");
        btnSaveAcc.setFocusable(false);
        btnSaveAcc.setIconTextGap(6);
        btnSaveAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAccActionPerformed(evt);
            }
        });

        btnUpdateAcc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_update_blue_x32.png"))); // NOI18N
        btnUpdateAcc.setText("Cập nhật");
        btnUpdateAcc.setFocusable(false);
        btnUpdateAcc.setIconTextGap(6);
        btnUpdateAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAccActionPerformed(evt);
            }
        });

        btnDeleteAcc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_remove_blue_x32.png"))); // NOI18N
        btnDeleteAcc.setText("Xóa tài khoản");
        btnDeleteAcc.setFocusable(false);
        btnDeleteAcc.setIconTextGap(6);
        btnDeleteAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAccActionPerformed(evt);
            }
        });

        btnChiTietThongTin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChiTietThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/UI_info_yellow_x32.png"))); // NOI18N
        btnChiTietThongTin.setText("Chi tiết thông tin");
        btnChiTietThongTin.setFocusable(false);
        btnChiTietThongTin.setIconTextGap(6);
        btnChiTietThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietThongTinActionPerformed(evt);
            }
        });

        lblVaiTro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblVaiTro.setText("Vai trò:");
        lblVaiTro.setPreferredSize(new java.awt.Dimension(100, 30));

        rdoQuanLy.setBackground(new java.awt.Color(255, 255, 255));
        btgVaiTro.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoQuanLy.setText("Quản lý");
        rdoQuanLy.setContentAreaFilled(false);
        rdoQuanLy.setPreferredSize(new java.awt.Dimension(93, 30));

        rdoNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        btgVaiTro.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.setContentAreaFilled(false);
        rdoNhanVien.setPreferredSize(new java.awt.Dimension(93, 30));

        cboMaNV.setEditable(true);
        cboMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboMaNV.setToolTipText("");
        cboMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNVActionPerformed(evt);
            }
        });
        cboMaNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboMaNVKeyReleased(evt);
            }
        });

        btnTaoQRCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoQRCode.setText("Tạo QR Code");
        btnTaoQRCode.setFocusable(false);
        btnTaoQRCode.setIconTextGap(6);
        btnTaoQRCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoQRCodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TaiKhoanjPanelLayout = new javax.swing.GroupLayout(TaiKhoanjPanel);
        TaiKhoanjPanel.setLayout(TaiKhoanjPanelLayout);
        TaiKhoanjPanelLayout.setHorizontalGroup(
            TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                        .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaNV_TK)
                            .addComponent(lblPassword)
                            .addComponent(lblUsername))
                        .addGap(24, 24, 24)
                        .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(cboMaNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                        .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdateAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNewAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChiTietThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnDeleteAcc, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addComponent(btnSaveAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnTaoQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        TaiKhoanjPanelLayout.setVerticalGroup(
            TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNV_TK)
                    .addComponent(cboMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TaiKhoanjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChiTietThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(TaiKhoanjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );

        TaiKhoanjPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboMaNV, txtUsername});

        tabs.addTab("TÀI KHOẢN", TaiKhoanjPanel);

        javax.swing.GroupLayout jPanel_WallLayout = new javax.swing.GroupLayout(jPanel_Wall);
        jPanel_Wall.setLayout(jPanel_WallLayout);
        jPanel_WallLayout.setHorizontalGroup(
            jPanel_WallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_WallLayout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_WallLayout.setVerticalGroup(
            jPanel_WallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_WallLayout.createSequentialGroup()
                .addComponent(HeaderJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Wall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Wall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewNVActionPerformed
        this.clearFormNV();
    }//GEN-LAST:event_btnNewNVActionPerformed

    private void btnSaveNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveNVActionPerformed
        this.insertNV();
    }//GEN-LAST:event_btnSaveNVActionPerformed

    private void btnUpdateNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateNVActionPerformed
        this.updateNV();
    }//GEN-LAST:event_btnUpdateNVActionPerformed

    private void btnDeleteNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNVActionPerformed
        this.deleteNV();
    }//GEN-LAST:event_btnDeleteNVActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.firstNV();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.prevNV();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.nextNV();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalActionPerformed
        this.lastNV();
    }//GEN-LAST:event_btnFinalActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        this.clickTableNV(evt);
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnNewAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAccActionPerformed
        this.clearFormAcc();
    }//GEN-LAST:event_btnNewAccActionPerformed

    private void btnSaveAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAccActionPerformed
        this.insertAcc();
    }//GEN-LAST:event_btnSaveAccActionPerformed

    private void btnUpdateAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAccActionPerformed
        this.updateAcc();
    }//GEN-LAST:event_btnUpdateAccActionPerformed

    private void btnDeleteAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAccActionPerformed
        this.deleteAcc();
    }//GEN-LAST:event_btnDeleteAccActionPerformed

    private void btnChiTietThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietThongTinActionPerformed
        this.xemTTCT();
    }//GEN-LAST:event_btnChiTietThongTinActionPerformed

    private void btnTaoTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTKActionPerformed
        this.taoTaiKhoan();
    }//GEN-LAST:event_btnTaoTKActionPerformed

    private void txtTimKiemDSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemDSKeyReleased
        this.timKiem();
    }//GEN-LAST:event_txtTimKiemDSKeyReleased

    private void txtTiemKiemCNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiemKiemCNKeyReleased
        this.timKiemCT();
    }//GEN-LAST:event_txtTiemKiemCNKeyReleased

    private void cboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSortActionPerformed
        this.sort();
    }//GEN-LAST:event_cboSortActionPerformed

    private void cboMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNVActionPerformed
        this.chonMaNV();
    }//GEN-LAST:event_cboMaNVActionPerformed

    private void cboMaNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboMaNVKeyReleased
        this.chonMaNV();
    }//GEN-LAST:event_cboMaNVKeyReleased

    private void tblAccountMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAccountMousePressed
        this.clickTableAcc(evt);
    }//GEN-LAST:event_tblAccountMousePressed

    private void btnTaoQRCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoQRCodeActionPerformed
        this.taoQRcode();
    }//GEN-LAST:event_btnTaoQRCodeActionPerformed

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
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                NhanVienJDialog dialog = new NhanVienJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel CapNhatJPanel;
    private javax.swing.JPanel DanhSachjPanel;
    private javax.swing.JScrollPane DiaChiJScrollPane;
    private javax.swing.JPanel HeaderJPanel;
    private javax.swing.JPanel InfoImageJPanel;
    private javax.swing.JScrollPane TableJScrollPane;
    private javax.swing.JPanel TaiKhoanjPanel;
    private javax.swing.ButtonGroup btgGioiTinh;
    private javax.swing.ButtonGroup btgVaiTro;
    private javax.swing.JButton btnChiTietThongTin;
    private javax.swing.JButton btnDeleteAcc;
    private javax.swing.JButton btnDeleteNV;
    private javax.swing.JButton btnFinal;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnNewAcc;
    private javax.swing.JButton btnNewNV;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSaveAcc;
    private javax.swing.JButton btnSaveNV;
    private javax.swing.JButton btnTaoQRCode;
    private javax.swing.JButton btnTaoTK;
    private javax.swing.JButton btnUpdateAcc;
    private javax.swing.JButton btnUpdateNV;
    private javax.swing.JComboBox cboMaNV;
    private javax.swing.JComboBox<String> cboSort;
    private com.toedter.calendar.JDateChooser dtcNgaySinh;
    private com.toedter.calendar.JDateChooser dtcNgayVaoLam;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Wall;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnhNhanVien;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaNV_TK;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSort;
    private javax.swing.JLabel lblTenAnh;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTimKiemCN;
    private javax.swing.JLabel lblTimKiemDS;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblViTri;
    private javax.swing.JRadioButton rdoKhac;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblAccount;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTiemKiemCN;
    private javax.swing.JTextField txtTimKiemDS;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables

}
