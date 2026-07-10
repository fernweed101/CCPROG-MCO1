public class Item {
    private String name;
    private int price;
    private double calories;

    
    public Item(String name, double calories, int price) {
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    public Item(String name, double calories){
        this.name = name;
        this.calories = calories;
    }


  
    public String getName() {
        return this.name;
    }

   
    public double getCalories() {
        return this.calories;
    }

  
    public int getPrice() {
        return this.price;
    }

   
    public void setPrice(int price) {
        if (price >= 0) {
            this.price = price;
        }
    }
}