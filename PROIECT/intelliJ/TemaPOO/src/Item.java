public class Item {
    protected int id;
    protected String name;
    protected double price;
    protected String department;

    public Item(String name, int id, double price, String department){
        this.id = id;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    public double getPrice(){
        return price;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDepartment(){return department;}

    public void setPrice(double price){
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getName() + ";" + this.getId() + ";" + this.getPrice();
    }
}
