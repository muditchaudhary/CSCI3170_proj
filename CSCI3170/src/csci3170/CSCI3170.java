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
        sc.nextLine();
        
        if(AdminChoice == 1)
        {
           createTables();
            AdminMenu();
        }
        
        else if (AdminChoice == 2)
        {
            deleteTables();
            AdminMenu();            
        }
        else if(AdminChoice == 3)
        {
            loadData();
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
    
    public static void createTables() // Creates new table and deletes older tables
    {
        try{
            // Dropping Tables. Keep the same order of statements due to participation constraints
            stm.executeUpdate("DROP TABLE IF EXISTS MARKED");
            stm.executeUpdate("DROP TABLE IF EXISTS POSITIONTABLE");
            stm.executeUpdate ("DROP TABLE IF EXISTS EMPLOYER");
            stm.executeUpdate("DROP TABLE IF EXISTS COMPANY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYMENT_HISTORY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES");
            
            
            //Creating Employees table
            stm.executeUpdate("CREATE TABLE EMPLOYEES"
                    + "(Employee_ID varchar(6) NOT NULL,"
                    + "Name varchar(30) NOT NULL,"
                    + "Expected_Salary integer NOT NULL CHECK (Expected_Salary > -1),"
                    + "Experience integer NOT NULL CHECK (Experience > -1)," 
                    + "Skills varchar(50),"
                    + "PRIMARY KEY (EMPLOYEE_ID)"
                    + ")");
            
            
            
            //Creating Company Table
            stm.executeUpdate("CREATE TABLE COMPANY"
                    + "(COMPANY varchar(30) NOT NULL,"
                    + "Size integer CHECK (Size > -1),"
                    + "Founded integer(4),"
                    + "PRIMARY KEY (COMPANY)"
                    + ")");
            
            //Create Employer Table
            stm.executeUpdate("CREATE TABLE EMPLOYER"
                    + "(EMPLOYER_ID varchar(6) NOT NULL,"
                    + "NAME varchar(30) NOT NULL,"
                    + "COMPANY varchar(30) NOT NULL,"
                    + "PRIMARY KEY (EMPLOYER_ID),"
                    + "CONSTRAINT fk_empr_cmp FOREIGN KEY (COMPANY) REFERENCES COMPANY(COMPANY) ON DELETE CASCADE"
                    + ")");
            
            //Create Position Table
             //Named it POSITIONTABLE because naming POSITION gave error 
            stm.executeUpdate("CREATE TABLE POSITIONTABLE"
                    + "(POSITION_ID varchar(6) NOT NULL,"
                    + "POSITION_TITLE varchar(30) NOT NULL,"
                    + "SALARY integer CHECK (SALARY > -1),"
                    + "EXPERIENCE integer CHECK (EXPERIENCE >-1),"
                    + "EMPLOYER_ID varchar(6) NOT NULL,"
                    + "STATUS boolean,"
                    + "PRIMARY KEY (POSITION_ID),"
                    + "CONSTRAINT fk_pos_empr FOREIGN KEY (EMPLOYER_ID) REFERENCES EMPLOYER(EMPLOYER_ID) ON DELETE CASCADE"
                    + ")");
            
            //Create Employment_History Table
            stm.executeUpdate("CREATE TABLE EMPLOYMENT_HISTORY"
                    + "(EMPLOYEE_ID varchar(6) NOT NULL,"
                    + "COMPANY varchar(30) NOT NULL,"
                    + "POSITION_ID varchar(6) NOT NULL,"
                    + "START DATE,"
                    + "END DATE NULL,"
                    + "PRIMARY KEY(POSITION_ID),"
                    + "CONSTRAINT fk_his_empe FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES(EMPLOYEE_ID) ON DELETE CASCADE)");
            
            //Create Marked Table 
            stm.executeUpdate("CREATE TABLE MARKED"
                    +"(EMPLOYEE_ID varchar(6) NOT NULL,"
                    + "POSITION_ID varchar(6) NOT NULL," 
                    + "STATUS boolean,"
                    + "PRIMARY KEY(POSITION_ID, EMPLOYEE_ID),"
                    + "CONSTRAINT fk_mar_empe FOREIGN KEY(EMPLOYEE_ID) REFERENCES EMPLOYEES(EMPLOYEE_ID) ON DELETE CASCADE,"
                    + "CONSTRAINT fk_mar_pos FOREIGN KEY(POSITION_ID) REFERENCES POSITIONTABLE(POSITION_ID) ON DELETE CASCADE)");
                   
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static void loadData()
    {
        System.out.println("Please enter the folder path: ");
        String Path = sc.nextLine();
        
        String files[] = {"employee.csv", "company.csv", "employer.csv" , "position.csv" , "history.csv"};
                
        for(int i = 0; i<=4;i++)
        {
            String FilePath = Path + files[i];
            BufferedReader buffer = null;
            
            try
            {
                buffer = new BufferedReader(new FileReader(FilePath));
                String line;
                
                while((line =buffer.readLine())!=null)
                {
                    String data[] = line.split(",");
                    
                    if(i==0)
                    {
                        data[0] = "'"+data[0]+"'";
                        data[1] = "'"+data[1]+"'";
                        data[4] = "'"+data[4]+"'";
                        
                        try
                        {
                            stm.executeUpdate("INSERT INTO EMPLOYEES VALUES("+data[0]+","+ data[1]+","+data[2]+","+data[3]+","+data[4]+")");
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);
                            System.out.println("EMPLOYEES error");
                            
                        }
                        
                        
                    }
                    else if(i==1)
                    {
                        data[0] = "'"+data[0]+"'";
              
                        
                        try
                        {
                            stm.executeUpdate("INSERT INTO COMPANY VALUES("+data[0]+","+ data[1]+","+data[2]+")");
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);
                            System.out.println("COMPANY error");
                        }
                    }
                    else if (i==2)
                    {
                        data[0] = "'"+data[0]+"'";
                        data[1] = "'"+data[1]+"'";
                        data[2] = "'"+data[2]+"'";
                        
                        try
                        {
                            stm.executeUpdate("INSERT INTO EMPLOYER VALUES("+data[0]+","+ data[1]+","+data[2]+")");
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);
                            System.out.println("EMPLOYER error");
                        }
                    }
                    else if(i==3)
                    {
                        data[0] = "'"+data[0]+"'";
                        data[1] = "'"+data[1]+"'";
                        data[4] = "'"+data[4]+"'";
                        
                        
                        try
                        {
                            stm.executeUpdate("INSERT INTO POSITIONTABLE VALUES("+data[0]+","+ data[1]+","+data[2]+","+data[3]+","+data[4]+","+data[5]+")");
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);
                            System.out.println("Position error");
                        }
                    }
                    else if(i==4)
                    {
                        data[0] = "'"+data[0]+"'";
                        data[1] = "'"+data[1]+"'";
                        data[2] = "'"+data[2]+"'";
                        data[3] = "'"+data[3]+"'";
                        if(data[4].equals("NULL"))
                        {
                            data[4]=data[4];
                        }
                        else
                        {
                            data[4] = "'"+data[4]+"'";
                        }
                        
                        
                        
                        try
                        {
                            stm.executeUpdate("INSERT INTO EMPLOYMENT_HISTORY VALUES("+data[0]+","+ data[1]+","+data[2]+","+data[3]+","+data[4]+")");
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);
                            System.out.println("EMPLOYEMENT_HISTORY error");
                        }
                    }
                    
                }
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
    }
    
    public static void deleteTables()
    {
        try
        {
            // Dropping Tables. Keep the same order of statements due to participation constraints
            stm.executeUpdate("DROP TABLE IF EXISTS MARKED");
            stm.executeUpdate("DROP TABLE IF EXISTS POSITIONTABLE");
            stm.executeUpdate ("DROP TABLE IF EXISTS EMPLOYER");
            stm.executeUpdate("DROP TABLE IF EXISTS COMPANY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYMENT_HISTORY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static void checkTables()
    {
        
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
            
           MainMenu();
            
                   
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
