/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
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
import models.Room;
import views.RoomListView;
/**
 *
 * @author MSI MODERN
 */
public class ControllerRoomList implements ActionListener, ListSelectionListener{
    ControllerLogin loginctrl;
    RoomListView viewrm;
    public ControllerRoomList(ControllerLogin lgnctrl){
        this.loginctrl=lgnctrl;
        viewrm = new RoomListView();
        viewrm.getTabel().getSelectionModel().addListSelectionListener(this);
        viewrm.getBcancel().addActionListener(this);
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = HelperRoom.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            viewrm.getTabel().setModel(dtm);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewrm.getBcancel()){
           loginctrl.showPage(true);
           viewrm.dispose();
       }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            try{
                String name = viewrm.getTabel().getValueAt(viewrm.getTabel().getSelectedRow(), 0).toString();
                ControllerRenterData rdc = new ControllerRenterData(name,this);
                rdc.viewren.setOnTop(true);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                
            }
        }
    }
    
    public void updateTable(){
        viewrm.setTableModel(null);
        viewrm.getTabel().clearSelection();
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Mengembalikan nilai false untuk mencegah pengeditan sel
            }
        };
        dtm.addColumn("Name");
        dtm.addColumn("Size");
        dtm.addColumn("Price");
        dtm.addColumn("Status");
        
        List<Room> roomList = new ArrayList<>();
        
        try{
            roomList = HelperRoom.getRoomList();
            for(Room r :roomList){
                dtm.addRow(new Object[]{r.getNama(),r.getSize(),r.getPrice(),r.getStatus()});
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException ex){
            
        }
        finally{
            viewrm.getTabel().setModel(dtm);
        }
    }
}
