package message;

public enum BasicInfoMessage {
    FILE_PATH("src/main/resources/"),
    PRODUCTS_FILE("products.md"),
    PROMOTIONS_FILE("promotions.md"),
    BASIC_DATETIME_FORMAT("yyyy-MM-dd");

    private final String message;

    BasicInfoMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

