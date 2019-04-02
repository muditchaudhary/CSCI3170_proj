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
    
    
    public static void MainMenu()
    {
        System.out.println("Welcome! Who are you?");
        System.out.println("1. An administator");
        System.out.println("2. An employee");
        System.out.println("3. An employer");
        System.out.println("4. Exit");
        System.out.println("Please enter [1-4]");
        
        userType = sc.nextInt();
        
        if(userType == 1)
        {
            //Adminstrator Menu
            AdminMenu();
        }
        else if(userType == 2)
        {
            //Employee Menu
        }
        else if (userType == 3)
        {
            //Employer Menu
        }
        else if (userType == 4)
        {
            System.out.println("Exiting.");
            System.exit(0);
        }
        else
        {
            System.out.println("[ERROR] Invalid Input");
            MainMenu();
                    
        }
        
    }
    
    public static void AdminMenu()
    {
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create tables");
        System.out.println("2. Delete tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");
        System.out.println("Please enter [1-5].");
        
        int AdminChoice = sc.nextInt();
        //sc.nextLine();
        
        if(AdminChoice == 1)
        {
           //createTables();
            AdminMenu();
        }
        
        else if (AdminChoice == 2)
        {
            //deleteTables();
            AdminMenu();            
        }
        else if(AdminChoice == 3)
        {
            //loadData();
            AdminMenu();
        }
        else if (AdminChoice == 4)
        {
            //checkData();
            AdminMenu();
        }
        else if (AdminChoice == 5)
        {
            MainMenu();
        }
        else
        {
            System.out.println("[ERROR] Invalid Input");
            AdminMenu();
        }
        
        
        
    }
    
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
