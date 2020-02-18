import java.util.Iterator;

public class SoftwareDepartment extends Department {

    public SoftwareDepartment(int id, String name){super(id, name);}

    public void accept(Visitor visitor) {
        //ShoppingCart cart = (ShoppingCart)(visitor);
        visitor.visit(this);
    }

}
