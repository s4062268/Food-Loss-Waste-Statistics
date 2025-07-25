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

public class PageMission implements Handler {
    //Creating jdbc object
    JDBCConnection jdbc = new JDBCConnection();
    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission";

    @Override
    public void handle(Context context) throws Exception {



        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
                <li>Our Mission</li>
            </ul>
            """;




        html = html + """
            
                <h1>Our Mission</h1>
            
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <div class="mission">
            <h3>OUR PURPOSE</h3>
            
            <div class="purpose">
            <p class='missionText'><img id = "waste" src="food_waste.jpg" alt="Image of Food waste/loss" width="450" height="290">This website intends to address the Social Challenge of Food Loss and Waste by raising awareness and educating key parties of the supply chain
            on the sources of food loss and waste. Food Loss and Waste is a major issue today and takes place during harvest/catch/slaughter stages up to
            the sales level (exclusive) of the supply chain, whilst food waste occurs during the retail and consumption stages. Food Loss and Waste may be a 
            result of various issues that take place in the different stages of the supply chain. In addition, this may further impact other global challenges such as food security,
            climate change, resource availablity and more. Hence, it is important to address this challenge by raising awareness and education levels on this topic among people
            both with and without expertise in this field. In order to do that, this website allows the examination of Food Loss and waste by Country, Year, the Activity during
            which this loss/waste occured, the stage in the supply chain during which this loss/waste occured and even the the type of food itself. This would help
            create a better understanding of this challenge, how the situation of the challenge has changed depending on different variables and also 
            helps persuade responsible parties to find solutions to resolve this challenge.</p></div>
             
            <br></br>
            <hr>
            <br></br>
            
            <h3>HOW CAN THE SITE BE USED?</h3>
            <p class='missionText'> This site can be used to do the following: <p>
            <ol>
                <li class = 'siteUseList'><b><a href= "http://localhost:7001/Food-Loss-and-Waste-by-Country"> The Food Loss and Waste by Country Page</a>:</b> Can be used to examine the food loss/waste
                    of a particular country or region within a country. The food loss/waste percentage level variations can be seen 
                    by the commodity type, activity, cause of loss and even by the years. It provides an overall idea
                    of the food loss and waste of that particular location.</li> 
                <li class = 'siteUseList'><b><a href= "http://localhost:7001/foodLossWasteChangeByFoodGroup"> The Food Loss and Waste Change by Food Group Page</a>:</b> Can be used to examine food loss/waste by different 
                    food groups and make comparisons between multiple food groups. It provides an overall wastage/loss
                    percentage based on differnt user selected criteria including waste/loss by specific activity, food supply
                    stage, cause of loss/waste and the year in which the loss/waste took place</li>
                <li class = 'siteUseList'><b><a href= "http://localhost:7001/Location-Loss-and-Waste-by-Similarity"> Similarity of Food Loss and Waste by Location Page</a>:</b> Can be used to compare locations which 
                    share similarities in relation to food loss and waste.</li>
                <li class = 'siteUseList'><b><a href= "http://localhost:7001/similarFoodGroups"> Similarity of Food Loss and Waste by Food Groups and Commodities Page</a>:</b> Can be used to compare Food Groups 
                     which share similarities in relation to food loss and waste.</li>

            </ol>
            <br></br>
            <hr>
            <br></br>
            <h2>PERSONAS</h2>
            <p class='missionText'>The following are some examples of people who may find this site useful. Their background and requirements
            may aid in identifying how to utilize this website and what sort of people are likely to benefir the most from this website.</p></div>
            
                 """;
                
        
       
        String pic1 = "";
        String pic2 = "";
        String pic3 = "";
        ArrayList<String> imgs1 = jdbc.getAllPersonaDetails("Rachel Robertson", "Image");
        for (String img: imgs1){
            pic1 += img;
        }

        ArrayList<String> imgs2 = jdbc.getAllPersonaDetails("Cindy Brown", "Image");
        for (String img: imgs2){
            pic2 += img;
        }
        
        ArrayList<String> imgs3 = jdbc.getAllPersonaDetails("Romeo Matar", "Image");
        for (String img: imgs3){
            pic3 += img;
        }

        html += "<div class=\"background-div\">";
        html +=  "<div class=\"image-container\">";
        
        html += "<div class=\"fullGridContainerMission\"><div class=\"gridPart\">";
        html += "<img class= \"persona\" src=\""+pic1;
        
        html += "\"onclick=\"showPopup('popup1')\" alt=\"Rachel Robertson\">"+ "</div>";
       
        html += "<div class=\"gridPart\">";
        html += "<img class= \"persona\" src=\""+pic2;

        html += "\"onclick=\"showPopup('popup2')\" alt=\"Cindy Brown\">"+ "</div>";
        
        html += "<div class=\"gridPart\">";
        html += "<img class= \"persona\" src=\""+pic3;

        html += "\"onclick=\"showPopup('popup3')\" alt=\"Romeo Matar\">" + "</div></div>";
        html +="</div></div>" +"""
                
      
      <div id="overlay" class="overlay"></div>
      
      
               
               """;

        html += "<div id=\"overlay\" class=\"overlay\"></div>" + """
                

                <div id="popup1" class="popuptext">
                <p>
                """;
                
        
        
        html += "<br><br><b>Name:</b> Rachel Robertson ";
        html += "<br><br><b>Attributes:</b> ";
        ArrayList<String> rachelBG = jdbc.getAllPersonaDetails("Rachel Robertson", "Attributes");
        for (String bg : rachelBG) {
            html += bg;
        }

        html += "<br><br><b>Goals and Needs:</b> ";
        ArrayList<String> rachelGN = jdbc.getAllPersonaDetails("Rachel Robertson", "Goals");
        for (String gn : rachelGN) {
            html += gn;
        }

        html += "<br><br><b>Skills/Experience:</b> ";
        ArrayList<String> rachelSkills = jdbc.getAllPersonaDetails("Rachel Robertson", "Skills");
        for (String skill :rachelSkills) {
            html += skill;
        }


        html += "<br><br><b>Pains:</b> ";
        ArrayList<String> rachelPains = jdbc.getAllPersonaDetails("Rachel Robertson", "Pains");
        for (String pain :rachelPains) {
            html += pain;
        } 
        html += "</p></div>";


        html += "<div id=\"overlay\" class=\"overlay\"></div>" + """
                

                <div id="popup2" class="popuptext">
                <p>
                """;
        
        html += "<br><br><b>Name:</b> Cindy Brown ";
        html += "<br><br><b>Attributes:</b> ";
        ArrayList<String> cindyBG = jdbc.getAllPersonaDetails("Cindy Brown", "Attributes");
        for (String bg : cindyBG) {
            html += bg;
        }

        html += "<br><br><b>Goals and Needs:</b> ";
        ArrayList<String> cindyGN = jdbc.getAllPersonaDetails("Cindy Brown", "Goals");
        for (String gn : cindyGN) {
            html += gn;
        }

        html += "<br><br><b>Skills/Experience:</b> ";
        ArrayList<String> cindySkills = jdbc.getAllPersonaDetails("Cindy Brown", "Skills");
        for (String skill :cindySkills) {
            html += skill;
        }


        html += "<br><br><b>Pains:</b> ";
        ArrayList<String> cindyPains = jdbc.getAllPersonaDetails("Cindy Brown", "Pains");
        for (String pain :cindyPains) {
            html += pain;
        } 
        html += "</p></div>";
              
          
        html += "<div id=\"overlay\" class=\"overlay\"></div>" + """
                

                <div id="popup3" class="popuptext">
                <p>
                """;

         
        html += "<br><br><b>Name:</b> Romeo Matar ";
        html += "<br><br><b>Attributes:</b> ";
        ArrayList<String> romeoBG = jdbc.getAllPersonaDetails("Romeo Matar", "Attributes");
        for (String bg : romeoBG) {
            html += bg;
        }

        html += "<br><br><b>Goals and Needs:</b> ";
        ArrayList<String> romeoGN = jdbc.getAllPersonaDetails("Romeo Matar", "Goals");
        for (String gn : romeoGN) {
            html += gn;
        }

        html += "<br><br><b>Skills/Experience:</b> ";
        ArrayList<String> romeoSkills = jdbc.getAllPersonaDetails("Romeo Matar", "Skills");
        for (String skill :romeoSkills) {
            html += skill;
        }


        html += "<br><br><b>Pains:</b> ";
        ArrayList<String> romeoPains = jdbc.getAllPersonaDetails("Romeo Matar", "Pains");
        for (String pain :romeoPains) {
            html += pain;
        } 
        html += "</p></div>";  
          
          
      
      
html += """
        
        

<script>
function showPopup(popupId) {
  var popups = document.getElementsByClassName('popuptext');
  var overlay = document.getElementById('overlay');
  for (var i = 0; i < popups.length; i++) {
    popups[i].style.visibility = 'hidden';
  }
  overlay.style.visibility = 'hidden';

  var popup = document.getElementById(popupId);
  popup.style.visibility = 'visible';
  overlay.style.visibility = 'visible';
}

window.onclick = function(event) {
  var popups = document.getElementsByClassName('popuptext');
  var overlay = document.getElementById('overlay');
  if (!event.target.matches('img')) {
    for (var i = 0; i < popups.length; i++) {
      popups[i].style.visibility = 'hidden';
    }
    overlay.style.visibility = 'hidden';
  }
}
</script> 

            <hr>
            <br></br>
        
        """;
      
        
        html = html +  " <div class=\"mission\"><h3>WEBSITE CREATORS</h3>";
        ArrayList<Student> teamMembers =  new ArrayList<Student>();
        teamMembers = jdbc.getAllTeamMembers();
        for (int i = 0; i < teamMembers.size(); ++i){
            html += "<p class='missionText'><b> Team Member " + (i+1) + ":</b> " + teamMembers.get(i).studentName() + " (Student ID: " + teamMembers.get(i).studentNumber()+ ")";
        }

        html += "</p></div></div>";

        // Close Content div
        html = html + "</div>";

        PageFooter footer = new PageFooter();
        html += footer.main();

        // Finish the HTML webpage
        html = html + "</body> </html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
