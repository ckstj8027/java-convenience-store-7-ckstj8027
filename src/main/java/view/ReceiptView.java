package view;

public class ReceiptView {


    public void printStatReceipt() {
        System.out.println();
        System.out.println("==============W 편의점================");
        System.out.printf("%-12s %6s %10s%n", "상품명", "수량", "금액");
    }


    public void printContent(String itemName, int totalQuantity, int currentAmount) {
        System.out.printf("%-12s %6d %15s%n", itemName, totalQuantity, String.format("%,d", currentAmount));
    }


    public void printFreeGift(String itemName, int freeGiftQuantity) {
        if (freeGiftQuantity > 0) {
            System.out.printf("%-12s %6d%n", itemName, freeGiftQuantity);
        }
    }


    public void printFreeGiftTitle() {
        System.out.println("=============증    정===============");
    }

    
    public void printTotalAmount(int salesTotalQuantity, int totalAmount) {
        System.out.println("====================================");
        System.out.printf("%-12s %6d %15s%n", "총구매액", salesTotalQuantity, String.format("%,d", totalAmount));
    }


    public void printDiscountContent(int totalFreeGiftAmount, int membershipDiscountAmount) {
        System.out.printf("%-10s %23s%n", "행사할인", String.format("-%s", String.format("%,d", totalFreeGiftAmount)));
        System.out.printf("%-10s %23s%n", "멤버십할인",
                String.format("-%s", String.format("%,d", membershipDiscountAmount)));
    }


    public void printReceiptAmount(int totalAmount, int totalFreeGiftAmount, int membershipDiscountAmount) {
        int payableAmount = totalAmount - totalFreeGiftAmount - membershipDiscountAmount;
        System.out.printf("%-10s %23s%n", "내실돈", String.format("%,d", payableAmount));
    }
}
