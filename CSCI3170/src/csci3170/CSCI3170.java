/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci3170;
import java.sql.*;
import java.text.DateFormat;
import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetTime;
import java.time.LocalDate;
import static java.time.OffsetTime.now;
import java.util.ArrayList;
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


    public static void MainMenu() {
        System.out.println("Welcome! Who are you?");
        System.out.println("1. An administator");
        System.out.println("2. An employee");
        System.out.println("3. An employer");
        System.out.println("4. Exit");
        System.out.println("Please enter [1-4]");

        userType = sc.nextInt();

        if (userType == 1) {
            //Adminstrator Menu
            AdminMenu();
        } else if (userType == 2) {
            //Employee Menu
            EmployeeMenu();
        } else if (userType == 3) {
            //Employer Menu
            EmployerMenu();
        } else if (userType == 4) {
            System.out.println("Exiting.");
            System.exit(0);
        } else {
            System.out.println("[ERROR] Invalid Input");
            MainMenu();

        }

    }

    public static void AdminMenu() {
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create tables");
        System.out.println("2. Delete tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");
        System.out.println("Please enter [1-5].");

        int AdminChoice = sc.nextInt();
        sc.nextLine();

        if (AdminChoice == 1) {
            createTables();
            AdminMenu();
        } else if (AdminChoice == 2) {
            deleteTables();
            AdminMenu();
        } else if (AdminChoice == 3) {
            loadData();
            AdminMenu();
        } else if (AdminChoice == 4) {
            checkTables();
            AdminMenu();
        } else if (AdminChoice == 5) {
            MainMenu();
        } else {
            System.out.println("[ERROR] Invalid Input");
            AdminMenu();
        }


    }

    public static void EmployeeMenu() {
        System.out.println("Employee, what would you like to do?");
        System.out.println("1. Show Available Positions");
        System.out.println("2. Mark Interested Positions");
        System.out.println("3. Check Average Working Time");
        System.out.println("4. Go back");
        System.out.println("Please enter [1-4].");

        int EChoice = sc.nextInt();
        sc.nextLine();

        if (EChoice == 1) {
            showPositions();
            EmployeeMenu();
        } else if (EChoice == 2) {
            markPositions();
            EmployeeMenu();
        } else if (EChoice == 3) {
            checkAverage();
            EmployeeMenu();
        } else if (EChoice == 4) {
            MainMenu();
        } else {
            System.out.println("[ERROR] Invalid Input");
            EmployeeMenu();
        }


    }

    public static void EmployerMenu() {
        System.out.println("Employer, what would you like to do?");
        System.out.println("1. Post Position Recruitment");
        System.out.println("2. Check employees and arrange an interview");
        System.out.println("3. Accept an employee");
        System.out.println("4. Go back");
        System.out.println("Please enter [1-4].");

        int EChoice = sc.nextInt();
        sc.nextLine();

        if (EChoice == 1) {
            postPosition();
            EmployerMenu();
        } else if (EChoice == 2) {
            checkAndInterview();
            EmployerMenu();
        } else if (EChoice == 3) {
            acceptEmployee();
            EmployerMenu();
        } else if (EChoice == 4) {
            MainMenu();
        } else {
            System.out.println("[ERROR] Invalid Input");
            EmployerMenu();
        }


    }

    //Adminstrator functions
    public static void createTables() // Creates new table and deletes older tables
    {
        try {
            // Dropping Tables. Keep the same order of statements due to participation constraints
            stm.executeUpdate("DROP TABLE IF EXISTS INTERVIEW");
            stm.executeUpdate("DROP TABLE IF EXISTS MARKED");
            stm.executeUpdate("DROP TABLE IF EXISTS POSITIONTABLE");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYER");
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
                    + "PRIMARY KEY (EMPLOYER_ID)"
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
                    + "PRIMARY KEY (POSITION_ID)"
                    + ")");

            //Create Employment_History Table
            stm.executeUpdate("CREATE TABLE EMPLOYMENT_HISTORY"
                    + "(EMPLOYEE_ID varchar(6) NOT NULL,"
                    + "COMPANY varchar(30) NOT NULL,"
                    + "POSITION_ID varchar(6) NOT NULL,"
                    + "START DATE,"
                    + "END DATE NULL,"
                    + "PRIMARY KEY(POSITION_ID))"
            );

            //Create Marked Table 
            stm.executeUpdate("CREATE TABLE MARKED"
                    + "( POSITION_ID varchar(6) NOT NULL,"
                    + "EMPLOYEE_ID varchar(6) NOT NULL,"
                    + "STATUS boolean,"
                    + "PRIMARY KEY(POSITION_ID, EMPLOYEE_ID))"
            );

            //Create Interview records table
            stm.executeUpdate("CREATE TABLE INTERVIEW"
                    + "( EMPLOYEE_ID varchar(6) NOT NULL,"
                    + "EMPLOYER_ID varchar(6) NOT NULL,"
                    + "POSITION_ID varchar(6) NOT NULL,"
                    + "PRIMARY KEY(EMPLOYEE_ID, POSITION_ID))"
            );

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void loadData() {
        System.out.println("Please enter the folder path: ");
        String Path = sc.nextLine();

        String files[] = {"employee.csv", "company.csv", "employer.csv", "position.csv", "history.csv"};

        for (int i = 0; i <= 4; i++) {
            String FilePath = Path + files[i];
            BufferedReader buffer = null;

            try {
                buffer = new BufferedReader(new FileReader(FilePath));
                String line;

                while ((line = buffer.readLine()) != null) {
                    String data[] = line.split(",");

                    if (i == 0) {
                        data[0] = "'" + data[0] + "'";
                        data[1] = "'" + data[1] + "'";
                        data[4] = "'" + data[4] + "'";

                        try {
                            stm.executeUpdate("INSERT INTO EMPLOYEES VALUES(" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("EMPLOYEES error");

                        }


                    } else if (i == 1) {
                        data[0] = "'" + data[0] + "'";


                        try {
                            stm.executeUpdate("INSERT INTO COMPANY VALUES(" + data[0] + "," + data[1] + "," + data[2] + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("COMPANY error");
                        }
                    } else if (i == 2) {
                        data[0] = "'" + data[0] + "'";
                        data[1] = "'" + data[1] + "'";
                        data[2] = "'" + data[2] + "'";

                        try {
                            stm.executeUpdate("INSERT INTO EMPLOYER VALUES(" + data[0] + "," + data[1] + "," + data[2] + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("EMPLOYER error");
                        }
                    } else if (i == 3) {
                        data[0] = "'" + data[0] + "'";
                        data[1] = "'" + data[1] + "'";
                        data[4] = "'" + data[4] + "'";


                        try {
                            stm.executeUpdate("INSERT INTO POSITIONTABLE VALUES(" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("Position error");
                        }
                    } else if (i == 4) {
                        data[0] = "'" + data[0] + "'";
                        data[1] = "'" + data[1] + "'";
                        data[2] = "'" + data[2] + "'";
                        data[3] = "'" + data[3] + "'";
                        if (data[4].equals("NULL")) {
                            data[4] = data[4];
                        } else {
                            data[4] = "'" + data[4] + "'";
                        }


                        try {
                            stm.executeUpdate("INSERT INTO EMPLOYMENT_HISTORY VALUES(" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println("EMPLOYEMENT_HISTORY error");
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteTables() {
        try {
            // Dropping Tables. Keep the same order of statements due to participation constraints
            stm.executeUpdate("DROP TABLE IF EXISTS INTERVIEW");
            stm.executeUpdate("DROP TABLE IF EXISTS MARKED");
            stm.executeUpdate("DROP TABLE IF EXISTS POSITIONTABLE");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYER");
            stm.executeUpdate("DROP TABLE IF EXISTS COMPANY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYMENT_HISTORY");
            stm.executeUpdate("DROP TABLE IF EXISTS EMPLOYEES");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void checkTables() {
        System.out.println("Number of records in each table: ");

        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet result = meta.getTables(null, null, "%", null);

            while (result.next()) {
                String TableName = result.getString(3);
                ResultSet count = stm.executeQuery("SELECT COUNT(*) FROM " + TableName);
                while (count.next() && (TableName.equals("EMPLOYEES") || TableName.equals("EMPLOYER") || TableName.equals("EMPLOYMENT_HISTORY") || TableName.equals("POSITIONTABLE") || TableName.equals("COMPANY") || TableName.equals("MARKED") || TableName.equals("INTERVIEW"))) {
                    System.out.println(TableName + ":" + count.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Employee functions
    public static void showPositions() {
        System.out.println("Please enter your Employee ID.");
        String Emp_ID = sc.nextLine();

        System.out.println("Your available positions are: ");
        String query = "SELECT p.Position_ID,p.Position_Title,p.Salary, em.Company,c.Size,c.Founded From POSITIONTABLE p, EMPLOYER em,EMPLOYEES e, COMPANY c WHERE p.Employer_ID = em.Employer_ID AND em.Company = c.Company AND p.Status=True AND e.EMPLOYEE_ID=? AND e.skills LIKE CONCAT ('%',p.Position_Title,'%') AND p.Salary >= e.Expected_Salary AND e.Experience >= p.Experience;";

        try {
            pstm = con.prepareStatement(query);
            pstm.setString(1, Emp_ID);
            ResultSet result = pstm.executeQuery();

            System.out.println("Position_ID, Position_Title, Salary, Company, Size, Founded");
            while (result.next()) {
                System.out.print(result.getString(1) + ", ");
                System.out.print(result.getString(2) + ", ");
                System.out.print(result.getInt(3) + ", ");
                System.out.print(result.getString(4) + ", ");
                System.out.print(result.getInt(5) + ", ");
                System.out.println(result.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void markPositions() {
        ArrayList<String> validList = new ArrayList<>();
        System.out.println("Please enter your Employee ID.");
        String Emp_ID = sc.nextLine();

        System.out.println("Your interested positions are: ");
        String query = "SELECT DISTINCT p.Position_ID,p.Position_Title,p.Salary, em.Company,c.Size,c.Founded From POSITIONTABLE p, EMPLOYER em,EMPLOYEES e, COMPANY c, EMPLOYMENT_HISTORY eh WHERE p.Employer_ID = em.Employer_ID AND em.Company = c.Company AND p.Status=True AND e.EMPLOYEE_ID=? AND e.skills LIKE CONCAT ('%',p.Position_Title,'%') AND p.Salary >= e.Expected_Salary AND e.Experience >= p.Experience AND NOT EXISTS (SELECT * FROM POSITIONTABLE p2, MARKED m2 WHERE p.Position_ID = p2.Position_ID AND p2.Position_ID = m2.Position_ID and m2.Employee_ID = ?) AND em.Company NOT IN (SELECT eh2.Company FROM EMPLOYMENT_HISTORY eh2 WHERE eh2.Employee_ID = ?);";

        try {
            pstm = con.prepareStatement(query);
            pstm.setString(1, Emp_ID);
            pstm.setString(2, Emp_ID);
            pstm.setString(3, Emp_ID);
            ResultSet result = pstm.executeQuery();

            System.out.println("Position_ID, Position_Title, Salary, Company, Size, Founded");
            while (result.next()) {
                validList.add(result.getString(1));
                System.out.print(result.getString(1) + ", ");
                System.out.print(result.getString(2) + ", ");
                System.out.print(result.getInt(3) + ", ");
                System.out.print(result.getString(4) + ", ");
                System.out.print(result.getInt(5) + ", ");
                System.out.println(result.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


        System.out.println("Please enter one interested Position_ID.");
        String Pos_ID = sc.nextLine();

        if (validList.contains(Pos_ID)) {
            try {
                String query2 = "INSERT INTO MARKED VALUES (?,?,FALSE)";
                pstm = con.prepareStatement(query2);
                pstm.setString(1, Pos_ID);
                pstm.setString(2, Emp_ID);
                pstm.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e);
            }

            System.out.println("Done!");

        } else {
            System.out.println("Invalid Input. You cannot mark this position");
            EmployeeMenu();
        }
    }

    public static void checkAverage() {
        System.out.println("Please enter your Employee ID.");
        String Emp_ID = sc.nextLine();

        
        String query = "SELECT DATEDIFF(END,START) as Days FROM EMPLOYMENT_HISTORY WHERE Employee_ID = ? AND END != 'NULL'";
        
        try 
        {
            int count =0;
            int sum = 0;
            int average;
            pstm = con.prepareStatement(query);
            pstm.setString(1, Emp_ID);
            ResultSet result = pstm.executeQuery();
            while(result.next())
            {
                count+=1;
                sum+= result.getInt(1);
            }
            
            if(count < 3)
            {
                System.out.println("Less than 3 records.");
            }
            else
            {
                average = sum/3;
                System.out.println("Your average working time is: " + average + " days.");
            }
            
        }
        catch(SQLException e)
        {
           System.out.println(e);
        }
    }


    //Employer functions

//    Generate a random alpha numeric string whose length is the number of characters specified.
//    Characters will be chosen from the set of alpha-numeric characters.
//    Count is the length of random string to create.

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static Integer checkEmployerId(String employer_id){
        String quary = "SELECT COUNT(1) from EMPLOYER WHERE EMPLOYER_ID = " + employer_id;
        Integer response = 0;
        try
        {
          ResultSet result = stm.executeQuery(quary);
          result.next();
          Integer exist = result.getInt(1);
          if(exist > 0){
              response = 1;
          }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return response;
    }


    public static Integer potentialEmployees(String skill, Integer salary, Integer experience){
        Integer count = 0;
        String quary = "SELECT Count(*) from EMPLOYEES e where e.Skills like '%" + skill + "%' and e.Experience >= " + experience + " and Expected_Salary <= " + salary + ";";
        try{
            ResultSet result = stm.executeQuery(quary);
            result.next();
            count = result.getInt(1);
        }
         catch(SQLException e){
            System.out.println(e);
         }
        return count;
    }

    public static void postPosition() {
        //Add code
        System.out.println("Please enter your ID.");
        String employer_id = sc.nextLine();
        employer_id = "'" + employer_id + "'";
        Integer exist = checkEmployerId(employer_id);
        if(exist == 1) {
            System.out.println("Please enter the position title.");
            String pos_title = sc.nextLine();
            String tmp_title = pos_title;
            pos_title = "'" + pos_title + "'";
            System.out.println("Please enter an upper bound of salary.");
            Integer salary = sc.nextInt();
            sc.nextLine();
            System.out.println("Please enter the required experience(press enter to skip)");
            String tmp = sc.nextLine();
            Integer experience = 0;
            if(!tmp.equals("")){
                experience = Integer.parseInt(tmp);
            }
            String pos_id = randomAlphaNumeric(3);
            pos_id = "'" + "pos" + pos_id + "'";
            Boolean status = true;

            String query = "INSERT INTO POSITIONTABLE VALUES(" + pos_id + "," + pos_title + "," + salary + "," + experience + "," + employer_id + "," + status + ")";
            String quary2 = "SELECT p.POSITION_ID, p.POSITION_TITLE, p.SALARY, p.EXPERIENCE, p.EMPLOYER_ID, p.STATUS FROM POSITIONTABLE p WHERE p.POSITION_ID = " + pos_id + " AND p.EMPLOYER_ID = " + employer_id;
            try {

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
                        " position_title: " + result.getString("POSITION_TITLE") +
                        " upper_salary: " + result.getInt("SALARY") +
                        " experience " + result.getInt("EXPERIENCE") +
                        " status: " + result.getBoolean("STATUS"));
                System.out.println(potentialEmployees(tmp_title, salary, experience) + " potential employees found.\n");

            } catch (SQLException e) {
                System.out.println(e);
            }

        }
        else{
            System.out.println("The Employer with that ID does not exist. Please enter another ID.");
            EmployerMenu();
        }
    }

    //This function is for printing the IDs of the positions posted by some employer. It takes the employer_id as an input.
    //Note that employer_id should be in SQL readable format
    public static Integer checkPostedPositions(String employer_id) {
        String quary1 = "SELECT p.POSITION_ID FROM POSITIONTABLE p WHERE p.EMPLOYER_ID =" + employer_id;
        Integer count = 0;
        try {
            ResultSet result1 = stm.executeQuery(quary1);
            System.out.println("The IDs of positions posted by you are:");
            while (result1.next()) {
                System.out.println(result1.getString("POSITION_ID"));
                count = count + 1;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }

    //This function prints the info who are interested in the particular position. It takes pos_id as an input


    public static Integer checkInterestedEmployees(String pos_id){

            String quary = "SELECT e.Employee_ID, e.Name, e.Expected_Salary, e.Experience, e.Skills FROM MARKED m, EMPLOYEES e WHERE e.Employee_ID = m.EMPLOYEE_ID AND m.POSITION_ID =" + pos_id;
            Integer count = 0;
            try {
                ResultSet result1 = stm.executeQuery(quary);
                System.out.println("The employees who marked interested in this position reqruitement are: ");
                System.out.println("Employee_ID, Name, Expected_Salary, Experience, Skills");
                while (result1.next()) {
                    System.out.println(result1.getString("Employee_ID") + ", " +
                                       result1.getString("Name") + ", " +
                                        result1.getString("Expected_Salary") + ", " +
                                        result1.getString("Experience") + ", " +
                                        result1.getString("Skills"));
                    count = count + 1;
                }
            } catch (SQLException e) {

                System.out.println(e);
            }

            return count;
                                                                                                                                                                                                             
    }

   //This function is for testing only. It shows whether checkAndInterview functionallity is working correctly.
    public static void successInterviewIndicator(String pos_id, String employee_id){

        String quary = "SELECT e.Employee_ID, e.Name, e.Expected_Salary, e.Experience, e.Skills FROM MARKED m, EMPLOYEES e WHERE e.Employee_ID = m.EMPLOYEE_ID AND m.POSITION_ID =" + pos_id + " AND e.Employee_ID=" + employee_id;



        try{
            ResultSet result = stm.executeQuery(quary);
            result.next();
            System.out.println("\nThe interview was done with the following employee: ");
            System.out.println("Employee_ID, Name, Expected_Salary, Experience, Skills\n");
            System.out.println(result.getString("Employee_ID") + ", " +
                    result.getString("Name") + ", " +
                    result.getString("Expected_Salary") + ", " +
                    result.getString("Experience") + ", " +
                    result.getString("Skills"));

        }

        catch(SQLException e){
            System.out.println(e);
        }

    }

    //This function is for testing only. It shows whether checkAndInterview functionality is working correctly. (It supplements the successInterviewIndicator()).

    public static void successInterviewIndicator2(String employee_id, String pos_id) {

       String quary3 = "SELECT i.EMPLOYEE_ID, i.EMPLOYER_ID, i.POSITION_ID FROM INTERVIEW i WHERE EMPLOYEE_ID = " + employee_id + " AND POSITION_ID = " + pos_id;


        try {
            ResultSet result3 = stm.executeQuery(quary3);
            result3.next();
            System.out.println("\nThis info was inserted in Interview table: ");
            System.out.println("EMPLOYEE_ID: " + result3.getString("EMPLOYEE_ID") +
                    " EMPLOYER_ID: " + result3.getString("EMPLOYER_ID") +
                    " POSITION_ID: " + result3.getString("POSITION_ID"));
        }
        catch(SQLException e){
            System.out.println(e);
        }


    }

    public static Integer checkExistence(String table_name, String col_name, String id){
        String quary = "SELECT COUNT(1) from " + table_name + " where " + col_name + " = " + id;
        Integer count = 0;
        try{
            ResultSet result = stm.executeQuery(quary);
            result.next();
            count = result.getInt(1);
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return count;
    }


    public static void checkAndInterview() {
        //Add code

        System.out.println("Please enter your ID.");
        String employer_id = sc.nextLine();
        employer_id = "'" + employer_id + "'";
        Integer count = checkPostedPositions(employer_id);
        if (count > 0) {
            System.out.println("Please pick one position id.");
            String pos_id = sc.nextLine();
            pos_id = "'" + pos_id + "'";
            int count1 = checkExistence("POSITIONTABLE", "POSITION_ID", pos_id);
            if (count1 > 0)  {
                Integer count2 = checkInterestedEmployees(pos_id);
            if (count2 > 0) {

                System.out.println("Please pick one employee by employee id.");
                String employee_id = sc.nextLine();
                employee_id = "'" + employee_id + "'";
                Integer count3 = checkExistence("EMPLOYEES", "Employee_ID", employee_id);
                if(count3 > 0) {

                    try {

                        String quary2 = "INSERT INTO INTERVIEW VALUES(" + employee_id + "," + employer_id + "," + pos_id + ")";

                        //quary3 is for testing only
                        stm.executeUpdate(quary2);

                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    successInterviewIndicator(pos_id, employee_id);
                    successInterviewIndicator2(employee_id, pos_id);
                }
                else{
                    System.out.println("That Employee_ID is not on the list.");
                    EmployerMenu();
                }

            } else {
                System.out.println("No one is interested in this position.\n");
                EmployerMenu();
            }

//            "CREATE TABLE MARKED"
//                                +"( POSITION_ID varchar(6) NOT NULL,"
//                                + "EMPLOYEE_ID varchar(6) NOT NULL,"
//                                + "STATUS boolean,"
//                                + "PRIMARY KEY(POSITION_ID, EMPLOYEE_ID))"
//           "CREATE TABLE EMPLOYEES"
//                               + "(Employee_ID varchar(6) NOT NULL,"
//                               + "Name varchar(30) NOT NULL,"
//                               + "Expected_Salary integer NOT NULL CHECK (Expected_Salary > -1),"
//                               + "Experience integer NOT NULL CHECK (Experience > -1),"
//                               + "Skills varchar(50),"
//                               + "PRIMARY KEY (EMPLOYEE_ID)"
//                               + ")"

        }
            else{
                System.out.println("That position is not on the list.");
                EmployerMenu();
            }
        }
        else
        {
            System.out.println("You haven't posted any position.");
            EmployerMenu();
        }

    }
   //This function takes employer_id as an input and returns the Company he/she is from.
    public static  String employerCompany(String employer_id){
        String quary = "SELECT e.COMPANY FROM EMPLOYER e WHERE EMPLOYER_ID = " + employer_id;
        String str = "";
        try{
             ResultSet result = stm.executeQuery(quary);
             result.next();
             str = result.getString(1);
        }
        catch(SQLException e){
             System.out.println(e);
        }
             return str;
    }
//
//       stm.executeUpdate("CREATE TABLE EMPLOYMENT_HISTORY"
//               + "(EMPLOYEE_ID varchar(6) NOT NULL,"
//               + "COMPANY varchar(30) NOT NULL,"
//               + "POSITION_ID varchar(6) NOT NULL,"
//               + "START DATE,"
//               + "END DATE NULL,"
//               + "PRIMARY KEY(POSITION_ID))"
//       );
//

    //This function inserts the new row to EMPLOYMENT_HISTORY table
    public static void insertHistoryAndUpdatePos(String employee_id, String company, String pos_id){


          LocalDate today = LocalDate.now();


        


        String quary3 = "INSERT INTO EMPLOYMENT_HISTORY VALUES(" + employee_id + ", " + company + ", " + pos_id + ", "
                                                                + "CURDATE()" + ", "+ null + ")";

        String quary2 = " update EMPLOYMENT_HISTORY SET END = CURDATE() WHERE EMPLOYEE_ID =" + employee_id + " and END IS NULL;";

        String quary1 = "UPDATE POSITIONTABLE SET STATUS = (1=1) where POSITION_ID = (SELECT e.POSITION_ID FROM EMPLOYMENT_HISTORY e WHERE EMPLOYEE_ID =" + employee_id + "and END IS NULL);";
        String quary4 = "UPDATE POSITIONTABLE SET STATUS = (1=0) where POSITION_ID = " + pos_id + ";";
        try
        {   stm.executeUpdate(quary1);
            stm.executeUpdate(quary2);
            stm.executeUpdate(quary3);
            stm.executeUpdate(quary4);
            System.out.println("An Employment History record is created., details are:");
            System.out.println("Employee_id, Company, Position_ID, Start, End");
            System.out.println(employee_id + ", " + company + ", " + pos_id + ", " + today + ", " + "NULL");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }


    }




    public static void acceptEmployee() {
        //Add code
        System.out.println("Please enter your ID.");
        String employer_id = sc.nextLine();
        employer_id = "'" + employer_id + "'";
        Integer count2 = checkExistence("EMPLOYER", "EMPLOYER_ID", employer_id);
        if (count2 > 0) {
            System.out.println("Please enter the  Employer_ID you want to hire.");
            String employee_id = sc.nextLine();
            employee_id = "'" + employee_id + "'";

            Integer count1 = checkExistence("INTERVIEW", "EMPLOYEE_ID", employee_id);
            if(count1 > 0) {
                String quary = "SELECT POSITION_ID FROM INTERVIEW WHERE EMPLOYER_ID = " + employer_id + " AND EMPLOYEE_ID = " + employee_id;
                try {
                    ResultSet count = stm.executeQuery(quary);
                    count.next();
                    String pos_id = count.getString(1);
                    if (!pos_id.isEmpty()) {
                        String company = "'" + employerCompany(employer_id) + "'";
                        pos_id = "'" + pos_id + "'";
                        insertHistoryAndUpdatePos(employee_id, company, pos_id);
                    } else {
                        System.out.println("You didn't interview the following employee");
                        EmployerMenu();
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            else{
                System.out.println("You haven't interviewed this Employee yet.");
                EmployerMenu();
            }
        }
        else{
            System.out.println("Wrong Employer_ID");
            EmployerMenu();
        }

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
