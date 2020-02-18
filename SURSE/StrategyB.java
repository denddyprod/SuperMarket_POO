import java.util.ListIterator;

public class StrategyB implements Strategy {

    public Item execute(WishList wishList){
        ListIterator<Item> it = wishList.listIterator();
        Item min = it.next();

        return min;
    }

}
