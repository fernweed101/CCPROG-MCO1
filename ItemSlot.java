import java.util.ArrayList;

public class ItemSlot {
    private int slotId;
    private ArrayList<Item> items;
    private int initalNum;
    private int slotCapacity;

    public ItemSlot(int slotId, int slotCapacity) {
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.items = new ArrayList<Item>();
    }

    public int getSlot() {
        return this.slotId;
    }

    public Item getItem() {
        Item item = null;
         if (!items.isEmpty()) {
            item = items.get(0);
        }
        return item;
    }


    public int getNumItems() {
        return this.items.size();
    }

    public int getInitialNum() {
        return this.initalNum;
    }

    public int getSlotCapacity() { 
        return this.slotCapacity;
    }


    public boolean addItem(Item newItem) {
        if (this.items.size() < this.slotCapacity) {
            this.items.add(newItem);
            return true;
        }
        return false;
    }

    public Item dispense() {
        if (!this.items.isEmpty()) {
            return this.items.remove(0);
        }
        return null;
    }
}