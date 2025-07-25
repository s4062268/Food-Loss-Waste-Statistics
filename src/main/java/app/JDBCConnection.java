package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/foodloss.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * @return
     *    Returns an ArrayList of Country objects
     */
    public ArrayList<Country> getAllCountries() {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String m49Code     = results.getString("m49code");
                String name  = results.getString("countryName");

                // Create a Country Object
                Country country = new Country(m49Code, name);

                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    // TODO: Add your required methods here

    //Persona Query for mission page
    public ArrayList<String> getAllPersonaDetails(String persona, String attributeType) {
        
        //Creating ArrayList of type String
        ArrayList<String> personaDetails = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT characteristic FROM describedPersona WHERE personaid = (SELECT personaid FROM persona WHERE personaname = '"+ persona + "') AND characteristicType LIKE '%"+attributeType+"%';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

             
 

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String PersonaCharacteristic    = results.getString("characteristic");

                // Add the Characteristic to the array
                personaDetails.add(PersonaCharacteristic);
            }

            
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the persona Details
        return personaDetails;
    }

    //Team Member Query for mission page
    //ArrayList of Student Objects
    public ArrayList<Student> getAllTeamMembers() {
        
        //Creating ArrayList of type Student
        ArrayList<Student> teamMembers = new ArrayList<Student>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM teamMember;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String studentNumber    = results.getString("studentNumber");
                String studentName    = results.getString("studentName");

                //Creating a Student object
                Student student = new Student(studentNumber,studentName);
                
                // Add the student to the array
                teamMembers.add(student);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the persona Details
        return teamMembers;
    }


    //Criteria options for level 2 sub task B
    //ArrayList of String Objects
    public ArrayList<String> foodGroups() {
        
        //Creating ArrayList of type String
        ArrayList<String> foodGroupOptions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT categoryName FROM foodType"+ """
                            WHERE LENGTH(cpc) == 3
                            ORDER BY categoryName ASC;
                           """;
            
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                //String cpc    = results.getString("cpc");
                String foodGroup    = results.getString("categoryName");

                
                // Add the foodGroup name to the array
                foodGroupOptions.add(foodGroup);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the food Group Options
        return foodGroupOptions;
    }



    //Criteria options for level 2 sub task B
    //ArrayList of String Objects
    public ArrayList<String> activities() {
        
        //Creating ArrayList of type String
        ArrayList<String> activityOptions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM activity WHERE activityName != '' ORDER BY activityName ASC;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                String activity    = results.getString("activityName");

                
                // Add the activity name to the array
                activityOptions.add(activity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the activity Options
        return activityOptions;
    }

    //Criteria options for level 2 sub task B
    //ArrayList of String Objects
    public ArrayList<String> foodSupplyStages() {
        
        //Creating ArrayList of type String
        ArrayList<String> foodSupplyStageOptions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM foodSupplyStage WHERE foodSupplyStageName != '' ORDER BY foodSupplyStageName ASC;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                String foodSupplyStage    = results.getString("foodSupplyStageName");

                
                // Add the foodSupplyStage name to the array
                foodSupplyStageOptions.add(foodSupplyStage);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Food Supply Stage Options
        return foodSupplyStageOptions;
    }


    //Criteria options for level 2 sub task B
    //ArrayList of String Objects
    public ArrayList<String> lossCauses() {
        
        //Creating ArrayList of type String
        ArrayList<String> lossCauseOptions = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM lossCause  WHERE lossCauseName != '' ORDER BY lossCauseName ASC;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                String lossCauseName    = results.getString("lossCauseName");

                
                // Add the loss Cause name to the array
                lossCauseOptions.add(lossCauseName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the loss cause Options
        return lossCauseOptions;
    }



    //Criteria options for level 2 sub task B
    //ArrayList of Integer Objects
    public ArrayList<Integer> years() {
        
        //Creating ArrayList of type Integer
        ArrayList<Integer> yearOptions = new ArrayList<Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM year;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                Integer year    = results.getInt("year");

                
                // Add the year to the array
                yearOptions.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the year Options
        return yearOptions;
    }
    

    

    /*Query for Level 2 Sub Task B */
    public ArrayList<String[]> WasteLossByFoodGroup(String[] foodGroups, String columnsToDisplay, String columnCriteria, String startYear, String endYear, String sort) {
        
      

        //Creating ArrayList of type String
        ArrayList<String[]> display = new ArrayList<String[]>();
        String query = "";

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            for (int f = 0; f < foodGroups.length; ++f){
                query += "SELECT" + """
                        
                        
                a.foodGroup AS foodGroup
                """;
                query += columnsToDisplay; 
            
                query += "FROM" + """

                (SELECT 
                    FoodGroup,
                    Activity,
                    FoodSupplyStage,
                    LossCause,
                    AVG(LossValue) AS AvgLossPercentageStart
                FROM 
                    (SELECT l1.m49code AS country, NULL AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                    FROM lossWithoutRegion l1
                    JOIN foodType f ON f.cpc = l1.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc
                                
                    UNION
                                
                    SELECT l2.m49code AS country, l2.regionName AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                    FROM lossWithRegion l2
                    JOIN foodType f ON f.cpc = l2.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc)
                WHERE 
                """;
                    
                 query += "year = '" + startYear;


          query+="'GROUP BY " + """
                  
                  
            FoodGroup, Activity, FoodSupplyStage, LossCause) AS a
            JOIN 
                (SELECT 
                    FoodGroup,
                    Activity,
                    FoodSupplyStage,
                    LossCause,
                    AVG(LossValue) AS AvgLossPercentageEnd
                FROM 
                    (SELECT g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                    FROM lossWithoutRegion l1
                    JOIN foodType f ON f.cpc = l1.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc
                                
                    UNION
                                
                    SELECT g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                    FROM lossWithRegion l2
                    JOIN foodType f ON f.cpc = l2.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc)
                WHERE 
                """;
                    
                 query += "year = '" + endYear;


          query+="' GROUP BY " + """ 
                    FoodGroup, Activity, FoodSupplyStage, LossCause) AS b
            ON 
                a.FoodGroup = b.FoodGroup
                AND a.Activity = b.Activity
                AND a.FoodSupplyStage = b.FoodSupplyStage
                AND a.LossCause = b.LossCause
            WHERE
            """;
                
                
                
            query += "a.FoodGroup = '" + foodGroups[f] +"'" + columnCriteria; 

            
            if (!columnsToDisplay.contains("a.FoodSupplyStage") && !columnsToDisplay.contains("a.LossCause") && !columnsToDisplay.contains("a.Activity")){
                query += " GROUP BY a.FoodGroup ";
            }
            else {
                query += " GROUP BY a.FoodGroup, ";
                String grouByColumns = "";
                if (columnsToDisplay.contains("a.Activity AS activity")){
                    grouByColumns += "a.Activity";

                }
                if (columnsToDisplay.contains("a.FoodSupplyStage AS foodSupplyStage")){
                    if (grouByColumns.isEmpty()){
                        grouByColumns += "a.FoodSupplyStage";
                    }
                    else {
                        grouByColumns += ", a.FoodSupplyStage";
                    }
                
                }

                if (columnsToDisplay.contains("a.LossCause AS lossCauseName")){
                    if (grouByColumns.isEmpty()){
                        grouByColumns += "a.LossCause";
                    }
                    else {
                        grouByColumns += ", a.LossCause";
                    }
                
                }
                
                query += grouByColumns;

                
            }

            if (f < foodGroups.length - 1){
                query += " UNION ";
             }
        }
    
        if(sort != null ) {
            query += " ORDER BY lossValueChange " + sort;
        }
    
        //  }
        //  System.out.println("---------------------------------------------");
        //  System.out.println(query);
        //  System.out.println("---------------------------------------------");      
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            //System.out.println(results);
            // Process all of the results
            
            while (results.next()) {
                // Lookup the columns we need
                String result = "";
                // if(columnsToDisplay.contains("foodGroup")) {
                    String foodGroupOutput    = results.getString("foodGroup");
                    //display.append(foodGroupOutput); //Adding to the list given that the column is to be displayed
                    result+= foodGroupOutput + "~";
                    
                    
                // }

                if(columnsToDisplay.contains("activity")) {
                    String activityOutput    = results.getString("activity");
                    //display.add(activityOutput); //Adding to the list given that the column is to be displayed

                    result+= activityOutput + "~";
                }

            
                if(columnsToDisplay.contains("foodSupplyStage")) {
                    String foodSupplyStageOutput    = results.getString("foodSupplyStage");
                    //display.add(foodSupplyStageOutput); //Adding to the list given that the column is to be displayed
                    //result += foodSupplyStageOutput + ",";
                                              
                    result+= foodSupplyStageOutput + "~";
                    
                }
                
                if(columnsToDisplay.contains("lossCauseName")) {
                    String lossCauseOutput    = results.getString("lossCauseName");
                    //display.add(lossCauseOutput); //Adding to the list given that the column is to be displayed
                    result += lossCauseOutput + "~";
                }

                // if(columnsToDisplay.contains("lossValueChange")) {
                    String lossWastePercentageOutput    = results.getString("lossValueChange");
                    //display.add(lossWastePercentageOutput); //Adding to the list given that the column is to be displayed
                    result += lossWastePercentageOutput + "~";
                // }
                String[] resultArray =  result.split("~");
                display.add(resultArray);
                
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Food waste loss records
        return display;
    }










      /*Query 1 For Level 3 Sub Task B */

      public ArrayList<String[]> SimilarityHighestLowestLossWaste(String commodity, String simBy, String numGrp, String sort) {
        
        

        //Creating ArrayList of type String
        ArrayList<String[]> display = new ArrayList<String[]>();
        String query = "";

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            query += """
                    SELECT Query1.foodGroup, ROUND(PercentageSpecific,4), ROUND(PercentageOthers,4), ROUND(ABS(PercentageOthers - PercentageSpecific),4) AS difference, ROUND(10 * (1 - ABS(PercentageOthers - PercentageSpecific) / PercentageSpecific), 1) AS similarityScore

                    FROM (
                    SELECT
                        foodGroup,
                        """;
                                
                        query += simBy + "(LossValue) AS PercentageOthers" + """
                                
                    FROM

                    (SELECT l1.m49code AS country, NULL AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                        FROM lossWithoutRegion l1
                        JOIN foodType f ON f.cpc = l1.cpc
                        JOIN foodType g ON f.groupCpc = g.cpc

                        UNION

                        SELECT l2.m49code AS country, l2.regionName AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                        FROM lossWithRegion l2
                        JOIN foodType f ON f.cpc = l2.cpc
                        JOIN foodType g ON f.groupCpc = g.cpc)
                    WHERE foodGroup != (SELECT gr.categoryName FROM foodType gr
                                    WHERE gr.cpc = (SELECT fd.groupCpc FROM foodType fd
                                                    WHERE fd.categoryName = 
                                                    
                                                            """;
                    query += "'" + commodity+"'))" +"""
    


                    GROUP BY FoodGroup
                    ) AS Query1,



                    (SELECT
                        foodGroup,
                        """;
                                
                        query += simBy + "(LossValue) AS PercentageSpecific " + """
                    FROM

                    (SELECT l1.m49code AS country, NULL AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                        FROM lossWithoutRegion l1
                        JOIN foodType f ON f.cpc = l1.cpc
                        JOIN foodType g ON f.groupCpc = g.cpc

                        UNION

                        SELECT l2.m49code AS country, l2.regionName AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                        FROM lossWithRegion l2
                        JOIN foodType f ON f.cpc = l2.cpc
                        JOIN foodType g ON f.groupCpc = g.cpc)
                    WHERE foodGroup = (SELECT gr.categoryName FROM foodType gr
                                    WHERE gr.cpc = (SELECT fd.groupCpc FROM foodType fd
                                                    WHERE fd.categoryName = 
                                                    
                                                            """;
                    query += "'" + commodity+"'))" +"""

                    GROUP BY FoodGroup
                    ) AS Query2
                     
                    
                            """;
                    query += " ORDER BY similarityScore DESC LIMIT " + numGrp + ";";
                    
        //  System.out.println("---------------------------------------------");
        //  System.out.println(query);
        //  System.out.println("---------------------------------------------");      
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            
            // Process all of the results
            
            while (results.next()) {
                // Lookup the columns we need
                String result = "";
                
                String foodGroupOutput    = results.getString("foodGroup");
                   
                result+= foodGroupOutput + "~";
                    
                    
                // }

               
                String PercentageSpecific    = results.getString("ROUND(PercentageSpecific,4)");
                 
                result+= PercentageSpecific + "~";
                

            
               
                String PercentageOthers    = results.getString("ROUND(PercentageOthers,4)");
                             
                result+= PercentageOthers + "~";
                    
                
                
                
                String difference    = results.getString("difference");
               
                result += difference + "~";
                

                
                String similarityScore   = results.getString("similarityScore");
                
                result += similarityScore + "~";
               
                String[] resultArray =  result.split("~");
                display.add(resultArray);
                
            }
            
            
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        Collections.sort(display, new Comparator<String[]>() {
            @Override
            public int compare(String[] row1, String[] row2) {
                
                Double value1 = Double.parseDouble(row1[row1.length - 1]);
                Double value2 = Double.parseDouble(row2[row2.length - 1]);
                
                return value1.compareTo(value2);
            }
        });

        
        if ("desc".equalsIgnoreCase(sort)) {
            Collections.reverse(display);
        }
   
        // Finally we return all records of the similar groups and associated details.
        return display;
    }


//Level 3 sub task B Food Commodities
//ArrayList of String Objects
public ArrayList<String> foodCommodities() {
        
    //Creating ArrayList of type String
    ArrayList<String> foodCommoditiesOptions = new ArrayList<String>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT categoryName From foodType WHERE LENGTH(cpc) <= 5 AND LENGTH(cpc) > 3 ORDER BY categoryName;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            
            String commodity    = results.getString("categoryName");

            
            // Add the activity name to the array
            foodCommoditiesOptions.add(commodity);
        }

        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    // Finally we return all of the activity Options
    return foodCommoditiesOptions;
}


public ArrayList<String[]> SimilarityLossWasteRatio(String commodity, String numGrp, String sort) {
        
        

    //Creating ArrayList of type String
    ArrayList<String[]> display = new ArrayList<String[]>();
    String query = "";

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        
        query += """
                SELECT Query1.foodGroup, ROUND(foodLossToWasteSpecific,4), ROUND(foodLossToWasteOthers,4), ROUND(ABS(foodLossToWasteOthers - foodLossToWasteSpecific),4) AS difference, ROUND(10 * (1 - ABS(foodLossToWasteOthers - foodLossToWasteSpecific) / foodLossToWasteSpecific), 1) AS similarityScore

                FROM (
                SELECT
                    foodGroup,
                    (AVG(CASE WHEN foodSupplyStage IN ('Harvest', 'Farm', 'Storage', 'Processing', 'Post-harvest', 'Transport', 'Whole supply chain', 'Pre-harvest', 'Collector', 'Stacking', 'Grading', 'Packing', 'Export','Market') THEN lossValue END) /
                    AVG(CASE WHEN foodSupplyStage IN ('Retail', 'Wholesale', 'Trader', 'Households', 'Food Services','Distribution', 'Market', 'Whole supply chain', 'Transport') THEN lossValue END)
                    ) * 100 AS foodLossToWasteOthers
                    
                FROM

                (SELECT l1.m49code AS country, NULL AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                    FROM lossWithoutRegion l1
                    JOIN foodType f ON f.cpc = l1.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc

                    UNION

                    SELECT l2.m49code AS country, l2.regionName AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                    FROM lossWithRegion l2
                    JOIN foodType f ON f.cpc = l2.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc)
                WHERE foodGroup != (SELECT gr.categoryName FROM foodType gr
                                WHERE gr.cpc = (SELECT fd.groupCpc FROM foodType fd
                                                WHERE fd.categoryName = 
                                                    
                                                            """;
                    query += "'" + commodity+"'))" +"""
                GROUP BY FoodGroup
                ) AS Query1,



                (SELECT
                    foodGroup,
                    (AVG(CASE WHEN foodSupplyStage IN ('Harvest', 'Farm', 'Storage', 'Processing', 'Post-harvest', 'Transport', 'Whole supply chain', 'Pre-harvest', 'Collector', 'Stacking', 'Grading', 'Packing') THEN lossValue END) /
                    AVG(CASE WHEN foodSupplyStage IN ('Retail', 'Wholesale', 'Trader', 'Households', 'Food Services', 'Export', 'Distribution', 'Market', 'Whole supply chain', 'Transport') THEN lossValue END)
                    ) * 100 AS foodLossToWasteSpecific
                FROM

                (SELECT l1.m49code AS country, NULL AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l1.activityName AS activity, l1.foodSupplyStageName AS foodSupplyStage, l1.lossCauseName AS lossCause, l1.year AS year, l1.lossValue AS lossValue
                    FROM lossWithoutRegion l1
                    JOIN foodType f ON f.cpc = l1.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc

                    UNION

                    SELECT l2.m49code AS country, l2.regionName AS Region, g.categoryName AS foodGroup, f.categoryName AS foodCommodity, l2.activityName AS activity, l2.foodSupplyStageName AS foodSupplyStage, l2.lossCauseName AS lossCause, l2.year AS year, l2.lossValue  AS lossValue
                    FROM lossWithRegion l2
                    JOIN foodType f ON f.cpc = l2.cpc
                    JOIN foodType g ON f.groupCpc = g.cpc)
                WHERE foodGroup = (SELECT gr.categoryName FROM foodType gr
                                WHERE gr.cpc = (SELECT fd.groupCpc FROM foodType fd
                                                WHERE fd.categoryName = 
                                                    
                                                            """;
                    query += "'" + commodity+"'))" +"""
                GROUP BY FoodGroup
                ) AS Query2
                WHERE difference IS NOT NULL AND similarityScore IS NOT NULL

                        """;
                query += " ORDER BY similarityScore "+ sort +" LIMIT " + numGrp + ";";
                
     //System.out.println("---------------------------------------------");
     //System.out.println(query);
    //System.out.println("---------------------------------------------");      
        
        // Get Result
        ResultSet results = statement.executeQuery(query);
        
        // Process all of the results
        
        while (results.next()) {
            // Lookup the columns we need
            String result = "";
            
            String foodGroupOutput    = results.getString("foodGroup");
               
            result+= foodGroupOutput + "~";
                
                
            // }

           
            String PercentageSpecific    = results.getString("ROUND(foodLossToWasteSpecific,4)");
             
            result+= PercentageSpecific + "~";
            

        
           
            String PercentageOthers    = results.getString("ROUND(foodLossToWasteOthers,4)");
                         
            result+= PercentageOthers + "~";
                
            
            
            
            String difference    = results.getString("difference");
           
            result += difference + "~";
            

            
            String similarityScore   = results.getString("similarityScore");
            
            result += similarityScore + "~";
           
            String[] resultArray =  result.split("~");
            display.add(resultArray);
            
        }
        
        
        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
    Collections.sort(display, new Comparator<String[]>() {
        @Override
        public int compare(String[] row1, String[] row2) {
            
            Double value1 = Double.parseDouble(row1[row1.length - 1]);
            Double value2 = Double.parseDouble(row2[row2.length - 1]);
            
            return value1.compareTo(value2);
        }
    });

    
    if ("desc".equalsIgnoreCase(sort)) {
        Collections.reverse(display);
    }

    // Finally we return all records of the similar groups and associated details.
    return display;
}




public String level3FoodGroup(String commodity) {
        
    //Creating String
    String group ="";

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "SELECT categoryName FROM foodType WHERE cpc = (SELECT groupCpc FROM foodType WHERE categoryName = '"+commodity+"');";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            
            group = results.getString("categoryName");

        }

        // Close the statement because we are done with it
        statement.close();
    } catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    // Finally we return all of the Food Supply Stage Options
    return group;
}





}
