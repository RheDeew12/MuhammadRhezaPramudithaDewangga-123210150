/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import models.Acc;
import java.sql.*;
/**
 *
 * @author MSI MODERN
 */
public class AccHelper {
    Acc acc;
    
    public static void LoginAccount(Acc acc) throws SQLException{
        KoneksiDB db = new KoneksiDB();
        
        String query = "select * from accounts where username = \""+acc.getUsername()+"\" and password=\""+acc.getPassword()+"\"";
        try{
            ResultSet rs =  db.getStatement().executeQuery(query);
            if(rs.next()){
                acc.setRole(rs.getString("role"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            db.closeConnection();
        }
    }
}
