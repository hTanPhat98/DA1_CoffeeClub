/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

/**
 *
 * @author phong
 */
public class ThongKeJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ThongKeJDialog
     */
    public ThongKeJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
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
        tabThongKe = new javax.swing.JTabbedPane();
        pnlHoaDonHomNay = new javax.swing.JPanel();
        pnlHomNay = new javax.swing.JPanel();
        lblTongSoHoaDonHomNay = new javax.swing.JLabel();
        lblTongTienHomNay = new javax.swing.JLabel();
        lblSort = new javax.swing.JLabel();
        txtTongHDHomNay = new javax.swing.JTextField();
        txtTongTienHomNay = new javax.swing.JTextField();
        cboSort = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        pnlChiTiet = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        pnlLichSu = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        pblLichSuHoaDon = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLichSuHoaDon = new javax.swing.JTable();
        pnlLocDoanhThu = new javax.swing.JPanel();
        lblTuNgay = new javax.swing.JLabel();
        lblDenNgay = new javax.swing.JLabel();
        dtcTuNgay = new com.toedter.calendar.JDateChooser();
        dtcDenNgay = new com.toedter.calendar.JDateChooser();
        pnlSapXep = new javax.swing.JPanel();
        lblSoTien = new javax.swing.JLabel();
        lblNhanVien = new javax.swing.JLabel();
        cboSoTien = new javax.swing.JComboBox<>();
        cboNhanVien = new javax.swing.JComboBox<>();
        pnlCTHD = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        pnlBieuDo = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setPreferredSize(new java.awt.Dimension(1280, 50));
        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("THỐNG KÊ");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(20, 4, 1240, 40);

        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1280x50.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 1280, 50);

        tabThongKe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnlHoaDonHomNay.setBackground(new java.awt.Color(255, 255, 255));

        pnlHomNay.setBackground(new java.awt.Color(255, 255, 255));
        pnlHomNay.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÔM NAY", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblTongSoHoaDonHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongSoHoaDonHomNay.setText("Tổng số hóa đơn hôm nay:");

        lblTongTienHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTienHomNay.setText("Tổng tiền tất cả hóa đơn:");

        lblSort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSort.setText("Sort:");

        txtTongHDHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTongHDHomNay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTongTienHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTongTienHomNay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        cboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền ít nhất", "Tiền nhiều nhất" }));

        tblHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày thanh toán", "Tổng tiền thanh toán", "Thu ngân"
            }
        ));
        jScrollPane2.setViewportView(tblHoaDon);

        javax.swing.GroupLayout pnlHomNayLayout = new javax.swing.GroupLayout(pnlHomNay);
        pnlHomNay.setLayout(pnlHomNayLayout);
        pnlHomNayLayout.setHorizontalGroup(
            pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(pnlHomNayLayout.createSequentialGroup()
                        .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHomNayLayout.createSequentialGroup()
                                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongSoHoaDonHomNay)
                                    .addComponent(lblTongTienHomNay))
                                .addGap(18, 18, 18)
                                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTongHDHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(txtTongTienHomNay)))
                            .addGroup(pnlHomNayLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lblSort)
                                .addGap(18, 18, 18)
                                .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHomNayLayout.setVerticalGroup(
            pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSort)
                    .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongHDHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongSoHoaDonHomNay))
                .addGap(20, 20, 20)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTongTienHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTienHomNay))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pnlChiTiet.setBackground(new java.awt.Color(255, 255, 255));
        pnlChiTiet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblChiTietHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HDCT", "Món", "Giá", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout pnlChiTietLayout = new javax.swing.GroupLayout(pnlChiTiet);
        pnlChiTiet.setLayout(pnlChiTietLayout);
        pnlChiTietLayout.setHorizontalGroup(
            pnlChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlChiTietLayout.setVerticalGroup(
            pnlChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlHoaDonHomNayLayout = new javax.swing.GroupLayout(pnlHoaDonHomNay);
        pnlHoaDonHomNay.setLayout(pnlHoaDonHomNayLayout);
        pnlHoaDonHomNayLayout.setHorizontalGroup(
            pnlHoaDonHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlHoaDonHomNayLayout.setVerticalGroup(
            pnlHoaDonHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHoaDonHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHoaDonHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabThongKe.addTab("HÓA ĐƠN HÔM NAY", pnlHoaDonHomNay);

        pnlLichSu.setBackground(new java.awt.Color(255, 255, 255));

        lblThongKe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThongKe.setText("THỐNG KÊ");

        tblThongKe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "TỔNG SỐ HÓA ĐƠN", "TỔNG TIỀN"
            }
        ));
        jScrollPane3.setViewportView(tblThongKe);

        pblLichSuHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        pblLichSuHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LỊCH SỬ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblLichSuHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày thanh toán", "Tổng tiền thanh toán", "Thu ngân"
            }
        ));
        jScrollPane4.setViewportView(tblLichSuHoaDon);

        pnlLocDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        pnlLocDoanhThu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc doanh thu theo ngày", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblTuNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTuNgay.setText("Từ ngày:");

        lblDenNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDenNgay.setText("Đến ngày:");

        javax.swing.GroupLayout pnlLocDoanhThuLayout = new javax.swing.GroupLayout(pnlLocDoanhThu);
        pnlLocDoanhThu.setLayout(pnlLocDoanhThuLayout);
        pnlLocDoanhThuLayout.setHorizontalGroup(
            pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLocDoanhThuLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lblTuNgay)
                .addGap(18, 18, 18)
                .addComponent(dtcTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(lblDenNgay)
                .addGap(18, 18, 18)
                .addComponent(dtcDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        pnlLocDoanhThuLayout.setVerticalGroup(
            pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtcDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtcTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSapXep.setBackground(new java.awt.Color(255, 255, 255));
        pnlSapXep.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sắp xếp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblSoTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoTien.setText("Số tiền:");

        lblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNhanVien.setText("Nhân viên:");

        cboSoTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSoTien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100000", "200000", "300000", "400000", "500000" }));

        cboNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên nv..." }));

        javax.swing.GroupLayout pnlSapXepLayout = new javax.swing.GroupLayout(pnlSapXep);
        pnlSapXep.setLayout(pnlSapXepLayout);
        pnlSapXepLayout.setHorizontalGroup(
            pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSapXepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNhanVien)
                    .addComponent(lblSoTien))
                .addGap(18, 18, 18)
                .addGroup(pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboSoTien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSapXepLayout.setVerticalGroup(
            pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSapXepLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoTien)
                    .addComponent(cboSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(pnlSapXepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNhanVien)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout pblLichSuHoaDonLayout = new javax.swing.GroupLayout(pblLichSuHoaDon);
        pblLichSuHoaDon.setLayout(pblLichSuHoaDonLayout);
        pblLichSuHoaDonLayout.setHorizontalGroup(
            pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblLichSuHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(pnlLocDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSapXep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pblLichSuHoaDonLayout.setVerticalGroup(
            pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblLichSuHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlLocDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCTHD.setBackground(new java.awt.Color(255, 255, 255));
        pnlCTHD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HDCT", "Món", "Giá", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane5.setViewportView(tblCTHD);

        javax.swing.GroupLayout pnlCTHDLayout = new javax.swing.GroupLayout(pnlCTHD);
        pnlCTHD.setLayout(pnlCTHDLayout);
        pnlCTHDLayout.setHorizontalGroup(
            pnlCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCTHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCTHDLayout.setVerticalGroup(
            pnlCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCTHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlLichSuLayout = new javax.swing.GroupLayout(pnlLichSu);
        pnlLichSu.setLayout(pnlLichSuLayout);
        pnlLichSuLayout.setHorizontalGroup(
            pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLichSuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pnlLichSuLayout.createSequentialGroup()
                        .addComponent(lblThongKe)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlLichSuLayout.createSequentialGroup()
                        .addComponent(pblLichSuHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlLichSuLayout.setVerticalGroup(
            pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLichSuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongKe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlLichSuLayout.createSequentialGroup()
                        .addComponent(pblLichSuHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabThongKe.addTab("LỊCH SỬ HÓA ĐƠN", pnlLichSu);

        pnlBieuDo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("ĐÂY LÀ CÁI BIỂU ĐỒ :))");

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartLayout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jLabel9)
                .addContainerGap(790, Short.MAX_VALUE))
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(jLabel9)
                .addContainerGap(400, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBieuDoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBieuDoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabThongKe.addTab("BIỂU ĐỒ THỐNG KÊ DOANH THU", pnlBieuDo);

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlWallLayout.setVerticalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWallLayout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabThongKe))
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
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongKeJDialog dialog = new ThongKeJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JComboBox<String> cboSoTien;
    private javax.swing.JComboBox<String> cboSort;
    private com.toedter.calendar.JDateChooser dtcDenNgay;
    private com.toedter.calendar.JDateChooser dtcTuNgay;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblDenNgay;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSoTien;
    private javax.swing.JLabel lblSort;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTongSoHoaDonHomNay;
    private javax.swing.JLabel lblTongTienHomNay;
    private javax.swing.JLabel lblTuNgay;
    private javax.swing.JPanel pblLichSuHoaDon;
    private javax.swing.JPanel pnlBieuDo;
    private javax.swing.JPanel pnlCTHD;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlChiTiet;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHoaDonHomNay;
    private javax.swing.JPanel pnlHomNay;
    private javax.swing.JPanel pnlLichSu;
    private javax.swing.JPanel pnlLocDoanhThu;
    private javax.swing.JPanel pnlSapXep;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JTabbedPane tabThongKe;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblLichSuHoaDon;
    private javax.swing.JTable tblThongKe;
    private javax.swing.JTextField txtTongHDHomNay;
    private javax.swing.JTextField txtTongTienHomNay;
    // End of variables declaration//GEN-END:variables
}
