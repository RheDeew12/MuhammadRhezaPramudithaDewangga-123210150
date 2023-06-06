/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/**
 *
 * @author MSI MODERN
 */
public class KoneksiDB {
    private Connection koneksiDB;
    private Statement  stmnt;
    public KoneksiDB(){
        try{
            koneksiDB = DriverManager.getConnection("jdbc:mysql://localhost/al-maulpbo", "root", "");
            stmnt=koneksiDB.createStatement();
        }
        catch(SQLException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Statement getStatement(){
        return this.stmnt;
    }
    public void closeConnection() throws SQLException{
        this.koneksiDB.close();
    }
}
