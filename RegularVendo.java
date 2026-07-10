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

    public int chooseItem(String slot){
        int index = -1;

        for(int i = 0; i < slots.length; i++){
            if(slots[i].getSlot().equalsIgnoreCase(slot) && slots[i].getNumItems() > 0){
                index = i;
            }
        }

        return index;  //returns the index if the slot entered is valid otherwise -1
    }

    public int dispenseItem(int index, int total){
        int change;

        if(total >= slots[index].getItem().getPrice()){
            System.out.println(slots[index].getItem().getName() + " has been dispensed");
            slots[index].setNumItems(slots[index].getNumItems() - 1);
            change = total - slots[index].getItem().getPrice();
        }else{
            System.out.println("Insufficient funds");
            change = total;
        }

        return change;  //returns the change after the transaction
    }
}

public void displayItems() {
        System.out.println("\n=====================================================================================================");
        System.out.println("                                    VENDING MACHINE GLASS WINDOW                                     ");
        System.out.println("=====================================================================================================");

        int columns = 4;
        int totalRows = (int) Math.ceil((double) slots.length / columns);

        // Standardized inner width for each cell (23 characters)
        String horizontalDivider = "+-----------------------+-----------------------+-----------------------+-----------------------+";

        for (int row = 0; row < totalRows; row++) {
            // Print the top border of the shelf row
            System.out.println(horizontalDivider);

            StringBuilder nameLine   = new StringBuilder("|");
            StringBuilder priceLine  = new StringBuilder("|");
            StringBuilder calLine    = new StringBuilder("|");
            StringBuilder stockLine  = new StringBuilder("|");

            for (int col = 0; col < columns; col++) {
                int index = (row * columns) + col;

                // Dynamically generate coordinates (A1, A2, A3, A4...)
                char rowChar = (char) ('A' + row);
                int colNum = col + 1;
                String slotId = "" + rowChar + colNum;

                if (index < slots.length) {
                    ItemSlot slot = slots[index];
                    
                    if (slot != null) {
                        if (slot.getNumItems() > 0) {
                            Item item = slot.getItem();
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(item.getName(), 16)));
                            priceLine.append(String.format(" Price: Php %-10.2f |", item.getPrice()));
                            calLine.append(String.format(" Calories: %-11.0f |", item.getCalories()));
                            stockLine.append(String.format(" Stock: %-14d |", slot.getNumItems()));
                        } else {
                            nameLine.append(String.format(" [%-2s] %-16s |", slotId, truncate(slot.getItem().getName(), 16)));
                            priceLine.append("                       |");
                            calLine.append("  --- OUT OF STOCK --- |");
                            stockLine.append(" Stock: 0              |");
                        }
                    } else {
                        nameLine.append(String.format(" [%-2s] %-16s |", slotId, "EMPTY SLOT"));
                        priceLine.append("                       |");
                        calLine.append("     --- VACANT ---    |");
                        stockLine.append(" Stock: 0              |");
                    }
                } else {
                    // Blank panel for any odd slots exceeding array length
                    nameLine.append("                       |");
                    priceLine.append("                       |");
                    calLine.append("                       |");
                    stockLine.append("                       |");
                }
            }

            // Print the constructed row details
            System.out.println(nameLine);
            System.out.println(priceLine);
            System.out.println(calLine);
            System.out.println(stockLine);
        }
        // Print the final bottom border of the machine frame
        System.out.println(horizontalDivider);
        System.out.println("=====================================================================================================");
    }