import java.sql.*;
import java.util.Scanner;

public class Task7 {
    static final String url="jdbc:mysql://127.0.0.1:3306/task7";
    static final String user="root";
    static final String password="admin123";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
            System.out.println("Welcome to customer record manager!!");
            char ans='y';
            while (ans=='y') 
            {
                System.out.println("Enter 1 to add a new customer\nEnter 2 to view all customers\nEnter 3 to update customer details\nEnter 4 to delete a customer");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) 
                {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        String insertSQL = "INSERT INTO customers (name, age) VALUES (?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
                        insertStmt.setString(1, name);
                        insertStmt.setInt(2, age);
                        insertStmt.executeUpdate();
                        insertStmt.close();
                        System.out.println("Customer added successfully");
                        break;

                    case 2:
                        String selectSQL = "SELECT * FROM customers";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(selectSQL);
                        System.out.println("Customers:");
                        while (rs.next()) 
                        {
                            System.out.println(rs.getString("name") + " - " + rs.getInt("age")+"years");
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case 3:
                        System.out.print("Enter name of customer to update: ");
                        String nameToUpdate = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();
                        String updateSQL = "UPDATE customers SET age = ? WHERE name = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                        updateStmt.setInt(1, newAge);
                        updateStmt.setString(2, nameToUpdate);
                        int rowsUpdated = updateStmt.executeUpdate();
                        updateStmt.close();
                        if (rowsUpdated > 0)
                            System.out.println("Customer updated.");
                        else
                            System.out.println("Customer not found.");
                        break;

                    case 4:
                        System.out.print("Enter name of customer to delete: ");
                        String nameToDelete = scanner.nextLine();
                        String deleteSQL = "DELETE FROM customers WHERE name = ?";
                        PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
                        deleteStmt.setString(1, nameToDelete);
                        int rowsDeleted = deleteStmt.executeUpdate();
                        deleteStmt.close();
                        if (rowsDeleted > 0)
                            System.out.println("Customer deleted.");
                        else
                            System.out.println("Customer not found.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                System.out.println("Do you want to continue?(y/n)");
                ans=scanner.next().toLowerCase().charAt(0);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        System.out.println("Thank you for using customer records manager");
        scanner.close();
    }
}