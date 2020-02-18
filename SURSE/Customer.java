import java.util.ArrayList;

public class Customer extends Observer {
    private String name;
    public ShoppingCart shoppingcart;
    public WishList wishlist;
    private ArrayList<Notification> notifications;
    private String strategy;

    public Customer(){}
    public Customer(String name, int buget, String strategy){
        this.name = name;
        wishlist = new WishList(getStrategy(strategy));
        shoppingcart = new ShoppingCart(buget);
        notifications = new ArrayList<>();
        this.strategy = strategy;
    }

    private Strategy getStrategy(String strategyname){
        switch(strategyname){
            case "A":
                return new StrategyA();
            case "B":
                return new StrategyB();
            case "C":
                return new StrategyC();
        }

        throw new IllegalArgumentException();
    }

    public String getName(){return name;}
    public ArrayList<Notification> getNotifications(){return notifications;}
    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }
}
