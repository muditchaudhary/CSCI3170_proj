# CSCI3170_proj

## Create Table commands 

### Employees
	create table employee( Employee_ID varchar(6) not null, Name varchar(30) not null, Experience int, Expected_Salary int, Skills varchar(50), primary key (Employee_ID) );

### Company
	create table Company( Company varchar(30) not null, Size int, Founded int(4), primary key (Company) );

### Employer
	create table Employer( Employer_ID varchar(6) not null , Name varchar(30) not null, Company varchar(30) not null, Primary key (Employer_ID) , Foreign key (Company) References Company(Company) ON DELETE CASCADE);

### Position
	create Table PositionTable(Position_ID varchar(6) not null, Position_Title varchar(30) not null, Salary int, Experience int, Status boolean, Employer_ID varchar(6) not null, Primary key (Position_ID), Foreign key (Employer_ID) references Employer(Employer_ID) on delete cascade); 

### Employment_History
	create Table Employment_History(Position_ID varchar(6) not null, Employee_ID varchar(6) not null, Start Date, End Date, Primary Key(Position_ID), Foreign key (Employee_ID) references Employee(Employee_ID))