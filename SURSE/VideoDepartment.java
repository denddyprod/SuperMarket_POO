import java.util.Iterator;

public class VideoDepartment extends Department {

    public VideoDepartment(int id, String name){super(id, name);}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
