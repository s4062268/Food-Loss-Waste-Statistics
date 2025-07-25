package app;

/**
 * Class represeting a Country from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class FoodWasteLoss {
   // foodGroup
   private String foodGroup;

   // activity
   private String activity;

   // Food Supply Stage
   private String foodSupplyStage;

   // Loss cause
   private String lossCause;

   //Start year
   private int startYear;

   //End year
   private int endYear;

   //Loss Waste Percentage
   private int lossWastePercentage;

   /**
    * Create a Country and set the fields
    */
   public FoodWasteLoss(String foodGroup, String activity, String foodSupplyStage, String lossCause, int lossWastePercentage) {
      this.foodGroup = foodGroup;
      this.activity = activity;
      this.foodSupplyStage = foodSupplyStage;
      this.lossCause = lossCause;
      this.startYear = startYear;
      this.endYear = endYear;
      
   }

   public String foodGroup() {
      return foodGroup;
   }

   public String activity() {
      return activity;
   }

   public String foodSupplyStage() {
      return foodSupplyStage;
   }

   public String lossCause() {
      return lossCause;
   }


   public int startYear() {
      return startYear;
   }

   public int endYear() {
      return endYear;
   }
}
