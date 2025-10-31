import java.util.Scanner;
public class Task1 {
    double addition(double a,double b)
    {
        return a+b;
    }
    double subtraction(double a,double b)
    {
        return a-b;
    }
    double multiplication(double a,double b)
    {
        return a*b;
    }
    double division(double a,double b)
    {
        if(b==0.0)
        {
            System.out.println("Division by 0 not possible");
            return Double.NaN;
        }
        return a/b;
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Task1 obj=new Task1();
        char op,choice='y';
        double a,b,result;
        while(choice=='y')
        {
            System.out.println("Enter the first number:");
            a=scanner.nextDouble();
            System.out.println("Enter the second number:");
            b=scanner.nextDouble();
            System.out.println("Enter the operator(+,-,*,/):");
            op=scanner.next().charAt(0);
            switch(op)
            {
                case '+':
                result=obj.addition(a,b);
                break;
                case '-':
                result=obj.subtraction(a,b);
                break;
                case '*':
                result=obj.multiplication(a,b);
                break;
                case '/':
                result=obj.division(a,b);
                break;
                default:
                System.out.println("Please enter valid operator(+,-,*,/)");
                result=Double.NaN;
            }
            if(!Double.isNaN(result))
            {
                System.out.println(result);
            }
            System.out.println("Do you want to perform another operation?(y/n)");
            choice=scanner.next().toLowerCase().charAt(0);
        }
        System.out.println("Thank you for using the calculator!!");
        scanner.close();
    }
}