/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.HelperRenter;
import helper.HelperRoom;
import models.Renter;
import views.RenterDataView;
/**
 *
 * @author MSI MODERN
 */
public class ControllerRenterData implements ActionListener{
    Renter rent;
    RenterDataView viewren;
    ControllerAdmin cntrladm;
    String selectRoom;
    public ControllerRenterData(Renter rtr, ControllerAdmin parent){
        this.rent=rtr;
        this.cntrladm = parent;
        viewren = new RenterDataView();
        viewren.setOnTop(true);
        viewren.setTfContact(rent.getContact());
        viewren.setTfName(rent.getNama());
        viewren.setTfRentTime(String.valueOf(rent.getDuration()));
        viewren.setTfid(String.valueOf(rent.getId()));
        
        //add listener
        viewren.getBtnAddPanel().addActionListener(this);
        viewren.getBtnLogout().setVisible(false);
    }
    
    ControllerRoomList rlc;
    public ControllerRenterData(String room, ControllerRoomList parent){
        viewren = new RenterDataView();
        this.selectRoom=room;
        this.rlc = parent;
        viewren.getBtnAddPanel().addActionListener(this);
        viewren.getBtnLogout().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewren.getBtnAddPanel()){
            if(viewren.getBtnLogout().isVisible()==true){
                System.out.println("added clicked");
                Renter renter = new Renter();
                renter.setNama(viewren.getTfName().getText());
                renter.setContact(viewren.getTfContact().getText());
                renter.setDuration(Integer.parseInt(viewren.getTfRentTime().getText().toString()));
                renter.setId(Integer.parseInt(viewren.getTfid().getText().toString()));
                renter.setRoom(selectRoom);
                rlc.updateTable();
                try {
                    HelperRenter.addRenter(renter);
                    HelperRoom.updateRoom(renter.getRoom(),renter.getNama());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                finally{
                    viewren.getWindow().dispose();
                }
            }
            else{
                rent.setContact(viewren.getTfContact().getText());
                rent.setId(Integer.parseInt(viewren.getTfid().getText()));
                rent.setDuration(Integer.parseInt(viewren.getTfRentTime().getText()));
                rent.setNama(viewren.getTfName().getText());
                try {
                    rent.setBill(rent.calculateTotalBill(HelperRoom.getPrice(rent.getRoom())));
                    HelperRenter.updateRenter(rent);
                    viewren.getWindow().dispose();
                    cntrladm.updateTable();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerRenterData.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch(ArrayIndexOutOfBoundsException aex){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,aex.getCause()+"\n"+aex.getMessage());
                }
            }
        }
        else if(e.getSource()==viewren.getBtnLogout()){
            viewren.getWindow().dispose();
        }
    }
}
