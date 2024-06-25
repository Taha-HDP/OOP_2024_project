package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            //user register
            if (input.matches(Regexes.userRegister.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.userRegister.pattern);
                //userRegister(matcher);
            }
            //user login
            else if (input.matches(Regexes.userLogin.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.userLogin.pattern);
                //userLogin(matcher, scanner);
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
}
