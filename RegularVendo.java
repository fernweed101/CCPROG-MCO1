import java.util.ArrayList;
import java.util.Scanner;

public class RegularVendo{
    private ItemSlot[] slots;
    private int totalCash;
    private ArrayList<Integer> cash = new ArrayList<Integer>();
    private ArrayList<Integer> customerDenominations = new ArrayList<Integer>();

    public RegularVendo(int slots){
        this.totalCash = 0;
        this.slots = new ItemSlot[slots];
    }

    public ItemSlot[] getItemSlots(){
        return this.slots;
    }

    public int getTotalCash(){
        return this.totalCash;
    }

    public void setTotalCash(int total){
        this.totalCash = total;
    }

    public ArrayList<Integer> getDenomination(){
        return this.customerDenominations;
    }

    public int recievePayment(){
        Scanner scanner = new Scanner(System.in);
        int total = 0;
        int input = scanner.nextInt();
        boolean testVal = false;    //placeholder

        //Should keep recieving payment as long as input is valid
        while (testVal){
            total += input;
            customerDenominations.add(input);
            input = scanner.nextInt();
        }

        scanner.close();
        return total;   //Returns the total amount of money the user put in the machine
    }

    public ArrayList<Integer> produceChange(int totalChage){
        ArrayList<Integer> change = new ArrayList<Integer>();

        if(totalChage <= totalChage){
            int curTotal = 0;
            while (curTotal < totalChage) {
                //goes through denoms in cash then transfers that denom from cash to change
            }
        }else{
            System.out.println("Not enough change in machine for " + totalChage);
            //Stop transaction
        }

        return change;  //returns the change arraylist
    }

    public int chooseItem(int slot){
        int index = -1;

        for(int i = 0; i < slots.length; i++){
            if(slots[i].getSlot() == slot && slots[i].getNumItems() > 0){
                index = i;
            }
        }

        return index;  //returns the index if the slot entered is valid otherwise -1
    }

    public int dispenseItem(int index, int total){
        int change;

        if(total >= slots[index].getItem().getPrice()){
            System.out.println(slots[index].getItem().getName() + " has been dispensed");
            slots[index].dispense();
            change = total - slots[index].getItem().getPrice();
        }else{
            System.out.println("Insufficient funds");
            change = total;
        }

        return change;  //returns the change after the transaction
    }
}