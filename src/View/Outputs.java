package View;

public class Outputs {
    public void emptyFields() {
        System.out.println("you have empty Fields");
    }

    public void invalidUsername() {
        System.out.println("your username must be number or letters");
    }

    public void DuplicatedUsername() {
        System.out.println("Duplicated username");
    }

    public void ShowPass(String Password) {
        System.out.println("your random password : " + Password);
        System.out.print("please enter your password : ");
    }

    public void InvalidPass() {
        System.out.println("your password must have a capital and small and character !");
        System.out.println("and it must be minimum 8 characters");
    }

    public void InvalidEmail() {
        System.out.println("your Email is invalid");
    }

    public void UserCreated() {
        System.out.println("User created successfully");
    }

    public void PasswordNotMatch() {
        System.out.println("your password and confirmation not match");
    }

    public void UserNotFound() {
        System.out.println("Username don't exist");
    }

    public void wrongPass() {
        System.out.println("Password and username don't match");
    }

    public void WrongSecurityQE() {
        System.out.println("sequrity answer is wrong");
    }

    public void changedPass() {
        System.out.println("password successfully changed");
    }

    public void changedUsername() {
        System.out.println("username successfully changed");
    }

    public void changedNickname() {
        System.out.println("nickname successfully changed");
    }

    public void changedEmail() {
        System.out.println("email successfully changed");
    }

    public void DuplicatedPass() {
        System.out.println("please enter new Password");
    }
}
