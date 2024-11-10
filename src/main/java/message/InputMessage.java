package message;

public enum InputMessage {
    ORDER_INPUT_PROMPT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    YES_OR_NO_PROMPT("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    MEMBERSHIP_DISCOUNT_PROMPT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    ADDITIONAL_ITEM_PROMPT("현재 %s(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    PARTIAL_PROMOTION_PROMPT("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");


    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
