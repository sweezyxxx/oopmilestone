import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Users> list = new ArrayList<>();
        while (true){
            System.out.println("\nMenu:");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    Users newuser = Users.createUserFromInput();
                    list.add(newuser);
                    System.out.println("New user registered.");
                    break;
                case 2:
                    System.out.print("Enter login: ");
                    String login = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    boolean loginSuccessful = false;
                    for (Users user : list) {
                        if (user.verifyLogin(login, password)) {
                            System.out.println("Login successful! Welcome, " + user.getName() + "!");
                            user.displayUserInfo();
                            loginSuccessful = true;
                            break;
                        }
                    }
                    if (!loginSuccessful) {
                        System.out.println("Invalid login or password.");
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}