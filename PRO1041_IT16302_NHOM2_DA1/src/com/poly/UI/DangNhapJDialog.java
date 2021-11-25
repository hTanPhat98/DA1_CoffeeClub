/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.UI;

import com.poly.DAO.AccountDAO;
import com.poly.Helper.Auth;
import com.poly.Helper.XImage;
import com.poly.Model.Account;
import com.poly.Model.TKQMK;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;

/**
 *
 * @author phong
 */
public class DangNhapJDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginJdialog
     */
    public DangNhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    AccountDAO dao = new AccountDAO();
    int otp = 0;
    TKQMK qmk;

    private void init() {
        this.setIconImage(XImage.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setDisplay();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new WaitingJFrame().setVisible(true);
            }
        });
    }

    private void setDisplay() {
        pnlLoginForm.setVisible(true);
    }

    private void openFormResetPass() {
        pnlLoginForm.setVisible(false);
        pnlResetPassword.setVisible(true);
    }

    private void exitFormResetPass() {
        pnlLoginForm.setVisible(true);
        pnlResetPassword.setVisible(false);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        Account acc = dao.selectById(username);
        if (acc == null) {
            new ThongBaoJDialog(null, true).alert(2, "Sai tên đăng nhập!");
        } else if (!password.equals(acc.getPassworld())) {
            new ThongBaoJDialog(null, true).alert(2, "Sai mật khẩu!");
        } else {
            Auth.user = acc;
            this.dispose();
            new MainJFrame().setVisible(true);
        }
    }

    private void resetPassword() {
        if (otp == 0) {
            //thông báo vui nhập mã otp
            System.out.println("vui lòng nhập mã otp");
        } else if (otp == Integer.parseInt(txtOTP.getText())) {
            if (txtNewPassword.getText().equalsIgnoreCase(txtConfirmPassword.getText())) {
                dao.update(new Account(qmk.getUserName(), String.valueOf(txtNewPassword.getPassword()), qmk.getMaNV(), qmk.isVaiTro()));
                //Thông báo đổi mk thành công
                System.out.println("Đặt lại mật khẩu thành công");
                otp = 0;
                qmk = null;
            } else {
                new ThongBaoJDialog(null, true).alert(2, "Mật khẩu xác thực không khớp!");
            }
        } else {
            new ThongBaoJDialog(null, true).alert(2, "OTP xác thực không khớp!");
        }
    }

    private void getOTP() {
        boolean OTPTC = false;
        otp = randomOTP();
        List<TKQMK> list = new ArrayList<>();
        list = dao.selectEmail();
        for (TKQMK tkqmk : list) {
            if (tkqmk.getEmail().equalsIgnoreCase(txtEmail.getText())) {
                qmk = tkqmk;
                Properties p = new Properties();
                p.put("mail.smtp.auth", "true");
                p.put("mail.smtp.starttls.enable", "true");
                p.put("mail.smtp.host", "smtp.gmail.com");
                p.put("mail.smtp.port", 587);
                String username = "dhoa8488@gmail.com", password = "Ma1412pet@";
                try {
                    Session s = Session.getInstance(p, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
                    String from = "TheCoffeeClub";
                    String to = txtEmail.getText();
                    String subject = "Mã xác thực tài khoản The Coffee Club";
                    String OTP = String.valueOf(otp);
                    String body = "Mã OTP: " + OTP;
                    Message message = new MimeMessage(s);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    message.setSubject(subject);
                    message.setText(body);
                    Transport.send(message);
                    OTPTC = true;
                } catch (MessagingException | HeadlessException e) {
                    Logger.getLogger(DangNhapJDialog.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        if (OTPTC) {
            new ThongBaoJDialog(null, true).alert(1, "Lấy OTP thành công!");
            btnGetOTP.setEnabled(false);
        } else {
            new ThongBaoJDialog(null, true).alert(2, "Lấy OTP thất bại!");
            otp = 0;
        }

    }

    private int randomOTP() {
        Integer otprd = 0;
        while (true) {
            if (otprd.toString().length() < 6) {
                Double randomotp = Math.random();
                randomotp = randomotp * 1000000;
                otprd = randomotp.intValue();
            } else {
                return otprd;
            }
        }
    }

    private void dangNhapkey(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.login();
        }
    }

    private void dangNhapQR() {
        new DangNhapQRJDialog(null, true, this).setVisible(true);
    }

    public void dangNhap(String tttk) {
        String[] tach = tttk.split("-");
        if (tach.length == 3) {
            txtUsername.setText(tach[0]);
            txtPassword.setText(tach[1]);
            this.login();
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
        pnlLoginForm = new javax.swing.JPanel();
        pnlLogin = new javax.swing.JPanel();
        lblIcoNguoi = new javax.swing.JLabel();
        lblLogoBangHieu = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblIconUser = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblIconPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        lblReset = new javax.swing.JLabel();
        lblForgotPassword = new javax.swing.JLabel();
        btnLoginQRcode = new javax.swing.JButton();
        lbl_LogoResetPassword = new javax.swing.JLabel();
        pnlResetPassword = new javax.swing.JPanel();
        lbl_LogoLogin = new javax.swing.JLabel();
        jPanel_ResetPasswordForm = new javax.swing.JPanel();
        lblIconResetPassWord = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblOTP = new javax.swing.JLabel();
        txtOTP = new javax.swing.JTextField();
        btnGetOTP = new javax.swing.JButton();
        lblNewPassword = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        lblConfirmPassword = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        cboShowPassword = new javax.swing.JCheckBox();
        btnResetPassword = new javax.swing.JButton();
        lblBackToLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setFocusable(false);
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        pnlWall.setBackground(new java.awt.Color(255, 255, 255));
        pnlWall.setLayout(new java.awt.CardLayout());

        pnlLoginForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoginForm.setLayout(new java.awt.GridLayout(1, 0));

        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));

        lblIcoNguoi.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblIcoNguoi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcoNguoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/login_logo_barista_x128.png"))); // NOI18N
        lblIcoNguoi.setText("ĐĂNG NHẬP");
        lblIcoNguoi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblIcoNguoi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblLogoBangHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/coffee-shop_text_edit.png"))); // NOI18N

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("Username");

        lblIconUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/login_icon_usename_x32.png"))); // NOI18N

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUsername.setText("taiquanly");
        txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password");

        lblIconPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/login_icon_password_x32.png"))); // NOI18N

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassword.setText("huutai80");
        txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(51, 153, 255));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login ");
        btnLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        btnLogin.setFocusable(false);
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnLogin.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/login_icon_button_24.png"))); // NOI18N
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblReset.setBackground(new java.awt.Color(255, 255, 255));
        lblReset.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblReset.setText("Reset!");
        lblReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResetMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblResetMouseExited(evt);
            }
        });

        lblForgotPassword.setBackground(new java.awt.Color(255, 255, 255));
        lblForgotPassword.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblForgotPassword.setText("Forgot password?");
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMousePressed(evt);
            }
        });

        btnLoginQRcode.setBackground(new java.awt.Color(51, 153, 255));
        btnLoginQRcode.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLoginQRcode.setForeground(new java.awt.Color(255, 255, 255));
        btnLoginQRcode.setText("QR Code");
        btnLoginQRcode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        btnLoginQRcode.setFocusable(false);
        btnLoginQRcode.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnLoginQRcode.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/login_icon_button_24.png"))); // NOI18N
        btnLoginQRcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginQRcodeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginQRcodeMouseExited(evt);
            }
        });
        btnLoginQRcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginQRcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLogoBangHieu))
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIcoNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlLoginLayout.createSequentialGroup()
                                    .addComponent(lblIconUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlLoginLayout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlLoginLayout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLoginQRcode, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlLoginLayout.createSequentialGroup()
                                        .addComponent(lblIconPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlLoginLayout.createSequentialGroup()
                                    .addGap(58, 58, 58)
                                    .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlLoginLayout.createSequentialGroup()
                                            .addComponent(lblReset, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(182, 182, 182)
                                            .addComponent(lblForgotPassword))))))))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addComponent(lblLogoBangHieu)
                .addGap(20, 20, 20)
                .addComponent(lblIcoNguoi)
                .addGap(38, 38, 38)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIconUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIconPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoginQRcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblReset)
                    .addComponent(lblForgotPassword))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        pnlLoginForm.add(pnlLogin);

        lbl_LogoResetPassword.setBackground(new java.awt.Color(255, 255, 255));
        lbl_LogoResetPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_LogoResetPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/loading_logo_coffee_540x419_black.png"))); // NOI18N
        lbl_LogoResetPassword.setPreferredSize(new java.awt.Dimension(620, 630));
        pnlLoginForm.add(lbl_LogoResetPassword);

        pnlWall.add(pnlLoginForm, "card2");

        pnlResetPassword.setBackground(new java.awt.Color(51, 51, 51));
        pnlResetPassword.setPreferredSize(new java.awt.Dimension(640, 720));
        pnlResetPassword.setLayout(new java.awt.GridLayout(1, 0));

        lbl_LogoLogin.setBackground(new java.awt.Color(255, 255, 255));
        lbl_LogoLogin.setForeground(new java.awt.Color(255, 255, 255));
        lbl_LogoLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_LogoLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/loading_logo_coffee_540x419_white.png"))); // NOI18N
        pnlResetPassword.add(lbl_LogoLogin);

        jPanel_ResetPasswordForm.setBackground(new java.awt.Color(255, 255, 255));

        lblIconResetPassWord.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblIconResetPassWord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconResetPassWord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/resetpassword_logo_x128.png"))); // NOI18N
        lblIconResetPassWord.setText("ĐẶT LẠI MẬT KHẨU");
        lblIconResetPassWord.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblIconResetPassWord.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblMail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMail.setText("Nhập emai của bạn");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        lblOTP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblOTP.setText("OTP");

        txtOTP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtOTP.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        btnGetOTP.setBackground(new java.awt.Color(51, 153, 255));
        btnGetOTP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGetOTP.setForeground(new java.awt.Color(255, 255, 255));
        btnGetOTP.setText("Get OTP");
        btnGetOTP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.black));
        btnGetOTP.setFocusable(false);
        btnGetOTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGetOTPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGetOTPMouseExited(evt);
            }
        });
        btnGetOTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetOTPActionPerformed(evt);
            }
        });

        lblNewPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNewPassword.setText("New password");

        txtNewPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNewPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        lblConfirmPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblConfirmPassword.setText("Confirm password");

        txtConfirmPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtConfirmPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        cboShowPassword.setBackground(new java.awt.Color(255, 255, 255));
        cboShowPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboShowPassword.setText("Show password");
        cboShowPassword.setFocusable(false);
        cboShowPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cboShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboShowPasswordActionPerformed(evt);
            }
        });

        btnResetPassword.setBackground(new java.awt.Color(51, 153, 255));
        btnResetPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnResetPassword.setForeground(new java.awt.Color(255, 255, 255));
        btnResetPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/resetpassword_icon_button_x24.png"))); // NOI18N
        btnResetPassword.setText(" Reset password");
        btnResetPassword.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.black));
        btnResetPassword.setFocusable(false);
        btnResetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnResetPasswordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnResetPasswordMouseExited(evt);
            }
        });
        btnResetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPasswordActionPerformed(evt);
            }
        });

        lblBackToLogin.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblBackToLogin.setText("Back to Login");
        lblBackToLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackToLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBackToLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBackToLoginMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBackToLoginMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ResetPasswordFormLayout = new javax.swing.GroupLayout(jPanel_ResetPasswordForm);
        jPanel_ResetPasswordForm.setLayout(jPanel_ResetPasswordFormLayout);
        jPanel_ResetPasswordFormLayout.setHorizontalGroup(
            jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                        .addComponent(lblBackToLogin)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                        .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIconResetPassWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblConfirmPassword)
                                    .addComponent(lblNewPassword))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtConfirmPassword)
                                    .addComponent(txtNewPassword)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ResetPasswordFormLayout.createSequentialGroup()
                                        .addComponent(btnResetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboShowPassword))))
                            .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMail)
                                    .addComponent(lblOTP))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                                        .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnGetOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtEmail))))
                        .addGap(20, 20, 20))))
        );
        jPanel_ResetPasswordFormLayout.setVerticalGroup(
            jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ResetPasswordFormLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblIconResetPassWord)
                .addGap(50, 50, 50)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGetOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOTP))
                .addGap(30, 30, 30)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNewPassword))
                .addGap(30, 30, 30)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfirmPassword))
                .addGap(30, 30, 30)
                .addGroup(jPanel_ResetPasswordFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboShowPassword)
                    .addComponent(btnResetPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(lblBackToLogin)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        pnlResetPassword.add(jPanel_ResetPasswordForm);

        pnlWall.add(pnlResetPassword, "card3");

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

    //lblBack MouseClicked
    private void lblBackToLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackToLoginMouseClicked
        this.exitFormResetPass();
    }//GEN-LAST:event_lblBackToLoginMouseClicked

    //lblForgotPassword MouseClicked
    private void lblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseClicked
        this.openFormResetPass();
    }//GEN-LAST:event_lblForgotPasswordMouseClicked

    //btn LOGIN ActionPerformed
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        this.login();
    }//GEN-LAST:event_btnLoginActionPerformed

    // lblForgotPassword MousePressed
    private void lblForgotPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMousePressed
        this.openFormResetPass();
    }//GEN-LAST:event_lblForgotPasswordMousePressed

    //lblBack MousePressed
    private void lblBackToLoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackToLoginMousePressed
        this.exitFormResetPass();
    }//GEN-LAST:event_lblBackToLoginMousePressed

    //HOVER
    private void setEntered(JButton btn) {
        // btn.setBackground(new Color(133, 233, 123));
        //btn.setBackground(new Color(0, 190, 0));
        btn.setBackground(new Color(0, 158, 0));
        btn.setForeground(Color.WHITE);
    }

    private void setDefaultButton(JButton btn) {
        btn.setBackground(new Color(51, 153, 255));
        btn.setForeground(Color.WHITE);
    }

    private void reset() {
        txtUsername.setText(null);
        txtPassword.setText(null);
    }

    //HOVER DANG NHAP
    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        this.setEntered(btnLogin);
    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        this.setDefaultButton(btnLogin);
    }//GEN-LAST:event_btnLoginMouseExited

    //HOVER RESET
    private void lblResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResetMouseClicked
        this.reset();
    }//GEN-LAST:event_lblResetMouseClicked

    private void lblResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResetMouseExited
        lblReset.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblResetMouseExited

    private void lblResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResetMouseEntered
        lblReset.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_lblResetMouseEntered

    //HOVER FORGOT PASSWORD
    private void lblForgotPasswordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseEntered
        lblForgotPassword.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_lblForgotPasswordMouseEntered

    private void lblForgotPasswordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseExited
        lblForgotPassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblForgotPasswordMouseExited

    //Panel RESET PASSWORD ActionPerformed
    private void btnGetOTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetOTPActionPerformed
        this.getOTP();
    }//GEN-LAST:event_btnGetOTPActionPerformed

    private void btnResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPasswordActionPerformed
        this.resetPassword();
    }//GEN-LAST:event_btnResetPasswordActionPerformed

    private void cboShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboShowPasswordActionPerformed
        if (cboShowPassword.isSelected()) {
            txtNewPassword.setEchoChar((char) 0);
            txtConfirmPassword.setEchoChar((char) 0);
        } else {
            txtNewPassword.setEchoChar('*');
            txtConfirmPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_cboShowPasswordActionPerformed

    //HOVER GET OTP
    private void btnGetOTPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetOTPMouseEntered
        this.setEntered(btnGetOTP);
    }//GEN-LAST:event_btnGetOTPMouseEntered

    private void btnGetOTPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetOTPMouseExited
        this.setDefaultButton(btnGetOTP);
    }//GEN-LAST:event_btnGetOTPMouseExited

    //HOVER GET OTP
    private void btnResetPasswordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetPasswordMouseEntered
        this.setEntered(btnResetPassword);
    }//GEN-LAST:event_btnResetPasswordMouseEntered

    private void btnResetPasswordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetPasswordMouseExited
        this.setDefaultButton(btnResetPassword);
    }//GEN-LAST:event_btnResetPasswordMouseExited

    //HOVER GET OTP
    private void lblBackToLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackToLoginMouseEntered
        lblBackToLogin.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_lblBackToLoginMouseEntered

    private void lblBackToLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackToLoginMouseExited
        lblBackToLogin.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblBackToLoginMouseExited

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        this.dangNhapkey(evt);
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void btnLoginQRcodeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginQRcodeMouseEntered
        this.setEntered(btnLoginQRcode);
    }//GEN-LAST:event_btnLoginQRcodeMouseEntered

    private void btnLoginQRcodeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginQRcodeMouseExited
        this.setDefaultButton(btnLoginQRcode);
    }//GEN-LAST:event_btnLoginQRcodeMouseExited

    private void btnLoginQRcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginQRcodeActionPerformed
        this.dangNhapQR();
    }//GEN-LAST:event_btnLoginQRcodeActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGetOTP;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLoginQRcode;
    private javax.swing.JButton btnResetPassword;
    private javax.swing.JCheckBox cboShowPassword;
    private javax.swing.JPanel jPanel_ResetPasswordForm;
    private javax.swing.JLabel lblBackToLogin;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblForgotPassword;
    private javax.swing.JLabel lblIcoNguoi;
    private javax.swing.JLabel lblIconPassword;
    private javax.swing.JLabel lblIconResetPassWord;
    private javax.swing.JLabel lblIconUser;
    private javax.swing.JLabel lblLogoBangHieu;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblNewPassword;
    private javax.swing.JLabel lblOTP;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblReset;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lbl_LogoLogin;
    private javax.swing.JLabel lbl_LogoResetPassword;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlLoginForm;
    private javax.swing.JPanel pnlResetPassword;
    private javax.swing.JPanel pnlWall;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JTextField txtOTP;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
