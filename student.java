import java.util.*;

/**
 * Represents a Student entity with personal details and academic metrics.
 * Implements rigid state encapsulation and immutable identifiers.
 */
class Student
{
    private final int id;
    private String name;
    private int age;
    private double gpa; 
    private String major; 
    private String email; 

    /**
     * Constructor enforces object safety right at birth.
     * Throws IllegalArgumentException to halt execution if initialization arguments are invalid.
     */
    public Student(int id, String name, int age, double gpa, String major, String email)
    {
        // Boundary checks to ensure a corrupted object state is never initialized
        if (age < 6 || age > 18) {
            throw new IllegalArgumentException("Creation Error: Age must be between 6 and 18.");
        }
        // A. Fixed GPA validation bug: Explicitly checking Double.isFinite() to intercept NaN and Infinities
        if (!Double.isFinite(gpa) || gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("Creation Error: GPA must be a finite number between 0.0 and 4.0.");
        }

        this.id = id;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.major = major;
        this.email = email;
    }

    // --- GETTERS AND SETTERS (Encapsulation Gatekeepers) ---

    public int getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getAge() 
    {
        return age;
    }

    public void setAge(int age) 
    {
        if (age >= 6 && age <= 18) {
            this.age = age;
        }   
        else  
        {
            System.out.println("Error: Internal data validation blocked an invalid age!");
        }
    }

    public double getGpa() 
    {
        return gpa;
    }

    public void setGpa(double gpa) 
    {
        // A. Fixed GPA validation bug: Using Double.isFinite() to stop bad mutation inputs down the line
        if (Double.isFinite(gpa) && gpa >= 0.0 && gpa <= 4.0) 
            {
            this.gpa = gpa;
        } else {
            System.out.println("Error: Internal data validation blocked an invalid GPA!");
        }
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString()
    {
        return "ID: " + id +
        ", Name: " + name +
        ", Age: " + age +
        ", GPA: " + gpa +
        ", Major: " + major +
        ", Email: " + email;
    }
}

/**
 * Static Utility class dedicated to filtering input tokens.
 * Bulletproofed with try-catch blocks to guarantee execution longevity.
 */
class InputValidator 
{
    private InputValidator() {}

    /**
     * Captures and validates floating-point GPA scores.
     * Survives non-numeric text entry input anomalies and special states like NaN.
     */
    static double getValidGPA(Scanner sc) 
    {
        while (true) 
        {
            try 
            {
                System.out.print("Enter student GPA (0.0 - 4.0): ");
                double gpa = sc.nextDouble();
                sc.nextLine(); // Clear newline buffer trailing successful scans
                
                // A. Fixed GPA validation bug: Using Double.isFinite() to handle malicious/accidental edge inputs
                if (Double.isFinite(gpa) && gpa >= 0.0 && gpa <= 4.0) 
                {
                    return gpa; 
                } 
                System.out.println("Invalid range! GPA must be a normal number between 0.0 and 4.0.");
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Type Error: Please enter a valid decimal number (e.g., 3.5).");
                sc.nextLine(); // Flushes out the bad alphabetical text token still trapped in the Scanner
            }
        }
    }

    /**
     * Captures and validates whole-number age variables.
     * Survives alphabetical input strings.
     */
    static int getValidAge(Scanner sc) 
    {
        while (true) 
        {
            try 
            {
                System.out.print("Enter student age (6 - 18): ");
                int age = sc.nextInt();
                sc.nextLine(); // Clear newline buffer
            
                if (age >= 6 && age <= 18) 
                {
                    return age; 
                } 
                System.out.println("Invalid range! Age must be between 6 and 18.");
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Type Error: Please enter a whole number integer (e.g., 14).");
                sc.nextLine(); // Flushes out the bad text string so the loop can poll cleanly again
            }
        }
    }
}

class Main {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        
        while (true)
        {
            System.out.println("\n================================");
            System.out.println(" STUDENT MANAGEMENT");
            System.out.println("================================");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student Details by ID");
            System.out.println("5. Delete Student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid selection format! Resetting interface context.");
                sc.nextLine(); 
                continue;
            }
            
            switch (choice)
            {
                case 1: // Add New Record
                    int id;
                    try {
                        System.out.print("Enter unique Student ID: ");
                        id = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: IDs must be numeric values. Operation aborted.");
                        sc.nextLine();
                        break;
                    }
                    
                    boolean idExists = false;
                    for (Student s : students)
                    {
                        if (s.getId() == id)
                        {
                            idExists = true;
                            break;
                        }
                    }
                    if (idExists)
                    {
                        System.out.println("Error: A student with this ID already exists!");
                        break; 
                    }
                    
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    
                    int age = InputValidator.getValidAge(sc);
                    double gpa = InputValidator.getValidGPA(sc);
                    
                    System.out.print("Enter student major: ");
                    String major = sc.nextLine();
                    System.out.print("Enter student email: ");
                    String email = sc.nextLine();
                    
                    students.add(new Student(id, name, age, gpa, major, email));
                    System.out.println("Student added successfully!");
                    break;
                    
                case 2: // Read Records
                    System.out.println("\nList of Students:");
                    if (students.isEmpty())
                    {
                        System.out.println("No students found.");
                    } else
                    {
                        for (Student student : students)
                        {
                            System.out.println(student);
                        }
                    }
                    break;
                    
                case 3: // Search Record
                    System.out.print("Enter student ID to search: ");
                    int searchId;
                    try {
                        searchId = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid ID format.");
                        sc.nextLine();
                        break;
                    }
                    
                    boolean found = false;
                    for (Student student : students)
                    {
                        if (student.getId() == searchId)
                        {
                            System.out.println(student);
                            found = true;
                            break; 
                        }
                    }
                    if (!found)
                    {
                        System.out.println("Student not found.");
                    }
                    break;
                    
                case 4: // Update Record
                    System.out.print("Enter student ID to update: ");
                    int updateId;
                    try {
                        updateId = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid ID format.");
                        sc.nextLine();
                        break;
                    }
                    boolean updated = false;
                    for (Student student : students)
                    {
                        if (student.getId() == updateId)
                        {
                            System.out.print("Enter new name: ");
                            String newName = sc.nextLine();
                            int newAge = InputValidator.getValidAge(sc);
                            double newGpa = InputValidator.getValidGPA(sc);
                            System.out.print("Enter new major: ");
                            String newMajor = sc.nextLine();
                            System.out.print("Enter new email: ");
                            String newEmail = sc.nextLine();
                            student.setName(newName);
                            student.setAge(newAge);
                            student.setGpa(newGpa);
                            student.setMajor(newMajor);
                            student.setEmail(newEmail);
                            updated = true;
                            System.out.println("Student details updated successfully!");
                            break;    
                        }
                    }
                    if (!updated)
                    {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5: // Delete Record
                    System.out.print("Enter student ID to delete: ");
                    int deleteId;
                    try
                    {
                        deleteId = sc.nextInt();
                        sc.nextLine();
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Invalid ID format.");
                        sc.nextLine();
                        break;
                    }
                    boolean deleted = false;
                    Iterator<Student> iterator = students.iterator();
                    while (iterator.hasNext())
                    {
                        Student student = iterator.next();
                        if (student.getId() == deleteId)
                        {
                            iterator.remove();
                            System.out.println("Student deleted successfully!");
                            deleted = true;
                            break;
                        }
                    }   
                    if (!deleted)
                    {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6: // Terminate Program Lifecycle
                    System.out.println("Exiting...");
                    return;

                default:
                    // FIXED: Removed stray trailing backslash here to resolve compilation issue
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}