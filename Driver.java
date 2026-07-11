import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
        static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int input;

        System.out.println("Choose a type of vending machine to create");
        System.out.println("[1] Regular Vending Machine");
        System.out.println("[2] Exit");
        input = readInt(scanner);
        System.out.println("=====================================================================================================");
        if(input == 1){
            Maintenance maint = new Maintenance();
            
            System.out.println("Input the number of slots for the vending machine (Minimum of 8)");
            int vendoSlots = readInt(scanner);
            System.out.println("Input the max capacity of items for a slot (Minimum of 10)");
            int slotCap = readInt(scanner);
            RegularVendo vendo = new RegularVendo(vendoSlots, slotCap);
            System.out.println("=====================================================================================================");
            
            do{
                System.out.println("Would you like to test the Vending Features or the Maintenance Features");
                System.out.println("[1] Vending machine features");
                System.out.println("[2] Maintenance Features");
                System.out.println("[3] Exit");
                input = readInt(scanner);
                System.out.println("=====================================================================================================");
                
                //Vending Fratures
                if(input == 1){
                    vendo.display();
                    transaction(vendo);
                    System.out.println("=====================================================================================================");
                }else if(input == 2){
                    //Maintenance Features
                    System.out.println("What Maintenance Features do you want to try");
                    System.out.println("[1] Stock/Restock an Item");
                    System.out.println("[2] Set an Item's Price");
                    System.out.println("[3] Collect Payment");
                    System.out.println("[4] Print Summary");
                    System.out.println("[5] Replenish Denomination");
                    System.out.println("[6] Exit");
                    int input2 = readInt(scanner);
                    System.out.println("=====================================================================================================");
                    
                    switch (input2) {
                        case 1:
                            Item createdItem = newItem(vendo);
                            System.out.println("How many of " + createdItem.getName() + " should be put in the machine");
                            int quantity = readInt(scanner);
                            maint.stockItem(vendo, createdItem, quantity);
                            break;
                        case 2:
                            System.out.println("What item's price should be changed");
                            for(ItemSlot slot : vendo.getItemSlots()){
                                if(slot.getNumItems() > 0){
                                    System.out.println("[" + slot.getSlot() + "] " + slot.getItem().getName());
                                }
                            }
                            int index = readInt(scanner) - 1;
                            if(index < 0 || index >= vendo.getItemSlots().length || vendo.getItemSlots()[index].getItem() == null) {
                                System.out.println("Invalid slot selection.");
                            }else{
                                 System.out.println("Enter new price for " + vendo.getItemSlots()[index].getItem().getName());
                                int price = readInt(scanner);
                                maint.setItemPrice(vendo.getItemSlots()[index].getItem(), price);
                            }
                            break;
                        case 3:
                            maint.collectPayment(vendo);
                            break;
                        case 4:
                            maint.printSummary(vendo);
                            break;
                        case 5:
                            System.out.println("Enter Denomination to be replenished");
                            int denom = readInt(scanner);
                            System.out.println("Enter quantity of denominations to be added");
                            int quant = readInt(scanner);
                            maint.replenishDenomination(vendo, denom, quant);
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                    System.out.println("=====================================================================================================");
                }
            }while(input == 1 || input == 2);
        }

        scanner.close();
    }

    private static void transaction(RegularVendo vendo){

        //--StartTransaction
        //------------------------------------------------------------------------------
        //--Enter Money
        System.out.println("Insert cash denominations (1, 5, 10, 20, 50, 100, 200, 500, 1000). Enter 0 to stop inserting:");
        
        boolean recieveMoney = true;
        while (recieveMoney) {
            int inputCash = readInt(scanner);
            
            if (inputCash == 0) {
                recieveMoney = false; 
            } else {
                boolean accepted = vendo.recievePayment(inputCash);
                if (!accepted) {
                    System.out.println("Invalid denomination rejected.");
                } else {
                    System.out.println("Current Inserted Balance: P" + vendo.getTotalCustomerCash());
                }
            }
        }

        //--------------------------------------------------------------------------------------
        //--Choose Item
        int itemWanted = -1;
        int targetIndex = -1;
        
        // LOOP: Keep asking until a valid, non-empty slot is selected
        while (targetIndex < 0 && itemWanted != 0) {
            System.out.println("Input Number of Slot with desired item: (Enter 0 to Cancel)");
            itemWanted = readInt(scanner);
            targetIndex = vendo.chooseItem(itemWanted);

            if (targetIndex < 0 && itemWanted != 0){
                System.out.println("Slot is either empty or does not exist. Please try again.");
            }
        }

       if(itemWanted != 0){
            // If loop breaks, it means targetIndex is valid!
            System.out.println("Selected: " + vendo.getItemSlots()[itemWanted - 1].getItem().getName() + ", Price: P" + vendo.getItemSlots()[itemWanted - 1].getItem().getPrice());
    
            System.out.println("Proceed with the transaction? (1 - Yes, 0 - No)");
            int proceed = readInt(scanner);
            //--------------------------------------------------------------------
            //--Dispense or Cancel or Return Change
            if (proceed == 1) {
                boolean transactionSuccess = vendo.dispenseItem(targetIndex);

                if (!transactionSuccess) {
                    vendo.cancelPurchase();
                }
            } else {
                System.out.println("Transaction cancelled by user.");
                vendo.cancelPurchase();
            }

        }else{
            vendo.cancelPurchase();
       }

    }

    private static Item newItem(RegularVendo vendo){
        Item retItem = null;
        System.out.println("Please Input Item Name: ");
        String name = scanner.next();

        for(ItemSlot slot : vendo.getItemSlots()){
            if(slot.getItem() != null && slot.getItem().getName().equalsIgnoreCase(name)){
                retItem = slot.getItem();
            }
        }

        if(retItem == null){
            System.out.println("Please Amount of Calories of Item: ");
            double calories = readDouble(scanner);
            System.out.println("Please Input Item Price: ");
            int price = readInt(scanner);

            retItem = new Item(name, calories, price);
        }

        return retItem;
    }

    private static int readInt(Scanner scanner) {
        int retval;
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number.");
            scanner.next();
        }
        retval = scanner.nextInt();
        return retval;
    }

    private static double readDouble(Scanner scanner) {
        double retval;
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a number.");
            scanner.next();
        }
    retval = scanner.nextDouble();
    return retval;
    }

}
