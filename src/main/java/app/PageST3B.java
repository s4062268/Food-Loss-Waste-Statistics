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

public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/similarFoodGroups";

    @Override
    public void handle(Context context) throws Exception {

        //Creating jdbc object
        JDBCConnection jdbc = new JDBCConnection(); 

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Similar Food Groups</title>";

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
            <ul class="breadcrumb">
                <li><a href="/">Homepage</a></li>
                <li>By Food</a></li>
                <li>Similar Food Groups</a></li>
            </ul>
            
                <h1>Similar Food Groups</h1>
           
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content


        //Storing temporary user selection to be displayed after submitting form
        String foodCommodity_drop1 = context.formParam("foodCommodity_drop");
        if (foodCommodity_drop1  == null  ) {
            foodCommodity_drop1  = "Alfalfa for forage";
            
        }
        
        
        
        String similarityBy_drop = context.formParam("similarGroupsBy_drop");
        if (similarityBy_drop  == null  ) {
            similarityBy_drop  = "Ratio of Food Loss to Food Waste (Percentage Average)";
            
        }



        String numGroups1 = context.formParam("numGroups");
        if (numGroups1  == null  ) {
            numGroups1  = "5";
            
        }


        String sortSelection1 = context.formParam("sort");
        String r1 = "checked";
        String r2 = "";
        if (sortSelection1 == null ){
            sortSelection1 = "DESC";

        }
        
        if (sortSelection1.equals("DESC")){
            r1 = "";
            r2 = "checked";
        }


      
        //Specifying URL for POST 
        html = html + """
            <div class = "centerDiv">
            <div><form action='/similarFoodGroups' method='post'>
            <div class='form-group'>
                <h3>SEARCH RECORDS BASED ON</h3>
                <div class="actionPanelST3B"> 
                

                <div class="fullGridContainer3">
                <div class="gridPart"><label for='foodCommodity_drop'><p> Food Commodity </p></label>
                                             <select id='foodCommodity_drop' name='foodCommodity_drop'>
                                              
                                            
                                                """;
                                                html += "<option>" + foodCommodity_drop1 +"</option>";
                                             ArrayList<String> foodcommodityOptions = new ArrayList<String>();
                                             foodcommodityOptions = jdbc.foodCommodities();
                                                
                                                for (String commodity : foodcommodityOptions){
                                                    if (!foodCommodity_drop1.equals(commodity))
                                                    html += "<option>" + commodity + "</option>";
                                                }
                                                
                                                html += "</select></div>" + """
                                             
                                             
                <div class="gridPart"><label for='similarGroupsBy_drop'><p> Similar Groups By </p></label> 
                        
                                            <select id='similarGroupsBy_drop' name='similarGroupsBy_drop'>
                                                
                                            
                                                """;
                                                html += "<option>" + similarityBy_drop +"</option>";
                                                if (!similarityBy_drop.equals("Ratio of Food Loss to Food Waste (Percentage Average)")){
                                                    html += "<option>Ratio of Food Loss to Food Waste (Percentage Average) </option>";
                                                }
                                                if (!similarityBy_drop.equals("Highest Percentage of Food Loss/Waste Product")){
                                                    html += "<option>Highest Percentage of Food Loss/Waste Product</option>";
                                                }
                                                if (!similarityBy_drop.equals("Lowest Percentage of food Loss/Waste Product")){
                                                    html += "<option>Lowest Percentage of food Loss/Waste Product</option>";
                                                }

                                         html+="</select></div>" +"""
                                                 
                                            
                
                <div class="gridPart"><label for='numGroups'><p> Number of similar groups </p></label>
                                            """;
                                  html +=    "<input type=\"number\" id=\"numGroups\" name=\"numGroups\" value=\"" + numGroups1 + "\" min = \"1\" title=\"Please enter a valid number.\" required>" + """
                                          
                                            </select></div>
                
                <div class="gridPart"><label for='sort'><p> Sort By Similarity Score </p></label>
               """;
                               html +=   "<input type=\"radio\" id=\"sort\" name=\"sort\" value=\"ASC\" " + r1 +" >"+"""
                                       
                                      
                                            <label for="ASC" class = "radio"> Ascending</label><br>
                                """;
                               html +=     "<input type=\"radio\" id=\"sort\" name=\"sort\" value=\"DESC\" " + r2 + ">" + """
                                                
                                        
                                            <label for="DESC" class = "radio"> Descending</label><br></div>
                                      
                </div>
            </div>
            <button type='submit' class='btn btn-primary'>SEARCH</button>
            </form>
            <br></br>
            <hr>
                    """;

         
          String commodity = context.formParam("foodCommodity_drop");
          String similarityBy = context.formParam("similarGroupsBy_drop");
          String numGroups = context.formParam("numGroups");
          String sortSelection = context.formParam("sort");
          ArrayList<String[]> display = new ArrayList<String[]>();
          String sim = "";


          if (commodity != null && numGroups != null && sortSelection!= null && similarityBy.equals("Highest Percentage of Food Loss/Waste Product")){
            display = jdbc.SimilarityHighestLowestLossWaste(commodity, "MAX", numGroups, sortSelection);
            sim = "Highest Percentage of Food Loss/Waste";

          }

          else if (commodity != null && numGroups != null && sortSelection!= null && similarityBy.equals("Lowest Percentage of food Loss/Waste Product")){
            display = jdbc.SimilarityHighestLowestLossWaste(commodity, "MIN", numGroups, sortSelection);
            sim = "Lowest Percentage of food Loss/Waste";

          }

          else if (commodity != null && numGroups != null && sortSelection!= null && similarityBy.equals("Ratio of Food Loss to Food Waste (Percentage Average)")){
            display = jdbc.SimilarityLossWasteRatio(commodity,  numGroups, sortSelection);
            sim = "Ratio of Food Loss to Waste";

          }



            
            if (commodity != null && !commodity.equals("")){
                html += "<h3>RESULTS</h3>";
                        
                

                html += "<p>Food Commodity <span class=\"commodity\">" + commodity + "</span> belongs to the food group: <span class=\"group\">" + jdbc.level3FoodGroup(commodity).toUpperCase() + "</span>. Below are "+ numGroups +" similar food groups by " + sim + ".</p>" +"""
                        

                <div class="TableOutput"> <table id = "results">
                    <tr>
                        <th>Similar Food Groups</th>
                        """;

        html+=          "<th>" + sim + " of " +jdbc.level3FoodGroup(commodity) + "</th>";
                      
        html +=                "<th>" + sim + " of Similar Groups</th>";
        html +=        "<th>Difference</th>" +"""
                
                
                        <th class="similarityScore" similarityScore-msg="Determines how similar the group's checked percentage is to that of the chosen commodity's food group."> Similarity Score</th>
                    </tr>
                """;
                
                
                
                for (String[] columnResultRecord : display){
                    html+=      "<tr>";       
                    for(String recordItem : columnResultRecord){
                        html += "<td>" + recordItem + "</td>";
        
                    }
                    html += "</tr>";
                 
                }        
        
        
                        
                html += "</tr>" + """
                        </table></div></div>
                        """;

        }

        // Close Content div
        html = html + "</div>";

        // Footer
        PageFooter footer = new PageFooter();
        html += footer.main();

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
