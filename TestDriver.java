import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) {
        Item item = new Item("Soda", 100, 67);
        RegularVendo vendo = new RegularVendo(2);
        Maintenance mainten = new Maintenance();

        vendo.getItemSlots()[0] = new ItemSlot(vendo.getItemSlots().length - 1, 10);
        vendo.getItemSlots()[1] = new ItemSlot(vendo.getItemSlots().length - 2, 10);
        mainten.stockItem(vendo, item, 2);
        System.out.println(vendo.getItemSlots()[0].getItem().getName() + " " + vendo.getItemSlots()[0].getNumItems() + " P" + vendo.getItemSlots()[0].getItem().getPrice());
        mainten.setItemPrice(item, 10);
        mainten.stockItem(vendo, item, 5);
        System.out.println(vendo.getItemSlots()[0].getItem().getName() + " " + vendo.getItemSlots()[0].getNumItems() + " P" + vendo.getItemSlots()[0].getItem().getPrice());
    }
}