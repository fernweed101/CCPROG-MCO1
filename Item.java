public class Item {
    

    private final String name;
    

    private final int calories;
    

    private double price;


    public Item(String name, int calories, double price) {
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

  
    public String getName() {
        return this.name;
    }

   
    public int getCalories() {
        return this.calories;
    }

  
    public double getPrice() {
        return this.price;
    }

   
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }
}