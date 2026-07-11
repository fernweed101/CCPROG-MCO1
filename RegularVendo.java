import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RegularVendo {
    private ItemSlot[] slots;
    private int totalCash;
    private int totalCustomerCash;
    private ArrayList<Integer> cash; 
    private ArrayList<Integer> customerDenominations; 

    //Constructor for RegularVendo
    public RegularVendo(int slots, int slotCapacity) {
        this.totalCash = 0;
        this.cash = new ArrayList<>();
        this.customerDenominations = new ArrayList<>();

        //Creates the slots of the vending machine
        if(slots < 8){
            this.slots = new ItemSlot[8];
            for (int i = 0; i < 8; i++) {
                this.slots[i] = new ItemSlot(i + 1, slotCapacity);
            }
        }else{
            this.slots = new ItemSlot[slots];
            for (int i = 0; i < slots; i++) {
            this.slots[i] = new ItemSlot(i + 1, slotCapacity);
            }
        }

        System.out.println("Vendo Created with " + this.slots.length + " slots and " + this.slots[0].getSlotCapacity() + " slot capacity");
    }

    public ItemSlot[] getItemSlots() { 
        return Arrays.copyOf(slots, slots.length);
    }
    public int getTotalCash() { 
        return this.totalCash; 
    }
    public int getTotalCustomerCash() { 
        return this.totalCustomerCash; 
    }
    public void setTotalCash(int total) { 
        if(total >= 0){
            this.totalCash = total;
        } 
    }

    //Returns true if payment value is valid
    public boolean recievePayment(int value) {
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
                this.customerDenominations.add(value);
                this.totalCustomerCash += value;
                return true;  
            default:
                return false;
        }
    }

    //Returns an arraylist of denominations for change
    public ArrayList<Integer> produceChange(int totalChange) {
        ArrayList<Integer> change = new ArrayList<>();
        
        ArrayList<Integer> tempCash = new ArrayList<>(this.cash);
        Collections.sort(tempCash, Collections.reverseOrder()); //Sorts the tempCash

        int remainingChange = totalChange;
        
        for (int i = 0; i < tempCash.size(); i++) {
            int denomination = tempCash.get(i);
            if (denomination <= remainingChange) {
                change.add(denomination);
                remainingChange -= denomination;
                tempCash.remove(i);
                i--; // Adjust index due to removal
            }
        }

        
        if (remainingChange == 0) {
            this.cash = tempCash; // Commit vault changes since change calculation succeeded
            return change;
        }else{
            change = null;
        }

        return change; // Could not make exact change
    }

    //returns the index of the slot with wanted item
    public int chooseItem(int slotIndex) {
        int retVal = -1;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getSlot() == slotIndex && slots[i].getItem() != null && slots[i].getNumItems() > 0) {
                retVal = i;
            }
        }

        //System.out.println("RETVAL FOR CHOOSE ITEM IS: " + retVal);
        return retVal; //Returns index of itemSlot and checks if its empty, -1 if empty or not found
    }

    
    //Returns true if dispense succesful
    public boolean dispenseItem(int index) {
        Item item = slots[index].getItem();
        boolean status = false;

        // Check for insufficient funds
        if (this.totalCustomerCash < item.getPrice()) {
            System.out.println("Insufficient funds for " + item.getName());
        }else{
            int changeNeeded = this.totalCustomerCash - item.getPrice();
            ArrayList<Integer> calculatedChange = produceChange(changeNeeded);

            //Checks if vendo has enough chagne for transaction
            if (calculatedChange == null && changeNeeded > 0) {
                System.out.println("Machine cannot provide exact change for this transaction.");
            }else{
                System.out.println(item.getName() + " has been dispensed!");
                slots[index].dispense();

                // Move the customer's cash permanently into the machine's bank
                this.cash.addAll(this.customerDenominations);

                
                if (changeNeeded > 0) {
                    System.out.println("Dispensing change: " + calculatedChange);
                    this.customerDenominations.clear();
                    this.totalCustomerCash = 0;
                }

                status = true;
            }
        }
        return status;
    }


    //Cancels purchase and returns all of customer's cash
    public ArrayList<Integer> cancelPurchase() {
        ArrayList<Integer> refund = new ArrayList<>(this.customerDenominations);
        System.out.println("Transaction failed/cancelled. Returning your original denominations: " + refund);
        this.customerDenominations.clear();
        this.totalCustomerCash = 0;

        return refund;
    }

    //For replenishing denomination
    public void addDenomination(int value) {
        this.cash.add(value);
    }

    //For stocking of items
    public void addItemStock(Item item, int quantity) {
        int index = -1;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getItem() != null && item.equals(slots[i].getItem())) {
                index = i;
            }
        }

        if (index == -1) {
            for (int i = slots.length - 1; i >= 0; i--) {
                if (slots[i].getItem() == null) {
                    index = i;
                }
            }
        }

        if (index != -1) {
            slots[index].addItem(item, quantity);
            System.out.println(item.getName() + " has been restocked");
        } else {
            System.out.println("There are currently no empty slots in the vending machine");
        }
    }

    //For Display
    public void display() {
        System.out.println("\n=====================================================================================================");
        System.out.println("                                    VENDING MACHINE GLASS WINDOW                                     ");
        System.out.println("=====================================================================================================");

        int columns = 4;
        int totalRows = (int) Math.ceil((double) slots.length / columns);

        // Standardized inner width for each cell (23 characters per slot block)
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

                if (index < slots.length) {
                    ItemSlot slot = slots[index];
                    // Using String.valueOf because your getSlot() currently returns an int
                    String slotId = String.valueOf(slot.getSlot()); 
                    
                    // Check if slot has an assigned item template
                    if (slot.getItem() != null) {
                        Item item = slot.getItem();
                        
                        if (slot.getNumItems() > 0) {
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(item.getName(), 16)));
                            priceLine.append(String.format(" Price: Php %-10d |", item.getPrice())); // Price is int
                            calLine.append(String.format(" Calories: %-11.0f |", item.getCalories()));
                            stockLine.append(String.format(" Stock: %-14d |", slot.getNumItems()));
                        } else {
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(item.getName(), 16)));
                            priceLine.append("                       |");
                            calLine.append("  --- OUT OF STOCK --- |");
                            stockLine.append(" Stock: 0              |");
                        }
                    } else {
                        // Slot exists but no item has been assigned to it yet by Maintenance
                        nameLine.append(String.format(" [%-2s] %-16s |", slotId, "EMPTY SLOT"));
                        priceLine.append("                       |");
                        calLine.append("     --- VACANT ---    |");
                        stockLine.append(" Stock: 0              |");
                    }
                } else {
                    // Blank panel for any odd slots exceeding array length to maintain grid shape
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
     * Helper method to ensure long item names don't break the ASCII layout spacing.
     */
    private String truncate(String text, int length) {
        if (text != null && text.length() > length) {
            return text.substring(0, length - 2) + "..";
        }
        return text;
    }
}


