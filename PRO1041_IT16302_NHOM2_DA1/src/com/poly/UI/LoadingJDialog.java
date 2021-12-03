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
public class LoadingJDialog extends javax.swing.JDialog {

    /**
     * Creates new form Loading
     */
    public LoadingJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
    }

    LoadingJDialog(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void init() {
        setLocationRelativeTo(null);
        //thread là 1 khỗi mã thực hiện nhiệm vụ
        Thread thread = new Thread() {
            int i = -1;

            @Override
            public void run() {
                while (true) {
                    try {
                        i++;
                        progressBar.setValue(i);
                        if (i == 20) {
                            lblLoadingStatut.setText("Đang khởi tạo Module...");
                        }
                        if (i == 50) {
                            lblLoadingStatut.setText("Đang kết nối Database...");
                        }
                        if (i == 90) {
                            lblLoadingStatut.setText("Chuẩn bị vào chương trình...");
                        }
                        if (i == 100) {
                            LoadingJDialog.this.dispose();
                            break;
                        }

                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }

            }

        };
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Wall = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        lblLoadingStatut = new javax.swing.JLabel();
        lblLoadingBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wellcome");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setUndecorated(true);
        setResizable(false);

        jPanel_Wall.setPreferredSize(new java.awt.Dimension(1280, 720));
        jPanel_Wall.setLayout(null);

        progressBar.setBackground(new java.awt.Color(255, 255, 255));
        progressBar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        progressBar.setForeground(new java.awt.Color(0, 153, 255));
        progressBar.setFocusable(false);
        progressBar.setPreferredSize(new java.awt.Dimension(1280, 24));
        progressBar.setStringPainted(true);
        jPanel_Wall.add(progressBar);
        progressBar.setBounds(0, 690, 1280, 24);

        lblLoadingStatut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLoadingStatut.setForeground(new java.awt.Color(255, 255, 255));
        lblLoadingStatut.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoadingStatut.setText("Loading...");
        lblLoadingStatut.setPreferredSize(new java.awt.Dimension(1280, 20));
        jPanel_Wall.add(lblLoadingStatut);
        lblLoadingStatut.setBounds(0, 664, 1280, 20);

        lblLoadingBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/Icons/loading_background_1280x720.png"))); // NOI18N
        jPanel_Wall.add(lblLoadingBackground);
        lblLoadingBackground.setBounds(0, 0, 1280, 720);

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
            java.util.logging.Logger.getLogger(LoadingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoadingJDialog dialog = new LoadingJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel_Wall;
    private javax.swing.JLabel lblLoadingBackground;
    private javax.swing.JLabel lblLoadingStatut;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
