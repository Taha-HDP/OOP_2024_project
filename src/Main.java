import View.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginMenu LM = new LoginMenu();
        LM.run(scanner);
    }
}