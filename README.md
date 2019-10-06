# CSCI3170_proj

## Create Table commands 

The create table commands can be viewed in:

```	
	public static void createTables(){...};
```

For testing the database on Windows I'd recommend to install WSL. The tutorial to install can be found here: https://docs.microsoft.com/en-us/windows/wsl/install-win10

For testing on MacOS, you can use the terminal directly

How to check the database on the sql server:
```	
	1. Connect to CSE VPN.
	2. Open Terminal (For MacOS). Open WSL terminal (For Windows).
	3. Login into linux server using: ssh linux1.cse.cuhk.edu.hk -l CSE_UNIX_Username
	4. Password is your CSE Unix password.
	5. Login into the sql server using: mysql --host=projgw --port=2633 -u Group30 -p
	6. Password is CSCI3170
	7. Access the database using: use db30
	8. You can now use the SQL commands to get output of the tables.
	9. The name of the tables are EMPLOYEES, EMPLOYER, COMPANY, EMPLOYMENT_HISTORY, MARKED, POSITIONTABLE. The table names are Case-sensitive.
```

How to run the Java Program:
```	
	1. Connect to CSE VPN.
	2. Open the project in NetBeans.
	3. Build and Run.
	4. For loading the data you need to enter the file path in the following format: M:\dbProject\CSCI3170_proj\CSCI3170\test_data\
```
