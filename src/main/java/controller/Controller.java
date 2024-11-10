package controller;


import inventoryInitializer.BasicInfo;


import service.ConvenienceService;


public class Controller {


    private final BasicInfo basicInfo;
    private final ConvenienceService convenienceService;


    public Controller(BasicInfo basicInfo, ConvenienceService convenienceService) {
        this.basicInfo = basicInfo;
        this.convenienceService = convenienceService;
    }

    public void play() {
        basicInfo.loadBasicInfo();  // 초기 프로덕트 프로모션 재고 다 만들어짐
        boolean continueShopping = true;
        shopping(continueShopping);
    }

    private void shopping(boolean continueShopping) {
        while (continueShopping) {
            try {
                displaySaleProducts();
                startOrder();
                purchasingProcess();
                continueShopping = convenienceService.checkContinueShopping();
                convenienceService.initSale();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

  private void displaySaleProducts(){
      convenienceService.displaySalesProductTable();
  }

    private void startOrder() {

        convenienceService.processOrder();
    }

    private void purchasingProcess() {
        convenienceService.makeSaleInfo();
        convenienceService.makeReceipt();
        convenienceService.adjustInventory();
    }


}








