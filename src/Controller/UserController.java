package Controller;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.SecureRandom;
import Model.User;
import View.Inputs;
import View.Outputs;

public class UserController {
    ArrayList<User> users = new ArrayList<>() ;
    Outputs output ;
    Inputs input ;
    void CreateUser(String Username , String Password , String PassConfirm , String Email , String Nickname){
        if(Username.isEmpty() || Password.isEmpty() || PassConfirm.isEmpty() || Email.isEmpty() || Nickname.isEmpty() ){
            output.emptyFields() ;
            return;
        }
        if(!Username.matches("[a-zA-Z0-9]+")){
            output.invalidUsername();
            return;
        }
        if(getByUsername(Username)!=null){
            output.DuplicatedUsername();
            return;
        }
        if(Password.equals("random")){
            Password = generatePassword() ;
            output.ShowPass(Password) ;
            if(!Password.equals(input.getinput())){
                output.PasswordNotMatch();
                return;
            }
        }else if(!Password.equals(PassConfirm)){
            output.PasswordNotMatch();
            return;
        }else if(Password.length()<8 || !checkPassword(Password)){
            output.InvalidPass();
            return;
        }
        if(!isValidEmail(Email)){
            output.InvalidEmail();
            return;
        }
        String FatherName="" , Color="" , Pet="" ;
        output.UserCreated();
        input.askQE();
        //injaro bayad kamel konam (soal amniati moonde)
        users.add(new User(Username , Password , Nickname , Email , FatherName , Color , Pet)) ;
    }
    User getByUsername(String username){
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user ;
            }
        }
        return null ;
    }
    public static boolean checkPassword(String input) {
        boolean hasSmallLetter = false;
        boolean hasCapitalLetter = false;
        boolean hasCharacter = false;

        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasSmallLetter = true;
            } else if (Character.isUpperCase(c)) {
                hasCapitalLetter = true;
            } else {
                hasCharacter = true;
            }

            if (hasSmallLetter && hasCapitalLetter && hasCharacter) {
                return true;
            }
        }

        return false;
    }
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < 8; i++) {
            String allChars = LOWER + UPPER + DIGITS + SPECIAL;
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }
}
