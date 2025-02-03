public enum ActivityLevel {
    SEDENTARY("Sedentary"),
    LIGHTLY_ACTIVE("Lightly Active"),
    MODERATELY_ACTIVE("Moderately Active"),
    VERY_ACTIVE("Very Active");

    private final String shortName;

    ActivityLevel(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
