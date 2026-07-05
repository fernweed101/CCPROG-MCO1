public class TestDriver{
    public static void main(String[] args) {
        Item soda = new Item("Soda", 100, 67);
        ItemSlot itemSlot = new ItemSlot("A1", 10, soda, 5);
        Denomination[] moneys = new Denomination[2];

        for(int i = 0; i < 2; i++){
            moneys[i] = new Denomination((i+1) * 5, 10);
        }

        System.out.println("Item name: " + itemSlot.getItem().getName());
        System.out.println("Initial num of items: " + itemSlot.getInitialNum());
        System.out.println("Amount of P5 coins: " + moneys[0].getValue());


    }
}