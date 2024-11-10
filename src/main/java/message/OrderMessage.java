package message;

public enum OrderMessage {
    // 정규식 패턴
    ITEM_SEPARATOR("],\\["),
    ORDER_INPUT_REGEX("^\\[([A-Za-z가-힣]+-\\d+)\\](,\\[([A-Za-z가-힣]+-\\d+)\\])*$");


    private final String message;

    OrderMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
