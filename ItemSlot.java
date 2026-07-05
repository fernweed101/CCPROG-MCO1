public class ItemSlot{
    private String slotId;
    private Item item;
    private int numItems;
    private int initalNum;
    private int slotCapacity;

    public ItemSlot(String slotId, int slotCapacity){
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
    }

    public ItemSlot(String slotId, int slotCapacity, Item item){
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.item = item;
    }

    public ItemSlot(String slotId, int slotCapacity, Item item, int initalNum){
        this.slotId = slotId;
        this.slotCapacity = slotCapacity;
        this.item = item;
        this.initalNum = initalNum;
    }

    public String getSlot(){
        return this.slotId;
    }

    public Item getItem(){
        return this.item;
    }

    public int getNumItems(){
        return this.numItems;
    }

    public int getInitialNum(){
        return this.initalNum;
    }

    public int getSlotCapcity(){
        return this.slotCapacity;
    }

    public void setNumItems(){

    }
}