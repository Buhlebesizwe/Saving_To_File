package challenge_2;

import java.util.*;

import static challenge_2.Employee.*;

public class EmployeeDetails {

    public static void main(String[] args) throws Exception {

        int choice;
        Scanner sInt = new Scanner(System.in);
        
     
        do {
            System.out.println("*****************************************************************************");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Update Employee");
            System.out.println("6. Sort Employee");
            System.out.println("0. Exit");
            System.out.println("*****************************************************************************");
            System.out.print("Enter your choice: ");
            while (!sInt.hasNextInt()) {
                System.out.print(sInt.next() + " is not a choice number please enter a valid choice number: ");

            }
            choice = sInt.nextInt();

            switch (choice) {
                // get employee details
                case 1:
                    addEmployee();
                    break;
                //Display employees
                case 2:
                    display();
                    break;
                //search employee
                case 3:
                    searchEmployee();
                    break;
                //delete employee
                case 4:
                    deleteEmployee();
                    break;
                //update employee
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    sortEmployee();
                    break;
                default :
                    System.out.println("Choice must be between -1 and 7");
            }

        } while (choice != 0 );
    }

}
