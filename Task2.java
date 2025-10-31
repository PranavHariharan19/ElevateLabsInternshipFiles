import java.util.ArrayList;
import java.util.Scanner;
class Student {
    String id;
    String name;
    double marks;
    static ArrayList<Student> students = new ArrayList<>();
    Student(String id,String name,double marks)
    {
        this.id=id;
        this.name=name;
        this.marks=marks;
    }
    static ArrayList<Student> addrecord(String id,String name,double marks)
    {
        students.add(new Student(id, name, marks));
        System.out.println("Record added successfully");
        return students;
    }
    static void viewrecord(String id)
    {
        for(Student student:students)
        {
            if(student.id.equals(id))
            {
                System.out.println("Student id is:"+student.id);
                System.out.println("Student name is:"+student.name);
                System.out.println("Student marks is:"+student.marks);
                return;
            }
        }
        System.out.println("Record not found");
    }
    static ArrayList<Student> updaterecord(String id)
    {
        Scanner scanner=new Scanner(System.in);
        boolean found=false;
        for(Student student:students)
        {
            if(student.id.equals(id))
            {
                found=true;
                System.out.println("Enter new marks:");
                double newmarks=scanner.nextDouble();
                student.marks=newmarks;
                System.out.println("Record updated successfully");
                break;
            }
        }
        if(!found)
        {
            System.out.println("Record not found");
        }
        return students;
    }
    static ArrayList<Student> deleterecord(String id)
    {
        boolean found=false;
        for (int i = 0; i < students.size(); i++) 
        {
            if (students.get(i).id.equals(id))
            {
                students.remove(i);
                System.out.println("Record deleted successfully");
                found = true;
                break;
            }
        }
        if(!found)
        {
            System.out.println("Record not found");
        }
        return students;
    }
}
public class Task2
{
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to student management system");
        char choice='y';
        while(choice=='y')
        {
            int operation;
            System.out.println("Enter 1 to add Record\nEnter 2 to view record\nEnter 3 to update record\nEnter 4 to delete record");
            operation=scanner.nextInt(); 
            scanner.nextLine();  
            switch(operation)
            {
                case 1:
                System.out.print("Enter Student ID:");
                String addId = scanner.nextLine();
                System.out.print("Enter Name: ");
                String addName = scanner.nextLine();
                System.out.print("Enter Marks: ");
                double addMarks = scanner.nextDouble();
                Student.addrecord(addId, addName, addMarks);
                break;

                case 2:
                System.out.print("Enter Student ID to view: ");
                String viewId = scanner.nextLine();
                Student.viewrecord(viewId);
                break;

                case 3:
                System.out.print("Enter Student ID to update: ");
                String updateId = scanner.nextLine();
                Student.updaterecord(updateId);
                break;

                case 4:
                System.out.print("Enter Student ID to delete: ");
                String deleteId = scanner.nextLine();
                Student.deleterecord(deleteId);
                break;
            }
            System.out.println("Do you want to continue?(y/n)");
            choice=scanner.next().toLowerCase().charAt(0);        
        }
        System.out.println("Thank you for using the student management system");
        scanner.close();
    }
}