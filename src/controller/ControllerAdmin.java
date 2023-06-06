/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import helper.HelperRenter;
import helper.HelperRoom;
import models.Renter;
import views.AdminPageView;
import views.EditDeleteDialogView;

/**
 *
 * @author MSI MODERN
 */
public class ControllerAdmin implements ActionListener , ListSelectionListener {
   JDialog dlg;
    AdminPageView viewadm;
    ControllerLogin ctrllogin;
    List<Renter> listrenter;
    public ControllerAdmin(ControllerLogin clgn){
        viewadm = new AdminPageView();
        ctrllogin = clgn;
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Untuk mengembalikan nilai false untuk mencegah pengeditan sel
                return false; 
            }
        };
        dtm.addColumn("ID");
        dtm.addColumn("Name");
        dtm.addColumn("Contact");
        dtm.addColumn("Room");
        dtm.addColumn("Duration");
        dtm.addColumn("Bill");
        dtm.addColumn("Status");
        viewadm.getTabel().getSelectionModel().addListSelectionListener(this);
        viewadm.getBlogout().addActionListener(this);
        try{
            listrenter = HelperRenter.getListRenter();
            for(Renter r : listrenter){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
            }
        }
        
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            viewadm.getTabel().setModel(dtm);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==viewadm.getBlogout()){
           ctrllogin.showPage(true);
           viewadm.dispose();
           
       }
    }
    
    public JDialog getDialog(){
        return this.dlg;
    }
    
    public void updateTable(){
        listrenter.clear();
        viewadm.setTableModel(null);
        try {
            listrenter = HelperRenter.getListRenter();
            DefaultTableModel dtm = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Untuk mengembalikan nilai false untuk mencegah pengeditan sel
                    return false; 
                }
            };
            dtm.addColumn("ID");
            dtm.addColumn("Name");
            dtm.addColumn("Contact");
            dtm.addColumn("Room");
            dtm.addColumn("Duration");
            dtm.addColumn("Bill");
            dtm.addColumn("Status");
            
            for(Renter r : listrenter){
                dtm.addRow(new Object[]{r.getId(),r.getNama(),r.getContact(),r.getRoom(),r.getDuration(),r.getBill(),r.getStatus()});
            }
            viewadm.getTabel().setModel(dtm);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(ArrayIndexOutOfBoundsException aex){
            
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            System.out.println(viewadm.getTabel().getValueAt(viewadm.getTabel().getSelectedRow(), 6));
            if(!"PAID".equals(viewadm.getTabel().getValueAt(viewadm.getTabel().getSelectedRow(), 6).toString().toUpperCase())){
                try{
                    int choice = JOptionPane.showOptionDialog(null, "Ubah jadi sudah dibayar?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if(choice == JOptionPane.YES_OPTION){
                        try {
                            HelperRenter.updateRenter(Integer.parseInt(viewadm.getTabel().getValueAt(viewadm.getTabel().getSelectedRow(),0).toString()));
                            updateTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
                catch(Exception ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,ex.getMessage());
                }
            }
            else{
                System.out.println("sudah dibayar");
                EditDeleteDialogController edc = null;
                try {
                    edc = new EditDeleteDialogController(this,HelperRenter.getRenterById(Integer.parseInt(viewadm.getTabel().getValueAt(viewadm.getTabel().getSelectedRow(), 0).toString())));
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(edc!=null){
                    dlg = new JDialog(viewadm.getWindow(), "Choose Action", true);
                    dlg.setContentPane(edc.getView().getContentPane());
                    dlg.pack();
                    dlg.setLocationRelativeTo(viewadm.getWindow());
                    dlg.setVisible(true);
                    dlg.requestFocus();
                }
                
                
            }
        }
    }
    
    public class EditDeleteDialogController implements ActionListener{
        EditDeleteDialogView EDview;
        Renter renter;
        ControllerAdmin parent;
        public EditDeleteDialogController(ControllerAdmin p,Renter r){
            this.EDview = new EditDeleteDialogView();
            this.EDview.getjButton1().addActionListener(this);
            this.EDview.getjButton2().addActionListener(this);
            this.renter = r;
            this.parent=p;
        }
        
        public EditDeleteDialogView getView(){
            return this.EDview;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==EDview.getjButton2()){
                try{
                    HelperRenter.deleteRenter(renter.getId());
                    HelperRoom.setEmptyRoom(renter.getRoom());
                    parent.updateTable();
                    parent.getDialog().dispose();
                }
                catch(SQLException ex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
                }
            }
            else{
                System.out.println(renter.getRoom());
                ControllerRenterData rdc = new ControllerRenterData(renter,parent);
                System.out.println("di klik");
                parent.getDialog().dispose();
            }
        }
        
    } 
}
