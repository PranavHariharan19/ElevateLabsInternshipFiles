import java.io.*;
import java.util.Scanner;
public class Task4 {
    void writenotes(String fileName,String note)
    {
        try 
        {
            FileWriter writer=new FileWriter(fileName,true);
            writer.write(note+"\n");
            writer.close();
            System.out.println("Note added to "+fileName+" successfully!");
        }
        catch (IOException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    void readnotes(String fileName)
    {
        try {
            BufferedReader reader=new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("No notes is available in the requested file");
        }
         catch (IOException e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Task4 notes=new Task4();
        int choice;
        char ans='y';
        while(ans=='y')
        {
            System.out.println("Welcome to Notes Manager!!");
            System.out.println("Enter 1 if you want to write notes\nEnter 2 if you want to read notes");
            choice=scanner.nextInt();
            scanner.nextLine();
            switch(choice)
            {
                case 1:
                System.out.print("Enter the filename to write notes to:");
                String fileName = scanner.nextLine();
                System.out.print("Enter your note: ");
                String note = scanner.nextLine();
                notes.writenotes(fileName,note);
                break;

                case 2:
                System.out.print("Enter the filename to read notes from:");
                String filename = scanner.nextLine();
                notes.readnotes(filename);
                break;

                default:
                System.out.println("Please enter a valid choice");
            }
            System.out.println("Do you want to continue?(y/n)");
            ans=scanner.next().toLowerCase().charAt(0);
        }
        System.out.println("Thank you for using notes manager!!");
        scanner.close();
    }
}