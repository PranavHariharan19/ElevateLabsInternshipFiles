import java.util.ArrayList;
import java.util.Scanner;
class Question
{
    String question;
    String[] options;
    char answer;
    Question(String question,String[] options,char answer)
    {
        this.question=question;
        this.options=options;
        this.answer=answer;
    }
    boolean iscorrect(char option)
    {
        return Character.toUpperCase(option)==answer;
    }
}
public class Task8 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        ArrayList<Question> questions =new ArrayList<>();
        questions.add(new Question("What is the hardest natural substance on Earth?",new String[]{"Gold","Iron","Diamond","Quartz"},'C'));
        questions.add(new Question("What is the name of the largest ocean on Earth?",new String[]{"Atlantic Ocean","Indian Ocean","Arctic Ocean","Pacific Ocean"},'D'));
        questions.add(new Question("What is the capital of Australia?",new String[]{"Sydney","Melbourne","Canberra","Brisbane"},'C'));
        questions.add(new Question("Which planet has the most moons?",new String[]{"Saturn","Jupiter","Neptune","Uranus"}, 'A'));
        questions.add(new Question("Who founded Microsoft?",new String[]{"Elon Musk","Steve Jobs","Bill Gates","Mark Zuckerberg"},'C'));
        int score=0;
        System.out.println("Welcome to General Knowledge Quiz");
        System.out.println("Are you ready to begin?(y/n)");
        char begin=scanner.next().charAt(0);
        System.out.println();
        if(begin=='y')
        {
            for(int i=0;i<questions.size();i++)
            {
                System.out.println((i+1)+". "+questions.get(i).question+"\n");
                for(String option:questions.get(i).options)
                {
                    System.out.println(option);
                }
                System.out.println();
                System.out.println("Enter you option:");
                char choice=scanner.next().charAt(0);
                if(questions.get(i).iscorrect(choice))
                {
                    System.out.println("Your answer is correct!!\n");
                    score++;
                }
                else
                {
                    System.out.println("Your answer is wrong.The correct answer is "+questions.get(i).answer+"\n");
                }
            }        
            System.out.println("Congratulations!! You have completed the quiz");
            System.out.println("Your score is "+score+"/"+questions.size()+"\n");
        }
        System.out.println("Thank you for using General Knowledge Quiz");
        scanner.close();
    }
}
