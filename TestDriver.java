public class TestDriver{
    public static void main(String[] args) {
        Item soda = new Item("Soda", 100, 67);
        ItemSlot itemSlot = new ItemSlot("A1", 10, soda, 5);

        System.out.println(itemSlot.getItem().getName());
        System.out.println(itemSlot.getInitialNum());
    }
}