/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import models.Room;
/**
 *
 * @author MSI MODERN
 */
public class HelperRoom {
    public static List<Room> getRoomList() throws SQLException{
        KoneksiDB db = new KoneksiDB();
        List<Room> rooms = new ArrayList<>();
        String query = " select * from rooms where status=\"empty\"";
        try{
            ResultSet rs = db.getStatement().executeQuery(query);
            while(rs.next()){
                Room room=new Room();
                room.setNama(rs.getString("name"));
                room.setPrice(rs.getDouble("price"));
                room.setSize(rs.getString("size"));
                room.setStatus(rs.getString("status"));
                rooms.add(room);
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
            return rooms;
        }
    }
    
    public static void updateRoom(String room,String namaRenter)throws SQLException{
        KoneksiDB db = new KoneksiDB();
        String query = "update rooms set status = \""+namaRenter+"\" where name = \""+room+"\"";
        try{
            db.getStatement().executeUpdate(query);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
        }
    }
    
    public static double getPrice(String room) throws SQLException{
        KoneksiDB db = new KoneksiDB();
        String query = "select price from rooms where name = \""+room+"\"";
        double re=0;
        try{
            ResultSet rs = db.getStatement().executeQuery(query);
            if(rs.next()){
                re=Double.parseDouble(rs.getString("price"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
            return re;
        }
    }
    
    public static void setEmptyRoom(String roomName) throws SQLException{
        KoneksiDB db = new KoneksiDB();
        String query = "update rooms set status = \"empty\" where name = \""+roomName+"\"";
        try{
            db.getStatement().executeUpdate(query);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
        }
    }
}
