package View;
import Controller.UserController;
import Model.User;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LoginMenu {
    UserController UC = new UserController();
    public void run(Scanner scanner) {
        System.out.println("entered login menu");
        while (true) {
            String input = scanner.nextLine();
            //user register
            if (input.matches(Regexes.userRegister.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.userRegister.pattern);
                userRegister(matcher);
            }
            //user login
            else if (input.matches(Regexes.userLogin.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.userLogin.pattern);
                userLogin(matcher, scanner);
            }
            //forget password
            else if (input.matches(Regexes.forgetPassword.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.forgetPassword.pattern);
                forgetPass(matcher, scanner);
            }
            //show menu name
            else if (input.matches(Regexes.showMenuName.pattern))
                System.out.println("login menu");
            //exit
            else if (input.equals("exit"))
                break;
            //invalid command
            else
                System.out.println("invalid command");
        }
    }
    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    private void userRegister(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username") ;
        String password = matcher.group("password") ;
        String passConfirm = matcher.group("passConfirm") ;
        String email = matcher.group("email") ;
        String nickName = matcher.group("nickName") ;
        UC.CreateUser(username,password,passConfirm, email ,nickName) ;
    }
    private void userLogin(Matcher matcher, Scanner scanner) {
        matcher.find();
        String username = matcher.group("username") ;
        String password = matcher.group("password") ;
        User loggedInUser = UC.login(username,password) ;
        if(loggedInUser!=null){
            System.out.println("logged in successfully !");
            MainMenu.run(scanner);
        }
    }
    private void forgetPass(Matcher matcher, Scanner scanner) {
        matcher.find();
        String username = matcher.group("username") ;
        UC.forgetPass(username);
    }
}
