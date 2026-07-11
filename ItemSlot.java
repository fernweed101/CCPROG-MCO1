import java.util.ArrayList;

public class ItemSlot {
    private int slotId;
    private Item item;
    private ArrayList<Item> items;
    private int initalNum;
    private int slotCapacity;
    private boolean initial;

    public ItemSlot(int slotId, int slotCapacity) {
        initial = true;
        this.item = null;
        this.slotId = slotId;
        this.initalNum = 0;
        this.items = new ArrayList<Item>();
        if (slotCapacity < 10) {
            this.slotCapacity = 10;
        }else{
            this.slotCapacity = slotCapacity;
        }
    }

    public int getSlot() {
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

    public void setItem(Item item){
        this.item = item;
    }

    public boolean addItem(Item newItem, int quantity) {
        if(initial){
            this.item = newItem;
            this.initalNum = quantity;
            initial = false;
        }
        if (this.items.size() + quantity < this.slotCapacity) {
            for(int i = 0; i < quantity; i++){
                this.items.add(newItem);
            }
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