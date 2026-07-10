import java.util.ArrayList;
import java.util.Scanner;

public class FernRegularVendo {
    private ItemSlot[] slots;
    private ArrayList<Denomination> cash;
    private ArrayList<Double> customerDenominations;

    // Fixed constructor parameter name to avoid variable shadowing confusion
    public FernRegularVendo(int totalSlots) {
        this.slots = new ItemSlot[totalSlots];
        this.cash = new ArrayList<>();
        this.customerDenominations = new ArrayList<>();
    }

    /**
     * Assigns an initialized ItemSlot to a specific index in the machine's slots array.
     */
    public boolean addSlot(int index, ItemSlot slot) {
        if (index >= 0 && index < slots.length) {
            slots[index] = slot;
            return true;
        }
        return false;
    }

    /**
     * Displays all available items in the vending machine, 
     * including their price, calories, and remaining stock.
     */
    /**
     * Displays available items in a beautiful side-by-side grid layout,
     * simulating a physical vending machine window view.
     */
    /**
     * Displays the vending machine in a locked 2-column x 4-row (8 slots) grid.
     * The physical grid frame stays completely intact even if slots are empty or uninitialized.
     */
    /**
     * Displays the vending machine in a dynamic 2-column layout.
     * The number of rows scales automatically based on the machine's actual capacity.
     */
   /**
     * Displays the vending machine in a dynamic, space-saving 4-column layout.
     * The number of rows scales automatically based on the machine's actual capacity.
     */
    /**
     * Displays the vending machine in a dynamic, space-saving 4-column layout.
     * The number of rows scales automatically based on the machine's actual capacity.
     */
    /**
     * Displays the vending machine in a single, solid ASCII grid frame with shared borders.
     */
    public void displayItems() {
        System.out.println("\n=====================================================================================================");
        System.out.println("                                    VENDING MACHINE GLASS WINDOW                                     ");
        System.out.println("=====================================================================================================");

        int columns = 4;
        int totalRows = (int) Math.ceil((double) slots.length / columns);

        // Standardized inner width for each cell (23 characters)
        String horizontalDivider = "+-----------------------+-----------------------+-----------------------+-----------------------+";

        for (int row = 0; row < totalRows; row++) {
            // Print the top border of the shelf row
            System.out.println(horizontalDivider);

            StringBuilder nameLine   = new StringBuilder("|");
            StringBuilder priceLine  = new StringBuilder("|");
            StringBuilder calLine    = new StringBuilder("|");
            StringBuilder stockLine  = new StringBuilder("|");

            for (int col = 0; col < columns; col++) {
                int index = (row * columns) + col;

                // Dynamically generate coordinates (A1, A2, A3, A4...)
                char rowChar = (char) ('A' + row);
                int colNum = col + 1;
                String slotId = "" + rowChar + colNum;

                if (index < slots.length) {
                    ItemSlot slot = slots[index];
                    
                    if (slot != null) {
                        if (slot.getNumItems() > 0) {
                            Item item = slot.getItem();
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(item.getName(), 16)));
                            priceLine.append(String.format(" Price: Php %-10.2f |", item.getPrice()));
                            calLine.append(String.format(" Calories: %-11.0f |", item.getCalories()));
                            stockLine.append(String.format(" Stock: %-14d |", slot.getNumItems()));
                        } else {
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(slot.getItem().getName(), 16)));
                            priceLine.append("                       |");
                            calLine.append("  --- OUT OF STOCK --- |");
                            stockLine.append(" Stock: 0              |");
                        }
                    } else {
                        nameLine.append(String.format(" [%-2s] %-16s |", slotId, "EMPTY SLOT"));
                        priceLine.append("                       |");
                        calLine.append("     --- VACANT ---    |");
                        stockLine.append(" Stock: 0              |");
                    }
                } else {
                    // Blank panel for any odd slots exceeding array length
                    nameLine.append("                       |");
                    priceLine.append("                       |");
                    calLine.append("                       |");
                    stockLine.append("                       |");
                }
            }

            // Print the constructed row details
            System.out.println(nameLine);
            System.out.println(priceLine);
            System.out.println(calLine);
            System.out.println(stockLine);
        }
        // Print the final bottom border of the machine frame
        System.out.println(horizontalDivider);
        System.out.println("=====================================================================================================");
    }

    /**
     * Helper method to ensure item names don't spill over and break the ASCII box layout.
     */
    private String truncate(String text, int length) {
        if (text != null && text.length() > length) {
            return text.substring(0, length - 2) + "..";
        }
        return text;
    }

    public double recievePayment() {
        Scanner scanner = new Scanner(System.in);
        double total = 0.0;
        System.out.print("Insert denomination (0 to stop): ");
        double input = scanner.nextDouble();
        
        // Temporarily active logic for testing input collection
        while (input > 0) {
            total += input;
            customerDenominations.add(input);
            System.out.print("Current Total: Php " + total + ". Insert more or 0 to stop: ");
            input = scanner.nextDouble();
        }
        scanner.close();
        return total;   // Returns the total amount of money the user put in the machine
    }

    public int chooseItem(String slotId) {
        int index = -1;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].getSlot().equalsIgnoreCase(slotId) && slots[i].getNumItems() > 0) {
                index = i;
            }
        }

        return index;  // returns the index where the slot was found, or -1 if not found/empty
    }

    public double dispenseItem(int index, double total) {
        double change;

        if (index >= 0 && index < slots.length && slots[index] != null) {
            if (total >= slots[index].getItem().getPrice()) {
                
                // Physically remove the item from the item slot's internal ArrayList
                Item dispensedItem = slots[index].dispense(); 
                
                if (dispensedItem != null) {
                    System.out.println("\n--- SUCCESS ---");
                    System.out.println(dispensedItem.getName() + " has been dispensed.");
                    System.out.println("Calories: " + dispensedItem.getCalories()); 
                    
                    change = total - dispensedItem.getPrice();
                } else {
                    System.out.println("Sorry, this item went out of stock.");
                    change = total;
                }
                
            } else {
                System.out.println("Insufficient funds.");
                change = total;
            }
        } else {
            System.out.println("Invalid slot selection.");
            change = total;
        }

        return change;  // returns the change after the transaction
    }

    public ArrayList<Denomination> getCurrentDenominations() {
        return this.cash;
    }
}