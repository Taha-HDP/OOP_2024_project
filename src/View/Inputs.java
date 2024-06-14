package View;
import java.util.Scanner;
public class Inputs {
    Scanner scanner = new Scanner(System.in);
    public void askQE(){
        System.out.println("please chose a security question :") ;
        System.out.println("\t . What is your father's name ?") ;
        System.out.println("\t . What is your favourite color ?") ;
        System.out.println("\t . What was the name of your first pet ?") ;

    }
    public String getinput(){
        return scanner.nextLine() ;
    }
}
