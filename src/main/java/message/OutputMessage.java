package message;

public enum OutputMessage {
    GREETING("안녕하세요. W편의점입니다."),
    CURRENT_PRODUCTS("현재 보유하고 있는 상품입니다."),
    SOLDOUT_DISPLAY_FORMAT("- %s %,d원 재고 없음 %s"),  // 재고 없음 포맷
    PRODUCT_DISPLAY_FORMAT("- %s %,d원 %d개 %s");
    
    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args); // 포맷팅 가능한 메시지
    }
}

