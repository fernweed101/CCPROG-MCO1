public class Maintenance{
    private int totalCash;

    public void stockItem(RegularVendo vendo, Item item, int quantity){
        ItemSlot[] itemSlots = vendo.getItemSlots();
        boolean found = false;

        //Checks if item is already inside one of the slots
        for(ItemSlot slot : itemSlots){
            if(slot.getItem() != null && slot.getItem().equals(item)){
                found = true;
                for(int i = 0; i < quantity; i++){
                    slot.addItem(item);
                }
            }
        }

        //If not found check for empty spaces to put the item into
        if(!found){
            for(ItemSlot slot : itemSlots){
                if(slot.getItem() == null){
                    found = true;
                    for(int i = 0; i < quantity; i++){
                        slot.addItem(item);
                    }
                }
            }

            //If all spaces are full, dont put item inside
            if(!found){
                System.out.println("Vendo Slots are all full");
            }
        }
    }

    public void setItemPrice(Item item, int price){
        item.setPrice(price);
    }

    public void collectPayment(RegularVendo vendo){
        totalCash += vendo.getTotalCash();
        vendo.setTotalCash(0);
    }

    public void replenishDenomination(RegularVendo vendo, int value, int quantity){
        for(int i = 0; i < quantity; i++){
            vendo.setTotalCash(vendo.getTotalCash() + quantity);
            vendo.getDenomination().add(value);
        }
    }
}