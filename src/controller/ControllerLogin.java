/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import models.Acc;
import views.LoginPageView;
import helper.AccHelper;
/**
 *
 * @author MSI MODERN
 */
public class ControllerLogin implements ActionListener {
    LoginPageView viewadm;
    public ControllerLogin(){
        viewadm = new LoginPageView();
        viewadm.getBlogin().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewadm.getBlogin()){
            
            Acc acc = new Acc();
            acc.setUsername(viewadm.getFusername().getText());
            acc.setPassword(viewadm.getFpassword().getText());
            try{
                AccHelper.LoginAccount(acc);
                if(acc.getRole()==null){
                    System.out.println("Akun tidak tersedia");
                }
                else if("ADMIN".equals(acc.getRole().toUpperCase())){
                    //navigate to admin page;
                    ControllerAdmin admin = new ControllerAdmin(this);
                    showPage(false);
                }
                else{
                    ControllerRoomList renter = new ControllerRoomList(this);
                    showPage(false);
                }
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void showPage(boolean stat){
        viewadm.setVisible(stat);
    }
    
}
