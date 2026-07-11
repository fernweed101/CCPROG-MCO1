import java.util.ArrayList;
import java.util.Collections;

public class RegularVendo {
    private ItemSlot[] slots;
    private int totalCash;
    private int totalCustomerCash;
    private ArrayList<Integer> cash; 
    private ArrayList<Integer> customerDenominations; 

    public RegularVendo(int slots, int slotCapacity) {
        this.totalCash = 0;
        this.cash = new ArrayList<Integer>();
        this.customerDenominations = new ArrayList<Integer>();

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

    //
    public ArrayList<Integer> produceChange(int totalChange) {
        ArrayList<Integer> change = new ArrayList<Integer>();
        
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

        // NO BREAKS: Directly check state and return
        if (remainingChange == 0) {
            this.cash = tempCash; // Commit vault changes since change calculation succeeded
            return change;
        }else{
            change = null;
        }

        return change; // Could not make exact change
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