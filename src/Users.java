import java.util.Scanner;

public class Users {

    // Fields for user information
    private String name;
    private String login;
    private String password;
    private int age;
    private double weight;
    private double height;
    private String gender;

    // Constructor
    public Users(String name, String login, String password, int age, double weight, double height, String gender) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Method to display user information
    public void displayUserInfo() {
        System.out.println("Name: " + name);
        System.out.println("Login: " + login);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Height: " + height + " m");
        System.out.println("Gender: " + gender);
    }

    // Method to verify login
    public boolean verifyLogin(String inputLogin, String inputPassword) {
        return this.login.equals(inputLogin) && this.password.equals(inputPassword);
    }

    // Method to create a user by taking input
    public static Users createUserFromInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter login: ");
        String login = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();

        System.out.print("Enter weight (kg): ");
        double weight = scanner.nextDouble();

        System.out.print("Enter height (m): ");
        double height = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        return new Users(name, login, password, age, weight, height, gender);
    }
}