import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GraphicInterface {
    private JFrame mainFrame;
    private FrameCustomization fcustom = new FrameCustomization();

    public GraphicInterface(){
        StartFrame start = new StartFrame();

        start.setVisible(true);
    }

    public static void main(String[] args){
        GraphicInterface g = new GraphicInterface();

    }
}

class FrameCustomization{

    public JFrame newFrame(){
        JFrame f = new JFrame();
        f.setSize(new Dimension(1300,750));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(new Color(255, 179, 217));
        return f;
    }

    public JFrame startFrame(ActionListener onStart){
        JFrame f = newFrame();
        JButton buttonStart = new JButton("LOAD STORE!");
        buttonStart.setBounds(425,260,400,200);
        f.add(buttonStart);
        buttonStart.addActionListener(onStart);
        return f;
    }

    public JFrame adminPanel(ActionListener prodAction, ActionListener shopAction, ActionListener wishAction){
        JFrame f = newFrame();
        JButton buttonProducts = new JButton("PRODUCTS OF STORE");
        JButton buttonShoppingCart = new JButton("ShoppingCart;");
        JButton buttonWishList = new JButton("WishList");

        buttonProducts.setBounds(100,260,300,200);
        buttonShoppingCart.setBounds(300,260,300,200);
        buttonWishList.setBounds(500,260,300,200);

        f.add(buttonProducts);
        f.add(buttonShoppingCart);
        f.add(buttonWishList);

        buttonProducts.addActionListener(prodAction);
        buttonShoppingCart.addActionListener(shopAction);
        buttonWishList.addActionListener(wishAction);
        return f;
    }
}

class ClickListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


class MyFrame extends JFrame{

    public MyFrame(){
        super();
        setSize(new Dimension(1300,750));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 179, 217));
    }

}

class StartFrame extends MyFrame {
    public StartFrame() {
        super();
        JButton buttonStart = new JButton("LOAD STORE!");
        buttonStart.setBounds(425,260,400,200);
        add(buttonStart);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CustomerLoader cload = new CustomerLoader("T:\\customers.txt");
                StoreLoader storeload = new StoreLoader("T:\\store.txt");
                try {
                    cload.load(Store.getInstance());
                    storeload.load(Store.getInstance());
                }
                catch(Exception ee){
                    System.out.println("Wrong files!");
                }

                AdminPanel nextFrame = new AdminPanel();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
    }
}

class AdminPanel extends MyFrame{
    public AdminPanel(){
        JButton buttonProducts = new JButton("PRODUCTS OF STORE");
        JButton buttonShoppingCart = new JButton("ShoppingCart;");
        JButton buttonWishList = new JButton("WishList");

        buttonProducts.setBounds(100,260,300,200);
        buttonShoppingCart.setBounds(300,260,300,200);
        buttonWishList.setBounds(500,260,300,200);

        add(buttonProducts);
        add(buttonShoppingCart);
        add(buttonWishList);

        buttonProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProductPanel nextFrame = new ProductPanel();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShoppingCartPanel nextFrame = new ShoppingCartPanel();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonWishList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                WishListPanel nextFrame = new WishListPanel();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
    }
}

class ProductPanel extends MyFrame{
    public ProductPanel(){

        JButton buttonShowProd = new JButton("Afisarea produselor");
        JButton buttonAddProd = new JButton("Adaugarea unui nou produs");
        JButton buttonDelProd = new JButton("Stergerea sau editarea unui produs");

        buttonShowProd.setBounds(100,260,300,200);
        buttonAddProd.setBounds(300,260,300,200);
        buttonDelProd.setBounds(500,260,300,200);

        add(buttonShowProd);
        add(buttonAddProd);
        add(buttonDelProd);

        buttonShowProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowProd nextFrame = new ShowProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonAddProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddProd nextFrame = new AddProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonDelProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DelProd nextFrame = new DelProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });

    }
}

class ShowProd extends MyFrame{
    public ShowProd(){

        JButton buttonAReorder = new JButton("Sortare alfabetica");
        JButton buttonUpReorder = new JButton("Sortare crescatoare");
        JButton buttonDownReorder = new JButton("Sortare descrescatoare");

        buttonAReorder.setBounds(100,260,300,200);
        buttonUpReorder.setBounds(300,260,300,200);
        buttonDownReorder.setBounds(500,260,300,200);

        String[] columnNames = { "Name", "ID", "Price", "Departament" };
        LinkedList<Item> allItems = new LinkedList();
        for(Department d : Store.getInstance().getDepartments()){
            allItems.addAll(d.getItems());
        }
        JTable listItem = new JTable(ProductsToString(allItems), columnNames);
        listItem.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(listItem);
        add(pane, BorderLayout.CENTER);
        listItem.setBounds(50, 40, 500, 300);
        //add(listItem);
        add(buttonAReorder);
        add(buttonUpReorder);
        add(buttonDownReorder);

        buttonAReorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonUpReorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        buttonDownReorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

    }

//    private String[][] ProductsToString(ItemList itemlist){
//        Item[] arr = itemlist.toArray();
//        String[][] newArr = new String[arr.length][];
//        for(int i = 0; i < arr.length; i++){
//            newArr[i] = ProducToString(arr[i]);
//        }
//        return newArr;
//    }

    private String[][] ProductsToString(LinkedList<Item> itemlist){
        Item[] arr = itemlist.toArray(new Item[itemlist.size()]);
        String[][] newArr = new String[arr.length][];
        for(int i = 0; i < arr.length; i++){
            newArr[i] = ProducToString(arr[i]);
        }
        return newArr;
    }

    private String[] ProducToString(Item item){
        return new String[] {item.getName(),String.valueOf(item.getId()), String.valueOf(item.getPrice()), item.getDepartment()};
    }
}

class AddProd extends MyFrame{
    public AddProd(){}
}

class DelProd extends MyFrame{
    public DelProd(){}
}

class ShoppingCartPanel extends MyFrame{
    public ShoppingCartPanel(){

        JButton buttonShowSCProd = new JButton("Afisarea produselor");
        JButton buttonAddDelProd = new JButton("Adaugarea/Stergerea unui nou produs");
        JButton buttonCommProd = new JButton("Comandare a produselor");
        JButton buttonAdditionalInfo = new JButton("Informatii suplimentare ShoppingCart");

        buttonShowSCProd.setBounds(100,260,300,200);
        buttonAddDelProd.setBounds(300,260,300,200);
        buttonCommProd.setBounds(500,260,300,200);
        buttonAdditionalInfo.setBounds(700,260,300,200);

        add(buttonShowSCProd);
        add(buttonAddDelProd);
        add(buttonCommProd);
        add(buttonAdditionalInfo);

        buttonShowSCProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowSCProd nextFrame = new ShowSCProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonAddDelProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddDelProd nextFrame = new AddDelProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonCommProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CommProd nextFrame = new CommProd();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });
        buttonAdditionalInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AdditionalInfo nextFrame = new AdditionalInfo();
                setVisible(false);
                nextFrame.setVisible(true);
            }
        });

    }
}

class ShowSCProd extends MyFrame{
    public ShowSCProd(){
        String[] columnNames = { "Name", "ID", "Price", "Departament" };
        LinkedList<Item> allItems = new LinkedList<>();
        for(Customer c : Store.getInstance().getCustomers()){
            LinkedList<Item> t = tempProdStr(c.shoppingcart);
            System.out.println(c.shoppingcart.toString());
            allItems.addAll(t);
        }

        JTable listItem = new JTable(ProductsToString(allItems), columnNames);
        listItem.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(listItem);
        add(pane, BorderLayout.CENTER);
        listItem.setBounds(50, 40, 500, 300);
    }
    private LinkedList<Item> tempProdStr(ShoppingCart w) {
        LinkedList<Item> t = new LinkedList<>();
        ListIterator<Item> it = w.listIterator();
        while(it.hasNext()){
            Item item = it.next();
            t.add(item);
        }
        return t;
    }

    private String[][] ProductsToString(LinkedList<Item> itemlist){
        Item[] arr = itemlist.toArray(new Item[itemlist.size()]);
        String[][] newArr = new String[arr.length][];
        for(int i = 0; i < arr.length; i++){
            newArr[i] = ProducToString(arr[i]);
        }
        return newArr;
    }

    private String[] ProducToString(Item item){
        return new String[] {item.getName(),String.valueOf(item.getId()), String.valueOf(item.getPrice()), item.getDepartment()};
    }
}

class AddDelProd extends MyFrame{

}

class CommProd extends MyFrame{

}

class AdditionalInfo extends MyFrame{

}

class WishListPanel extends MyFrame{
    public WishListPanel(){}
}
