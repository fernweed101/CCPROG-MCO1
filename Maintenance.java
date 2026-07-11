public class Maintenance{
    public void stockItem(RegularVendo vendo, Item item, int quantity){
        vendo.addItemStock(item, quantity);
    }

    public void setItemPrice(Item item, int price){
        if(price > 0){
            item.setPrice(price);
        }else{
            System.out.println("Price cannot be a negative value");
        }
    }

    public void collectPayment(RegularVendo vendo){
        //totalCash += vendo.getTotalCash();
        System.out.println("Total of P" + vendo.getTotalCash() + " was Collected");
        vendo.setTotalCash(0);
    }

    public void printSummary(RegularVendo vendo){
        System.out.println("=====================================================================================================");
        System.out.println("Summary of Transactions");
        System.out.println("Item: Initial, Current, Sold");
        int itemsSold = 0;

        for(ItemSlot slot : vendo.getItemSlots()){
            int numSold = slot.getInitialNum() - slot.getNumItems();
            itemsSold += numSold;
            if(slot.getItem() != null){
                System.out.println(slot.getItem().getName() + ": " + slot.getInitialNum() + ", " +  slot.getNumItems() + ", " +  numSold);
            }
        }

        System.out.println("Total Items Sold: " + itemsSold);
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
                    System.out.println("Added "+ quantity +" Php" + value + " to vendo denominations");
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