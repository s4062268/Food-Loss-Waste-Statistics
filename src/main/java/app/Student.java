package app;

/**
 * Class represeting a Student from the Studio Project database
 *
 * @author Menuki Vinhara Wijetunge, 2024. email: s4062268@student.rmit.edu.au
 */

public class Student {
   // Student Number
   private String studentNumber;

   // Student Name
   private String studentName;

   /**
    * Create a Student and set the fields
    */
   public Student(String studentNumber, String studentName) {
      this.studentNumber = studentNumber;
      this.studentName = studentName;
   }

   public String studentNumber() {
      return studentNumber;
   }

   public String studentName() {
      return studentName;
   }
}
