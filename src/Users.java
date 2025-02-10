import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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

    public void saveToDatabase() {
        String sql = "INSERT INTO users (name, login, password, age, weight, height, gender, activity_level, bmi, bmi_category, bmr, calorie_needs) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
             "ON CONFLICT (login) DO UPDATE " +
             "SET weight = EXCLUDED.weight, height = EXCLUDED.height, activity_level = EXCLUDED.activity_level, " +
             "bmi = EXCLUDED.bmi, bmi_category = EXCLUDED.bmi_category, bmr = EXCLUDED.bmr, calorie_needs = EXCLUDED.calorie_needs";
    
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            double bmi = calculateBMI();
            String bmiCategory = getBMICategory();
            double bmr = calculateBMR();
            double calorieNeeds = calculateCalorieNeeds();
    
            stmt.setString(1, this.name);
            stmt.setString(2, this.login);
            stmt.setString(3, this.password);
            stmt.setInt(4, this.age);
            stmt.setDouble(5, this.weight);
            stmt.setDouble(6, this.height);
            stmt.setString(7, this.gender);
            stmt.setString(8, this.activityLevel.getShortName());
            stmt.setDouble(9, bmi);
            stmt.setString(10, bmiCategory);
            stmt.setDouble(11, bmr);
            stmt.setDouble(12, calorieNeeds);
    
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" User data saved successfully.");
            } else {
                System.out.println(" Failed to save user data.");
            }
    
        } catch (SQLException e) {
            System.out.println(" Error saving user data: " + e.getMessage());
        }
    }
    
            

    public static Users getUserByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                ActivityLevel activity = null;
                for (ActivityLevel level : ActivityLevel.values()) {
                    if (level.getShortName().equalsIgnoreCase(rs.getString("activity_level"))) {
                        activity = level;
                        break;
                    }
                }
                if (activity == null) {
                    throw new IllegalArgumentException("Unknown activity level: " + rs.getString("activity_level"));
                }
    
                Users user = new Users(
                    rs.getString("name"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getInt("age"),
                    rs.getDouble("weight"),
                    rs.getDouble("height"),
                    rs.getString("gender"),
                    activity
                );
    
                System.out.println("\nUser Data Loaded:");
                System.out.println("BMI: " + rs.getDouble("bmi"));
                System.out.println("BMI Category: " + rs.getString("bmi_category"));
                System.out.println("BMR: " + rs.getDouble("bmr"));
                System.out.println("Calorie Needs: " + rs.getDouble("calorie_needs"));
    
                return user;
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
        return null;
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
            case 1 -> "Male";
            case 2 -> "Female";
            default -> throw new IllegalArgumentException("Invalid gender choice.");
        };
    
        System.out.println("Choose activity level: ");
        System.out.println("1. Sedentary [Little to no exercise]");
        System.out.println("2. Lightly Active [Light exercise 1-2 days/week]");
        System.out.println("3. Moderately Active [Moderate exercise 3-4 days/week]");
        System.out.println("4. Very Active [Intense exercise 5+ days/week]");
        int activityChoice = scanner.nextInt();
        scanner.nextLine();
    
        ActivityLevel activityLevel = switch (activityChoice) {
            case 1 -> ActivityLevel.SEDENTARY;
            case 2 -> ActivityLevel.LIGHTLY_ACTIVE;
            case 3 -> ActivityLevel.MODERATELY_ACTIVE;
            case 4 -> ActivityLevel.VERY_ACTIVE;
            default -> throw new IllegalArgumentException("Invalid activity level choice.");
        };
    
        return new Users(name, login, password, age, weight, height, gender, activityLevel);
    }

    public boolean verifyLogin(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, login);
            stmt.setString(2, password);
    
            System.out.println("Executing login query for: " + login);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                System.out.println("Login successful for: " + login);
                return true;
            } else {
                System.out.println("No matching user found for login: " + login);
                return false;
            }
    
        } catch (SQLException e) {
            System.out.println("Error verifying login: " + e.getMessage());
            return false;
        }
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

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
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
        return switch (activityLevel) {
            case SEDENTARY -> bmr * 1.2;
            case LIGHTLY_ACTIVE -> bmr * 1.375;
            case MODERATELY_ACTIVE -> bmr * 1.55;
            case VERY_ACTIVE -> bmr * 1.725;
            default -> throw new IllegalArgumentException("Invalid activity level.");
        };
    }

    public void displayUserInfo() {
        System.out.println("\n======================================");
        System.out.println("             USER PROFILE            ");
        System.out.println("======================================");
        System.out.printf(" Name:          %s\n", name);
        System.out.printf(" Login:         %s\n", login);
        System.out.printf(" Age:           %d years\n", age);
        System.out.printf(" Weight:        %.1f kg\n", weight);
        System.out.printf(" Height:        %.1f cm\n", height);
        System.out.printf(" Gender:        %s\n", gender);
        System.out.printf(" Activity:      %s\n",
            activityLevel != null ? activityLevel.getShortName() : "Not set");

        double bmi = calculateBMI();
        String bmiCategory = getBMICategory();
        System.out.println("--------------------------------------");
        System.out.printf(" BMI:           %.2f (%s)\n", bmi, bmiCategory);
        System.out.println("======================================\n");
    }
}
