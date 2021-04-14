# DATABASE APPLICATION REQUIREMENTS

## System Requirement

There are many categories of people that need to be tracked in the PAN database. Each person may fall into more than one of the following categories: clients, volunteers, employees, and donors. PAN tracks the name, social security number, birth date, race, gender, and profession of each person. In addition, PAN stores the contact information for each person consisting of a mailing address, email address, and home, work, and cell phone number. PAN also sends a monthly newsletter to people on its mailing list, so the database should indicate whether or not each person in the database is on that list. Finally, the system should have the ability to store a list of emergency contacts for the people in the database. This information should record the name and contact information for each of the emergency contacts along with their respective relationship to the person in the database.

PAN tracks its list of clients in the database. For each client, PAN tracks the name and phone number of his or her doctor and attorney. PAN also tracks the date the client was first assigned to the organization. Each client has a list of needs. Examples of these needs include visiting, shopping, housekeeping, transportation, yard work, and food. Each of these needs is also associated with a value indicating its importance to the client (1-10). PAN also tracks the list of insurance policies that each client has. Each insurance policy has a unique policy id, a provider id, provider address, and a type such as life, health, home, or auto.

PAN provides care for each client using teams that contain many volunteers. Each team cares for several clients, and more than one team may care for a client. Each team is identified by its name, and each team also has a type and a date it was formed. A volunteer may serve on multiple teams. For each volunteer, the database should store the date he or she first joined PAN and the date and location of his or her most recent training course. In addition, PAN should record the number of hours a volunteer worked each month for a particular team. Note that the volunteers do not work the same number of hours each month. One of the volunteers on a team serves as the team leader. This information should be tracked in the database as well. In addition, volunteers and clients may switch teams; so, the database system should provide the ability to mark whether or not each volunteer and client is active or inactive on a specific team.

Every team reports periodically to one PAN employee to discuss its current status, and more than one team may report to the same employee. The database should record the date of each report as well as a description of its content. For each employee, the database should store the employee’s salary, marital status, and hire date. An employee may charge several expenses each month. The database should track the date of the expense, along with the amount and its description.

PAN depends on support from its donors. The database should track these people as well as record each of their donations. This information should include the date, amount, and type of donation along with the name of the fund raising campaign that generated the donation if it is applicable. If the donation was made by check, the database should record the check number. If the donation was made by credit card, the database should record the card number, card type, and expiration date. In addition, each donor may wish to remain anonymous, so the database should record that information as well.

Each person in the database may be affiliated with an external organization. The database should track this information as well. Each organization should have a unique name, mailing address, phone number, and contact person. In addition, each organization may sponsor one or more PAN teams, and a team may have more than one sponsor. If the organization is a business, the database should record the business type, size, and company web site. If the organization is a church, the database should record its religious affiliation. Each organization may also make several donations to PAN, and the database should track the same donation information as it does for individual donors. This includes the ability for the organization to make anonymous donations.

### Queries and Their Frequencies for the PAN Database System

1. Enter a new team into the database (1/month).
2. Enter a new client into the database and associate him or her with one or more teams (1/week).
3. Enter a new volunteer into the database and associate him or her with one or more teams (2/month).
4. Enter the number of hours a volunteer worked this month for a particular team (30/month).
5. Enter a new employee into the database and associate him or her with one or more teams (1/year).
6. Enter an expense charged by an employee (1/day).
7. Enter a new organization and associate it to one or more PAN teams (2/week).
8. Enter a new donor and associate him or her with several donations (1/day).
9. Enter a new organization and associate it with several donations (1/day).
10. Retrieve the name and phone number of the doctor of a particular client (1/week).
11. Retrieve the total amount of expenses charged by each employee for a particular period of time. The list should be sorted by the total amount of expenses (1/month).
12. Retrieve the list of volunteers that are members of teams that support a particular client (4/year).
13. Retrieve the names and contact information of the clients that are supported by teams sponsored by an organization whose name starts with a letter between B and K. The client list should be sorted by name (1/week).
14. Retrieve the name and total amount donated by donors that are also employees. The list should be sorted by the total amount of the donations, and indicate if each donor wishes to remain anonymous (1/week).
15. Retrieve the names of all teams that were founded after a particular date (1/month).
16. Increase the salary by 10% of all employees to whom more than one team must report. (1/year)
17. Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5 (4/year).
