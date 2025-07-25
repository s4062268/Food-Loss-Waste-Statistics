package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

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

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/Food-Loss-and-Waste-by-Country";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";

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
        
        // Add the topnav
        // This uses a Java v15+ Text Block
        html += "<div class='PageTopNav'>";
        NavigationBar NavBar = new NavigationBar();
        html += NavBar.main();

        html = html + """
            <ul class="breadcrumb">
                <li><a href="/">Homepage</a></li>
                <li>Food Loss and Waste by Country</a></li>
            </ul>
        """;

        html += "</div>";
        
        html += "<div class ='all-content'>";
        
        html += "<h1>Food Loss and Waste by Country</h1>";

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h3>SEARCH RECORDS BASED ON</h3>    
            <form action='/Food-Loss-and-Waste-by-Country' method='post' id='Page2A'>
            <div class='contentSort'>
                <div class='gridContainer'>
                    <div class="grid-item1">
                        <div class="item-content">
                            <h3>countries</h3>
                        </div>
                    </div>
                    <div class="grid-item2">
                        <div class="item-content">
                            <h3>filter by</h3>
                        </div>
                    </div>
                    <div class="grid-item3">
                        <div class="item-content">
                            <h3>year range</h3>
                        </div>
                    </div>
                    <div class="grid-item4">
                        <div class="item-content">
                            <select name="countries" id="countries-select" class="select" multiple>
                            """;
                    ArrayList<String> allCountries = getAllCountries();
                        for (String country : allCountries) {
                            html += "<option>" + country + "</option>";
                            }
        
        html += """
                            </select>
                        </div>
                    </div>
                    <div class="grid-item5">
                        <div class="item-content">
                            <input type="checkbox" id="commodity" value="categoryName" name="show-collumns" checked>
                            <label for="commodity">commodity</label>

                            <input type="checkbox" id="activity" value="activityName" name="show-collumns" checked>
                            <label for="activity">activity</label>

                            <input type="checkbox" id="supply-stage" value="foodSupplyStageName" name="show-collumns" checked>
                            <label for="base">food supply stage</label>

                            <input type="checkbox" id="loses-cause" value="lossCauseName" name="show-collumns" checked>
                            <label for="base">cause of loss</label>
                        </div>
                    </div>
                    <div class="grid-item6">
                        <div class="item-content">
                            <div class="year-set">    
                                <input type="number" id="year-range" name="year-range" min="1966" max="2022" value="1966">
                                <p>to</p>
                                <input type="number" id="year-range" name="year-range" min="1966 max="2022" value="2022">
                            </div>
                            <div class="year-display">
                                <input type="radio" id="range" value="by range" name="year-filter" checked>
                                <label for="range">show as range</label>
                                <input type="radio" id="base" value="start end" name="year-filter">
                                <label for="base">show start and end</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" id="filter-search" name="filter-search">search</input>
            </form>
            """;
        
        // Close Content div
        html = html + "</div>";

        html += "<div class=filterResult>";

        List <String> countries = context.formParams("countries");
        List <String> filters = context.formParams("show-collumns");
        List <String> yearRange = context.formParams("year-range");
        String yearFilter = context.formParam("year-filter");
        
        // gets the users country input and replaces them to be fit for the SQL query
        // if (countries != null) {
        //     countries = countries.replace(", ", "\" OR countryName=\"");
        //     countries = "countryName=\"" + countries + "\"";
        // }
        String totalCountrySearch = "";
        if (countries.size() > 0) {
            for (int i = 0; i < countries.size(); i++) { 
                if (i < countries.size() - 1) {
                    String countrySearch = "countryName=\"" + countries.get(i) + "\" OR ";
                    totalCountrySearch = totalCountrySearch + countrySearch;
                }
                else {
                    String countrySearch = "countryName=\"" + countries.get(i) + "\" ";
                    totalCountrySearch = totalCountrySearch + countrySearch;
                }
                
            }
        }

        System.out.println(totalCountrySearch);
        
        
        // collumncount += countries.split("countryName", -1).length - 1;
        // System.out.println(collumncount);

        boolean BoolCategory = false;
        boolean BoolActivity = false;
        boolean BoolStage = false;
        boolean BoolLossName = false;
        
        // checks if each indivisual filter is selected by the user
        if (filters.size() > 0) {
            for (int i = 0 ; i < filters.size(); i++) {
                String currFilter = filters.get(i);
                System.out.println(currFilter);
                if (currFilter.contains("categoryName")) BoolCategory = true;
                if (currFilter.contains("activityName")) BoolActivity = true;
                if (currFilter.contains("foodSupplyStageName")) BoolStage = true;
                if (currFilter.contains("lossCauseName")) BoolLossName = true;
            }    
        }
        
        // expands the filters List as a string, replaces spacing with comma for SQL query
        String expandedFilter = String.join(" ", filters);
        if (filters.size() > 0) expandedFilter = expandedFilter.replace(" ", ", ") + ", ";
        System.out.println(expandedFilter);

        // see's if user has inputed a year range, sets new variables for start and end year, then finally calls the countryFilter
        if (yearRange.size() > 0) {
            if (yearRange.get(0) == "") yearRange.set(0, "1966");
            if (yearRange.get(1) == "") yearRange.set(1, "2022");
            if (yearRange.get(0) != "" || yearRange.get(1) != "") {
                String startYear = yearRange.get(0);
                String endYear = yearRange.get(1);
                // sets the deafult query to be the example given upon page entry
                ArrayList<String> countryFilter = getCountryFilterRange("categoryName, activityName, foodSupplyStageName, lossCauseName, ", "countryName=\"Australia\" OR countryname=\"France\"", "1966", "2022", true, true, true, true);
                // query search for if show by year range is selected
                if (yearFilter.equals("by range"))  {
                    countryFilter = getCountryFilterRange(expandedFilter, totalCountrySearch, startYear, endYear, BoolCategory, BoolActivity, BoolStage, BoolLossName);
                }
                // query search for is show start and end is selected
                if (yearFilter.equals("start end")) {
                    countryFilter = getCountryFilterStartEnd(expandedFilter, totalCountrySearch, startYear, endYear, BoolCategory, BoolActivity, BoolStage, BoolLossName);
                } 
                
                if (totalCountrySearch.equals("")) {
                    html += "<p><b>No country has been selected, please select a country for data to be displayed</b></p>";
                }

                // sets the header columns for table based on the filters selected by the user
                html += "<table id='a2Results'> <tr>";
                    html += "<th>country</th>";
                    html += "<th>year</th>";
                    if (BoolCategory) html += "<th>commodity</th>";
                    if (BoolActivity) html += "<th>activity</th>";
                    if (BoolStage) html += "<th>supply stage</th>";
                    if (BoolLossName) html += "<th>loss cause</th>";
                    html += "<th>loss %</th>";
                html += "</tr> <tr>";

                int currcolumn = 0;
                // finds the total number of columns that are going to be displayed!! 3 for country, year and loss value, variable for how many filters the user has selected
                int collumncount = 3 + filters.size();
                // prints out name in indivisual column
                for (String attribute : countryFilter) {
                    if (attribute.equals("") || attribute == null) attribute += "--";
                    html = html + "<td>" + attribute + "</td>";
                    // total number of columns for current row
                    currcolumn += 1;
                    // if the it reaches the total column count, then it created a new row!!!
                    if (currcolumn > collumncount -1) {
                        html += "</tr>" + "<tr>";
                        currcolumn = 0;
                    }
                }
                html += "</tr> </table>";
            } 
        } 

        // html += "<p> you have currently selected " + countries + expandedFilter + yearRange + yearFilter + "we will display " + collumncount + " columns </p>";
        html += "</div>";
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

    public ArrayList<String> getCountryFilterRange(String filter, String countries, String startYear, String endYear, Boolean BoolCategory, Boolean BoolActivity, Boolean BoolStage, Boolean BoolLossName) {
        ArrayList<String> filterResults = new ArrayList<String>();

        Connection connection = null; 

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "Select countryName, year, " + filter + "lossValue " +
                    "From LossWithoutRegion AS L " +
                    "JOIN Country as C JOIN foodType as F " +
                    "where F.cpc = L.cpc AND L.m49code = C.m49code " +
                    "AND (year >= " + startYear + " AND year <= " + endYear + ") " +
                    "AND (" + countries + ") " +
                    "GROUP BY L.cpc " +
                    "ORDER BY year";
            
            System.out.print(query);


                    ResultSet results = statement.executeQuery(query);

                    while (results.next()) {
                        String country = results.getString("CountryName");
                        filterResults.add(country);

                        String year = results.getString("year");
                        filterResults.add(year);

                        if (BoolCategory) {
                            String foodName = results.getString("categoryName");
                            filterResults.add(foodName);
                        }
                        if (BoolActivity) {
                            String activity = results.getString("activityName");    
                            filterResults.add(activity);
                        }
                        if (BoolStage) {
                            String foodStage = results.getString("foodSupplyStageName");
                            filterResults.add(foodStage);
                        }
                        if (BoolLossName) {
                            String lossCause = results.getString("lossCauseName");
                            filterResults.add(lossCause);
                        }
                        String lossValue = results.getString("lossValue");
                        filterResults.add(lossValue);
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
        // System.out.println(filterResults);
        return filterResults;
    }

    public ArrayList<String> getCountryFilterStartEnd(String filter, String countries, String startYear, String endYear, Boolean BoolCategory, Boolean BoolActivity, Boolean BoolStage, Boolean BoolLossName) {
        ArrayList<String> filterResults = new ArrayList<String>();

        Connection connection = null; 

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "Select countryName, year, " + filter + "lossValue " +
                    "From LossWithoutRegion AS L " +
                    "JOIN Country as C JOIN foodType as F " +
                    "where F.cpc = L.cpc AND L.m49code = C.m49code " +
                    "AND (year = " + startYear + " OR year = " + endYear + ") " +
                    "AND (" + countries + ") " +
                    "GROUP BY L.cpc " +
                    "ORDER BY year";
            
            System.out.print(query);


                    ResultSet results = statement.executeQuery(query);

                    while (results.next()) {
                        String country = results.getString("CountryName");
                        filterResults.add(country);

                        String year = results.getString("year");
                        filterResults.add(year);

                        if (BoolCategory) {
                            String foodName = results.getString("categoryName");
                            filterResults.add(foodName);
                        }
                        if (BoolActivity) {
                            String activity = results.getString("activityName");    
                            filterResults.add(activity);
                        }
                        if (BoolStage) {
                            String foodStage = results.getString("foodSupplyStageName");
                            filterResults.add(foodStage);
                        }
                        if (BoolLossName) {
                            String lossCause = results.getString("lossCauseName");
                            filterResults.add(lossCause);
                        }
                        String lossValue = results.getString("lossValue");
                        filterResults.add(lossValue);
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
        // System.out.println(filterResults);
        return filterResults;
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