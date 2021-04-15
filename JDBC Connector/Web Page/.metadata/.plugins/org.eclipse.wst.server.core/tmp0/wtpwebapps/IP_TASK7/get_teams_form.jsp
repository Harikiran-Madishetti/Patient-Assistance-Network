<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Get Teams</title>
    </head>
    <body>
        <h2>Get Teams </h2>
        <h4>Please enter date to get teams which are formed after a particular date </h4>
        <!--
            Form for collecting user input to get teams formed after a date.
            Upon form submission, get_teams.jsp file will be invoked.
        -->
        <form action="get_teams.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">		Enter the Date to find the teams	</th>
                </tr>
                <tr>
                    <td> Date (MM/DD/YYY): </td>
                    <td><div style="text-align: center;">
                    <input type=text name=formation_date>
                    </div></td>
                </tr>
                <tr>
                    <td><div style="text-align: center;">
                    <input type=reset value=Clear>
                    </div></td>
                    <td><div style="text-align: center;">
                    <input type=submit value=Insert>
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
