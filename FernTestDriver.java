import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       System.out.println("=== INITIALIZING VENDING MACHINE ===");
        // 1. Create a vending machine with 8 slots (for our 2x4 grid)
        RegularVendo vendingMachine = new RegularVendo(10);

        // 2. Create physical item templates
        Item soda = new Item("Coke Soda", 50, 140.0);
        Item chips = new Item("Potato Chips", 35, 220.0);
        Item candy = new Item("Chocolate Bar", 40, 250.0);

        // 3. Create physical slots with stocks
        // Slot coordinates map directly to indices 0, 1, and 2
        ItemSlot slotA1 = new ItemSlot("A1", 10, soda, 5);
        ItemSlot slotA2 = new ItemSlot("A2", 10, chips, 3);
        ItemSlot slotB1 = new ItemSlot("B1", 10, candy, 2);

        // 4. Load them into corresponding indices
        vendingMachine.addSlot(0, slotA1); // Maps to A1 in display
        vendingMachine.addSlot(1, slotA2); // Maps to A2 in display
        vendingMachine.addSlot(2, slotB1); // Maps to B1 in display

        System.out.println("Vending machine setup complete with 3 items!\n");

        // --- MAIN INTERACTIVE TEST LOOP ---
        boolean running = true;
        while (running) {
            System.out.println("\n===== MAIN TEST MENU =====");
            System.out.println("1. View Machine Display (See items, stock, and calories)");
            System.out.println("2. Purchase an Item (Simulate User Flow)");
            System.out.println("3. Exit Test Driver");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the scanner buffer

            switch (choice) {
                case 1:
                    // Tests your new displayItems() method and calorie tracking
                    vendingMachine.displayItems();
                    break;

                case 2:
                    // Step A: Display available options first
                    vendingMachine.displayItems();

                    // Step B: Collect payment from the user
                    System.out.println("--- Step 1: Insert Payment ---");
                    double paymentReceived = vendingMachine.recievePayment();
                    System.out.println("Successfully accepted a total of Php " + paymentReceived);

                    if (paymentReceived > 0) {
                        // Step C: Choose item slot
                        System.out.println("\n--- Step 2: Select a Slot ---");
                        System.out.print("Enter Slot ID (e.g., A1, B1, C1): ");
                        String selectedSlot = scanner.nextLine();

                        // Look up the slot index
                        int slotIndex = vendingMachine.chooseItem(selectedSlot);

                        // Step D: Attempt to dispense
                        System.out.println("\n--- Step 3: Dispensing ---");
                        double remainingChange = vendingMachine.dispenseItem(slotIndex, paymentReceived);
                        
                        System.out.printf("Returned Change to User: Php %.2f\n", remainingChange);
                    } else {
                        System.out.println("Transaction canceled. No money inserted.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Test Driver. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}