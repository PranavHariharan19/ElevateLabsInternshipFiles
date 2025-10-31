import java.util.ArrayList;
import java.util.Scanner;
class InsufficientFundsException extends Exception
{
    InsufficientFundsException(double balance)
    {
        super("Insufficient balance! Available balance:"+balance+" Rupees");
    }
}
class Account
{
    private double balance;
    private ArrayList<String> transactionHistory;
    Account(double balance)
    {
        this.balance=balance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account opened with balance:"+balance+" Rupees");
    }
    void deposit(double amount)
    {
        System.out.println("Deposited:"+amount+" Rupees");
        this.balance+=amount;
        transactionHistory.add("Deposited:" + amount+" Rupees");
        displayBalance();
    }
    void withdraw(double amount) throws InsufficientFundsException
    {
        if(amount>this.balance)
        {
            throw new InsufficientFundsException(this.balance);
        }
        System.out.println("Withdrawn:"+amount+" Rupees");
        this.balance-=amount;
        transactionHistory.add("Withdrawn:" + amount+" Rupees");
        displayBalance();
    }
    void displayBalance()
    {
        System.out.println("Current Balance:"+this.balance+" Rupees");
    }
    void printTransactionHistory() 
    {
        System.out.println("\nTransaction History:");
        for (String entry : transactionHistory) 
        {
            System.out.println("- " + entry);
        }
        displayBalance();
    }
}
public class Task5
{
    public static void main(String[] args)
    {
        System.out.println("Welcome to banking management system");
        Scanner scanner=new Scanner(System.in);
        double initial,deposit,withdrawal;
        char ans='y';
        System.out.println("Enter initial balance:");
        initial=scanner.nextDouble();
        Account acc=new Account(initial);
        while(ans=='y')
        {
            System.out.println("Enter 1 to deposit money\nEnter 2 to withdraw money\nEnter 3 to view transaction history");
            int choice=scanner.nextInt();
            switch(choice)
            {
                case 1:
                System.out.println("Enter deposit amount:");
                deposit=scanner.nextDouble();
                acc.deposit(deposit);
                break;

                case 2:
                System.out.println("Enter withdrawal amount:");
                withdrawal=scanner.nextDouble();
                try
                {
                    acc.withdraw(withdrawal);
                }
                catch(InsufficientFundsException e)
                {
                    System.out.println("Exception: " + e.getMessage());
                }
                break;

                case 3:
                acc.printTransactionHistory();
                break;

                default:
                System.out.println("Please enter a valid choice");
            }
            System.out.println("Do you want to continue?(y/n)");
            ans=scanner.next().toLowerCase().charAt(0);
        }
        System.out.println("Thank you for visiting banking management system!!");
        scanner.close();
    }
}