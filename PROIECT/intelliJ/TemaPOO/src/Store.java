import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;

class Comparatorr implements Comparator<Item> {
    public int compare(Item a, Item b) {
        if (a.getPrice() != b.getPrice()) {
            if (a.getPrice() < b.getPrice())
                return -1;
            else
                return 1;
        } else
            return a.getName().compareTo(b.getName());
    }
}

public class Store {
    private static Store single_instance = null;
    public String name;
    private LinkedList<Department> deps;
    private LinkedList<Customer> custs;

    private Store(){ deps = new LinkedList<Department>(); custs = new LinkedList<Customer>();}

    public static Store getInstance()
    {
        if (single_instance == null)
            single_instance = new Store();

        return single_instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enter(Customer e){
        custs.add(e);
    }

    public void exit(Customer e){
        custs.remove(e);
    }

    public ShoppingCart getShoppingCart(double buget){
        ShoppingCart s = new ShoppingCart(buget);
        return s;
    }

    public Customer getCustomer(String name){
        Customer kek = null;
        LinkedList<Customer> c = getCustomers();
        Iterator<Customer> c2 = c.iterator();
        while(c2.hasNext()){
            kek = c2.next();
            if(kek.getName().equals(name))
                return kek;
        }
        throw new NoSuchElementException();
    }

    public Item getItem(int ItemID){
        Iterator<Department> it = getDepartments().iterator();
        while(it.hasNext()){
            LinkedList<Item> its = it.next().getItems();
            Iterator<Item> it2 = its.iterator();
            while(it2.hasNext()){
                Item searched = it2.next();
                if(searched.getId() == ItemID)
                    return searched;
            }
        }
        throw new NoSuchElementException();
    }

    public void delProduct(int ItemID){
        Iterator<Department> it = getDepartments().iterator();
        while(it.hasNext()){
            Department d = it.next();
            Iterator<Item> it2 = d.getItems().iterator();
            while(it2.hasNext()){
                Item item = it2.next();
                if(item.getId() == ItemID){
                    int index = d.getItems().indexOf(item);
                    d.getItems().remove(index);
                    break;
                }
            }
        }

        Iterator<Customer> cit = getCustomers().iterator();
        while(cit.hasNext()){
            Customer c = cit.next();
            ListIterator<Item> lit = c.shoppingcart.listIterator();
            while(lit.hasNext()){
                Item item = lit.next();
                if(item.getId() == ItemID){
                    int index = c.shoppingcart.indexOf(item);
                    c.shoppingcart.remove(index);
                }
            }

            ListIterator<Item> lit2 = c.wishlist.listIterator();
            while(lit2.hasNext()){
                Item item = lit2.next();
                if(item.getId() == ItemID){
                    int index = c.wishlist.indexOf(item);
                    c.wishlist.remove(index);
                }
            }
        }
    }

    public LinkedList<Customer> getCustomers(){
        return custs;
    }

    public LinkedList<Department> getDepartments(){
        return deps;
    }

    public void addDepartment(Department e){
        deps.add(e);
    }

    public Department getDepartmentID(String name){
        Iterator<Department> it = getDepartments().iterator();
        while(it.hasNext()){
            Department d = it.next();
            if(d.getName() == name)
                return d;
        }
        throw new NoSuchElementException();
    }

    public Department getDepartment(int id){
        Iterator<Department> it = getDepartments().iterator();
        while(it.hasNext()){
            Department d = it.next();
            if(d.getId() == id)
                return d;
        }
        throw new NoSuchElementException();
    }

    public Department getDepartment(String name){
        Iterator<Department> it = getDepartments().iterator();
        while(it.hasNext()){
            Department d = it.next();
            if(d.getName() == name)
                return d;
        }
        throw new NoSuchElementException();
    }

}

class Test {
    public static void main(String args[]) throws FileNotFoundException {

        CustomerLoader cload = new CustomerLoader("T:\\customers.txt");
        StoreLoader storeload = new StoreLoader("T:\\store.txt");

        cload.load(Store.getInstance());
        storeload.load(Store.getInstance());

        EventRunner eventRunner = new EventRunner(Store.getInstance());


        Scanner sc = new Scanner(new File("T:\\events.txt"));
        int number_of_events = sc.nextInt();
        sc.nextLine();
        while(sc.hasNextLine() && number_of_events > 0){
            String line = sc.nextLine();
            Scanner parsedline = new Scanner(line);
            parsedline.useDelimiter(";");
            String command = parsedline.next();
            if(command.equals("addItem")){
                int ItemID = parsedline.nextInt();
                String hz = parsedline.next();
                String customerName = parsedline.next();
                eventRunner.addItem(customerName, ItemID, hz);
            }
            if(command.equals("delItem")){
                int ItemID = parsedline.nextInt();
                String hz = parsedline.next();
                String customerName = parsedline.next();
                eventRunner.delItem(customerName, ItemID, hz);
            }
            if(command.equals("addProduct")){
                int DepID = parsedline.nextInt();
                int ItemID = parsedline.nextInt();
                double price = Double.parseDouble(parsedline.next());
                String name = parsedline.next();
                String department = Store.getInstance().getDepartment(DepID).getName();
                eventRunner.addProduct(DepID, ItemID, price, name, department);
            }
            if(command.equals("modifyProduct")){
                int DepID = parsedline.nextInt();
                int ItemID = parsedline.nextInt();
                double price = Double.parseDouble(parsedline.next());
                eventRunner.modifyProduct(DepID, ItemID, price);
            }
            if(command.equals("delProduct")){
                int ItemID = parsedline.nextInt();
                eventRunner.delProduct(ItemID);

            }
            if(command.equals("getItem")){
                String customerName = parsedline.next();
                eventRunner.getItem(customerName);
            }
            if(command.equals("getItems")){
                String from = parsedline.next();
                String customerName = parsedline.next();
                eventRunner.getItems(from, customerName);
            }
            if(command.equals("getTotal")){
                String from = parsedline.next();
                String customerName = parsedline.next();
                eventRunner.getTotal(from, customerName);
            }
            if(command.equals("accept")){
                int DepID = parsedline.nextInt();
                String customerName = parsedline.next();
                eventRunner.accept(DepID, customerName);
            }
            if(command.equals("getObservers")){
                int DepID = parsedline.nextInt();

                eventRunner.getObservers(DepID);
            }
            if(command.equals("getNotifications")){
                String customerName = parsedline.next();

                eventRunner.getNotifications(customerName);
            }
            number_of_events--;
        }
    }
}

class EventRunner {
    private Store store;
    private Path path;

    public EventRunner(Store store) {
        this.store = store;
        path = Paths.get("T:\\results.txt");
        resetFile();
    }

    public void addItem(String customerName, int ItemID, String hz){
        Customer customer = Store.getInstance().getCustomer(customerName);
        Item item = Store.getInstance().getItem(ItemID);
        Department d = Store.getInstance().getDepartment(item.getDepartment());

        if( hz.equals("ShoppingCart")){
            customer.shoppingcart.add(item);
            d.enter(customer);
        }
        else{
            customer.wishlist.add(item);
            d.addObserver(customer);
        }

    }

    public void delItem(String customerName, int ItemID, String hz){
        Customer customer = Store.getInstance().getCustomer(customerName);
        Item item = Store.getInstance().getItem(ItemID);

        int index = customer.shoppingcart.indexOf(item);
        if( hz.equals("ShoppingCart")){
            customer.shoppingcart.remove(index);
        }
        else{
            customer.wishlist.remove(index);
        }
    }

    public void addProduct(int DepID, int ItemID, double price, String name, String department){
        Item item = new Item(name, ItemID, price, department);
        Store.getInstance().getDepartment(DepID).addItem(item);

        Department d = Store.getInstance().getDepartment(DepID);
        Date date = new Date();
        Notification notification = new Notification(date.toString(), NotificationType.ADD, DepID, ItemID);
        d.notifyAllObservers(notification);
    }

    public void modifyProduct(int DepID, int ItemID, double price ){
        Item item = Store.getInstance().getItem(ItemID);
        item.setPrice(price);
        Department d = Store.getInstance().getDepartment(DepID);
        Date date = new Date();
        Notification notification = new Notification(date.toString(), NotificationType.MODIFY, DepID, ItemID);
        d.notifyAllObservers(notification);
    }

    public void delProduct(int ItemID){
        Item item = Store.getInstance().getItem(ItemID);
        Store.getInstance().delProduct(ItemID);
        Date date = new Date();
        Department d =  Store.getInstance().getDepartment(item.getDepartment());
        Notification notification = new Notification(date.toString(), NotificationType.REMOVE, d.getId(), ItemID);
        d.notifyAllObservers(notification);
    }

    public void getItem(String customerName){
        Customer c = Store.getInstance().getCustomer(customerName);
        Item item = c.wishlist.executeStrategy();

        int index = c.wishlist.indexOf(item);
        c.wishlist.remove(index);
        c.shoppingcart.add(item);

        Department d = Store.getInstance().getDepartment(item.getDepartment());
        d.removeObserver(c);

        System.out.println(item.toString());
        WriteString(item.toString());

    }

    public void getItems(String from, String customerName){
        Customer c = Store.getInstance().getCustomer(customerName);

        if(from.equals("ShoppingCart")){
            System.out.println(c.shoppingcart.toString());
            WriteString(c.shoppingcart.toString());
        }
        else {
            System.out.println(c.wishlist.toString());
            WriteString(c.wishlist.toString());
        }
    }

    public void getTotal(String from, String customerName){
        Customer c = Store.getInstance().getCustomer(customerName);

        if(from.equals("ShoppingCart")){
            System.out.println(c.shoppingcart.getTotal());
            WriteString(String.valueOf(c.shoppingcart.getTotal()));
        }
        else {
            System.out.println(c.wishlist.getTotal());
            WriteString(String.valueOf(c.wishlist.getTotal()));
        }
    }

    public void accept(int DepID, String customerName){
        Customer c = Store.getInstance().getCustomer(customerName);
        Department d = Store.getInstance().getDepartment(DepID);
        d.accept(c.shoppingcart);
    }

    public void getObservers(int DepID){
        Department d = Store.getInstance().getDepartment(DepID);

        System.out.println(d.getObservers());
        WriteString(d.getObservers());
    }

    public void getNotifications(String customerName){
        Customer c = Store.getInstance().getCustomer(customerName);

        System.out.println(c.getNotifications());
        WriteString(c.getNotifications().toString());
    }

    private void WriteString(String str){
        try {
            str += "\n";
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception eee){
            System.out.println(eee);
        }
    }
    private void resetFile(){
        try {
            Files.write(path, "".getBytes());
        }
        catch (Exception eee){
            System.out.println(eee);
        }
    }
}

class CustomerLoader {
    private String filename;

    public CustomerLoader(String filename){this.filename = filename;}
    public void load(Store store) throws FileNotFoundException{

        Scanner sc = new Scanner(new File(filename));
        int number_of_clients = sc.nextInt();
        sc.nextLine();
        while(sc.hasNextLine() && number_of_clients > 0){
            String line = sc.nextLine();
            Scanner parsedline = new Scanner(line);
            parsedline.useDelimiter(";");
            Customer customer = new Customer(parsedline.next(), parsedline.nextInt(), parsedline.next());
            store.getInstance().enter(customer);
            number_of_clients--;
        }

    }
}

class StoreLoader{
    private String filename;

    public StoreLoader(String filename){this.filename = filename;}
    public void load(Store store) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(filename));
        store.setName(sc.nextLine());

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            Scanner parsedline = new Scanner(line);
            parsedline.useDelimiter(";");
            String department = parsedline.next();
            Integer departmentID = parsedline.nextInt();

            if(department.equals("BookDepartment"))
                store.addDepartment(new BookDepartment(departmentID, department));
            else if(department.equals("MusicDepartment"))
                store.addDepartment(new MusicDepartment(departmentID, department));
            else if(department.equals("VideoDepartment"))
                store.addDepartment(new VideoDepartment(departmentID, department));
            else if(department.equals("SoftwareDepartment"))
                store.addDepartment(new SoftwareDepartment(departmentID, department));


            int number_of_products = Integer.parseInt(sc.next());
            sc.nextLine();
            for(int i = 0; i < number_of_products; i++){
                line = sc.nextLine();
                parsedline = new Scanner(line);
                parsedline.useDelimiter(";");

                Item item = new Item(parsedline.next(), parsedline.nextInt(), Double.parseDouble(parsedline.next()), department);
                store.getDepartment(departmentID).addItem(item);
            }
        }

    }
}