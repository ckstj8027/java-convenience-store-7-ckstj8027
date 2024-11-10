package view;

import camp.nextstep.edu.missionutils.Console;
import message.ExceptionMessage;
import message.InputMessage;

public class InputView {


    public String getOrderInput() {
        System.out.println();
        System.out.println(InputMessage.ORDER_INPUT_PROMPT.getMessage());

        return Console.readLine();
    }


    public boolean getYesOrNoInput(String message) {
        try {
            String input = getString(message);
            Boolean booleanValue = getBooleanValue(input);
            if (booleanValue == null) {
                throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT.getMessage());
            }
            return booleanValue;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getYesOrNoInput(message);
        }
    }


    private Boolean getBooleanValue(String input) {
        Boolean result = null;
        if (input.equals("Y")) {
            result = true;
        }
        if (input.equals("N")) {
            result = false;
        }
        return result;
    }


    private String getString(String message) {
        System.out.println();
        System.out.println(message);
        String input = Console.readLine().trim().toUpperCase();
        return input;
    }


    public boolean continueView() {
        return getYesOrNoInput(InputMessage.YES_OR_NO_PROMPT.getMessage());
    }


    public boolean isMembershipDiscount() {
        return getYesOrNoInput(InputMessage.MEMBERSHIP_DISCOUNT_PROMPT.getMessage());

    }


    public boolean getMoreOneView(String orderName) {
        return getYesOrNoInput(InputMessage.ADDITIONAL_ITEM_PROMPT.getMessage(orderName));

    }


    public boolean applyPartialPromotion(String saleItemName, int generalSaleQuantity) {
        return getYesOrNoInput(InputMessage.PARTIAL_PROMOTION_PROMPT.getMessage(saleItemName, generalSaleQuantity));

    }


}
