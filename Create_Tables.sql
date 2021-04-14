-- Create a new table called 'ORGANIZATION'
-- Drop the table if it already exists
IF OBJECT_ID('ORGANIZATION', 'U') IS NOT NULL
DROP TABLE ORGANIZATION
GO
-- Create the table
CREATE TABLE ORGANIZATION
(
    Name VARCHAR(50) NOT NULL PRIMARY KEY, -- primary key column
    Phone_No VARCHAR (20) NOT NULL,
    Mailing_Address VARCHAR (200) NOT NULL,
    Contact_Person VARCHAR(30) NOT NULL
);
GO

-- Create a new table called 'CHURCH'
-- Drop the table if it already exists
IF OBJECT_ID('CHURCH', 'U') IS NOT NULL
DROP TABLE CHURCH
GO
-- Create the table
CREATE TABLE CHURCH
(
    Name VARCHAR(50) NOT NULL PRIMARY KEY, -- primary key column
    Religious_Affiliation VARCHAR (50) NOT NULL,
    FOREIGN KEY (Name) REFERENCES ORGANIZATION ON DELETE CASCADE
);
GO


-- Create a new table called 'BUSINESS'
-- Drop the table if it already exists
IF OBJECT_ID('BUSINESS', 'U') IS NOT NULL
DROP TABLE BUSINESS
GO
-- Create the table
CREATE TABLE BUSINESS
(
    Name VARCHAR(50) NOT NULL PRIMARY KEY, -- primary key column
    Business_type VARCHAR (20) NOT NULL,
    Size VARCHAR (20) NOT NULL,
    Company_Website VARCHAR (20) NOT NULL,
    FOREIGN KEY (Name) REFERENCES ORGANIZATION  ON DELETE CASCADE
);
GO


-- Create a new table called 'PERSON'
-- Drop the table if it already exists
IF OBJECT_ID('PERSON', 'U') IS NOT NULL
DROP TABLE PERSON
GO
-- Create the table
CREATE TABLE PERSON
(
    SSN INT NOT NULL PRIMARY KEY, -- primary key column
    Name VARCHAR(30) NOT NULL,
    Birth_Date DATE NOT NULL,
    Race VARCHAR (15) NOT NULL,
    Gender VARCHAR (10) NOT NULL,
    Profession VARCHAR(20) NOT NULL,
    Address VARCHAR (200) NOT NULL,
    Email VARCHAR (50) NOT NULL,
    Home_Ph VARCHAR (20) NOT NULL,
    Work_Ph VARCHAR (20) NOT NULL,
    Cell_Ph VARCHAR (20) NOT NULL,
    Mailing_List BIT NOT NULL,
    Org_Name VARCHAR (50) NULL,
    FOREIGN KEY (Org_Name) REFERENCES ORGANIZATION ON DELETE SET NULL
);
GO

-- Create a new table called 'CLIENT'
-- Drop the table if it already exists
IF OBJECT_ID('CLIENT', 'U') IS NOT NULL
DROP TABLE CLIENT
GO
-- Create the table
CREATE TABLE CLIENT
(
    SSN INT NOT NULL PRIMARY KEY, -- primary key column
    Doc_Name VARCHAR(30) NOT NULL,
    Att_Name VARCHAR(30) NOT NULL,
    Doc_Ph VARCHAR (20) NOT NULL,
    Att_Ph VARCHAR (20) NOT NULL,
    Assigned_Date DATE NOT NULL,
    FOREIGN KEY (SSN) REFERENCES PERSON ON DELETE CASCADE, 
);
GO

-- Create a new table called 'VOLUNTEER'
-- Drop the table if it already exists
IF OBJECT_ID('VOLUNTEER', 'U') IS NOT NULL
DROP TABLE VOLUNTEER
GO
-- Create the table
CREATE TABLE VOLUNTEER
(
    SSN INT NOT NULL PRIMARY KEY, -- primary key column
    Joining_Date DATE NOT NULL,
    Training_Date DATE NOT NULL,
    Training_Location VARCHAR(50) NOT NULL,
    FOREIGN KEY (SSN) REFERENCES PERSON ON DELETE CASCADE, 
);
GO

-- Create a new table called 'EMPLOYEE'
-- Drop the table if it already exists
IF OBJECT_ID('EMPLOYEE', 'U') IS NOT NULL
DROP TABLE EMPLOYEE
GO
-- Create the table
CREATE TABLE EMPLOYEE
(
    SSN INT NOT NULL PRIMARY KEY, -- primary key column
    Salary REAL NOT NULL,
    Marital_Status VARCHAR (10) NOT NULL,
    Hire_Date DATE NOT NULL,
    FOREIGN KEY (SSN) REFERENCES PERSON ON DELETE CASCADE
);
GO

-- Create a new table called 'DONOR'
-- Drop the table if it already exists
IF OBJECT_ID('DONOR', 'U') IS NOT NULL
DROP TABLE DONOR
GO
-- Create the table
CREATE TABLE DONOR
(
    SSN INT NOT NULL PRIMARY KEY, -- primary key column
    Anonymous BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (SSN) REFERENCES PERSON ON DELETE CASCADE
);
GO

-- Create a new table called 'EMERG_CONTACT'
-- Drop the table if it already exists
IF OBJECT_ID('EMERG_CONTACT', 'U') IS NOT NULL
DROP TABLE EMERG_CONTACT
GO
-- Create the table
CREATE TABLE EMERG_CONTACT
(
    SSN INT NOT NULL, 
    Name VARCHAR(30) NOT NULL,
    Contact_Info VARCHAR (300) NOT NULL,
    Relationship VARCHAR (20) NOT NULL,
    PRIMARY KEY (SSN, Name, Contact_Info), -- primary key column
    FOREIGN KEY (SSN) REFERENCES PERSON ON DELETE CASCADE
);
GO

-- Create a new table called 'INSURANCE_POLICY'
-- Drop the table if it already exists
IF OBJECT_ID('INSURANCE_POLICY', 'U') IS NOT NULL
DROP TABLE INSURANCE_POLICY
GO
-- Create the table
CREATE TABLE INSURANCE_POLICY
(
    Policy_Id INT NOT NULL PRIMARY KEY, -- primary key column
    SSN INT NOT NULL,
    Provider_Id INT NOT NULL,
    Provider_Address VARCHAR(200) NOT NULL,
    Policy_Type VARCHAR (10) NOT NULL,
    FOREIGN KEY (SSN) REFERENCES CLIENT ON DELETE CASCADE,
    CHECK (Policy_Type in ('Life', 'Health', 'Home', 'Auto'))
);
GO

-- Create a new table called 'NEEDS'
-- Drop the table if it already exists
IF OBJECT_ID('NEEDS', 'U') IS NOT NULL
DROP TABLE NEEDS
GO
-- Create the table
CREATE TABLE NEEDS
(
    SSN INT NOT NULL , 
    Need_Type VARCHAR(20) NOT NULL,
    Importance INT NOT NULL,
    PRIMARY KEY (SSN, Need_Type), -- primary key column
    FOREIGN KEY (SSN) REFERENCES CLIENT ON DELETE CASCADE,
    CHECK ( Need_Type in ('Visiting', 'Shopping', 'Transportation', 'House Keeping', 'Yard Work', 'Food')),
    CHECK (Importance between 1 and 10)
);
GO


-- Create a new table called 'TEAMS'
-- Drop the table if it already exists
IF OBJECT_ID('TEAMS', 'U') IS NOT NULL
DROP TABLE TEAMS
GO
-- Create the table
CREATE TABLE TEAMS
(
    Name VARCHAR (30) NOT NULL PRIMARY KEY, -- primary key column
    Type VARCHAR (20) NOT NULL,
    Formation_Date DATE NOT NULL
);
GO

-- Create a new table called 'EXPENSES'
-- Drop the table if it already exists
IF OBJECT_ID('EXPENSES', 'U') IS NOT NULL
DROP TABLE EXPENSES
GO
-- Create the table
CREATE TABLE EXPENSES
(
    SSN INT NOT NULL , 
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Description VARCHAR (100) NOT NULL,
    PRIMARY KEY (SSN, Date, AMOUNT, Description), -- primary key column
    FOREIGN KEY (SSN) REFERENCES EMPLOYEE ON DELETE CASCADE
);
GO

-- Create a new table called 'DONOR_DONATIONS'
-- Drop the table if it already exists
IF OBJECT_ID('DONOR_DONATIONS', 'U') IS NOT NULL
DROP TABLE DONOR_DONATIONS
GO
-- Create the table
CREATE TABLE DONOR_DONATIONS
(
    SSN INT NOT NULL , 
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    PRIMARY KEY (SSN, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (SSN) REFERENCES DONOR ON DELETE CASCADE
);
GO

-- Create a new table called 'DONOR_DONATIONS_CHECK'
-- Drop the table if it already exists
IF OBJECT_ID('DONOR_DONATIONS_CHECK', 'U') IS NOT NULL
DROP TABLE DONOR_DONATIONS_CHECK
GO
-- Create the table
CREATE TABLE DONOR_DONATIONS_CHECK
(
    SSN INT NOT NULL , 
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    Check_No INT NOT NULL,
    PRIMARY KEY (SSN, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (SSN, Date, AMOUNT, Campaign_Name, Type) REFERENCES DONOR_DONATIONS ON DELETE CASCADE
);
GO

-- Create a new table called 'DONOR_DONATIONS_CARD'
-- Drop the table if it already exists
IF OBJECT_ID('DONOR_DONATIONS_CARD', 'U') IS NOT NULL
DROP TABLE DONOR_DONATIONS_CARD
GO
-- Create the table
CREATE TABLE DONOR_DONATIONS_CARD
(
    SSN INT NOT NULL , 
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    Card_No VARCHAR (20) NOT NULL,
    Card_Type VARCHAR (10) NOT NULL,
    Exp_Date VARCHAR(7) NOT NULL,
    PRIMARY KEY (SSN, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (SSN, Date, AMOUNT, Campaign_Name, Type) REFERENCES DONOR_DONATIONS ON DELETE CASCADE
);
GO

-- Create a new table called 'ORG_DONATIONS'
-- Drop the table if it already exists
IF OBJECT_ID('ORG_DONATIONS', 'U') IS NOT NULL
DROP TABLE ORG_DONATIONS
GO
-- Create the table
CREATE TABLE ORG_DONATIONS
(
    Name VARCHAR (50) NOT NULL , 
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    Anonymous BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (Name, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (Name) REFERENCES ORGANIZATION ON DELETE CASCADE
);
GO

-- Create a new table called 'ORG_DONATIONS_CHECK'
-- Drop the table if it already exists
IF OBJECT_ID('ORG_DONATIONS_CHECK', 'U') IS NOT NULL
DROP TABLE ORG_DONATIONS_CHECK
GO
-- Create the table
CREATE TABLE ORG_DONATIONS_CHECK
(
    Name VARCHAR (50) NOT NULL ,
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    Check_No INT NOT NULL,
    PRIMARY KEY (Name, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (Name, Date, AMOUNT, Campaign_Name, Type) REFERENCES ORG_DONATIONS ON DELETE CASCADE
);
GO

-- Create a new table called 'ORG_DONATIONS_CARD'
-- Drop the table if it already exists
IF OBJECT_ID('ORG_DONATIONS_CARD', 'U') IS NOT NULL
DROP TABLE ORG_DONATIONS_CARD
GO
-- Create the table
CREATE TABLE ORG_DONATIONS_CARD
(
    Name VARCHAR (50) NOT NULL ,
    Date DATE NOT NULL,
    AMOUNT REAL NOT NULL,
    Campaign_Name VARCHAR (50) NOT NULL,
    Type VARCHAR (20) NOT NULL,
    Card_No VARCHAR (20) NOT NULL,
    Card_Type VARCHAR (10) NOT NULL,
    Exp_Date VARCHAR(7) NOT NULL,
    PRIMARY KEY (Name, Date, AMOUNT, Campaign_Name, Type), -- primary key column
    FOREIGN KEY (Name, Date, AMOUNT, Campaign_Name, Type) REFERENCES ORG_DONATIONS ON DELETE CASCADE
);
GO

-- Create a new table called 'CARES'
-- Drop the table if it already exists
IF OBJECT_ID('CARES', 'U') IS NOT NULL
DROP TABLE CARES
GO
-- Create the table
CREATE TABLE CARES
(
    SSN INT NOT NULL,
    Name VARCHAR (30) NOT NULL ,
    Active BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (SSN, Name), -- primary key column
    FOREIGN KEY (SSN) REFERENCES CLIENT ON DELETE CASCADE,
    FOREIGN KEY (Name) REFERENCES TEAMS ON DELETE CASCADE,
);
GO


-- Create a new table called 'SERVES'
-- Drop the table if it already exists
IF OBJECT_ID('SERVES', 'U') IS NOT NULL
DROP TABLE SERVES
GO
-- Create the table
CREATE TABLE SERVES
(
    SSN INT NOT NULL,
    Name VARCHAR (30) NOT NULL ,
    Active BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (SSN, Name), -- primary key column
    FOREIGN KEY (SSN) REFERENCES VOLUNTEER ON DELETE CASCADE,
    FOREIGN KEY (Name) REFERENCES TEAMS ON DELETE CASCADE,
);
GO

-- Create a new table called 'WORK'
-- Drop the table if it already exists
IF OBJECT_ID('WORK', 'U') IS NOT NULL
DROP TABLE WORK
GO
-- Create the table
CREATE TABLE WORK
(
    Name VARCHAR (30) NOT NULL , 
    SSN INT NOT NULL,
    Month VARCHAR (3) NOT NULL ,
    Hours REAL NOT NULL,
    PRIMARY KEY (SSN, Name,  Hours), -- primary key column
    FOREIGN KEY (SSN, Name) REFERENCES SERVES ON DELETE CASCADE
);
GO

-- Create a new table called 'LEADER'
-- Drop the table if it already exists
IF OBJECT_ID('LEADER', 'U') IS NOT NULL
DROP TABLE LEADER
GO
-- Create the table
CREATE TABLE LEADER
(
    Name VARCHAR (30) NOT NULL , 
    SSN INT NOT NULL,
    PRIMARY KEY (SSN, Name), -- primary key column
    FOREIGN KEY (SSN, Name) REFERENCES SERVES ON DELETE CASCADE
);
GO

-- Create a new table called 'SPONSOR'
-- Drop the table if it already exists
IF OBJECT_ID('SPONSOR', 'U') IS NOT NULL
DROP TABLE SPONSOR
GO
-- Create the table
CREATE TABLE SPONSOR
(
    Team_Name VARCHAR (30) NOT NULL ,
    Org_Name VARCHAR (50) NOT NULL ,
    PRIMARY KEY (Team_Name, Org_Name), -- primary key column
    FOREIGN KEY (Team_Name) REFERENCES TEAMS ON DELETE CASCADE,
    FOREIGN KEY (Org_Name) REFERENCES ORGANIZATION ON DELETE CASCADE
);
GO

-- Create a new table called 'REPORTS'
-- Drop the table if it already exists
IF OBJECT_ID('REPORTS', 'U') IS NOT NULL
DROP TABLE REPORTS
GO
-- Create the table
CREATE TABLE REPORTS
(
    Name VARCHAR (30) NOT NULL PRIMARY KEY, -- primary key column
    SSN INT  NOT NULL ,
    Date DATE NOT NULL,
    Description VARCHAR (500) NOT NULL,
    FOREIGN KEY (SSN) REFERENCES EMPLOYEE ON DELETE CASCADE,
    FOREIGN KEY (Name) REFERENCES TEAMS ON DELETE CASCADE,
);
GO


-- CREATE INDEXES ON TABLES
CREATE INDEX insurance_policy_type ON INSURANCE_POLICY(POLICY_TYPE)
CREATE INDEX insurance_ssn ON INSURANCE_POLICY(SSN)
CREATE INDEX needs_type_importance ON NEEDS(NEED_TYPE, IMPORTANCE)
CREATE INDEX needs_ssn ON NEEDS(SSN)
CREATE INDEX teams_date ON TEAMS(FORMATION_DATE)
CREATE INDEX expenses_date ON EXPENSES(DATE)
CREATE INDEX dd_ssn ON DONOR_DONATIONS(SSN)
