/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci3170proj;
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
 * @author Aman
 */
public class Csci3170proj {

     static int who;
     static Scanner sc = new Scanner(System.in);
     static Connection con = null;
     static Statement stmt;
     static PreparedStatement pstmt;
     static int requestID = 0;
     static String timestamp1;
     
    public static void menuPrinter() // prints the base menu for admin, passenger and driver {error handling done}
    {
                System.out.println("Welcome! who are you?");
                System.out.println("1. An administrator");
                System.out.println("2. A passenger");
                System.out.println("3. A driver");
                System.out.println("4. None of the above");
                System.out.println("Please enter [1-4].");
                
                who = sc.nextInt();
                if(who==1)
                {
                    adminMenu();
                }
                else if(who==2)
                {
                    passengerMenu();
                }
                else if(who==3)
                {
                    driverMenu();
                }
                else if(who==4)
                {
                    System.out.println("You cannot enter the system. Exitting");
                    System.exit(0);
                }
                else
                {
                    System.out.println("[ERROR] Invalid Input");
                    menuPrinter();
                }
    }
    
    public static void  adminMenu() //prints the menu for administrators with all the functions they can use {error handling done}
    {
                System.out.println("Administrator, what would you like to do?");
                System.out.println("1. Create tables");
                System.out.println("2. Delete tables");
                System.out.println("3. Load data");
                System.out.println("4. Check data");
                System.out.println("5. Go back");
                System.out.println("Please enter [1-5].");
                
                int adminOption = sc.nextInt();
                sc.nextLine();
                if(adminOption==1)
                {
                    createTables();
                    adminMenu();
                }
                else if(adminOption==2)
                {
                    deleteTables();
                    adminMenu();
                }
                else if(adminOption==3)
                {
                    loadData();
                    adminMenu();
                }
                else if(adminOption==4)
                {
                    checkData();
                    adminMenu();
                }
                else if(adminOption==5)
                {
                    menuPrinter();
                }
                else
                {
                    System.out.println("[ERROR] Invalid Input");
                    adminMenu();
                }
    }
    
    public static void createTables() // function create table which creates the table required for running the database. It will drop existing tables and create fresh new ones.
    {
        //creating drivers table
        // CHECK VEHICLE ID EXACT 6 CONSTRAINTS CHECKKKK
        
        try{
            stmt.executeUpdate("DROP TABLE IF EXISTS DRIVER");
            
            stmt.executeUpdate( "CREATE TABLE DRIVER" +
                    "(ID integer PRIMARY KEY CHECK (ID>0), "
                    + "name varchar(30) NOT NULL, "
                    + "vehicle_id varchar(6) NOT NULL CHECK (len(vehicle_id) = 6))" );
            
            stmt.executeUpdate("DROP TABLE IF EXISTS VEHICLE");
            
            stmt.executeUpdate( "CREATE TABLE VEHICLE" +
                    "(ID varchar(6) PRIMARY KEY CHECK (len(ID) = 6), "
                    + "model varchar(30) NOT NULL, "
                    + "model_year integer NOT NULL CHECK (model_year BETWEEN 2010 AND 2018),"
                    + "seats integer NOT NULL CHECK (seats BETWEEN 3 AND 7))");
            
            stmt.executeUpdate("DROP TABLE IF EXISTS PASSENGER");
            
            stmt.executeUpdate( "CREATE TABLE PASSENGER" +
                    "(ID integer PRIMARY KEY CHECK (ID>0), "
                    + "name varchar(30) NOT NULL)");
            
            stmt.executeUpdate("DROP TABLE IF EXISTS REQUEST");
            
            stmt.executeUpdate( "CREATE TABLE REQUEST" +
                    "(ID integer PRIMARY KEY, "
                    + "passenger_id integer NOT NULL, "
                    + "model_year integer NOT NULL,"
                    + "model varchar(30) NOT NULL,"
                    + "passengers integer NOT NULL CHECK (passengers BETWEEN 1 AND 8),"
                    + "taken integer NOT NULL)");
            
            stmt.executeUpdate("DROP TABLE IF EXISTS TRIP");
            
            stmt.executeUpdate( "CREATE TABLE TRIP" +
                    "(ID integer PRIMARY KEY CHECK (ID>0), "
                    + "driver_id integer NOT NULL CHECK (ID>0),"
                    + "passenger_id integer NOT NULL CHECK (ID>0), "
                    + "start timestamp DEFAULT CURRENT_TIMESTAMP,"
                    + "end timestamp,"
                    + "fee integer,"
                    + "rating float DEFAULT 0)");
            
            System.out.println("Tables Created!");
            
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        
        System.out.println();

    }
    
    public static void deleteTables() // drops the existing tables
    {
        
        try{
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) 
                {
                    String table_name = rs.getString(3);
                    stmt.executeUpdate("DROP TABLE IF EXISTS " + table_name);
                }
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        
        System.out.println("Tables deleted!");
        System.out.println();

    }
    
    public static void loadData() // loads the data from the folder path. This is assuming it has the 4 files mentioned.
    {
        System.out.println("Please enter the folder path:");
        String FOLDERPATH = sc.nextLine();
//        String arr[] = new String[4];
        String arr[] = {"drivers.csv", "passengers.csv", "trips.csv", "vehicles.csv"};
        for(int i = 0; i<4;i++)
        {
            String FILENAME = FOLDERPATH + arr[i];
            BufferedReader br = null;
       
            
            try 
            {
                br = new BufferedReader(new FileReader(FILENAME));
                String sCurrentLine ;
                while((sCurrentLine= br.readLine()) != null)
                {
                    String[] data = sCurrentLine.split(",");
//                    for(int j = 0; j<data.length;j++)
//                    {
//                         System.out.println(data[j]);
//                    }
                    if(i==0)
                    {
                        data[1] = "'" + data[1] + "'";
                        data[2] = "'" + data[2] + "'";
                        try{
                            stmt.executeUpdate("INSERT INTO DRIVER VALUES(" + data[0] + "," + data[1] + "," + data[2] + ")");
                        }catch(SQLException e)
                        {
                            System.out.println(e);
                        }
                    }
                    if(i==1)
                    {
                        data[1] = "'" + data[1] + "'";
                        try{
                            stmt.executeUpdate("INSERT INTO PASSENGER VALUES(" + data[0] + "," + data[1] + ")");
                        }catch(SQLException e)
                        {
                            System.out.println(e);
                        }
                    }
                    if(i==2)
                    {
                        data[3] = "'" + data[3] + "'";
                        data[4] = "'" + data[4] + "'";
                        try{
                            stmt.executeUpdate("INSERT INTO TRIP VALUES(" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + "," + data[6] + ")");
                        }catch(SQLException e)
                        {
                            System.out.println(e);
                        }
                    }
                    if(i==3)
                    {
                        data[0] = "'" + data[0] + "'";
                        data[1] = "'" + data[1] + "'";
                        try{
                            stmt.executeUpdate("INSERT INTO VEHICLE VALUES(" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + ")");
                        }catch(SQLException e)
                        {
                            System.out.println(e);
                        }
                    }
                    
                }
            }catch(IOException e)
            {
                System.out.println(e);
            }
        }
          
        System.out.println("Data is loaded!");
        System.out.println();

    }
    
    public static void checkData() // displays the number of records in each table in the database
    {
        System.out.println("Number of records in each table:");
        
        try{
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) 
                {
                    String table_name = rs.getString(3);
                    ResultSet check = stmt.executeQuery("SELECT COUNT(*) FROM " + table_name);
                    while (check.next()) {
                         System.out.println(table_name + ":" + check.getInt(1));
                        }
                }
        }catch(SQLException e)
        {
            System.out.println(e);
        }
        System.out.println();
    }
    
    public static void passengerMenu() //prints the menu for passengers with all the functions they can use {error handling done}
    {
                System.out.println("Passenger, what would you like to do?");
                System.out.println("1. Request a ride");
                System.out.println("2. check trip records");
                System.out.println("3. Rate a trip");
                System.out.println("4. Go back");
                System.out.println("Please enter [1-4].");
                
                int passOption = sc.nextInt();
                sc.nextLine();
                
                if(passOption==1)
                {
                    requestRide();
                    passengerMenu();
                }
                else if(passOption==2)
                {
                    checkTripRecords();
                    passengerMenu();
                }
                else if(passOption==3)
                {
                       rateTrip();
                       passengerMenu();
                }
                else if(passOption==4)
                {
                    menuPrinter();
                }
                else
                {
                    System.out.println("[ERROR] Invalid Input");
                    passengerMenu();
                }
    }
    
    public static void requestRide() // allows a passenger to request for a ride {error handling done for passenger id}
    {
        System.out.println("Please enter your ID.");
        int passID = sc.nextInt();
        System.out.println("Please enter the number of passengers.");
        int passNumber = sc.nextInt();
        sc.nextLine();
        System.out.println("Please enter the earliest model year. (Press enter to skip)");
        String passeModelYear = sc.nextLine();
        int passModelYear = 0;
        if(passeModelYear.equals(""))
        {
            passeModelYear = "0";
            passModelYear = Integer.parseInt(passeModelYear);
        }
        else
        {
            passModelYear = Integer.parseInt(passeModelYear);
        }
        System.out.println("Please enter the model. (Press enter to skip)");
        String passModel = sc.nextLine();
        String pModel = passModel;
        String qq = "SELECT COUNT(*) AS total FROM PASSENGER P WHERE P.ID = " + passID;
        String query = "SELECT COUNT(*) AS total FROM VEHICLE V, DRIVER D, PASSENGER P WHERE P.ID = ? AND V.ID = D.vehicle_id AND V.seats >= ? AND V.model_year >= ? AND lower(V.model) LIKE ?";
        passModel = "%"+passModel+"%";
        try
        {
            
                String requestCountQuery = "SELECT COUNT(*) AS total FROM REQUEST R";
                pstmt = con.prepareStatement(requestCountQuery);
                ResultSet reqset = pstmt.executeQuery();
                int reqCount = 0;
                while(reqset.next())
                {
                    reqCount = reqset.getInt("total");
                }
                
                
            pstmt = con.prepareStatement(qq);
            ResultSet passIDcheck = pstmt.executeQuery();
            int passIDcheck1 = 0;
            while(passIDcheck.next())
            {
                passIDcheck1 = passIDcheck.getInt("total");
            }
            if(passIDcheck1 > 0)
            {
            String passReqCheckQuery = "SELECT COUNT(*) AS total FROM REQUEST R WHERE R.passenger_id = " + passID + " AND taken = 0";
            pstmt = con.prepareStatement(passReqCheckQuery);
            ResultSet passReqCheckQuerySet = pstmt.executeQuery();
            int passReqCheckQuerynum = 0;
            while(passReqCheckQuerySet.next())
            {
                passReqCheckQuerynum = passReqCheckQuerySet.getInt("total");
            }
            
            if(passReqCheckQuerynum == 0)
            {
                
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, passID);
            pstmt.setInt(2, passNumber);
            pstmt.setInt(3, passModelYear);
            pstmt.setString(4, passModel);
            ResultSet resultSet = pstmt.executeQuery();
            int availableDrivers = 0;
            while(resultSet.next())
            {
                availableDrivers = resultSet.getInt("total");
            }
            if(availableDrivers == 0 )
            {
                System.out.println("Please adjust criteria.");
            }
            else
            {
                reqCount++;
                System.out.println("Request Placed. " + availableDrivers + " Drivers are able to take the request.");
//                requestID++;
                pModel = "'" + pModel + "'";
                try{
                        stmt.executeUpdate("INSERT INTO REQUEST VALUES(" + reqCount + "," + passID + "," + passModelYear + "," + pModel + "," + passNumber + ",0)");
                        }catch(SQLException e)
                        {
                            System.out.println(e);
                        }
            }
            }
            else
            {
                System.out.println("Passenger already has an open request by him/her");
            }
            
            }
            else
            {
                System.out.println("[ERROR] Passenger not found.");
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }
        
        
    
    }
    
    public static boolean isValid(String text) // error handling function for the input of start and end dates. 
    {
    
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
        return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
    try {
        df.parse(text);
        return true;
    } catch (ParseException ex) {
        return false;
        }
    }
    
    public static void checkTripRecords() // function to display all the trips in a certain period. {error handling done for incorrect format}
    {
        System.out.println("Please enter your ID.");
        int passID = sc.nextInt();
        sc.nextLine();
        System.out.println("Please enter the start date.");
        String tripStart = sc.nextLine();
        boolean checkk1 = isValid(tripStart);

        while(checkk1 == false)
        {
            System.out.println("[ERROR] Please Input Start in correct format (YYYY-MM-DD)");
            tripStart = sc.nextLine();
            checkk1 = isValid(tripStart);
        }


        System.out.println("Please enter the end date.");
        String tripEnd = sc.nextLine();
        boolean checkk2 = isValid(tripEnd);
        
        while(checkk2 == false)
        {
            System.out.println("[ERROR] Please Input End in correct format (YYYY-MM-DD)");
            tripEnd = sc.nextLine();
            checkk2 = isValid(tripEnd);
        }
        
        String query = "SELECT T.ID,D.name,D.vehicle_id,V.model,T.start,T.end,T.fee,T.rating "
                + "FROM VEHICLE V, DRIVER D, TRIP T "
                + "WHERE V.ID = D.vehicle_id AND T.driver_id = D.ID AND T.passenger_id = ? AND DATE(T.start) >= ? AND DATE(T.end) <= ? AND DATE(T.end) <> 0000-00-00 "
                + "ORDER BY T.start DESC";
        try
        {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, passID);
            pstmt.setString(2, tripStart);
            pstmt.setString(3, tripEnd);
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData md = resultSet.getMetaData();
                int colCount = md.getColumnCount();
                System.out.println("Trip ID, Driver name, Vehicle ID, Vehicle model, Start, End, Fee, Rating");
                System.out.println();
            while(resultSet.next())
            {
                for(int i=1;i<=colCount;i++)
                {
                    if(i==colCount)
                    {
                        String colName = md.getColumnName(i);
                        String s = resultSet.getString(colName);
                        if(colName.equals("start") || colName.equals("end"))
                        {
                            s = s.substring(0, s.length()-1);
                            s = s.substring(0, s.length()-1);
                        }
                        System.out.print(s);
                    }
                    else
                    {
                        String colName = md.getColumnName(i);
                        String s = resultSet.getString(colName);
                        if(colName.equals("start") || colName.equals("end"))
                        {
                            s = s.substring(0, s.length()-1);
                            s = s.substring(0, s.length()-1);
                        }
                        System.out.print(s+", ");
                    }
                    
                }
                System.out.println();
            }
            System.out.println();
        }catch(SQLException e)
        {
            System.out.println(e);
        }
   }
    
    public static void rateTrip() // lets a passenger rate the trip they finished. 
    {
        System.out.println("Please enter your ID.");
        int passID = sc.nextInt();
        System.out.println("Please enter the trip ID.");
        int tripID = sc.nextInt();
        System.out.println("Please enter the rating.");
        float tripRating = sc.nextFloat();
        
        String updQuery = "UPDATE TRIP SET RATING = ? WHERE ID = ?";
        
        String query = "SELECT T.ID,D.name,D.vehicle_id ,V.model,T.start,T.end,T.fee,T.rating FROM VEHICLE V, DRIVER D, TRIP T WHERE V.ID = D.vehicle_id AND T.driver_id = D.ID AND T.ID = ? ";
        try
        {
            pstmt = con.prepareStatement(updQuery);
            pstmt.setFloat(1, tripRating);
            pstmt.setInt(2, tripID);
            pstmt.executeUpdate();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, tripID);
            ResultSet resultSet = pstmt.executeQuery();
            System.out.println("Trip ID, Driver name, Vehicle ID, Vehicle model, Start, End, Fee, Rating");
            ResultSetMetaData md = resultSet.getMetaData();
                int colCount = md.getColumnCount();
                System.out.println();
            while(resultSet.next())
            {
                for(int i=1;i<=colCount;i++)
                {
                    if(i==colCount)
                    {
                        String colName = md.getColumnName(i);
                        String s = resultSet.getString(colName);
                        if(colName.equals("start") || colName.equals("end"))
                        {
                            s = s.substring(0, s.length()-1);
                            s = s.substring(0, s.length()-1);
                        }
                        System.out.print(s);
                    }
                    else
                    {
                        String colName = md.getColumnName(i);
                        String s = resultSet.getString(colName);
                        if(colName.equals("start") || colName.equals("end"))
                        {
                            s = s.substring(0, s.length()-1);
                            s = s.substring(0, s.length()-1);
                        }
                        System.out.print(s+", ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    public static void driverMenu() //prints the menu for drivers with all the functions they can use {error handling done}
    {
                System.out.println("Driver, what would you like to do?");
                System.out.println("1. Take a request");
                System.out.println("2. Finish a trip");
                System.out.println("3. Check driver rating");
                System.out.println("4. Go back");
                System.out.println("Please enter [1-4].");
                
                int driverOption = sc.nextInt();
                
                if(driverOption==1)
                {
                    takeRequest();
                    driverMenu();
                }
                else if(driverOption==2)
                {
                    finishTrip();
                    driverMenu();
                }
                else if(driverOption==3)
                {
                    checkRating();
                    driverMenu();
                }
                else if(driverOption==4)
                {
                    menuPrinter();
                }
                else
                {
                    System.out.println("[ERROR] Invalid Input");
                    driverMenu();
                }
    }
    
    public static void takeRequest() // lets the driver take a request. all error handling done so that driver cant take a request if a passenger is in a trip even though she has made an open request for the future.
    {
        System.out.println("Please enter your ID.");
        int driverID = sc.nextInt();
        
        String query1 = "SELECT COUNT(*) AS total FROM TRIP T WHERE T.driver_id = ? AND DATE(T.end) = 0000-00-00";
        
        try
        {
            pstmt = con.prepareStatement(query1);
            pstmt.setInt(1, driverID);
            ResultSet resultSet1 = pstmt.executeQuery();
            
            int notavailableDrivers = 0;
            while(resultSet1.next())
            {
                notavailableDrivers = resultSet1.getInt("total");
            }
            if(notavailableDrivers != 0 )
            {
                System.out.println("Please finish your current trip first.");
            }
            else
            {
                String query = "SELECT DISTINCT COUNT(*) AS total,R.ID, P.name, R.passengers FROM REQUEST R, PASSENGER P, DRIVER D, VEHICLE V WHERE V.ID = D.vehicle_id and R.passenger_id = P.ID AND V.seats >= R.passengers AND V.model_year >= R.model_year AND R.taken = 0";// AND lower(V.model) LIKE '%R.model%'";
                
                pstmt = con.prepareStatement(query);
                ResultSet resultSet = pstmt.executeQuery();
                ResultSetMetaData md = resultSet.getMetaData();
                int colCount = md.getColumnCount();
                int check = 1;
                int availableRequests = 0;
                while(resultSet.next())
                {
                    availableRequests = resultSet.getInt("total");
                    if(availableRequests == 0)
                    {
                        break;
                    }
                    else{
                    for(int i=1;i<=colCount;i++)
                    {
                        if(i==1 && check == 1)
                        {
                            System.out.println("Request ID, Passenger name, Passengers");
                            check = 0;
                        }
                        if(i==colCount)
                        {
                            String colName = md.getColumnName(i);
                            String s = resultSet.getString(colName);
                            if(colName.equals("total"))
                            {
                                continue;
                            }
                            System.out.print(s);
                        }
                        else
                        {
                            String colName = md.getColumnName(i);
                            String s = resultSet.getString(colName);
                            if(colName.equals("total"))
                            {
                                continue;
                            }
                            System.out.print(s+", ");
                        }
                    }
                    }
                    System.out.println();
                }
                if(availableRequests == 0)
                {
                    System.out.println("Sorry there are no requests which match your data.");
                }
                else{
                System.out.println("Please enter the request ID");
                int takeRequestID = sc.nextInt();
                
                String checkPassenger = "SELECT R.passenger_id FROM REQUEST R WHERE R.ID = " + takeRequestID;
                ResultSet checkPassengerSet = stmt.executeQuery(checkPassenger);
                int passengerIDD = 0;
                while(checkPassengerSet.next())
                {
                    passengerIDD = checkPassengerSet.getInt("passenger_id");
                }
                
                
                String checkOngoingPassenger = "SELECT COUNT(*) AS total FROM REQUEST R WHERE R.passenger_id = " + passengerIDD + " AND taken = 1";
                ResultSet checkOngoingPassengerSet = stmt.executeQuery(checkOngoingPassenger);
                int checkOngoing = 0;
                while(checkOngoingPassengerSet.next())
                {
                    checkOngoing = checkOngoingPassengerSet.getInt("total");
                }
                
                
                if(checkOngoing == 0)
                {
                String takeRequestQuery = "SELECT * FROM REQUEST R, PASSENGER P WHERE R.ID = P.ID AND R.ID = " + takeRequestID;
                ResultSet takeReqSet = stmt.executeQuery(takeRequestQuery);
                takeReqSet.next();
                
                String updQuery = "UPDATE REQUEST SET taken = 1 WHERE ID = " + takeRequestID;
                pstmt = con.prepareStatement(updQuery);
                pstmt.executeUpdate();
                
                String tripCountQuery = "SELECT COUNT(*) AS total FROM TRIP T";
                pstmt = con.prepareStatement(tripCountQuery);
                ResultSet tripset = pstmt.executeQuery();
                int tripCount = 0;
                while(tripset.next())
                {
                    tripCount = tripset.getInt("total");
                }
                
                tripCount++;
                final String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()).toString();
                timestamp1 = "'" + timestamp + "'";
                String passName = takeReqSet.getString("name");
                stmt.executeUpdate("INSERT INTO TRIP(ID,driver_id,passenger_id,start) VALUES (" + tripCount + "," + driverID + "," + "'" + takeReqSet.getString(2)+ "'" + "," + timestamp1 + ")");
              
                System.out.println("Trip ID, Passenger name, Start");
                System.out.println(tripCount + ", " + passName + ", " + timestamp);
                }
                else
                {
                    System.out.println("The Passenger selected is currently in a trip. Please try to accept this request later as this is a future request");
                }
                }
            }

            

        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static void finishTrip() // lets the driver finish the trip. error handling done if the driver has no trips to finish.
    {
        System.out.println("Please enter your ID.");
        int driverID = sc.nextInt();
        sc.nextLine();
        try{
            String query111 = "SELECT COUNT(*) AS total"
                + " FROM TRIP T"
                + " WHERE T.driver_id = " + driverID + " AND DATE(T.end) = 0000-00-00";
            ResultSet count = stmt.executeQuery(query111);
            int counter = 0;
            while(count.next())
            {
                counter = count.getInt("total");
            }
            
            if(counter>0)
            {
            
            String query = "SELECT T.ID, T.passenger_id, T.start"
                + " FROM TRIP T"
                + " WHERE T.driver_id = " + driverID + " AND DATE(T.end) = 0000-00-00";
        
            ResultSet resultSet = stmt.executeQuery(query);
            ResultSetMetaData md = resultSet.getMetaData();
            int colCount = md.getColumnCount();
            int tripId = 0;
            int passengerId= 0;
            System.out.println("Trip ID, Passenger ID, Start");
            while(resultSet.next())
                {
                    tripId = resultSet.getInt("ID");
                    passengerId = resultSet.getInt("passenger_id");
                    for(int i=1;i<=colCount;i++)
                    {
                        if(i==colCount)
                        {
                            String colName = md.getColumnName(i);
                            String s = resultSet.getString(colName);
                            if(colName.equals("start") || colName.equals("end"))
                            {
                                s = s.substring(0, s.length()-1);
                                s = s.substring(0, s.length()-1);
                            }
                            System.out.print(s);
                        }
                        else
                        {
                            String colName = md.getColumnName(i);
                            String s = resultSet.getString(colName);
                            if(colName.equals("start") || colName.equals("end"))
                            {
                                s = s.substring(0, s.length()-1);
                                s = s.substring(0, s.length()-1);
                            }
                            System.out.print(s+", ");
                        }

                    
                    }
                    System.out.println();
                }
            System.out.println("Do you want to finish the trip? [y/n]");
            String decision = sc.nextLine();
            if(decision.equals("y"))
            {
                final String timestamp12 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()).toString();
                String  j= "'"+timestamp12+"'";
//                System.out.println(timestamp1 + " " + j);
                final String query1 = "UPDATE TRIP T SET T.end = " + j+  " WHERE T.ID = ? ";
                pstmt = con.prepareStatement(query1);
                pstmt.setInt(1, tripId);
                pstmt.executeUpdate();
                
                String query2 = "UPDATE TRIP SET FEE = TIMESTAMPDIFF(MINUTE, start, end) WHERE ID = ?";
                pstmt = con.prepareStatement(query2);
                pstmt.setInt(1, tripId);
                pstmt.executeUpdate();
                
                String queryDel = "DELETE FROM REQUEST WHERE passenger_id = " + passengerId + " AND taken = 1";
                pstmt = con.prepareStatement(queryDel);
                pstmt.executeUpdate();
                
                String query3 = "SELECT T.ID, P.name, T.start,T.end,T.fee FROM TRIP T, PASSENGER P WHERE T.ID = ? AND T.passenger_id = P.ID";
                pstmt = con.prepareStatement(query3);
                pstmt.setInt(1, tripId);
                ResultSet tripEnded = pstmt.executeQuery();
                ResultSetMetaData md1 = tripEnded.getMetaData();
                int colCount1 = md1.getColumnCount();
                System.out.println("Trip ID, Passenger Name, Start, End, Fee");
                while(tripEnded.next())
                {
                    
                    for(int i=1;i<=colCount1;i++)
                    {
                        if(i==colCount1)
                        {
                            String colName = md1.getColumnName(i);
                            String s = tripEnded.getString(colName);
                            if(colName.equals("start") || colName.equals("end"))
                            {
                                s = s.substring(0, s.length()-1);
                                s = s.substring(0, s.length()-1);
                            }
                            System.out.print(s);
                        }
                        else
                        {
                            String colName = md1.getColumnName(i);
                            String s = tripEnded.getString(colName);
                            if(colName.equals("start") || colName.equals("end"))
                            {
                                s = s.substring(0, s.length()-1);
                                s = s.substring(0, s.length()-1);
                            }
                            System.out.print(s+", ");
                        }
                    
                    }
                    System.out.println();
                }
            }
            else
            {
                System.out.println("Going Back to Driver Menu");
            }
            }
            else
            {
                System.out.println("You are currently not in any unfinished trip");
                System.out.println("Going Back to Driver Menu");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
    }
    
    public static void checkRating() // lets the driver check their rating
    {
        System.out.println("Please enter your ID.");
        int driverID = sc.nextInt();
        sc.nextLine();
        
        try
        {
            String q = "SELECT COUNT(*) AS total FROM TRIP T WHERE T.rating <> 0 AND T.driver_id = ?";
            pstmt = con.prepareStatement(q);
            pstmt.setInt(1, driverID);
            ResultSet noOfTrips = pstmt.executeQuery();
            int noOft = 0;
            while(noOfTrips.next())
            {
                noOft = noOfTrips.getInt("total");
            }
            if(noOft<5)
            {
                System.out.println("Driver rating is not yet determined.");
            }
            else
            {
                String query = "SELECT T.rating FROM TRIP T WHERE T.rating IS NOT NULL AND T.rating <> 0 AND T.driver_id = ? ORDER BY T.end DESC";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, driverID);
                int ctr = 0;
                float rating = 0;
                ResultSet tripRatings = pstmt.executeQuery();
                while(tripRatings.next() && ctr<5)
                {
                    rating = rating + tripRatings.getFloat("rating");
                    ctr++;
                }
                float avgRating = rating/5;
                System.out.printf("Your driver rating is %.2f", avgRating);
            }

        }catch(SQLException e)
        {
            System.out.println(e);
        }
        System.out.println();
    }
    
    
    
    
    public static void main(String[] args) { // main function which puts in the databse connection and runs the menuPrinter() function
        // TODO code application logic here
            String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db2";
            String dbUsername = "Group2";
            String dbPassword = "shaata";
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                stmt = con.createStatement();

                menuPrinter();
                
                
            }catch (ClassNotFoundException e){
                System.out.println("[Error]: Java MySQL DB DRIVER not found!!");
                System.exit(0);
            }catch (SQLException e){
                System.out.println(e);
            }
    }
    
}
