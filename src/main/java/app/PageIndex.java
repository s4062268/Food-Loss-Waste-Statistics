package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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


public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        NavigationBar NavBar = new NavigationBar();
        html += NavBar.main();

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                    <!-- img src='logo.png' class='top-image' alt='RMIT logo' height='75' -->
                    Food Loss and Waste Recorder
                </h1>
                <p>Collecting data about food loss and waste from 1966 - 2022.</p>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h2>What we do</h2>
            <div class="HomeSec1">
            <div class="sec1-left">
                <p style="padding-right: 3rem"> The social challenge of Food Loss and Waste has been a growing concern internationally, with increasing food scarecity globally, we grow closer to the need for extreme action. Our site, The FoodLossWasteTracker</p>
                <ul>
                    <li> Helps educate the world about the challenges of food loss and waste </li>
                    <li> Gives every day people access to a large collection of food loss and waste records internationally </li>
                    <li> Easy sorting and filtering of over our vast record collection </li>
                </ul>
                <p style="padding-right: 3rem; margin-top: 0rem">we hope to further inform and educate everyday people about the social challenge of food loss and waste, and the ever expanding impact it has on our fellow humans </p>
            </div>
                <img src='FoodWaste1.webp' class='main-image' alt='RMIT logo' height='325' width='1100' style='margin-right: 4rem; margin-bottom: 1rem'>
            </div>
            """;
        
            // Close Content div
        html = html + "</div>";


        
        
        html += "<div class='HomeSec2'>";

        
        html += """
                <svg class ='backgroundImage' xmlns="http://www.w3.org/2000/svg" width="1440" height="275" viewBox="0 0 1440 275" fill="none">
                    <path d="M6.85954e-05 63.4464H1440V209H6.85954e-05V63.4464Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M804.513 5.46349L583.529 29.89L258.488 17.4701L6.85954e-05 35.9897V49.8028L278.522 29.8484L621.661 42.9601L869.448 15.5701L1440 10.6649V0L804.513 5.46349Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M934.388 25.6761L659.793 56.0291L298.553 42.2259L6.85954e-05 63.6161V84.1704L371.422 88.1626L666.383 66.4261L992.461 76.9391L1440 38.4628V21.329L934.388 25.6761Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M1023.54 89.2743L691.332 78.564L410.293 99.2743L6.85954e-05 94.8648V120.928H1440V53.4696L1023.54 89.2743Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M505.612 271.735L780.207 248.94L1141.45 259.306L1440 243.242V227.806L1068.58 224.807L773.617 241.132L447.539 233.236L5.11728e-05 262.132L6.85954e-05 275L505.612 271.735Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M416.461 223.972L748.668 232.016L1029.71 216.462L1440 219.774V200.2L0 200.2L6.85954e-05 250.862L416.461 223.   972Z" fill="#F3F3F3" style="width: 100"/>
                    <path d="M0 176H1440V275H6.85954e-05L0 176Z" fill="#F3F3F3" style="width: 100"/>
                </svg>
                """;
        
        ArrayList<String> highestLoss = highestLoss();

        html += "<h1>The largest food loss of " + highestLoss.get(0) + " was in The " + highestLoss.get(3) + " where " + highestLoss.get(2) + "% of " + highestLoss.get(1) + " was lost</h1>";

        html += """ 
            <div class='homeSec3'>
            <img src='exampleGraph.png' class='main-image' alt='RMIT logo' height='240' width='400' style='margin-right: 4rem; margin-bottom: 1rem; margin-left: 5rem;'>
                <div class='sec3-right'>
                    <h2> What you can do </h2>
                    <p align="right">With The FoodLossWasteTracker you can find, filter, and digest the challenege of Food Loss and Waste from the years 1966-2022. Our state of the art record filtering tools allow you to find any information in regards to Food Loss by commodity or by country, whether thats raw loss values or similarities. We hope you find this information useful for any needs, whether thats personal, educational or professional.</p>
                </div>
            </div>
                """;
        
        html += "</div>";

        // Finish the List HTML
        html = html + "</tr>" + "</table>";
        

        

        // Footer

        PageFooter footer = new PageFooter();
        html += footer.main();
        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }


    /**
     * Get the names of the countries in the database.
     */
    public ArrayList<String> highestLoss() {
        ArrayList<String> losses = new ArrayList<String>();

        Connection connection = null; 

        try {
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = """
                SELECT year, CountryName, categoryName, L.cpc, lossValue, activityName
                From lossWithoutRegion as L
                JOIN Country as C  
                JOIN foodType as F 
                where F.cpc = L.cpc AND L.m49code = C.m49code AND year = '2012'
                GROUP BY L.cpc
                ORDER BY lossValue DESC
                    """;

                    ResultSet results = statement.executeQuery(query);

                    int topRow = 0;
                    while (topRow < 1) {
                        String year = results.getString("year");
                        String foodName = results.getString("categoryName");
                        String lossValue = results.getString("lossValue");
                        String country = results.getString("countryName");

                        losses.add(year);
                        losses.add(foodName);
                        losses.add(lossValue);
                        losses.add(country);
                        
                        topRow+=1;
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

        return losses;
    }
}
