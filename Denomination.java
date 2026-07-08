public class Denomination{
    private double value;
    private int quantity;

    public Denomination(int value){
        this.value = value;
    }

    public Denomination(int value, int quantity){
        this.value = value;
        this.quantity = quantity;
    }

    public double getValue(){
        return this.value;
    }

    public int getQuantity(){
        return this.quantity;
    }
}