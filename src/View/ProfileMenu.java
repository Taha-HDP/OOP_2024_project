package View;

import Controller.UserController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu {

    public static void run(Scanner scanner){
        System.out.println("entered profile menu");
        System.out.println("1- Show information");
        System.out.println("2- Profile change -u (username)");
        System.out.println("3- Profile change -n (nickname)");
        System.out.println("4- Profile change password -o (oldPass) -n (newPass)");
        System.out.println("5- Profile change -e (email)");
        while (true) {
            String input = scanner.nextLine();
            //show info
            if (input.matches(Regexes.showProfileInfo.pattern)) {
                User myUser = User.getLoggedInUser() ;
                System.out.println("username : " + myUser.getUsername());
                System.out.println("password : " + myUser.getPassword());
                System.out.println("email : " + myUser.getEmail());
                System.out.println("nickname : " + myUser.getNickname());
                System.out.println("xp : " + myUser.getXP());
                System.out.println("level : " + myUser.getLevel());
                System.out.println("gold : " + myUser.getGold());
            }
            //change username
            else if (input.matches(Regexes.changeUsername.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.changeUsername.pattern);
                changeUsername(matcher);
            }
            //change nickname
            else if (input.matches(Regexes.changeNickname.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.changeNickname.pattern);
                changeNickname(matcher);
            }
            //change email
            else if (input.matches(Regexes.changeEmail.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.changeEmail.pattern);
                changeEmail(matcher);
            }
            //change password
            else if (input.matches(Regexes.changePassword.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.changePassword.pattern);
                changePassword(matcher);
            }
            //show menu name
            else if (input.matches(Regexes.showMenuName.pattern))
                System.out.println("profile menu");
            //exit
            else if (input.equals("exit"))
                System.exit(0);
            //back to main
            else if (input.equals("back"))
                MainMenu.run(scanner);
            //invalid command
            else
                System.out.println("invalid command");
        }
    }
    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    private static void changeNickname(Matcher matcher) {
        matcher.find();
        String nickname = matcher.group("nickname") ;
        UserController UC = new UserController() ;
        User user = UC.changeNickname(User.getLoggedInUser() , nickname) ;
        User.setLoggedInUser(user);
    }
    private static void changeEmail(Matcher matcher) {
        matcher.find();
        String email = matcher.group("email") ;
        UserController UC = new UserController() ;
        User user = UC.changeEmail(User.getLoggedInUser() , email) ;
        User.setLoggedInUser(user);
    }
    private static void changeUsername(Matcher matcher) {
        matcher.find();
        String username = matcher.group("username") ;
        UserController UC = new UserController() ;
        User user = UC.changeUsername(User.getLoggedInUser() , username) ;
        User.setLoggedInUser(user);
    }
    private static void changePassword(Matcher matcher) {
        matcher.find();
        String oldPass = matcher.group("oldPass") ;
        String newPass = matcher.group("newPass") ;
        UserController UC = new UserController() ;
        User user = UC.changePass(User.getLoggedInUser() , oldPass , newPass) ;
        User.setLoggedInUser(user);
    }
}
