import java.util.Scanner;

public class Users {

    private String name;
    private String login;
    private String password;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private ActivityLevel activityLevel;

    public Users(String name, String login, String password, int age, double weight, double height, String gender, ActivityLevel activityLevel) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activityLevel = activityLevel;
    }

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
        if (age > 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Invalid age value");
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Invalid weight value");
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        } else {
            throw new IllegalArgumentException("Invalid height value");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double calculateBMI() {
        double heightInMeters = height / 100;
        return weight / (heightInMeters * heightInMeters);
    }
    
    public String getBMICategory() {
        double bmi = calculateBMI();
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }
    
    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public double calculateBMR() {
        if (gender.equalsIgnoreCase("male")) {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else if (gender.equalsIgnoreCase("female")) {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Must be 'male' or 'female'.");
        }
    }
    

    public double calculateCalorieNeeds() {
        double bmr = calculateBMR();
        switch (activityLevel) {
            case SEDENTARY:
                return bmr * 1.2;
            case LIGHTLY_ACTIVE:
                return bmr * 1.375;
            case MODERATELY_ACTIVE:
                return bmr * 1.55;
            case VERY_ACTIVE:
                return bmr * 1.725;
            default:
                throw new IllegalArgumentException("Invalid activity level.");
        }
    }
    

    public void displayUserInfo() {
        System.out.println("Name: " + name);
        System.out.println("Login: " + login);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Height: " + height + " cm");
        System.out.println("Gender: " + gender);
        System.out.println("Activity Level: " + (activityLevel != null ? activityLevel : "Not set"));
    
        try {
            double bmi = calculateBMI();
            System.out.printf("BMI: %.2f\n", bmi);
            System.out.println("BMI Category: " + getBMICategory());
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to calculate BMI: " + e.getMessage());
        }
    }
    

    public boolean verifyLogin(String inputLogin, String inputPassword) {
        return this.login.equals(inputLogin) && this.password.equals(inputPassword);
    }

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

        System.out.print("Enter height (cm): ");
        double height = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Choose gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        int genderChoice = scanner.nextInt();
        scanner.nextLine();
    
        String gender = switch (genderChoice) {
            case 1 -> "male";
            case 2 -> "female";
            default -> throw new IllegalArgumentException("Invalid gender choice. Must be 1 (Male) or 2 (Female).");
        };

        System.out.println("Choose activity level: ");
        System.out.println("1. Sedentary");
        System.out.println("2. Lightly Active");
        System.out.println("3. Moderately Active");
        System.out.println("4. Very Active");
        int activityChoice = scanner.nextInt();

        ActivityLevel activityLevel = switch (activityChoice) {
            case 1 -> ActivityLevel.SEDENTARY;
            case 2 -> ActivityLevel.LIGHTLY_ACTIVE;
            case 3 -> ActivityLevel.MODERATELY_ACTIVE;
            case 4 -> ActivityLevel.VERY_ACTIVE;
            default -> throw new IllegalArgumentException("Invalid activity level choice.");
        };
        
        return new Users(name, login, password, age, weight, height, gender, activityLevel);
        
    }
}
