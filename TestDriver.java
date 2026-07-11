import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        Maintenance maint = new Maintenance();
        Scanner scanner = new Scanner(System.in);
        
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
        for (int i = 0; i < 200; i++) {
            vendo.addDenomination(1);
        }

        System.out.println("Input Number of Slot with desired item:");
        int itemWanted = scanner.nextInt();

        // Safety check to ensure user doesn't input an out-of-bounds slot number
        if (itemWanted < 1 || itemWanted > slots) {
            System.out.println("Invalid slot selection.");
            scanner.close();
            return;
        }

        System.out.println("Selected: " + vendo.getItemSlots()[itemWanted - 1].getItem().getName() 
                           + ", Price: P" + vendo.getItemSlots()[itemWanted - 1].getItem().getPrice());
        
        System.out.println("Insert cash denominations (1, 5, 10, 20, 50, 100, 200, 500, 1000). Enter 0 to stop inserting:");
        
        boolean status = true;
        while (status) {
            int inputCash = scanner.nextInt();
            
            // FIXED: Directly stop processing if the user inputs 0 to avoid printing an invalid error
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
        
        // Passing itemWanted to chooseItem to get the verified internal array index
        int targetIndex = vendo.chooseItem(itemWanted);
        
        if (targetIndex >= 0) {
            // FIXED: dispenseItem returns a boolean success status now
            boolean transactionSuccess = vendo.dispenseItem(targetIndex);

            // FIXED: If the transaction fails, trigger the original denomination refund mechanism
            if (!transactionSuccess) {
                vendo.cancelPurchase();
            }
        } else {
            System.out.println("Item unavailable or slot empty.");
            vendo.cancelPurchase();
        }
       
        scanner.close();
    }
}