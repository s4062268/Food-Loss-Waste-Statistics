package app;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Location-Loss-and-Waste-by-Similarity";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

        // Add some CSS (external file)
        html += """
            <script src="https://unpkg.com/slim-select@latest/dist/slimselect.min.js"></script>
            <link href="https://unpkg.com/slim-select@latest/dist/slimselect.css" rel="stylesheet"></link>
                """;

        html = html + "<link rel='stylesheet' type='text/css' href='common.css'/>";
        html = html + "</head>";

        // Add the body
        html = html + "<body>" +  "<main>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html += "<div class='PageTopNav'>";
        NavigationBar NavBar = new NavigationBar();
        html += NavBar.main();

        html = html + """
            <ul class="breadcrumb">
                <li><a href="/">Homepage</a></li>
                <li>Location Food Loss and Waste by Similarity</a></li>
            </ul>
        """;

        html += "</div>";
        html += "<div class='all-content'>";
        // Add header content block
        html = html + "<h1>Location Food Loss and Waste by Similarity</h1>";

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h3>SEARCH RECORDS BASED ON</h3>
            <form action='/Location-Loss-and-Waste-by-Similarity' method='post'>
            <div class='similarity-sort'> 
                <div class='location-filter'> 
                    <h3>country</h3>
                    <div class='s-item-content'>
                        <select name="countries" id="countries-select" class="select" multiple>
                        """;
                        ArrayList<String> allCountries = getAllCountries();
                        for (String country : allCountries) {
                            html += "<option>" + country + "</option>";
                            }
    html += """                            
                        <input type="checkbox" id="location" value="activity" name="show-collumns">
                        <label for="location">nearby countries</label>
                    </div>
                </div>
                <div class='similarity-fitler'> 
                    <h3>similarity</h3>
                     <div class='s-item-content'>
                        <input type="radio" id="commodity" value="range" name="similarity-set">
                        <label for="commodity">commodity loss</label>
                        <input type="radio" id="overall" value="base" name="similarity-set">
                        <label for="overall">overall %</label>
                        <input type="radio" id="all" value="base" name="similarity-set">
                        <label for="all">all</label>
                    </div>
                </div>
                <div class='determine-filter'> 
                    <h3>determine similarity</h3>
                    <div class='s-item-content'>
                        <input type="radio" id="range" value="range" name="similarity-determine">
                        <label for="range">aboslute loss %</label>
                        <input type="radio" id="base" value="base" name="similarity-determine">
                        <label for="base">loss overlap</label>
                    </div>
                </div>
                <div class='year-filter'> 
                    <h3>year range</h3>
                    <div class='s-item-content'>
                        <input type="number" id="quantity" name="quantity" min="1966" max="2022" value="1966">
                        <p>to</p>
                        <input type="number" id="quantity" name="quantity" min="1966" max="2022" value="2022">
                    </div>
                </div>
            </div>
            <button type="submit" id="filter-search" name="filter-search">search</button>
            </form>
            """;

            // Close Content div
            html += "</div>";
            int collumncount = 5;
            int currcolumn = 0;
            int totalColumn = 15;
            html += "<div class='filterResult'>";
            html += "<p><b>java / SQL implementation has not yet been added, please check back shortly!</b></p>";
            html += """
                    <table id='a3Results'> 
                    <tr>
                    <th>country</th>
                    <th>region</th>
                    <th>year</th>
                    <th>similarities</th>
                    <th>similarity score</th>
                    """;
            html += "</tr> <tr>";
            for (int i = 0; i < totalColumn; i++) {
                html = html + "<td>" + "--" + "</td>";
                // total number of columns for current row
                currcolumn += 1;
                // if the it reaches the total column count, then it created a new row!!!
                if (currcolumn > collumncount -1) {
                    html += "</tr>" + "<tr>";
                    currcolumn = 0;
                }
            }
            html += "</table>";

            // close the filter result div
            html += "</div>";

        // this should close the all-content div?
            html += "</div>";

        // Footer
        PageFooter footer = new PageFooter();
        html += footer.main();

        html += """
            <script>
                new SlimSelect({
                    select: '#countries-select'
                })
            </script>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</main>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }


public ArrayList<String> getAllCountries() {
    // Create the ArrayList of String objects to return
    ArrayList<String> countries = new ArrayList<String>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(JDBCConnection.DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT * FROM country ORDER BY countryName";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            String countryName  = results.getString("countryName");

            // Add the country object to the array
            countries.add(countryName);
        }

        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just print the error
        System.err.println(e.getMessage());
        //e.printStackTrace();
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    // Finally we return all of the countries
    return countries;
}
}
