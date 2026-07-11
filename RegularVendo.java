import java.util.ArrayList;
import java.util.Collections;

public class RegularVendo {
    private ItemSlot[] slots;
    private int totalCash;
    private int totalCustomerCash;
    private ArrayList<Integer> cash; 
    private ArrayList<Integer> customerDenominations; 

    public RegularVendo(int slots, int slotCapacity) {
        this.slots = new ItemSlot[slots];
        this.totalCash = 0;
        this.cash = new ArrayList<Integer>();
        this.customerDenominations = new ArrayList<Integer>();

        for (int i = 0; i < slots; i++) {
            this.slots[i] = new ItemSlot(i + 1, slotCapacity);
        }
    }

    public ItemSlot[] getItemSlots() { 
        return this.slots; 
    }
    public int getTotalCash() { 
        return this.totalCash; 
    }
    public int getTotalCustomerCash() { 
        return this.totalCustomerCash; 
    }
    public void setTotalCash(int total) { 
        this.totalCash = total; 
    }

    public boolean recievePayment(int value) {
        switch (value) {
            case 1: case 5: case 10: case 20: case 50: 
            case 100: case 200: case 500: case 1000:
                this.customerDenominations.add(value);
                this.totalCustomerCash += value;
                return true; // Returns immediately, no break needed   
            default:
                return false;
        }
    }

    /**
     * Attempts to build change using the machine's bank.
     * If exact change cannot be formed, it returns null.
     */
    public ArrayList<Integer> produceChange(int totalChange) {
        ArrayList<Integer> change = new ArrayList<Integer>();
        
        // Create a temporary clone of the vault so we don't accidentally 
        // permanently modify it if change generation fails halfway through
        ArrayList<Integer> tempVault = new ArrayList<>(this.cash);
        Collections.sort(tempVault, Collections.reverseOrder());

        int remainingChange = totalChange;
        
        for (int i = 0; i < tempVault.size(); i++) {
            int coinOrBill = tempVault.get(i);
            if (coinOrBill <= remainingChange) {
                change.add(coinOrBill);
                remainingChange -= coinOrBill;
                tempVault.remove(i);
                i--; // Adjust index due to removal
            }
        }

        // NO BREAKS: Directly check state and return
        if (remainingChange == 0) {
            this.cash = tempVault; // Commit vault changes since change calculation succeeded
            return change;
        }

        return null; // Could not make exact change
    }

    public int chooseItem(int slot) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getSlot() == slot && slots[i].getItem() != null && slots[i].getNumItems() > 0) {
                return i; // Returns immediately once found
            }
        }
        return -1; 
    }

    /**
     * Handles the dispensing logic safely. 
     * Returns true if successful, false if it fails (triggering a refund).
     */
    public boolean dispenseItem(int index) {
        // Validation Guard Rails (Handles invalid index or empty slots safely)
        if (index < 0 || index >= slots.length || slots[index].getItem() == null) {
            System.out.println("Invalid slot or item unavailable.");
            return false;
        }

        Item item = slots[index].getItem();

        // Check for insufficient funds
        if (this.totalCustomerCash < item.getPrice()) {
            System.out.println("Insufficient funds for " + item.getName());
            return false;
        }

        int changeNeeded = this.totalCustomerCash - item.getPrice();
        ArrayList<Integer> calculatedChange = produceChange(changeNeeded);

        // Check if machine lacks proper change denominations
        if (calculatedChange == null && changeNeeded > 0) {
            System.out.println("Machine cannot provide exact change for this transaction.");
            return false;
        }

        // Everything passed! Finalize the sale
        System.out.println(item.getName() + " has been dispensed!");
        slots[index].dispense();

        // Move the customer's cash permanently into the machine's bank
        this.cash.addAll(this.customerDenominations);
        
        // Print change given to user (if any)
        if (changeNeeded > 0) {
            System.out.println("Dispensing change: " + calculatedChange);
        }

        // Reset customer state for next user
        this.customerDenominations.clear();
        this.totalCustomerCash = 0;
        return true;
    }

    /**
     * Completely refunds the exact denominations the user inserted
     */
    public ArrayList<Integer> cancelPurchase() {
        ArrayList<Integer> refund = new ArrayList<>(this.customerDenominations);
        this.customerDenominations.clear();
        this.totalCustomerCash = 0;
        
        System.out.println("Transaction failed/cancelled. Returning your original denominations: " + refund);
        return refund;
    }

    public void addDenomination(int value) {
        this.cash.add(value);
    }

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
            for (int i = 0; i < quantity; i++) {
                slots[index].addItem(item);
            }
            System.out.println(item.getName() + " has been restocked");
        } else {
            System.out.println("There are currently no empty slots in the vending machine");
        }
    }
}