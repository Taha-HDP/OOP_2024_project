package View;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inputs {
    Scanner scanner = new Scanner(System.in);
    public ArrayList<String> askQE(){
        System.out.println("please chose a security question :") ;
        System.out.println("\t . What is your father's name ?") ;
        System.out.println("\t . What is your favourite color ?") ;
        System.out.println("\t . What was the name of your first pet ?") ;

        ArrayList<String> answers = new ArrayList<>() ;
        String ans1="" , ans2="" , ans3="" ;
        while (true){
            String input = scanner.nextLine();
            if (input.matches(Regexes.QE.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.QE.pattern);
                matcher.find();
                if(!matcher.group("answer").equals(matcher.group("answerConfirm"))){
                    System.out.println("answer and answer confirm is not same ! try again");
                }else if(matcher.group("QENumber").equals("1")){
                    ans1 = matcher.group("answer") ;
                    System.out.println("answer 1 accepted");
                }else if(matcher.group("QENumber").equals("2")){
                    ans2 = matcher.group("answer") ;
                    System.out.println("answer 2 accepted");
                }else if(matcher.group("QENumber").equals("3")){
                    ans3 = matcher.group("answer") ;
                    System.out.println("answer 3 accepted");
                }else{
                    System.out.println("invalid number");
                }
                if(!ans1.isEmpty() && !ans2.isEmpty() && !ans3.isEmpty()){
                    answers.add(ans1) ;
                    answers.add(ans2) ;
                    answers.add(ans3) ;
                    System.out.println("all question answered");
                    break ;
                }
            }else{
                System.out.println("invalid input");
            }
        }
        while (true){
            if(Captcha.ask()){
                break;
            }
        }
        return answers ;
    }
    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    public String getInput(){
        return scanner.nextLine() ;
    }
}
