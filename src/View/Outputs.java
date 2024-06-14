package View;
import java.util.Scanner;
public class Outputs {
    public void emptyFields(){
        System.out.println("you have empty Fields");
    }
    public void invalidUsername(){
        System.out.println("your username must be number or letters");
    }
    public void DuplicatedUsername(){
        System.out.println("Duplicated username");
    }
    public void ShowPass(String Password){
        System.out.println("your random password : " + Password);
        System.out.print("please enter your password");
    }
    public void InvalidPass(){
        System.out.println("your password must have a capital and small and character !");
    }
    public void InvalidEmail(){
        System.out.println("your Email is invalid");
    }
    public void UserCreated(){
        System.out.println("User created successfully") ;
    }
    public void PasswordNotMatch(){
        System.out.println("your password and confirmation not match") ;
    }
}
