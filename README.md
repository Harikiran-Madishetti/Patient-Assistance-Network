# A PATIENT ASSISTANT NETWORK DATABASE SYSTEM

#### Author: Harikiran Madishetti

---

## About

The Patient Assistance Network (PAN) is a non-profit organization that provides support and care for patients. PAN needs to implement a database system to keep track of the personnel necessary to support the organization. In this project, your task will be to design and implement this database system. The information that needs to be stored in the database is described in the next section.

## System Requirements

All the details required to build a database system is present in [Design_Requirements.md](https://github.com/Harikiran-Madishetti/Patient-Assistance-Network/blob/main/Design_Requirements.md 'Design_Requirements.md')

## Entity-Relationship Diagram

![ER Diagram](<![](https://github.com/Harikiran-Madishetti/Patient-Assistance-Network/blob/main/ER_Diagram.png)> 'ER Diagram')

## Database Relational Schema

#### Entity Sets:

Person (SSN, Birth_Date, Race, Gender, Profession, Mailing_Address, Email_Address,
Home_phone, Cell_Phone, Work_Phone, Mailing_List, Org_Name)
Client (SSN, Doc_Name, Doc_Phone, Att_Name, Att_Phone, Assigned_Date)
Volunteer (SSN, Joining_Date, Training_Date, Training_Location)
Employee (SSN, Salary, Marital_Status, Hire_Date)
Donor (SSN, Anonymous)
Emerg_Contact (SSN, Name, Contact_Info, Relationship)
Insurance_policy (Policy_Id, SSN, Provider_Id, Provider_Address, Policy_Type)
Needs (SSN, Need_Type, Importance)
Teams (Name, Type, Formation_Date)
Expenses (SSN, Date, Amount, Description)
Donor_Donations (SSN, Date, Amount, Campaign_Name, Type)
Donor_Donations_Check (SSN, Date, Amount, Campaign_Name, Type, Check_No)
Donor_Donations_Card (SSN, Date, Amount, Campaign_Name, Type, Card_No, Card_Type,
Exp_Date)
Organization (Name, Mailing_Address, Phone_No, Contact_Person)
Church (Name, Religious_Affiliation)
Business (Name, Business_Type, Size, Company_Website)
Org_Donations (Name, Date, Amount, Campaign_Name, Type, Anonymous)
Org_Donations_Check (Name, Date, Amount, Campaign_Name, Type, Check_No)
Org_Donations_Card (Name, Date, Amount, Campaign_Name, Type, Card_No, Card_Type,
Exp_Date)

#### Relationships:

Cares (SSN, Name, Active)
Serves (SSN, Name)
Leader (Name, SSN)
Work (SSN, Name, Months, Hours)
Sponsor (Team_Name, Org_Name)
Reports (Name, SSN, Date, Description)
