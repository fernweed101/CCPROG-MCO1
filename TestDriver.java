import java.util.Scanner;

public class TestDriver {
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        Maintenance maint = new Maintenance();
        RegularVendo vendo = setUpVendo(maint);
        
        // Display the machine before starting the transaction
        vendo.display(); 
        
        transaction(vendo);
    }

    public static RegularVendo setUpVendo(Maintenance maint){
        
        //Creating vendo -----------------------------------------------------------------------
        System.out.println("Input Number of Slots for Vending Machine: (At least 8)");
        int slots = scanner.nextInt();
        System.out.println("Input Item Capacity for vendo slot: (At least 10)");
        int slotCap = scanner.nextInt();
        RegularVendo vendo = new RegularVendo(slots, slotCap);

        //Creating items-----------------------------------------------------------------
        Item soda = new Item("Soda", 150, 67);
        Item chips = new Item("Chips", 300, 80);
        Item terraria = new Item("Terraria", 50, 167); 

        maint.stockItem(vendo, soda, 5);
        maint.stockItem(vendo, chips, 6);
        maint.stockItem(vendo, terraria, 7);
        
        // Simulating loading the machine's bank vault with a bunch of 1-peso coins for change
        maint.replenishDenomination(vendo, 1, 200);
        return vendo;
    }

    public static void transaction(RegularVendo vendo){

        //--StartTransaction
        //------------------------------------------------------------------------------
        //--Enter Money
         System.out.println("Insert cash denominations (1, 5, 10, 20, 50, 100, 200, 500, 1000). Enter 0 to stop inserting:");
        
        boolean recieveMoney = true;
        while (recieveMoney) {
            int inputCash = scanner.nextInt();
            
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
        while (targetIndex < 0) {
            System.out.println("Input Number of Slot with desired item:");
            itemWanted = scanner.nextInt();
            targetIndex = vendo.chooseItem(itemWanted);

            if (targetIndex < 0) {
                System.out.println("Slot is either empty or does not exist. Please try again.");
            }
        }

        // If loop breaks, it means targetIndex is valid!
        System.out.println("Selected: " + vendo.getItemSlot(itemWanted - 1).getItem().getName() + ", Price: P" + vendo.getItemSlot(itemWanted - 1).getItem().getPrice());
    
        System.out.println("Proceed with the transaction? (1 - Yes, 0 - No)");
        int proceed = scanner.nextInt();
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
    }

    public static void test2() {
        //--SetupVendo
        Maintenance maint = new Maintenance();
        
        System.out.println("Input Number of Slots and Slot Capacity in Vending Machine: (e.g., 3 10)");
        int slots = scanner.nextInt();
        int slotCap = scanner.nextInt();
        RegularVendo vendo = new RegularVendo(slots, slotCap);

        Item soda = new Item("Soda", 150, 67);
        Item chips = new Item("Chips", 300, 80);
        Item terraria = new Item("Terraria", 50, 167); 

        maint.stockItem(vendo, soda, 5);
        maint.stockItem(vendo, chips, 6);
        maint.stockItem(vendo, terraria, 7);
        
        // Simulating loading the machine's bank vault with a bunch of 1-peso coins for change
        maint.replenishDenomination(vendo, 1, 200);

        vendo.display();

        //--StartTransaction
        System.out.println("Insert cash denominations (1, 5, 10, 20, 50, 100, 200, 500, 1000). Enter 0 to stop inserting:");
        
        boolean status = true;
        while (status) {
            int inputCash = scanner.nextInt();
            
            if (inputCash == 0) {
                status = false; 
            } else {
                boolean accepted = vendo.recievePayment(inputCash);
                if (!accepted) {
                    System.out.println("Invalid denomination rejected.");
                } else {
                    System.out.println("Current Inserted Balance: P" + vendo.getTotalCustomerCash());
                }
            }
        }

        int itemWanted = -1;
        int targetIndex = -1;
        
        // LOOP: Keep asking until a valid, non-empty slot is selected
        while (targetIndex < 0) {
            System.out.println("Input Number of Slot with desired item:");
            itemWanted = scanner.nextInt();
            
            // Check bounds just in case they input something crazy like slot -50
            if (itemWanted < 1 || itemWanted > slots) {
                System.out.println("Invalid slot selection. Please try again.");
                continue;
            }

            targetIndex = vendo.chooseItem(itemWanted);

            if (targetIndex < 0) {
                System.out.println("Slot is either empty or does not exist. Please try again.");
            }
        }

        System.out.println("Selected: " + vendo.getItemSlot(itemWanted - 1).getItem().getName() 
                           + ", Price: P" + vendo.getItemSlot(itemWanted - 1).getItem().getPrice());
        
        boolean transactionSuccess = vendo.dispenseItem(targetIndex);

        if (!transactionSuccess) {
            vendo.cancelPurchase();
        }
    }
}