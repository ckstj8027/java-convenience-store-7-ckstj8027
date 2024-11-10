package message;

public enum ExceptionMessage {

    INVALID_PRODUCT_QUANTITY("[ERROR] 수량은 양수여야 합니다. 잘못된 값: %d"),
    FILE_READ_ERROR("[ERROR] 파일을 읽을 수 없습니다: %s"),


    INVALID_ORDER_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OUT_OF_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),

    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args); // 포맷팅 가능한 메시지
    }

}

