package Controller;

import Model.User;
import View.Inputs;
import View.Outputs;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {
    public static ArrayList<User> users = new ArrayList<>();
    Outputs output = new Outputs();
    Inputs input = new Inputs();

    public void CreateUser(String Username, String Password, String PassConfirm, String Email, String Nickname) {
        if (Username.isEmpty() || Password.isEmpty() || PassConfirm.isEmpty() || Email.isEmpty() || Nickname.isEmpty()) {
            output.emptyFields();
            return;
        }
        if (!Username.matches("[a-zA-Z0-9]+")) {
            output.invalidUsername();
            return;
        }
        if (getByUsername(Username) != null) {
            output.DuplicatedUsername();
            return;
        }
        if (Password.equals("random")) {
            Password = generatePassword();
            output.ShowPass(Password);
            while (true) {
                if (!Password.equals(input.getInput())) {
                    output.PasswordNotMatch();
                } else {
                    break;
                }
            }
        } else if (!Password.equals(PassConfirm)) {
            output.PasswordNotMatch();
            return;
        } else if (Password.length() < 8 || invalidPassword(Password)) {
            output.InvalidPass();
            return;
        }
        if (invalidValidEmail(Email)) {
            output.InvalidEmail();
            return;
        }
        String FatherName, Color, Pet;
        ArrayList<String> answers = input.askQE();
        FatherName = answers.get(0);
        Color = answers.get(1);
        Pet = answers.get(2);
        users.add(new User(Username, Password, Nickname, Email, FatherName, Color, Pet));

        output.UserCreated();
    }

    public User login(String Username, String Password) {
        User user = getByUsername(Username);
        if (user == null) {
            output.UserNotFound();
            return null;
        } else if (!user.getPassword().equals(Password)) {
            output.wrongPass();
            return null;
        }
        User.setLoggedInUser(user);
        return user;
    }

    public void forgetPass(String Username) {
        User user = getByUsername(Username);
        if (user == null) {
            output.UserNotFound();
        } else {
            String FatherName, Color, Pet;
            while (true) {
                ArrayList<String> answers = input.askQE();
                FatherName = answers.get(0);
                Color = answers.get(1);
                Pet = answers.get(2);
                if (!user.getFathersName().equals(FatherName) || !user.getColor().equals(Color) || !user.getPet().equals(Pet)) {
                    output.WrongSecurityQE();
                } else {
                    break;
                }
            }
            while (true) {
                String newPass = input.getInput();
                if (newPass.length() < 8 || invalidPassword(newPass)) {
                    output.InvalidPass();
                } else {
                    user.setPassword(newPass);
                    output.changedPass();
                    break;
                }
            }
        }
    }

    public User changeUsername(User user, String newUser) {
        if (!newUser.matches("[a-zA-Z0-9]+")) {
            output.invalidUsername();
        } else if (getByUsername(newUser) != null) {
            output.DuplicatedUsername();
        } else {
            user.setUsername(newUser);
            output.changedUsername();
        }
        return user;
    }

    public User changeNickname(User user, String nickname) {
        user.setNickname(nickname);
        output.changedNickname();
        return user;
    }

    public User changePass(User user, String CurrentPass, String Pass) {
        if (!user.getPassword().equals(CurrentPass)) {
            output.wrongPass();
        } else if (CurrentPass.equals(Pass)) {
            output.DuplicatedPass();
        } else if (Pass.length() < 8 || invalidPassword(Pass)) {
            output.InvalidPass();
        } else {
            while (true) {
                System.out.println("please renter your new password again : ");
                String checkPass = input.getInput();
                if (checkPass.equals(Pass)) {
                    user.setPassword(Pass);
                    output.changedPass();
                    return user;
                } else {
                    System.out.println("not match! please try again");
                }
            }
        }
        return user;
    }

    public User changeEmail(User user, String email) {
        if (invalidValidEmail(email)) {
            output.InvalidEmail();
        } else {
            user.setEmail(email);
            output.changedEmail();
        }
        return user;
    }

    public User getByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static boolean invalidPassword(String input) {
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
                return false;
            }
        }
        return true;
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean invalidValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
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

    public void changeXP(User user, int value) {
        int xp = user.getXP();
        xp += value;
        int levelUp = (int) Math.pow(10, User.getLoggedInUser().getLevel());
        int level = 0;
        while (xp >= levelUp) {
            xp -= levelUp;
            level++;
        }
        user.setXP(xp);
        user.setLevel(user.getLevel() + level);
    }
    public void changeGold(User user , int value){
        int gold = user.getGold() ;
        gold+=value ;
        user.setGold(gold);
    }

    public void getData(User user){

    }

}
