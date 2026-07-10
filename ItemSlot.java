import java.util.ArrayList;

public class ItemSlot {
    private String slotId;
    private Item item; 
    private ArrayList<Item> items = new ArrayList<Item>();
    private int initalNum;
    private int slotCapacity;

    public ItemSlot(String slotId, int slotCapacity) {
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.items = new ArrayList<>();
    }

    public ItemSlot(String slotId, int slotCapacity, Item item) {
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.item = item;
        this.items = new ArrayList<>();
    }

    public ItemSlot(String slotId, int slotCapacity, Item item, int initalNum) {
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.item = item;
        this.initalNum = initalNum;
        this.items = new ArrayList<>();
        
      
        for (int i = 0; i < initalNum; i++) {
            Item newItem = new Item(item.getName(), item.getCalories(), item.getPrice());
            this.items.add(newItem);
        }
    }

    public String getSlot() {
        return this.slotId;
    }

    public Item getItem() {
        return this.item;
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