/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QLUser;

import chatserver.ConnectData;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AnhThu
 */
public class TableUser extends javax.swing.JFrame {

    private ConnectData loadCSDL;
    private AddNew frNew;
    private Edit frEdit;
    private DefaultTableModel tbModel;
    private String values;
    private String nameCol;

//Constructor
    public TableUser() {
        initComponents();
        setIcon();
        loadCSDL = new ConnectData();
    }

    public JButton getBtEdit() {
        return btEdit;
    }

    public void LoadData() {
        try {
            tbModel = new DefaultTableModel();
            ResultSet rs = loadCSDL.ExecuteSelectDB("select * from UserLogin");
            ResultSetMetaData rsmt = rs.getMetaData();
            int numberColum = rsmt.getColumnCount(); // dem so cot
            ArrayList<String> arrColum = new ArrayList<>();
            for (int i = 1; i <= numberColum; i++) { // lay tieu de cot bo vao ArrayList String
                arrColum.add(rsmt.getColumnName(i));
            }
            tbModel.setColumnIdentifiers(arrColum.toArray());
            ArrayList<String> arrRow = new ArrayList<>();
            while (rs.next()) {
                for (int i = 1; i <= numberColum; i++) {
                    arrRow.add(rs.getString(i));
                }
                tbModel.addRow(arrRow.toArray());
                arrRow.clear();
                // dua arrow vao table
            }
            tbdata.setModel(tbModel);


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(TableUser.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbdata = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btEdit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tbdata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbdata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbdataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbdata);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btEdit.setBackground(new java.awt.Color(204, 204, 255));
        btEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btEdit.setForeground(new java.awt.Color(0, 0, 255));
        btEdit.setText("Edit");
        btEdit.setEnabled(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 204, 0));
        jButton4.setText("Seach");

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 204, 51));
        jButton1.setText("Add New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        close.setBackground(new java.awt.Color(204, 204, 255));
        close.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        close.setForeground(new java.awt.Color(255, 153, 102));
        close.setText("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frNew = new AddNew(this);
        frNew.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        frEdit = new Edit(this, nameCol, values);
        frEdit.setVisible(true);
        ResultSet rs = loadCSDL.ExecuteSelectDB("SELECT * FROM UserLogin WHERE " + nameCol + " LIKE '" + values + "'");
        try {
            rs.next();
            frEdit.getTxtusername().setText(rs.getNString("Username"));
            frEdit.getTxtpassword().setText(rs.getNString("Password"));
            frEdit.getTxtcofirmpass().setText(rs.getNString("Password"));
            frEdit.getTxtfullname().setText(rs.getNString("Fullname"));
            frEdit.getTxtaddress().setText(rs.getNString("Address"));
        } catch (SQLException ex) {
            Logger.getLogger(TableUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btEditActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int jp = JOptionPane.showConfirmDialog(null,"Are you Delete it?", "Delete Item", JOptionPane.YES_NO_OPTION);
        if (jp == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE From UserLogin where " + nameCol + " Like '" + values + "'";
                loadCSDL.ExecuteDB(sql, false);
                JOptionPane.showMessageDialog(null, "Deleted Successful!", "Delete", 1);
                LoadData();
            } catch (Exception io) {
            }
        } else if (jp == JOptionPane.NO_OPTION) {
            System.out.println("Chosse No");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbdataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbdataMouseClicked
        int row = tbdata.rowAtPoint(evt.getPoint());
        int col = tbdata.columnAtPoint(evt.getPoint());
        values = tbdata.getValueAt(row, col).toString();
        nameCol = tbdata.getColumnName(col);
        System.out.println(values + " ncot: " + nameCol);
        btEdit.setEnabled(true);
    }//GEN-LAST:event_tbdataMouseClicked

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
       this.dispose();
    }//GEN-LAST:event_closeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEdit;
    private javax.swing.JButton close;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbdata;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/tray.png")));
    }
}
