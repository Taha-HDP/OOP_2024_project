package View;

import Controller.UserController;
import Model.SQL_setup;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    UserController UC = new UserController();
    int loginAttempt = 0;
    long elapsedTime = 0, startTime = 0;

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
            //admin login
            else if (input.matches(Regexes.adminLogin.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.adminLogin.pattern);
                adminLogin(matcher, scanner);
            }
            //forget password
            else if (input.matches(Regexes.forgetPassword.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.forgetPassword.pattern);
                forgetPass(matcher);
            }
            //show menu name
            else if (input.matches(Regexes.showMenuName.pattern))
                System.out.println("login menu");
                //exit
            else if (input.equals("exit"))
                System.exit(0);
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
        String username = matcher.group("username");
        String password = matcher.group("password");
        String passConfirm = matcher.group("passConfirm");
        String email = matcher.group("email");
        String nickName = matcher.group("nickName");
        UC.CreateUser(username, password, passConfirm, email, nickName);
    }

    private void userLogin(Matcher matcher, Scanner scanner) {
        matcher.find();
        if (startTime != 0) {
            elapsedTime = System.currentTimeMillis() / 1000 - startTime;
            if (elapsedTime >= (5L * loginAttempt)) {
                startTime = 0;
                elapsedTime = 0;
            } else {
                System.out.println("try again in (" + ((5L * loginAttempt) - elapsedTime) + ") seconds");
                return;
            }
        }
        String username = matcher.group("username");
        String password = matcher.group("password");
        User loggedInUser = UC.login(username, password);
        if (loggedInUser != null) {
            System.out.println("logged in successfully !");
            loginAttempt = 0;
            startTime = 0;
            UC.getData(loggedInUser) ;
            MainMenu.run(scanner);
        } else {
            loginAttempt++;
            startTime = System.currentTimeMillis() / 1000;
            elapsedTime = System.currentTimeMillis() / 1000 - startTime;
            System.out.println("try again in (" + ((5L * loginAttempt) - elapsedTime) + ") seconds");
        }
    }

    private void adminLogin(Matcher matcher, Scanner scanner) {
        matcher.find();
        String password = matcher.group("password");
        if (password.equals("AdminPassword")) {
            System.out.println("admin logged in successfully !");
            AdminMenu.run(scanner);
        }
    }

    private void forgetPass(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username");
        UC.forgetPass(username);
    }
}
