import java.util.Comparator;
import java.util.ListIterator;

class Comparatorr2 implements Comparator<Item> {
    public int compare(Item a, Item b) {
            return a.getName().compareTo(b.getName());
    }
}

public class WishList extends ItemList {
    Strategy strategy;

    public WishList(Strategy strategy){ super(new Comparatorr2()); this.strategy = strategy; }

    public Item executeStrategy(){ return strategy.execute(this);}

    public boolean add(Item item){
        return super.add(item);
    }

    public Item remove(int index){
        return super.remove(index);
    }

    public double getTotal(){
        double total = 0;

        ListIterator<Item> it = this.listIterator();
        while(it.hasNext()){
            Item item = it.next();
            total += item.getPrice();
        }

        return total;
    }

    public String toString() {
        ListIterator<Item> it = this.listIterator();
        String s = "[";
        while(it.hasNext()){
            Item item = it.next();
            s += item.getName() + ";" + item.getId() + ";" + item.getPrice();

            if(it.hasNext())
                s += ", ";
        }
        s += "]";
        return s;
    }
}
