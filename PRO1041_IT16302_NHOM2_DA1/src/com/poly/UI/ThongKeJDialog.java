/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.HoaDonCTDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.MenuDAO;
import com.poly.DAO.NhanVienDAO;
import com.poly.DAO.ThongKeDAO;
import com.poly.Model.HDTHONGKE;
import com.poly.Model.HoaDon;
import com.poly.Model.HoaDonCT;
import com.poly.Model.Menu;
import com.poly.Model.NhanVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author phong
 */
public class ThongKeJDialog extends javax.swing.JDialog {

    public ThongKeJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.init();
    }

    //BIEN
    float tthd = 0;
    float ttmon;
    float tongtienHN = 0;

    private Locale localeVN = new Locale("vi", "VN");
    private NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    MenuDAO daoMenu = new MenuDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    ThongKeDAO tkdao = new ThongKeDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    HoaDonCTDAO hdctdao = new HoaDonCTDAO();

    List<HoaDon> list = new ArrayList<HoaDon>();

    private void init() {
        this.setLocationRelativeTo(null);
        this.LoadHDhomnay();
        this.LoadLichSuHD();
        this.showTenNV();
        this.showLineChart();
    }

    //TABLE
    private void LoadHDhomnay() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {

            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();

            List<HoaDon> list = tkdao.selectAll(day, month, year);
            for (HoaDon hd : list) {
                NhanVien listnv = nvdao.selectById(hd.getMaNV());
                Object[] row = {
                    hd.getMaHD(),
                    dateformat.format(hd.getNgayHD()),
                    currencyVN.format(hd.getTongTien()),
                    listnv.getTenNV()
                };
                model.addRow(row);
                tongtienHN = tongtienHN + hd.getTongTien();
            }

            int tonghdHnay = list.size();
            txtTongHDHomNay.setText(String.valueOf(tonghdHnay));
            txtTongTienHomNay.setText(String.valueOf(tongtienHN));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void Sort() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            List<HDTHONGKE> listHD = tkdao.selectHDNV(day, month, year
            );
            switch (cboSort.getSelectedIndex()) {
                case 1:
                    Collections.sort(listHD, (HDTHONGKE o1, HDTHONGKE o2) -> o1.getTenNV().compareTo(o2.getTenNV()));
                    break;
                case 2:
                    Collections.sort(listHD, (HDTHONGKE o1, HDTHONGKE o2) -> o1.getTongTien() < o2.getTongTien() ? 1 : -1);
                    break;
                default:
                    Collections.sort(listHD, (HDTHONGKE o1, HDTHONGKE o2) -> o1.getMaHD() > o2.getMaHD() ? 1 : -1);
                    break;
            }
            for (HDTHONGKE hd : listHD) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getNgayHD(),
                    currencyVN.format(hd.getTongTien()),
                    hd.getTenNV()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    private void showHDCT() {
        Integer mahd = (Integer) tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0);
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);

        List<HoaDonCT> listHDCT = (List<HoaDonCT>) hdctdao.selectByHDCT(mahd);

        for (HoaDonCT hdct : listHDCT) {
            Menu mon = daoMenu.selectById(hdct.getMaMon());
            ttmon = hdct.getDonGia() * hdct.getSoLuong();
            Object[] row = {
                hdct.getMaHDCT(),
                mon.getTenMon(),
                currencyVN.format(hdct.getDonGia()),
                hdct.getSoLuong(),
                currencyVN.format(ttmon)
            };
            model.addRow(row);
        }
    }

    private void LoadLichSuHD() {
        DefaultTableModel model = (DefaultTableModel) tblLichSuHoaDon.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblThongKe.getModel();
        float tongTien = 0;
        int tongHD = 0;
        model.setRowCount(0);
        model2.setRowCount(0);
        try {
            List<HoaDon> list = tkdao.selectLichSuHD();
            for (HoaDon hd : list) {
                NhanVien listnv = nvdao.selectById(hd.getMaNV());
                Object[] row = {
                    hd.getMaHD(),
                    dateformat.format(hd.getNgayHD()),
                    currencyVN.format(hd.getTongTien()),
                    listnv.getTenNV()
                };
                model.addRow(row);
                tongHD = hd.getMaHD();
                tongTien += hd.getTongTien();
            }

            Object[] row = {
                tongHD, currencyVN.format(tongTien)
            };
            model2.addRow(row);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showTenNV() {

        List<HDTHONGKE> list = tkdao.selectByTenNV();
        for (HDTHONGKE nv : list) {
            cboNhanVien.addItem(nv.getTenNV());
        }
    }

    private void showHDTenNV() {
        String TenNV = (String) cboNhanVien.getSelectedItem();
        if (TenNV.equals("Tất cả")) {
            this.LoadLichSuHD();

        } else {
            DefaultTableModel model = (DefaultTableModel) tblLichSuHoaDon.getModel();
            model.setRowCount(0);
            try {
                List<HDTHONGKE> listTK = tkdao.selectListByTenNV(TenNV);
                for (HDTHONGKE hd : listTK) {

                    Object[] row = {
                        hd.getMaHD(),
                        dateformat.format(hd.getNgayHD()),
                        currencyVN.format(hd.getTongTien()),
                        hd.getTenNV()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
            }
        }

    }

    private void showLichSuHDCT() {
        Integer MaHD = (Integer) tblLichSuHoaDon.getValueAt(tblLichSuHoaDon.getSelectedRow(), 0);
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();
        model.setRowCount(0);

        try {
            List<HoaDonCT> listCTHD = hdctdao.selectByHDCT(MaHD);

            for (HoaDonCT list : listCTHD) {
                Menu mon = daoMenu.selectById(list.getMaMon());
                ttmon = list.getSoLuong() * list.getDonGia();
                Object[] row = {
                    list.getMaHDCT(),
                    mon.getTenMon(),
                    currencyVN.format(list.getDonGia()),
                    list.getSoLuong(),
                    currencyVN.format(ttmon)
                };
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    private void findNgayThangNamHD() {
        Date dt1 = dtcTuNgay.getDate();
        Date dt2 = dtcDenNgay.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DefaultTableModel model = (DefaultTableModel) tblLichSuHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> listhd = tkdao.findNgayThangNam(formatter.format(dt1), formatter.format(dt2));
            for (HoaDon list : listhd) {
                NhanVien nv = nvdao.selectById(list.getMaNV());
                Object[] row = {
                    list.getMaHD(),
                    dateformat.format(list.getNgayHD()),
                    currencyVN.format(list.getTongTien()),
                    nv.getTenNV()
                };

                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

    private void ShowTheoKhoangTien() {
        DefaultTableModel model = (DefaultTableModel) tblLichSuHoaDon.getModel();
        String money = "" + cboSoTien.getSelectedItem();
        model.setRowCount(0);
        if (money.equals("Tất cả")) {
            this.LoadLichSuHD();
        } else {
            try {
                List<HoaDon> listHD = tkdao.selectByKhoangTien(money);
                for (HoaDon list : listHD) {
                    NhanVien nv = nvdao.selectById(list.getMaNV());
                    Object[] row = {
                        list.getMaHD(),
                        dateformat.format(list.getNgayHD()),
                        currencyVN.format(list.getTongTien()),
                        nv.getTenNV()

                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
            }
        }

    }

    //CHART
    private void showLineChart() {
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();

        for (int month = 1; month < 13; month++) {
            List<HoaDon> hd = tkdao.selectDoanhThu(month, year);
            for (HoaDon hoaDon : hd) {
                dataset.setValue(hoaDon.getTongTien(), "Doanh thu", "Tháng " + month);
            }
        }

        //CREATE CHART
        JFreeChart linechart = ChartFactory.createLineChart(
                "Biểu đồ doanh thu năm " + year,
                "Tháng",
                "Doanh thu (VNĐ)", dataset, PlotOrientation.VERTICAL, false, true, false);

        //create plot object
        CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.GRAY);
        lineCategoryPlot.setBackgroundPaint(Color.white);

        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(0, 204, 0);
        lineRenderer.setSeriesPaint(0, lineChartColor);

        //CHART FRAME
//        ChartFrame frame = new ChartFrame("Line chart", linechart, true);
//        frame.setSize(1600, 900);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setResizable(false);
//
//        JDialog jdl = new JDialog(this);
//        jdl.setContentPane(frame.getChartPanel());
//        jdl.setSize(frame.getSize());
//        jdl.setTitle("Xem Hóa Đơn");
//        jdl.setLocationRelativeTo(null);
//        jdl.setVisible(true);
        jpnBieuDo.removeAll();
        jpnBieuDo.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(linechart);
        jpnBieuDo.add(CP, BorderLayout.CENTER);
        jpnBieuDo.validate();

    }

    public void showBarChart() {
        //Khởi tạo datatset cho chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();

        for (int month = 1; month < 13; month++) {
            List<HoaDon> hd = tkdao.selectDoanhThu(month, year);
            for (HoaDon hoaDon : hd) {
                dataset.setValue(hoaDon.getTongTien(), "Doanh thu", "Tháng " + month);
            }
        }

        //Tạo chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu năm " + year,
                "Tháng",
                "Doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);

        //Tạo thuộc tính hiện thị của đối tượng
        CategoryPlot barCategoryPlot = barChart.getCategoryPlot();
        barCategoryPlot.setRangeGridlinePaint(Color.GRAY); //kẻ ngang
        barCategoryPlot.setBackgroundPaint(Color.WHITE); //nền

        BarRenderer barRender = (BarRenderer) barCategoryPlot.getRenderer(); //render bar color
        Color barColor = new Color(0, 153, 255);
        barRender.setSeriesPaint(0, barColor);

        //Tạo khung chart
//        ChartFrame frame = new ChartFrame("Line chart", barChart, true);
//        frame.setVisible(true);
//        frame.setSize(1600, 900);
//        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
        jpnBieuDo.removeAll();
        jpnBieuDo.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(barChart);
        jpnBieuDo.add(CP, BorderLayout.CENTER);
        jpnBieuDo.validate();
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
        jLabel1 = new javax.swing.JLabel();
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
        lblSoTien = new javax.swing.JLabel();
        cboSoTien = new javax.swing.JComboBox<>();
        lblNhanVien = new javax.swing.JLabel();
        cboNhanVien = new javax.swing.JComboBox<>();
        lblTuNgay = new javax.swing.JLabel();
        lblDenNgay = new javax.swing.JLabel();
        dtcTuNgay = new com.toedter.calendar.JDateChooser();
        dtcDenNgay = new com.toedter.calendar.JDateChooser();
        pnlBoxBtn = new javax.swing.JPanel();
        btnLocTheoNgay = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLineChart = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnBarChart = new javax.swing.JButton();
        pnlCTHD = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jpnBieuDo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("THỐNG KÊ");
        setResizable(false);

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));

        pnlHeader.setPreferredSize(new java.awt.Dimension(1600, 50));
        pnlHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("THỐNG KÊ");
        pnlHeader.add(lblTitle);
        lblTitle.setBounds(20, 4, 1570, 40);

        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/header_black_1600x50.png"))); // NOI18N
        pnlHeader.add(lblHeader);
        lblHeader.setBounds(0, 0, 1620, 50);

        tabThongKe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnlHoaDonHomNay.setBackground(new java.awt.Color(255, 255, 255));

        pnlHomNay.setBackground(new java.awt.Color(255, 255, 255));
        pnlHomNay.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÔM NAY", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblTongSoHoaDonHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongSoHoaDonHomNay.setText("Tổng số hóa đơn hôm nay:");

        lblTongTienHomNay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTienHomNay.setText("Tổng tiền tất cả hóa đơn:");

        lblSort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSort.setText("Sort:");

        txtTongHDHomNay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongHDHomNay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTongTienHomNay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongTienHomNay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        cboSort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã Hóa Đơn", "Theo Họ Tên", "Tổng Tiền" }));
        cboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSortActionPerformed(evt);
            }
        });

        tblHoaDon.setAutoCreateRowSorter(true);
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(20);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VNĐ");

        javax.swing.GroupLayout pnlHomNayLayout = new javax.swing.GroupLayout(pnlHomNay);
        pnlHomNay.setLayout(pnlHomNayLayout);
        pnlHomNayLayout.setHorizontalGroup(
            pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomNayLayout.createSequentialGroup()
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHomNayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(pnlHomNayLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlHomNayLayout.createSequentialGroup()
                                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongSoHoaDonHomNay)
                                    .addComponent(lblTongTienHomNay))
                                .addGap(18, 18, 18)
                                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTongHDHomNay)
                                    .addGroup(pnlHomNayLayout.createSequentialGroup()
                                        .addComponent(txtTongTienHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(pnlHomNayLayout.createSequentialGroup()
                                .addComponent(lblSort)
                                .addGap(18, 18, 18)
                                .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 35, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHomNayLayout.setVerticalGroup(
            pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongSoHoaDonHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongHDHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTienHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTienHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        pnlChiTiet.setBackground(new java.awt.Color(255, 255, 255));
        pnlChiTiet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblChiTietHoaDon.setAutoCreateRowSorter(true);
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietHoaDon.setRowHeight(20);
        jScrollPane1.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout pnlChiTietLayout = new javax.swing.GroupLayout(pnlChiTiet);
        pnlChiTiet.setLayout(pnlChiTietLayout);
        pnlChiTietLayout.setHorizontalGroup(
            pnlChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
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
                .addComponent(pnlHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHoaDonHomNayLayout.setVerticalGroup(
            pnlHoaDonHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonHomNayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHoaDonHomNayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabThongKe.addTab("HÓA ĐƠN HÔM NAY", pnlHoaDonHomNay);

        pnlLichSu.setBackground(new java.awt.Color(255, 255, 255));

        lblThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThongKe.setText("THỐNG KÊ");

        tblThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "TỔNG SỐ HÓA ĐƠN", "TỔNG TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblThongKe);

        pblLichSuHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        pblLichSuHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LỊCH SỬ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblLichSuHoaDon.setAutoCreateRowSorter(true);
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLichSuHoaDon.setRowHeight(20);
        tblLichSuHoaDon.getTableHeader().setResizingAllowed(false);
        tblLichSuHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblLichSuHoaDon);

        pnlLocDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        pnlLocDoanhThu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bộ lọc & Biểu đồ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblSoTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoTien.setText("Số tiền:");

        cboSoTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSoTien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "100000", "200000", "500000", "1000000", "2000000" }));
        cboSoTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSoTienActionPerformed(evt);
            }
        });

        lblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNhanVien.setText("Nhân viên:");

        cboNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVienActionPerformed(evt);
            }
        });

        lblTuNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTuNgay.setText("Từ ngày:");

        lblDenNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDenNgay.setText("Đến ngày:");

        dtcTuNgay.setDateFormatString("dd-MM-yyyy");
        dtcTuNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dtcDenNgay.setDateFormatString("dd-MM-yyyy");
        dtcDenNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnlBoxBtn.setBackground(new java.awt.Color(255, 255, 255));
        pnlBoxBtn.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        btnLocTheoNgay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLocTheoNgay.setText("LỌC");
        btnLocTheoNgay.setPreferredSize(new java.awt.Dimension(100, 40));
        btnLocTheoNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocTheoNgayActionPerformed(evt);
            }
        });
        pnlBoxBtn.add(btnLocTheoNgay);

        btnLineChart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLineChart.setText("Biểu đồ đường (Doanh thu từng tháng)");
        btnLineChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLineChartActionPerformed(evt);
            }
        });

        btnBarChart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBarChart.setText("Biểu đồ cột (Doanh thu từng tháng)");
        btnBarChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLocDoanhThuLayout = new javax.swing.GroupLayout(pnlLocDoanhThu);
        pnlLocDoanhThu.setLayout(pnlLocDoanhThuLayout);
        pnlLocDoanhThuLayout.setHorizontalGroup(
            pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDenNgay)
                            .addComponent(lblTuNgay))
                        .addGap(40, 40, 40)
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtcDenNgay, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(dtcTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlBoxBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSoTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                        .addComponent(btnLineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlLocDoanhThuLayout.setVerticalGroup(
            pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlLocDoanhThuLayout.createSequentialGroup()
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtcTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtcDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlBoxBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSoTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlLocDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBarChart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pblLichSuHoaDonLayout = new javax.swing.GroupLayout(pblLichSuHoaDon);
        pblLichSuHoaDon.setLayout(pblLichSuHoaDonLayout);
        pblLichSuHoaDonLayout.setHorizontalGroup(
            pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblLichSuHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(pnlLocDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pblLichSuHoaDonLayout.setVerticalGroup(
            pblLichSuHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pblLichSuHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlLocDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCTHD.setBackground(new java.awt.Color(255, 255, 255));
        pnlCTHD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblCTHD.setAutoCreateRowSorter(true);
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTHD.setRowHeight(20);
        tblCTHD.getTableHeader().setResizingAllowed(false);
        jScrollPane5.setViewportView(tblCTHD);

        javax.swing.GroupLayout pnlCTHDLayout = new javax.swing.GroupLayout(pnlCTHD);
        pnlCTHD.setLayout(pnlCTHDLayout);
        pnlCTHDLayout.setHorizontalGroup(
            pnlCTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCTHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
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
                    .addComponent(pblLichSuHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabThongKe.addTab("LỊCH SỬ HÓA ĐƠN", pnlLichSu);

        javax.swing.GroupLayout jpnBieuDoLayout = new javax.swing.GroupLayout(jpnBieuDo);
        jpnBieuDo.setLayout(jpnBieuDoLayout);
        jpnBieuDoLayout.setHorizontalGroup(
            jpnBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1607, Short.MAX_VALUE)
        );
        jpnBieuDoLayout.setVerticalGroup(
            jpnBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );

        tabThongKe.addTab("BIỂU ĐỒ THỐNG KÊ DOANH THU", jpnBieuDo);

        javax.swing.GroupLayout pnlWallLayout = new javax.swing.GroupLayout(pnlWall);
        pnlWall.setLayout(pnlWallLayout);
        pnlWallLayout.setHorizontalGroup(
            pnlWallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabThongKe)
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

    private void btnLineChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineChartActionPerformed
        showLineChart();
        tabThongKe.setSelectedIndex(2);
    }//GEN-LAST:event_btnLineChartActionPerformed

    private void btnBarChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarChartActionPerformed
        showBarChart();
        tabThongKe.setSelectedIndex(2);
    }//GEN-LAST:event_btnBarChartActionPerformed

    private void cboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSortActionPerformed
        this.Sort();
    }//GEN-LAST:event_cboSortActionPerformed

    private void btnLocTheoNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocTheoNgayActionPerformed
        findNgayThangNamHD();
    }//GEN-LAST:event_btnLocTheoNgayActionPerformed

    private void cboSoTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSoTienActionPerformed
        this.ShowTheoKhoangTien();
    }//GEN-LAST:event_cboSoTienActionPerformed

    private void cboNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVienActionPerformed
        this.showHDTenNV();
    }//GEN-LAST:event_cboNhanVienActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        showHDCT();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblLichSuHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuHoaDonMouseClicked
        showLichSuHDCT();
    }//GEN-LAST:event_tblLichSuHoaDonMouseClicked

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
    private javax.swing.JButton btnBarChart;
    private javax.swing.JButton btnLineChart;
    private javax.swing.JButton btnLocTheoNgay;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JComboBox<String> cboSoTien;
    private javax.swing.JComboBox<String> cboSort;
    private com.toedter.calendar.JDateChooser dtcDenNgay;
    private com.toedter.calendar.JDateChooser dtcTuNgay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel jpnBieuDo;
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
    private javax.swing.JPanel pnlBoxBtn;
    private javax.swing.JPanel pnlCTHD;
    private javax.swing.JPanel pnlChiTiet;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHoaDonHomNay;
    private javax.swing.JPanel pnlHomNay;
    private javax.swing.JPanel pnlLichSu;
    private javax.swing.JPanel pnlLocDoanhThu;
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
