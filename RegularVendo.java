import java.util.ArrayList;
import java.util.Scanner;

public class RegularVendo{
    private ItemSlot[] slots;
    private ArrayList<Denomination> cash;
    private ArrayList<Double> customerDenominations;

    public RegularVendo(int slots){
        this.slots = new ItemSlot[slots];
    }

    public double recievePayment(){
        Scanner scanner = new Scanner(System.in);
        double total = 0.0;
        double input = scanner.nextDouble();
        boolean testVal = false;    //placeholder

        //Should keep recieving payment as long as input is valid
        while (testVal){
            total += input;
            customerDenominations.add(input);
            input = scanner.nextDouble();
        }

        scanner.close();
        return total;   //Returns the total amount of money the user put in the machine
    }

    public int chooseItem(String slot){
        int index = -1;

        for(int i = 0; i < slots.length; i++){
            if(slots[i].getSlot().equalsIgnoreCase(slot) && slots[i].getNumItems() > 0){
                index = i;
            }
        }

        return index;  //returns the index where the slot with wanted item was found or if not found -1
    }

    public double dispenseItem(int index, double total){
        double change;


        if(total >= slots[index].getItem().getPrice()) {
            

            Item dispensedItem = slots[index].dispense(); 
            
            if (dispensedItem != null) {
                System.out.println(dispensedItem.getName() + " has been dispensed.");
                System.out.println("Calories: " + dispensedItem.getCalories()); // Added calorie display requirement
                
                change = total - dispensedItem.getPrice();
            } else {
                System.out.println("Sorry, this item is out of stock.");
                change = total;
            }
            
        } else {
            System.out.println("Insufficient funds.");
            change = total;
        }

        return change;  //returns the change after the transaction
    }

    public ArrayList<Denomination> getCurrentDenominations(){
        return this.cash;
    }
}