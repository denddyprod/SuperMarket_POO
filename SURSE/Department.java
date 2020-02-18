import java.util.Iterator;
import java.util.LinkedList;

abstract public class Department implements Subject{
    private int id;
    private String name;
    public LinkedList<Item> items_for_sale;
    public LinkedList<Customer> customers;
    public LinkedList<Customer> wish_customers;

    public Department(int id, String name){
        this.id = id;
        this.name = name;
        items_for_sale = new LinkedList<>();
        customers = new LinkedList<>();
        wish_customers = new LinkedList<>();
    }

    public void enter(Customer c){
        if(!customers.contains(c))
            customers.add(c);
    }

    public void exit(Customer c){
        customers.remove(c);
        removeObserver(c);
    }

    public LinkedList<Customer> getCustomers(){
        return customers;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public void addItem(Item item){items_for_sale.add(item);}

    public LinkedList<Item> getItems(){ return items_for_sale; }

    public void addObserver(Customer customer){ wish_customers.add(customer);}
    public void removeObserver(Customer c){wish_customers.remove(c);}
    public String getObservers(){
        String s ="[";
        Iterator<Customer> it = wish_customers.iterator();
        while(it.hasNext()){
            Customer c = it.next();
            s += c.getName();
            if(it.hasNext())
                s += ", ";
        }
        s += "]";
        return s;
    }
    public void notifyAllObservers(Notification notification){
        for (Customer observer : wish_customers) {
            observer.update(notification);
        }
    }

    public void accept(Visitor visitor){}
}
