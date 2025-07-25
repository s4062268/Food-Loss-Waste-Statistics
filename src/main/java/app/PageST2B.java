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

public class PageST2B implements Handler {

    

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/foodLossWasteChangeByFoodGroup";

    @Override
    public void handle(Context context) throws Exception {
        //Creating jdbc object
        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Food Loss/Waste Change by Food Group</title>";

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
                <li>By Food</li>
                <li>Food Loss and Waste Change</li>
            </ul>
            <!-- div class='header'>
                <!-- h1>Food Loss and Waste Change</h1 -->
            </div -->
        """;
        
        html = html + "<h1>Food Loss and Waste Change</h1>";

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content

        //Storing temporary user selection to be displayed after submitting form
        String activity_drop1 = context.formParam("activity_drop");
        if (activity_drop1 == null  ) {
            activity_drop1="All";
            
        }
        String foodSupplyStage_drop1 = context.formParam("foodSupplyStage_drop");
        if (foodSupplyStage_drop1 == null  ) {
            foodSupplyStage_drop1 = "All";
            
        }

        String lossCause_drop1 = context.formParam("causeOfLoss_drop");
        if (lossCause_drop1 == null  ) {
            lossCause_drop1 = "All";
            
        }

        String startYear_drop1 = context.formParam("startYear_drop");
        if (startYear_drop1 == null  ) {
            startYear_drop1 = "1966";
            
        }

         String endYear_drop1 = context.formParam("endYear_drop");
         if (endYear_drop1 == null  ) {
             endYear_drop1 = "1966";
            
         }


         String foodgroup_drop1 = context.formParam("foodgroup_drop");
         //System.out.println(foodgroup_drop1);

         //ArrayList<String> chosenfoodGroups1 = new ArrayList <String> ();
         if (foodgroup_drop1 == null){
             foodgroup_drop1="";
         }
     
         foodgroup_drop1=foodgroup_drop1.replace("|", "| ");


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
            <div><form action='/foodLossWasteChangeByFoodGroup' method='post'>
                <div class='form-group'>
                    <h3>SEARCH RECORDS BASED ON</h3>
                    <div class="actionPanelST2B"> 
                    

                    <div >
                    <div class="gridPart"><label for='foodgroup_drop'><p> Food Group </p></label>
                                            
                                                <div class="mvw_dropdown-container">
                                                   <div class="mvw_dropdown">
                                                   <button type="button" id = "foodGrp" onclick="mvw_toggleDropdown()">Select Options</button>
                                                     <div id="mvw_options" class="mvw_dropdown-content">
                                                """;
                                                ArrayList<String> foodGroupOptions = new ArrayList<String>();
                                                foodGroupOptions = jdbc.foodGroups();

                                                for (String foodGroup : foodGroupOptions){
                                                    
                                                    html += "<label><input type=\"checkbox\" value=\"" + foodGroup + "\"> "+ foodGroup + "</label>";                                                }
                                                    

                                                
                                        html += "</div>" + """
                                                
                                                
                                                </div>
                                                """;
                                       html +=     "<div class=\"mvw_selected-items\">" + foodgroup_drop1 +"</div>" +"""
                                               
                                                <div class="gridPart"><input type="hidden" name="foodgroup_drop" id="foodgroup_drop"></div>
                                            </div>
                                            </div>
                                    

                                        <script>
                                            function mvw_toggleDropdown() {
                                                document.getElementById("mvw_options").classList.toggle("mvw_show");
                                            }

                                            window.onclick = function(event) {
                                                if (!event.target.closest('.mvw_dropdown')) {
                                                    document.querySelectorAll('.mvw_dropdown-content').forEach(mvw_dropdown => {
                                                        mvw_dropdown.classList.remove('mvw_show');
                                                    });
                                                }
                                            }

                                            document.querySelectorAll('.mvw_dropdown-content input[type="checkbox"]').forEach(mvw_checkbox => {
                                                mvw_checkbox.addEventListener('change', function() {
                                                    const mvw_text = this.value;
                                                    const mvw_selectedItems = document.querySelector('.mvw_selected-items');
                                                    const mvw_selectedItemsInput = document.getElementById('foodgroup_drop');
                                                    const mvw_selectedTexts = new Set(mvw_selectedItems.innerText.split('| ').filter(Boolean));

                                                    this.checked ? mvw_selectedTexts.add(mvw_text) : mvw_selectedTexts.delete(mvw_text);

                                                    const mvw_selectedTextsArray = Array.from(mvw_selectedTexts);
                                                    mvw_selectedItems.innerText = mvw_selectedTextsArray.join('| ');
                                                    mvw_selectedItemsInput.value = mvw_selectedTextsArray.join('|');
                                                });
                                            });
                                           
                                        </script>

        
                    
                    </div>

                    <div class="fullGridContainer2">
                        
                        <div class="gridPart"><label for='activity_drop'><p> Activity </p></label>
                                                <select id='activity_drop' name='activity_drop' required>
                                                
                                                """;
                                                html += "<option>" + activity_drop1 +"</option>";
                                            
                                                ArrayList<String> activityOptions = new ArrayList<String>();
                                                activityOptions = jdbc.activities();
                                                
                                                for (String activity : activityOptions){
                                                    html += "<option>" + activity + "</option>";
                                                }
                                                
                                                html += "</select></div>" + """
                                                  
                                              
                        
                        <div class="gridPart"><label for='foodSupplyStage_drop'><p> Food Supply Stage </p></label>
                                                <select id='foodSupplyStage_drop' name='foodSupplyStage_drop' required>
                                                
                                                """;
                                                html += "<option>" + foodSupplyStage_drop1 +"</option>";
                                                ArrayList<String> foodSupplyStageOptions = new ArrayList<String>();
                                                foodSupplyStageOptions = jdbc.foodSupplyStages();
                                                
                                                for (String foodSupplyStage : foodSupplyStageOptions){
                                                    html += "<option>" + foodSupplyStage + "</option>";
                                                }
                                                
                                                html += "</select></div>" + """
                                               
                        
                        <div class="gridPart"><label for='causeOfLoss_drop'><p> Cause of Loss </p></label>
                                                <select id='causeOfLoss_drop' name='causeOfLoss_drop' required>
                                                
                                                """;
                                                html += "<option>" + lossCause_drop1 +"</option>";
                                                ArrayList<String> lossCauseOptions = new ArrayList<String>();
                                                lossCauseOptions = jdbc.lossCauses();
                                                
                                                for (String lossCause : lossCauseOptions){
                                                    html += "<option>" + lossCause + "</option>";
                                                }
                                                
                                                html += "</select></div>" + """
                        <div class="gridPart"></div>
                        <div class="gridPart"><label for='startYear_drop'><p> Start Year</p></label>
                                                <select id='startYear_drop' name='startYear_drop' required>
                                                
                                                """;

                                                html += "<option>" + startYear_drop1 +"</option>";
                                                
                                                ArrayList<Integer> startYearOptions = new ArrayList<Integer>();
                                                startYearOptions = jdbc.years();
                                                
                                                for (Integer year : startYearOptions){
                                                    if (!startYear_drop1.equals(String.valueOf(year))) {
                                                    html += "<option>" + year + "</option>";
                                                }
                                                    
                                                }
                                                
                                                html += "</select></div>" + """
                        <div class="gridPart"><label for='endYear_drop'><p> End Year</p></label>
                                                <select id='endYear_drop' name='endYear_drop' required>
                                                
                                                """;
                                                html += "<option>" + endYear_drop1 +"</option>";
                                                ArrayList<Integer> endYearOptions = new ArrayList<Integer>();
                                                endYearOptions = jdbc.years();
                                                
                                                for (Integer year : endYearOptions){
                                                    if (!endYear_drop1.equals(String.valueOf(year))) {
                                                        html += "<option>" + year + "</option>";
                                                    }
                                                }
                                                
                                                html += "</select></div>" + """ 
                       
             
                    </div>
                    <div class="fullGridContainer4">
                    <div class="gridPart"><p> Columns to Display </p>
                        <div class="fullGridContainer5">
                                          <input  class = "BCheck" type="checkbox" id="column1" name="column1" value="FoodGroupNeeded" checked disabled>
                                          <label class="checkboxesGroup1" for="column1"> Food Group<span class="spacetab"></span></label><br>
                                          <input class = "BCheck" type="checkbox" id="column2" name="column2" value="activityNeeded" checked>
                                          <label class="checkboxesGroup1" for="column2"> Activity<span class="spacetab"></span></label><br>
                                          <input class = "BCheck" type="checkbox" id="column3" name="column3" value="foodSupplyStageNeeded" checked>
                                          <label class="checkboxesGroup1" for="column3"> Food Suply Stage<span class="spacetab"></span></label><br>
                                          <input class = "BCheck" type="checkbox" id="column4" name="column4" value="lossCauseNeeded" checked>
                                          <label class="checkboxesGroup1" for="column4"> Cause of Loss<span class="spacetab"></span></label><br>
                                          <input class = "BCheck" type="checkbox" id="colum5" name="column5" value="lossPercentageNeeded"  checked disabled>
                                          <label class="checkboxesGroup1" for="column5"> Food Loss/Waste Percentage Change </label><br> 
                        </div>
                    </div>


                    <div class="gridPart"></div>
                    <div class="gridPart"><p id= sortP> Sort by Loss/Waste Change </p>
                        <div class="fullGridContainer5">
                        """;
                                  html+=        "<input class = \"Bradio\" type=\"radio\" id=\"sort\" name=\"sort\" value=\"ASC\" " + r1 + ">" + """
                                          
                                                <label for="ASC" class = "radio"> Ascending</label><br>

                           """;
                                  html+=       "<input class = \"Bradio\" type=\"radio\" id=\"sort\" name=\"sort\" value=\"DESC\" " + r2 + ">" + """               
                                                
                                                <label for="DESC" class = "radio"> Descending</label><br>
                        </div>

                    </div>
                    
                    </div>
                    
                </div>
                <button type='submit' class='btn btn-primary' >SEARCH</button>
            </form>
            <br></br>
            <hr>

                <script>
        const startYearSelect = document.getElementById('startYear_drop');
        const endYearSelect = document.getElementById('endYear_drop');

    
        function updateEndYearOptions() {
    const selectedStartYear = parseInt(startYearSelect.value);
    endYearSelect.innerHTML = ''; 

    for (let year = selectedStartYear; year <= 2022; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.text = year;
        endYearSelect.appendChild(option);
    }
}

    updateEndYearOptions(); 
    startYearSelect.addEventListener('change', updateEndYearOptions);


        </script>
        
                
         """;

         /* Getting the Form Data*/
         String sortSelection = context.formParam("sort");
        //  System.out.print(sortSelection);
         String criteria = "";
         String foodgroup_drop = context.formParam("foodgroup_drop");
         // System.out.println(foodgroup_drop);

         ArrayList<String> chosenfoodGroups = new ArrayList <String> ();
         if (foodgroup_drop != null){

            String [] temp = foodgroup_drop.split("\\|");
            for (String x : temp){
                chosenfoodGroups.add(x);
            }



         }
         //System.out.println(chosenfoodGroups);

         //criteria.add(foodgroup_drop);
         //if (foodgroup_drop == null) { Commented out as required attributes do not allow nulls.
             // If NULL, nothing to show, therefore we make some "no results" HTML
             //  html = html + "<h2><i>No Results to show for dropbox</i></h2>";
         //} else {
             // If NOT NULL, then lookup the result by activity!
             //}




        String activity_drop = context.formParam("activity_drop");
        if (activity_drop != null && !activity_drop.equals("All") ) {
            criteria += " AND a.activity = '" + activity_drop + "' ";
            //System.out.println("Activity....."+ activity_drop + "....");
            
        }
        
       

        String foodSupplyStage_drop = context.formParam("foodSupplyStage_drop");
        if (foodSupplyStage_drop != null && !foodSupplyStage_drop.equals("All" )) {
            // if (criteria.isEmpty()){
            //     criteria += "a.foodSupplyStage = '" + foodSupplyStage_drop + "' ";
            // }
            // else {
                criteria += " AND a.foodSupplyStage = '" + foodSupplyStage_drop + "' ";
            // }
        
        }
        //if (foodSupplyStage_drop == null) { Commented out as required attributes do not allow nulls.
            // If NULL, nothing to show, therefore we make some "no results" HTML
            //  html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        //} else {
            // If NOT NULL, then lookup the result by activity!
            //}
        

        String causeOfLoss_drop = context.formParam("causeOfLoss_drop");
        
        if (causeOfLoss_drop != null && !causeOfLoss_drop.equals("All") ) {
            // if (criteria.isEmpty()){
            //     criteria += "lossCauseName = '" + causeOfLoss_drop + "' ";
            // }
            // else {
                criteria += "AND a.lossCause = '" + causeOfLoss_drop + "' ";
            // }
        }
        //else{criteria.add("");}

        //if (causeOfLoss_drop == null) { Commented out as required attributes do not allow nulls.
            // If NULL, nothing to show, therefore we make some "no results" HTML
            //  html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        //} else {
            // If NOT NULL, then lookup the result by activity!
            //}
        
        String startYear_drop = context.formParam("startYear_drop");
        String endYear_drop = context.formParam("endYear_drop");
        
        
        
        
        
        //if (endYear_drop == null) { Commented out as required attributes do not allow nulls.
            // If NULL, nothing to show, therefore we make some "no results" HTML
            //  html = html + "<h2><i>No Results to show for dropbox</i></h2>";
        //} else {
            // If NOT NULL, then lookup the result by activity!
            //}

        String column1 =context.formParam("column1");
        String column2 =context.formParam("column2");
        String column3 =context.formParam("column3");
        String column4 =context.formParam("column4");
        String column5 =context.formParam("column5");

        
        // ArrayList<String> columnsToShow = new ArrayList<String>();
        ArrayList<String> actualColumnDisplay = new ArrayList<String>();
        String columnsToShow = "";
        //if (column1 != null){
            // columnsToShow.add("foodGroup");
            //columnsToShow = columnsToShow + "foodGroup";
            actualColumnDisplay.add("Food Group");
        //}
        if (column2 != null){
            //if (!columnsToShow.isEmpty() && columnsToShow.charAt(columnsToShow.length() - 1) != ',') {
                columnsToShow = columnsToShow + ", a.Activity AS activity";
            // }
            // else {
            //     columnsToShow = columnsToShow + "activity";
            // }
            actualColumnDisplay.add("Activity");
        }

    

        if (column3 != null){
            // if (!columnsToShow.isEmpty() && columnsToShow.charAt(columnsToShow.length() - 1) != ',') {
            //     columnsToShow = columnsToShow + ", a.FoodSupplyStage AS foodSupplyStage";
            // }
            // else {
                columnsToShow = columnsToShow + ", a.FoodSupplyStage AS foodSupplyStage";
            // }
             actualColumnDisplay.add("Food Supply Stage");
        }

        if (column4 != null){
            // if (!columnsToShow.isEmpty() && columnsToShow.charAt(columnsToShow.length() - 1) != ',') {
            //     columnsToShow = columnsToShow + ", a.LossCause AS lossCauseName,";
            // }
            // else {
                columnsToShow = columnsToShow + ", a.LossCause AS lossCauseName ";
            //}
            actualColumnDisplay.add("Cause of Loss");
        }
        
        if (!columnsToShow.contains("a.FoodSupplyStage") || !columnsToShow.contains("a.LossCause") || !columnsToShow.contains("a.Activity")){
            columnsToShow += ", ROUND(AVG(COALESCE(b.AvgLossPercentageEnd, 0) - COALESCE(a.AvgLossPercentageStart, 0)),2) AS lossValueChange ";
        }
        else{
            columnsToShow += ", ROUND(COALESCE(b.AvgLossPercentageEnd, 0) - COALESCE(a.AvgLossPercentageStart, 0),2) AS lossValueChange ";
        }

        
        // if (column5 != null){
            // if (!columnsToShow.isEmpty() && columnsToShow.charAt(columnsToShow.length() - 1) != ',') {
            //     columnsToShow = columnsToShow + ", MAX(lossValue)-MIN(lossValue) AS lossValueChange";
            // }
            // else {
            //     columnsToShow = columnsToShow + "MAX(lossValue)-MIN(lossValue) AS lossValueChange";
            // }
            actualColumnDisplay.add("Food Loss/Waste Percentage Change");
        
        //}
        String[] NewChosenFoddGroups = new String [chosenfoodGroups.size()];
        for (int a = 0; a < NewChosenFoddGroups.length; ++a) {
            //System.out.println(actualColumnDisplay.get(a));
            NewChosenFoddGroups[a] = chosenfoodGroups.get(a);

        }
        ArrayList<String[]> display = new ArrayList<String[]>();

        
        display = jdbc.WasteLossByFoodGroup(NewChosenFoddGroups, columnsToShow, criteria, startYear_drop, endYear_drop, sortSelection);


        
        for (int m = 0; m < display.size(); ++m){
            //System.out.println(display.get(m));
        }
       

        if (display.size()>0){ html += "<h3>RESULTS</h3>";}
        
        if (display.size()==0 && NewChosenFoddGroups.length != 0) {
            html += "<h3>RESULTS</h3>";
            html += "<p style=\"color: red; font-size: 24px; text-align: center;\"><b>There are no records that exist for your chosen combination. Please Try again.</b></p>";
        }
        
        html +=    "<div class=\"TableOutput\"><table id = \"results\">" + """
                
                <tr>
                """;
                if (NewChosenFoddGroups.length != 0 && display.size()>0){
                    for (String column : actualColumnDisplay){
                        html += "<th>" + column + "</th>";
                    }
                 }
                   
        html +=     "</tr>";



        
        for (String[] columnResultRecord : display){
            html+=      "<tr>";
            for(String recordItem : columnResultRecord){
                if (recordItem.isEmpty() || recordItem.equals(" ")){
                    recordItem = "-";
                }
                html += "<td>" + recordItem + "</td>";

            }
            html += "</tr>";
             
        }        


                
        html += "</tr>" + """
                </table></div></div>
                """;
                
                
    

            

        
        // Close Content div
        html = html + "</div>";
        // String xxxxx = context.formParam("column1");
        // html ="<p>" +  xxxxx + "</p>";
        // Footer
        PageFooter footer = new PageFooter();
        html += footer.main();

        // Finish the HTML webpage
        html = html + "</body></html>";
        
        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
