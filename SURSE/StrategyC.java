import java.util.ListIterator;

public class StrategyC implements Strategy {

    public Item execute(WishList wishList){
        ListIterator<Item> it = wishList.listIterator();
        Item item = null;
        while(it.hasNext()){
            item = it.next();
        }

        return item;
    }

}
