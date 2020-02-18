import java.util.Iterator;
import java.util.ListIterator;

public class ShoppingCart extends ItemList implements Visitor{
    public double buget;

    public ShoppingCart(){super(new Comparatorr());}

    public ShoppingCart(double buget){
        super(new Comparatorr());
        this.buget = buget;
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


    @Override
    public String toString() {
        ListIterator<Item> it = this.listIterator();
        String s = "[";
        while(it.hasNext()){
            Item item = it.next();
            s += item.getName() + ";" +item.getId() + ";" + item.getPrice();

            if(it.hasNext())
                s += ", ";
        }
        s += "]";
        return s;
    }

    public boolean add(Item item){
        if(buget > item.getPrice()){
            super.add(item);
            buget -= item.getPrice();
            return true;
        }
        return false;
    }

    public Item remove(int index){
        Item tmp = super.remove(index);

        if(tmp != null){
            buget += tmp.getPrice();
            return tmp;
        }
        return null;
    }

    @Override
    public void visit(BookDepartment bookDepartment) {
        Iterator<Item> it = this.listIterator();
        while(it.hasNext()){
            Item item = it.next();
            if(item.getDepartment().equalsIgnoreCase("BookDepartment")) {
                double oldPrice = item.getPrice();
                double newPrice = oldPrice - ((oldPrice / 100) * 10);
                item.setPrice(newPrice);
            }
        }
    }

    @Override
    public void visit(MusicDepartment musicDepartment) {
        Iterator<Item> it = this.listIterator();
        double reduced_price = 0;
        while(it.hasNext()){
            Item item = it.next();
            if(item.getDepartment().equalsIgnoreCase("MusicDepartment")) {
                double newPrice = (item.getPrice() / 100) * 10;
                reduced_price += newPrice;
            }
        }
        buget += reduced_price;
    }

    @Override
    public void visit(VideoDepartment videoDepartment) {
        Iterator<Item> it = this.listIterator();
        double maximum = this.getItem(0).getPrice();

        while(it.hasNext()) {
            Item item = it.next();
            if(item.getPrice() > maximum)
                maximum = item.getPrice();
        }

        double sum = 0;
        it = this.listIterator();
        while(it.hasNext()) {
            Item item = it.next();
            if(item.getDepartment().equalsIgnoreCase("VideoDepartment"))
                sum += item.getPrice();
        }

        if(sum > maximum){
            it = this.listIterator();
            while(it.hasNext()) {
                Item item = it.next();
                if(item.getDepartment().equalsIgnoreCase("VideoDepartment")) {
                    double newPrice = item.getPrice() - ((item.getPrice() / 100) * 10);
                    item.setPrice(newPrice);
                }
            }

            buget += ((maximum / 100) * 5);
        }
    }

    @Override
    public void visit(SoftwareDepartment softwareDepartment) {
        Iterator<Item> it = this.listIterator();
        double minimum = this.getItem(0).getPrice();

        while(it.hasNext()) {
            Item item = it.next();
            if(item.getPrice() < minimum)
                minimum = item.getPrice();
        }

        if(buget < minimum){
            it = this.listIterator();
            while(it.hasNext()){
                Item item = it.next();
                if(item.getDepartment().equalsIgnoreCase("SoftwareDepartment")){
                    double newPrice = item.getPrice() - ((item.getPrice() / 100) * 10);
                    item.setPrice(newPrice);
                }
            }
        }
    }
}
