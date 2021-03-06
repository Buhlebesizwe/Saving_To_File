package challenge_2;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee implements Serializable {

    String name, surname, email, date_of_birth;

    Employee(String name, String surname, String email, String date_of_birth) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.date_of_birth = date_of_birth;

    }

    @Override
    public String toString() {
        return name + " " + surname + " " + email + " " + date_of_birth;
    }

    static File file = new File("employee.txt");
    static ArrayList<Employee> aList = new ArrayList<>();
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    static ListIterator li;
    static Scanner sString = new Scanner(System.in);
//checking if the input is an email

    public static boolean isEmail(String email) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.find();
    }
//checking if email already exist

    public static boolean isEmailExist(String email) throws Exception {

        boolean found = false;
        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();
            li = aList.listIterator();
            while (li.hasNext()) {
                Employee e = (Employee) li.next();
                found = e.email.contentEquals(email);
            }
        }
        return found;
    }
//Date validation

    public static boolean isDate(String isDate) {
        Pattern p = Pattern.compile("[A-Z,a-z,&%$#@!()*^]");
        Matcher m;

        boolean validation = false;
        String date_of_birth;

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);

            m = p.matcher(isDate);
            try {
                if (m.find()) {
                    System.out.print("Please enter a valid date of birth. \n");
                } else {
                    String[] dateParts = isDate.split("/");

                    // Getting  year from date
                    String year = dateParts[2];

                    int theYear = Integer.parseInt(year);
                    LocalDate current_date = LocalDate.now();
                    int current_year = current_date.getYear();

                    if (current_year - theYear < 18) {
                        System.out.print("An employee cannot be younger than  18, please enter a valid date of birth. \n");
                    } else if (current_year - theYear >= 18) {
                        date_of_birth = isDate;
                        Date date = format.parse(date_of_birth);
                        validation = true;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("Please enter a valid date of birth. \n");
            }
        } catch (ParseException e) {
            System.out.print("Please enter a valid date of birth. \n");
        }
        date_of_birth = isDate;

        return validation;
    }

//Add employee
    public static void addEmployee() throws  IOException, Exception {
        int number = 0;
        do {

            System.out.print("Enter Employee Name: ");
            while (!sString.hasNext("[A-Za-z]*")) {
                System.out.print(sString.nextLine() + " is not a valid name please enter a valid name: ");
            }
            String name = sString.nextLine();

            System.out.print("Enter Employee Surname: ");
            while (!sString.hasNext("[A-Za-z]*")) {
                System.out.print(sString.nextLine() + " is not a valid surname please enter a valid surname: ");
            }
            String surname = sString.nextLine();

            String email;
            String str;
            boolean eValidation;
            do {
                System.out.print("Enter Employee Email: ");
                str = sString.nextLine();
                if (Employee.isEmailExist(str)) {
                    System.out.print("Email already exist. \n");
                    eValidation = false;
                } else if (Employee.isEmail(str)) {
                    eValidation = true;
                } else {
                    System.out.print("Invalid email. \n");
                    eValidation = false;
                }
                email = str;
            } while (!eValidation);

            String date_of_birth;
            String d;
            boolean isDate;
            do {
                System.out.print("Enter your date of birth(dd/MM/yyyy): ");
                d = sString.nextLine();
                isDate = Employee.isDate(d);
                date_of_birth = d;
            } while (!isDate);
            aList.add(new Employee(name, surname, email, date_of_birth));
            number++;
        } while (number < 0);

        oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(aList);
        oos.close();
    }

//Display Employees
    public static void display() throws  IOException, ClassNotFoundException {
        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            System.out.println("*****************************************************************************");
            li = aList.listIterator();
            while (li.hasNext()) {
                System.out.println(li.next());
            }
        } else {
            System.out.println("There are no employees to display");
        }
    }
//Search employee

    public static void searchEmployee() throws  IOException, ClassNotFoundException {

        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();
            boolean found = false;

            System.out.println("*****************************************************************************");
            System.out.print("Enter Employee email to search: ");
            String email = sString.nextLine();
            System.out.println("*****************************************************************************");

            li = aList.listIterator();
            while (li.hasNext()) {
                Employee e = (Employee) li.next();
                if (e.email.contentEquals(email)) {
                    System.out.println(e);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Employee not found.");
            }
        } else {
            System.out.println("File does not exist");
        }
    }

// update employee
    public static void updateEmployee() throws IOException, ClassNotFoundException {
        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            System.out.print("Enter Employee email to update: ");
            String empEmail = sString.nextLine();
            boolean found = false;
            System.out.println("*****************************************************************************");

            li = aList.listIterator();
            while (li.hasNext()) {
                Employee e = (Employee) li.next();
                if (e.email.contentEquals(empEmail)) {

                    System.out.print("Enter Employee Name: ");
                    while (!sString.hasNext("[A-Za-z]*")) {
                        System.out.print(sString.nextLine() + " is not a valid name please enter a valid name: ");
                    }
                    String name = sString.nextLine();

                    System.out.print("Enter Employee Surname: ");
                    while (!sString.hasNext("[A-Za-z]*")) {
                        System.out.print(sString.nextLine() + " is not a valid surname please enter a valid surname: ");
                    }
                    String surname = sString.nextLine();

                    String date_of_birth = null;
                    String d;
                    boolean isDate;
                    do {
                        System.out.print("Enter your date of birth(dd/MM/yyyy): ");
                        d = sString.nextLine();
                        isDate = Employee.isDate(d);
                        date_of_birth = d;
                    } while (!isDate);
                    li.set(new Employee(name, surname, empEmail, date_of_birth));
                    found = true;
                }
            }
            if (found) {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(aList);
                oos.close();
                System.out.println("Record updated successfully");
            } else {
                System.out.println("Employee not found.");
            }

        } else {
            System.out.println("File does not exist");
        }
    }

//Delete employee
    public static void deleteEmployee() throws  IOException, ClassNotFoundException {

        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            System.out.println("*****************************************************************************");
            System.out.print("Enter Employee email to delete: ");
            String email = sString.nextLine();
            boolean found = false;

            System.out.println("*****************************************************************************");
            li = aList.listIterator();
            while (li.hasNext()) {
                Employee e = (Employee) li.next();
                if (e.email.contentEquals(email)) {
                    li.remove();
                    found = true;
                }
            }
            if (found) {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(aList);
                oos.close();
                System.out.println("Record deleted successfully");
            } else {
                System.out.println("Employee not found.");
            }
        } else {
            System.out.println("File does not exist");
        }
    }

//sort employees
    public static void sortEmployee() throws IOException, ClassNotFoundException {
        if (file.isFile()) {
            ois = new ObjectInputStream(new FileInputStream(file));
            aList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            aList.sort(new NameComparator());

            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(aList);
            oos.close();

            System.out.println("*****************************************************************************");
            li = aList.listIterator();
            while (li.hasNext()) {
                System.out.println(li.next());
            }
        } else {
            System.out.println("File does not exist");
        }
    }
}

class NameComparator implements Comparator<Employee> {

    // override the compare() method
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.name.compareTo(e2.name);
    }
}
