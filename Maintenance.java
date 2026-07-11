public class Maintenance{
    private int totalCash;

    public void stockItem(RegularVendo vendo, Item item, int quantity){
        vendo.addItemStock(item, quantity);
    }

    public void setItemPrice(Item item, int price){
        item.setPrice(price);
    }

    public void collectPayment(RegularVendo vendo){
        totalCash += vendo.getTotalCash();
        vendo.setTotalCash(0);
    }

    public void replenishDenomination(RegularVendo vendo, int value, int quantity){
        if(quantity > 0){
            switch (value) {
                case 1:
                case 5:
                case 10:
                case 20:
                case 50:
                case 100:
                case 200:
                case 500:
                case 1000:
                    for(int i = 0; i < quantity; i++){
                        vendo.addDenomination(value);
                    }
                    System.out.println("Added "+ quantity +" " + value + " to the internal cash bank.");
                    break;    
                default:
                    System.out.println("This vending machine does not use Php" + value);
                    break;
            }
        }else{
            System.out.println("Invalid Input");
        }
    }
}