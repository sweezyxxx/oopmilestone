import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Users> list = new ArrayList<>();
        while (true){
            System.out.println("════════════════════════");
            System.out.println("       MAIN MENU");      
            System.out.println("════════════════════════");
            System.out.println(" 1 - Register User");
            System.out.println(" 2 - Login User");
            System.out.println(" 3 - Exit");
            System.out.println("════════════════════════");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    Users newuser = Users.createUserFromInput();
                    boolean userExists = false;
                    for (Users user : list) {
                        if (user.getLogin().equalsIgnoreCase(newuser.getLogin())) {
                            userExists = true;
                            break;
                        }
                    }
                    if (userExists) {
                        System.out.println("Error: A user with this login already exists.");
                    } else {
                        list.add(newuser);
                        System.out.println("New user registered successfully.");
                    }
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

                            System.out.println("\nWhat is your goal?");
                            System.out.println("1. Lose weight");
                            System.out.println("2. Gain weight");
                            System.out.println("3. Maintain weight");
                            System.out.print("Enter your choice: ");
                            if (!sc.hasNextInt()) {
                                System.out.println("Ошибка: Введите число от 1 до 3.");
                                sc.nextLine();
                                break;
                            }
                            int goalChoice = sc.nextInt();
                            sc.nextLine();
                            String goal = "";
                            switch (goalChoice) {
                                case 1:
                                    goal = "Lose Weight";
                                    break;
                                case 2:
                                    goal = "Gain Weight";
                                    break;
                                case 3:
                                    goal = "Maintain Weight";
                                    break;
                                default:
                                    System.out.println("Invalid choice. Choose from 1 to 3");
                                    break;
                            }

                            CalorieCount calorieCount = new CalorieCount(user);
                            calorieCount.displayCalorieRecommendation(goal);
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