/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci3170;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetTime;
import static java.time.OffsetTime.now;
/**
 *
 * @author mudit
 */
public class CSCI3170 {

    /**
     * @param args the command line arguments
     */
    
    static int userType;
    static Scanner sc = new Scanner(System.in);
    static Connection con = null;
    static Statement stm;
    static PreparedStatement pstm;
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        String dbAddress =  "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db30";
        String dbUsername = "Group30";
        String dbPassword = "CSCI3170";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            stm = con.createStatement();
            
            System.out.println("Lolol");
            //Add entry point for the program
                   
        }
        catch (ClassNotFoundException e){
            System.out.println("[Error]: Java MySQL DB DRIVER not found!!");
            System.exit(0);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
                
    }
    
   
}
