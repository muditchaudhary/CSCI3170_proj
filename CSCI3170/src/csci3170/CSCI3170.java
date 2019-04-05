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
            EmployeeMenu();
        }
        else if (userType == 3)
        {
            //Employer Menu
            EmployerMenu();
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
            checkTables();
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
    
    public static void EmployeeMenu()
    {
        System.out.println("Employee, what would you like to do?");
        System.out.println("1. Show Available Positions");
        System.out.println("2. Mark Interested Positions");
        System.out.println("3. Check Average Working Time");
        System.out.println("4. Go back");
        System.out.println("Please enter [1-4].");
        
        int EChoice = sc.nextInt();
        sc.nextLine();
        
        if(EChoice == 1)
        {
            showPositions();
            EmployeeMenu();
        }
        
        else if (EChoice == 2)
        {
            markPositions();
            EmployeeMenu();            
        }
        else if(EChoice == 3)
        {
            checkAverage();
            EmployeeMenu();
        }

        else if (EChoice == 4)
        {
            MainMenu();
        }
        else
        {
            System.out.println("[ERROR] Invalid Input");
            EmployeeMenu();
        }
        
        
        
    }
    
    public static void EmployerMenu()
    {
        System.out.println("Employer, what would you like to do?");
        System.out.println("1. Post Position Recruitment");
        System.out.println("2. Check employees and arrange an interview");
        System.out.println("3. Accept an employee");
        System.out.println("4. Go back");
        System.out.println("Please enter [1-4].");
        
        int EChoice = sc.nextInt();
        sc.nextLine();
        
        if(EChoice == 1)
        {
            postPosition();
            EmployerMenu();
        }
        
        else if (EChoice == 2)
        {
            checkAndInterview();
            EmployerMenu();            
        }
        else if(EChoice == 3)
        {
            acceptEmployee();
            EmployerMenu();
        }

        else if (EChoice == 4)
        {
            MainMenu();
        }
        else
        {
            System.out.println("[ERROR] Invalid Input");
            EmployerMenu();
        }
        
        
        
    }
    
    //Adminstrator functions
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
        System.out.println("Number of records in each table: ");
        
        try
        {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet result = meta.getTables(null, null, "%", null);
            
            while(result.next())
            {
                String TableName = result.getString(3);
                ResultSet count = stm.executeQuery("SELECT COUNT(*) FROM "+ TableName);
                while(count.next() && (TableName.equals("EMPLOYEES") || TableName.equals("EMPLOYER") || TableName.equals("EMPLOYMENT_HISTORY") || TableName.equals("POSITIONTABLE")|| TableName.equals("COMPANY") || TableName.equals("MARKED")) )
                {
                    System.out.println(TableName+":"+count.getInt(1));
                }
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //Employee functions
    public static void showPositions()
    {
        System.out.println("Please enter your Employee ID.");
        String Emp_ID = sc.nextLine();
        
        String query = "SELECT p.Position_ID,p.Position_Title,p.Salary, em.Company,c.Size,c.Founded From POSITIONTABLE p, EMPLOYER em,EMPLOYEES e, COMPANY c WHERE p.Employer_ID = em.Employer_ID AND em.Company = c.Company AND p.Status=True AND e.EMPLOYEE_ID=? AND e.skills LIKE CONCAT ('%',p.Position_Title,'%') AND p.Salary >= e.Expected_Salary AND e.Experience >= p.Experience;";
        
        try
        {
            pstm = con.prepareStatement(query);
            pstm.setString(1, Emp_ID);
            ResultSet result = pstm.executeQuery();
            
            System.out.println("Position_ID, Position_Title, Salary, Company, Size, Founded");
            while(result.next())
            {
                System.out.print(result.getString(1)+", ");
                System.out.print(result.getString(2)+", ");
                System.out.print(result.getInt(3)+", ");
                System.out.print(result.getString(4)+", ");
                System.out.print(result.getInt(5)+", ");
                System.out.println(result.getInt(6));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
    }
    
    public static void markPositions()
    {
        //Add Code
    }
    
    public static void checkAverage()
    {
        //Add code
    }
    
    
    //Employer functions

//    Generate a random alpha numeric string whose length is the number of characters specified.
//    Characters will be chosen from the set of alpha-numeric characters.
//    Count is the length of random string to create.

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static String randomAlphaNumeric(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
    int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
    builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
    }






    public static void postPosition()
    {
        //Add code
        System.out.println("Please enter your ID.");
        String employer_id = sc.nextLine();
        employer_id = "'" + employer_id + "'";
        System.out.println("Please enter the position title.");
        String pos_title = sc.nextLine();
        pos_title = "'" + pos_title + "'";
        System.out.println("Please enter an upper bound of salary.");
        Integer salary = sc.nextInt();
        System.out.println("Please enter the required experience(press enter to skip)");
        Integer experience = sc.nextInt();
        String pos_id = randomAlphaNumeric(6);
        pos_id = "'" + pos_id + "'";
        Boolean status = true;

        String query = "INSERT INTO POSITIONTABLE VALUES("+pos_id+","+ pos_title+","+salary+","+experience+","+employer_id+","+status+")";
        String quary2 = "SELECT p.POSITION_ID, p.POSITION_TITLE, p.SALARY, p.EXPERIENCE, p.EMPLOYER_ID, p.STATUS FROM POSITIONTABLE p WHERE p.POSITION_ID = " + pos_id + " AND p.EMPLOYER_ID = " + employer_id;
        try
        {

//"CREATE TABLE POSITIONTABLE"
//                    + "(POSITION_ID varchar(6) NOT NULL,"
//                    + "POSITION_TITLE varchar(30) NOT NULL,"
//                    + "SALARY integer CHECK (SALARY > -1),"
//                    + "EXPERIENCE integer CHECK (EXPERIENCE >-1),"
//                    + "EMPLOYER_ID varchar(6) NOT NULL,"
//                    + "STATUS boolean,"
//                    + "PRIMARY KEY (POSITION_ID),"
//                    + "CONSTRAINT fk_pos_empr FOREIGN KEY (EMPLOYER_ID) REFERENCES EMPLOYER(EMPLOYER_ID) ON DELETE CASCADE"
//                    + ")"


            stm.executeUpdate(query);
            ResultSet result = stm.executeQuery(quary2);
            result.next();

            System.out.println("Position added with position ID: " + result.getString("POSITION_ID") +
                                                    " position title: " + result.getString("POSITION_TITLE") +
                                                    " upper_salary: " + result.getInt("SALARY") +
                                                    " experience " + result.getInt("EXPERIENCE") +
                                                    " status: " + result.getBoolean("STATUS"));

        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static void checkAndInterview()    
    {
        //Add code

    }
    
    public static void acceptEmployee()
    {
        //Add code
    }
    
    //Main function
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
