import java.util.ListIterator;

public class StrategyA implements Strategy {

    public Item execute(WishList wishList){
        ListIterator<Item> it = wishList.listIterator();
        Item min = it.next();
        while(it.hasNext()){
            Item item = it.next();
            if(item.getPrice() < min.getPrice())
                min = item;
        }

        return min;
    }
}
