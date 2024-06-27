package View;

import java.util.Random;
import java.util.Scanner;

public class Captcha {
    private static final String[][] asciiNumbers = {
            {"  ___  ", "/ _ \\ ", "| | | |", "| | | |", "| |_| |", " \\___/ "},
            {" __ ", "/_ |", " | |", " | |", " | |", " |_|"},
            {" ____  ", "|___ \\ ", "  __) |", " / __/ ", "|_____|", "       "},
            {" _____ ", "|___ / ", "  |_ \\ ", " ___) |", "|____/ ", "       "},
            {" _  _  ", "| || | ", "| || |_ ", "|__   _|", "   |_|  ", "       "},
            {" ____  ", "| ___| ", "|___ \\ ", " ___) |", "|____/ ", "       "},
            {"  ____ ", " / ___|", "| |  _ ", "| |_| |", " \\____|", "       "},
            {" ______", "|____  |", "    / / ", "   / /  ", "  /_/   ", "       "},
            {"  ___  ", " ( _ ) ", " / _ \\ ", "| (_) |", " \\___/ ", "       "},
            {"  ___  ", " / _ \\ ", "| (_) |", " \\__, |", "   /_/ ", "       "}
    };

    public static boolean ask() {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);
        int length = 5 + rand.nextInt(3);
        StringBuilder[] captchaLines = new StringBuilder[6];
        StringBuilder plainCaptcha = new StringBuilder();
        for (int i = 0; i < captchaLines.length; i++) {
            captchaLines[i] = new StringBuilder();
        }
        for (int i = 0; i < length; i++) {
            int randomNum = rand.nextInt(10);
            plainCaptcha.append(randomNum);
            for (int j = 0; j < asciiNumbers[randomNum].length; j++) {
                captchaLines[j].append(asciiNumbers[randomNum][j]).append(" ");
            }
        }
        addNoise(captchaLines);
        for (StringBuilder line : captchaLines) {
            System.out.println(line.toString());
        }
        System.out.print("Please enter the captcha: ");
        String userInput = scanner.nextLine();
        if (userInput.equals(plainCaptcha.toString())) {
            System.out.println("Accepted");
            return true;
        } else {
            System.out.println("Captcha does not match. Please try again.");
            return false;
        }
    }

    private static void addNoise(StringBuilder[] captchaLines) {
        Random rand = new Random();
        for (StringBuilder line : captchaLines) {
            for (int i = 0; i < line.length(); i++) {
                if (rand.nextInt(10) < 1) {
                    line.setCharAt(i, (char) ('!' + rand.nextInt(14)));
                }
            }
        }
    }
}