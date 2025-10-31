import java.util.ArrayList;
import java.util.Scanner;
class Book
{
    String title;
    Boolean borrow;
    String addedby;
    String borrowedby;
    Book(String title,String addedby)
    {
        this.title=title;
        borrow=false;
        this.addedby=addedby;
        borrowedby=null;
    }
}
class User
{
    String name;
    User(String name)
    {
        this.name=name;
    }
}
class Library
{
    ArrayList<Book> books=new ArrayList<>();
    void addBook(Book book)
    {
        books.add(new Book(book.title,book.addedby));
    }
    void listBooks()
    {
        for(Book b:books)
        {
            System.out.print(b.title+" - ");
            if(!b.borrow)
            {
                System.out.println("Available");
            }
            else
            {
                System.out.println("Borrowed by "+b.borrowedby);
            }
        }
    }
    void borrowBook(String title,String name)
    {
        for(Book b:books)
        {
            if(b.title.equals(title))
            {
                if(!b.borrow)
                {
                    b.borrow=true;
                    b.borrowedby=name;
                    System.out.println("Book borrowed successfully!!");
                    return;
                }
                else
                {
                    System.out.println("Requested book is already borrowed by "+b.borrowedby);
                    return;
                }
            }
        }
        System.out.println("Requested book does not exist");
    }
    void returnBook(String title,String name)
    {
        for(Book b:books)
        {
            if(b.title.equals(title))
            {
                if(b.borrow)
                {
                    if(b.borrowedby.equals(name))
                    {
                        b.borrow=false;
                        b.borrowedby=null;
                        System.out.println("Book returned successfully!!");
                        return;
                    }
                    System.out.println("The book was borrowed by "+b.borrowedby+" so you cannot return it");
                    return;
                }
                System.out.println("Book is not borrowed by anyone");
                return;
            }
        }
        System.out.println("Requested book does not exist");
    }
}
public class Task3
{
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Library library=new Library();
        int choice;
        char ans='y';
        System.out.println("Welcome to Library Management System");
        System.out.println("Enter your name:");
        String name=scanner.nextLine();
        User user=new User(name);
        while(ans=='y')
        {
            System.out.println("Enter 1 if you want to add a new book\nEnter 2 if you want to borrow a book\nEnter 3 if you want to list all the books\nEnter 4 if you want to return a book");
            choice=scanner.nextInt();
            scanner.nextLine();
            switch(choice)
            {
                case 1:
                System.out.println("Enter title of the book:");
                String title=scanner.nextLine();
                Book newbook=new Book(title,name);
                library.addBook(newbook);
                break;

                case 2:
                System.out.println("Enter title of the book:");
                String title1=scanner.next();
                library.borrowBook(title1,name);
                break;

                case 3:
                library.listBooks();
                break;

                case 4:
                System.out.println("Enter title of the book:");
                String title2=scanner.next();
                library.returnBook(title2,name);
                break;

                default:
                System.out.println("Please enter a valid choice");
            }
            System.out.println("Do you want to continue?(y/n)");
            ans=scanner.next().toLowerCase().charAt(0);
        }
        System.out.println("Thank you for using Library Management System");
        scanner.close();
    }
}