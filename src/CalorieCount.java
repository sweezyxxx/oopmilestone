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
            System.out.printf("For '%s' you need to eat approximately %.2f calories a day.\n", goal, requiredCalories);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
