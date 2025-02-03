public class CalorieCount {
    private Users user;

    public CalorieCount(Users user) {
        this.user = user;
    }

    public double calculateCaloriesForGoal(String goal) {
        double maintenanceCalories = user.calculateCalorieNeeds();
        return switch (goal.toLowerCase()) {
            case "lose weight" -> maintenanceCalories - 500;
            case "gain weight" -> maintenanceCalories + 500;
            case "maintain weight" -> maintenanceCalories;
            default ->
                    throw new IllegalArgumentException("Invalid choice. Choose 'Lose Weight','Gain Weight' or 'Maintain Weight'.");
        };
    }

    public void displayCalorieRecommendation(String goal) {
        try {
            double requiredCalories = calculateCaloriesForGoal(goal);
            double weight = user.getWeight();

            double proteinPerKg;
            double fatPerKg;

            switch (goal.toLowerCase()) {
                case "lose weight":
                    proteinPerKg = 1.8;
                    fatPerKg = 0.8;
                    break;
                case "gain weight":
                    proteinPerKg = 2.0;
                    fatPerKg = 1.0;
                    break;
                case "maintain weight":
                    proteinPerKg = 1.5;
                    fatPerKg = 0.9;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid choice. Choose: 'Lose Weight', 'Gain Weight' or 'Maintain Weight'.");
            }

            double proteins = weight * proteinPerKg;
            double fats = weight * fatPerKg;
            double proteinCalories = proteins * 4;
            double fatCalories = fats * 9;
            double remainingCalories = requiredCalories - (proteinCalories + fatCalories);
            double carbs = remainingCalories / 4;

            System.out.println("\n══════════════════════════════════════════════════════════════════");
            System.out.printf(" Goal: %-15s  |  Recommended daily intake: %.2f kcal\n", goal, requiredCalories);
            System.out.println("══════════════════════════════════════════════════════════════════");
            System.out.printf(" Protein:        %.1f g (%.1f g/kg body weight)\n", proteins, proteinPerKg);
            System.out.printf(" Fats:           %.1f g (%.1f g/kg body weight)\n", fats, fatPerKg);
            System.out.printf(" Carbohydrates:  %.1f g (Remaining calories)\n", carbs);
            System.out.println("══════════════════════════════════════════════════════════════════\n");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
